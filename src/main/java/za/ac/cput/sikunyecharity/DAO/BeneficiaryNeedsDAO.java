package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.BeneficiarySchema;
import za.ac.cput.sikunyecharity.Domain.BeneficiaryNeed;

public class BeneficiaryNeedsDAO {

    public List<BeneficiaryNeed> getForBeneficiary(int beneficiaryId)
            throws SQLException {
        List<BeneficiaryNeed> assigned = new ArrayList<>();
        String sql = "SELECT bn.BeneficiaryID, bn.NeedCode, n.Description, bn.Quantity "
                + "FROM BeneficiaryNeeds bn JOIN Needs n ON bn.NeedCode = n.NeedCode "
                + "WHERE bn.BeneficiaryID = ? ORDER BY bn.NeedCode";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, beneficiaryId);
            try (ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    assigned.add(new BeneficiaryNeed(rows.getInt("BeneficiaryID"),
                            rows.getInt("NeedCode"), rows.getString("Description"),
                            rows.getInt("Quantity")));
                }
            }
        }
        return assigned;
    }

    public void assign(int beneficiaryId, int needCode, int quantity)
            throws SQLException {
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement update = connection.prepareStatement(
                    "UPDATE BeneficiaryNeeds SET Quantity = ? "
                    + "WHERE BeneficiaryID = ? AND NeedCode = ?");
                    PreparedStatement insert = connection.prepareStatement(
                            "INSERT INTO BeneficiaryNeeds "
                            + "(BeneficiaryID, NeedCode, Quantity) VALUES (?, ?, ?)")) {
                update.setInt(1, quantity);
                update.setInt(2, beneficiaryId);
                update.setInt(3, needCode);
                if (update.executeUpdate() == 0) {
                    insert.setInt(1, beneficiaryId);
                    insert.setInt(2, needCode);
                    insert.setInt(3, quantity);
                    insert.executeUpdate();
                }
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }
        }
    }

    public boolean remove(int beneficiaryId, int needCode) throws SQLException {
        String sql = "DELETE FROM BeneficiaryNeeds "
                + "WHERE BeneficiaryID = ? AND NeedCode = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, beneficiaryId);
            statement.setInt(2, needCode);
            return statement.executeUpdate() == 1;
        }
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
