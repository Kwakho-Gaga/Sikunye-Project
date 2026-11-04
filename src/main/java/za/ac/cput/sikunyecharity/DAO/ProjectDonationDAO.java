package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.ProjectDonationRecord;

public class ProjectDonationDAO {

    public List<ProjectDonationRecord> getDonations(String projectId)
            throws SQLException {
        new DonorDonationDAOED().createTablesIfNotExist();

        List<ProjectDonationRecord> donations = new ArrayList<>();
        String sql = "SELECT DONATIONID, DONORID, PROJECTID, AMOUNT, "
                + "PAYMENTMETHOD, DONATIONDATE FROM DONATION "
                + (projectId == null ? "" : "WHERE PROJECTID = ? ")
                + "ORDER BY DONATIONDATE DESC, DONATIONID DESC";

        try (Connection connection = DBConnection.derbyConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            if (projectId != null) {
                statement.setString(1, projectId);
            }
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    donations.add(new ProjectDonationRecord(
                            result.getInt("DONATIONID"),
                            result.getInt("DONORID"),
                            result.getString("PROJECTID"),
                            result.getBigDecimal("AMOUNT"),
                            result.getString("PAYMENTMETHOD"),
                            result.getDate("DONATIONDATE")));
                }
            }
        }
        return donations;
    }
}
