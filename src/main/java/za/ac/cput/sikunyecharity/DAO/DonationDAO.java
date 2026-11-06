/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.DAO;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.Donation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DonationDAO {
    
    public void createTable() throws SQLException {

    String sql = "CREATE TABLE DONATION ("
            + "DonationID INT PRIMARY KEY, "
            + "DonorID INT, "
            + "ProjectID INT, "
            + "DonationType VARCHAR(20), "
            + "Amount DECIMAL(10,2), "
            + "PaymentStatus VARCHAR(20), "
            + "PaymentDate TIMESTAMP, "
            + "ReceiptGenerated BOOLEAN, "
            + "DateReceived DATE"
            + ")";

    try (Connection conn = DBConnection.derbyConnection()) {

        // Check whether DONATION already exists
        try (ResultSet tables = conn.getMetaData().getTables(
                null, null, "DONATION", new String[]{"TABLE"})) {

            if (tables.next()) {
                System.out.println("DONATION table already exists.");
                return;
            }
        }

        // Create the table if it does not exist
        try (Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("DONATION table created successfully.");
        }
    }
}
    public void seedInitialDonations() {
    try {
        // Only seed if the table is currently empty
        if (getAllDonations().isEmpty()) {
            System.out.println("Seeding initial donation records...");
            
            insertDonation(new Donation(101, 1, 201, "FINANCIAL", new java.math.BigDecimal("1500.00"), "PAID", Timestamp.valueOf("2026-08-01 09:56:00"), true, Date.valueOf("2026-08-01")));
            insertDonation(new Donation(102, 2, 202, "IN-KIND", new java.math.BigDecimal("0.00"), "PENDING", Timestamp.valueOf("2026-08-05 12:30:00"), false, Date.valueOf("2026-08-05")));
            insertDonation(new Donation(103, 3, 203, "IN-KIND", new java.math.BigDecimal("0.00"), "PENDING", Timestamp.valueOf("2026-08-10 16:12:30"), false, Date.valueOf("2026-08-10")));
            insertDonation(new Donation(104, 4, 204, "FINANCIAL", new java.math.BigDecimal("25000.00"), "PAID", Timestamp.valueOf("2026-08-12 00:00:00"), true, Date.valueOf("2026-08-12")));
            insertDonation(new Donation(105, 5, 205, "IN-KIND", new java.math.BigDecimal("0.00"), "PENDING", Timestamp.valueOf("2026-08-15 10:00:00"), false, Date.valueOf("2026-08-15")));
            
            System.out.println("Donations seeded successfully.");
        }
    } catch (SQLException e) {
        System.out.println("Error seeding donations: " + e.getMessage());
    }
}

    public void insertDonation(Donation donation) throws SQLException {
        String sql = "INSERT INTO DONATION (DonationID, DonorID, ProjectID, DonationType, Amount, PaymentStatus, PaymentDate, ReceiptGenerated, DateReceived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.derbyConnection() ; PreparedStatement stmt = conn.prepareStatement(sql)) {

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
        try (Connection conn = DBConnection.derbyConnection() ; PreparedStatement stmt = conn.prepareStatement(sql)) {

            {

                stmt.setInt(1, donationID);
                ResultSet rs = stmt.executeQuery();

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
        try (Connection conn = DBConnection.derbyConnection() ; Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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
        try (Connection conn = DBConnection.derbyConnection() ; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, donationID);
            stmt.executeUpdate();
        }
    }

    public void deleteDonation(int donationID) throws SQLException {
        String sql = "DELETE FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection() ; PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            stmt.executeUpdate();
        }
    }

    public int getNextDonationID() throws SQLException {

        String sql = "SELECT COALESCE(MAX(DonationID), 0) + 1 "
                + "AS NextID FROM DONATION";

        try (Connection conn = DBConnection.derbyConnection() ; PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("NextID");
            }
        }

        return 1;

    }

    public int getLatestInKindDonationID() throws SQLException {

    String sql =
        "SELECT DonationID " +
        "FROM DONATION " +
        "WHERE UPPER(DonationType) = 'IN-KIND' " +
        "ORDER BY DonationID DESC " +
        "FETCH FIRST 1 ROW ONLY";

    try (Connection conn = DBConnection.derbyConnection() ;
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

    try (Connection conn = DBConnection.derbyConnection() ;
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, donationID);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String donationType = rs.getString("DonationType");

                return donationType != null
                        && donationType.equalsIgnoreCase("In-Kind");
            }
        }
    }

    return false;
}
}
