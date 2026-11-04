package za.ac.cput.sikunyecharity.DAO;

import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.Donation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

public class DonationDAO {

    public void insertDonation(Donation donation) throws SQLException {
        String sql = "INSERT INTO DONATION (DonationID, DonorID, ProjectID, DonationType, Amount, PaymentStatus, PaymentDate, ReceiptGenerated, DateReceived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donation.getDonationID());
            stmt.setInt(2, donation.getDonorID());
            stmt.setInt(3, donation.getProjectID());
            stmt.setString(4, donation.getDonationType());
            stmt.setBigDecimal(5, donation.getAmount());
            stmt.setString(6, donation.getPaymentStatus());
            stmt.setTimestamp(7, donation.getPaymentDate());
            stmt.setBoolean(8, donation.isReceiptGenerated());
            stmt.setDate(9, donation.getDateReceived());

            stmt.executeUpdate();
        }
    }

    public Donation searchDonation(int donationID) throws SQLException {
        String sql = "SELECT * FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donationID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Donation(
                            rs.getInt("DonationID"),
                            rs.getInt("DonorID"),
                            rs.getInt("ProjectID"),
                            rs.getString("DonationType"),
                            rs.getBigDecimal("Amount"),
                            rs.getString("PaymentStatus"),
                            rs.getTimestamp("PaymentDate"),
                            rs.getBoolean("ReceiptGenerated"),
                            rs.getDate("DateReceived")
                    );
                }
            }
            return null;
        }
    }

    public List<Donation> getAllDonations() throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM DONATION";
        try (Connection conn = DBConnection.derbyConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                donations.add(new Donation(
                        rs.getInt("DonationID"),
                        rs.getInt("DonorID"),
                        rs.getInt("ProjectID"),
                        rs.getString("DonationType"),
                        rs.getBigDecimal("Amount"),
                        rs.getString("PaymentStatus"),
                        rs.getTimestamp("PaymentDate"),
                        rs.getBoolean("ReceiptGenerated"),
                        rs.getDate("DateReceived")
                ));
            }
        }
        return donations;
    }

    public void updateDonationStatus(int donationID, String newStatus) throws SQLException {
        String sql = "UPDATE DONATION SET PaymentStatus = ? WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, donationID);
            stmt.executeUpdate();
        }
    }

    public void deleteDonation(int donationID) throws SQLException {
        String sql = "DELETE FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            stmt.executeUpdate();
        }
    }

    public int getNextDonationID() throws SQLException {
        String sql = "SELECT COALESCE(MAX(DonationID), 0) + 1 AS NextID FROM DONATION";

        try (Connection conn = DBConnection.derbyConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("NextID");
            }
        }
        return 1;
    }

    public int getLatestInKindDonationID() throws SQLException {
        String sql = "SELECT DonationID FROM DONATION " +
                     "WHERE UPPER(DonationType) = 'IN-KIND' " +
                     "ORDER BY DonationID DESC " +
                     "FETCH FIRST 1 ROW ONLY";

        try (Connection conn = DBConnection.derbyConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("DonationID");
            }
        }
        return -1;
    }

    public boolean isInKindDonation(int donationID) throws SQLException {
        String sql = "SELECT DonationType FROM DONATION WHERE DonationID = ?";

        try (Connection conn = DBConnection.derbyConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donationID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String donationType = rs.getString("DonationType");
                    return donationType != null && donationType.equalsIgnoreCase("In-Kind");
                }
            }
        }
        return false;
    }
}