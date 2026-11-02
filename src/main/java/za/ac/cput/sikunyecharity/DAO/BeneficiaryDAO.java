package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.BeneficiarySchema;
import za.ac.cput.sikunyecharity.Domain.Beneficiary;

public class BeneficiaryDAO {

    public List<Beneficiary> getAll() throws SQLException {
        List<Beneficiary> beneficiaries = new ArrayList<>();
        String sql = "SELECT BeneficiaryID, FirstName, LastName, FamilySize, "
                + "Address, LivingSituation FROM Beneficiary ORDER BY BeneficiaryID";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                beneficiaries.add(new Beneficiary(rows.getInt("BeneficiaryID"),
                        rows.getString("FirstName"), rows.getString("LastName"),
                        rows.getInt("FamilySize"), rows.getString("Address"),
                        rows.getString("LivingSituation")));
            }
        }
        return beneficiaries;
    }

    public void add(Beneficiary beneficiary) throws SQLException {
        String sql = "INSERT INTO Beneficiary "
                + "(FirstName, LastName, FamilySize, Address, LivingSituation) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            setDetails(statement, beneficiary);
            statement.executeUpdate();
        }
    }

    public boolean update(Beneficiary beneficiary) throws SQLException {
        String sql = "UPDATE Beneficiary SET FirstName = ?, LastName = ?, "
                + "FamilySize = ?, Address = ?, LivingSituation = ? "
                + "WHERE BeneficiaryID = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            setDetails(statement, beneficiary);
            statement.setInt(6, beneficiary.getBeneficiaryId());
            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(int beneficiaryId) throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement removeNeeds = connection.prepareStatement(
                    "DELETE FROM BeneficiaryNeeds WHERE BeneficiaryID = ?");
                    PreparedStatement removeBeneficiary = connection.prepareStatement(
                            "DELETE FROM Beneficiary WHERE BeneficiaryID = ?")) {
                removeNeeds.setInt(1, beneficiaryId);
                removeNeeds.executeUpdate();
                removeBeneficiary.setInt(1, beneficiaryId);
                boolean deleted = removeBeneficiary.executeUpdate() == 1;
                connection.commit();
                return deleted;
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }
        }
    }

    private void setDetails(PreparedStatement statement, Beneficiary beneficiary)
            throws SQLException {
        statement.setString(1, beneficiary.getFirstName());
        statement.setString(2, beneficiary.getLastName());
        statement.setInt(3, beneficiary.getFamilySize());
        statement.setString(4, beneficiary.getAddress());
        statement.setString(5, beneficiary.getLivingSituation());
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DBConnection.derbyConnection();
        try {
            BeneficiarySchema.initialize(connection);
            return connection;
        } catch (SQLException ex) {
            connection.close();
            throw ex;
        }
    }
}
