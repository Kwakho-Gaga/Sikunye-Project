package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.ProjectSchema;
import za.ac.cput.sikunyecharity.Domain.Donation;

public class DonationDAO {

    public void saveDonation(Donation donation) throws SQLException {
        String sql = "INSERT INTO ProjectDonation "
                + "(ProjectID, DonorName, DonorEmail, "
                + "Amount, DonationDate) "
                + "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, donation.getProjectId());
            statement.setString(2, donation.getDonorName());
            statement.setString(3, donation.getDonorEmail());
            statement.setBigDecimal(4, donation.getAmount());
            statement.executeUpdate();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DBConnection.derbyConnection();
        try {
            ProjectSchema.initialize(connection);
            return connection;
        } catch (SQLException ex) {
            connection.close();
            throw ex;
        }
    }
}
