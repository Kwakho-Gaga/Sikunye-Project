package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.BeneficiarySchema;
import za.ac.cput.sikunyecharity.Domain.Need;

public class NeedsDAO {

    public List<Need> getAll() throws SQLException {
        List<Need> needs = new ArrayList<>();
        String sql = "SELECT NeedCode, Description FROM Needs ORDER BY NeedCode";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rows = statement.executeQuery()) {
            while (rows.next()) {
                needs.add(new Need(rows.getInt("NeedCode"),
                        rows.getString("Description")));
            }
        }
        return needs;
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
