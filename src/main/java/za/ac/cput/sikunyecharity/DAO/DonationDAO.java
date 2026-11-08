package za.ac.cput.sikunyecharity.DAO;

import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.Donation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DonationDAO {

    public void createTable() throws SQLException {
        // FIX: ProjectTable MUST exist with PK before DONATION FK can be created
        // Do this ONCE, on the SAME connection, before checking if DONATION exists
        try (Connection conn = DBConnection.derbyConnection()) {
            za.ac.cput.sikunyecharity.DBConection.ProjectSchema.initialize(conn);

            // UNIFIED TABLE - MUST match DonorDonationDAOED.CREATE_DONATION_TABLE
            // DonationID = PK
            // DonorID = FK -> DONOR(DonorID)
            // ProjectID = FK -> ProjectTable(ProjectID), can be NULL = General Fund
            String sql = "CREATE TABLE DONATION ("
                    + "DonationID INT NOT NULL PRIMARY KEY, "
                    + "DonorID INT NOT NULL, "
                    + "ProjectID VARCHAR(20), "
                    + "DonationType VARCHAR(20) DEFAULT 'FINANCIAL', "
                    + "Amount DECIMAL(12,2) NOT NULL, "
                    + "PaymentMethod VARCHAR(30) DEFAULT 'Card', "
                    + "PaymentStatus VARCHAR(20) DEFAULT 'PENDING', "
                    + "PaymentDate TIMESTAMP, "
                    + "ReceiptGenerated BOOLEAN DEFAULT FALSE, "
                    + "DonationDate DATE, "
                    + "DateReceived DATE, " // FIXED: Was NOT NULL, broke DonorDonationDAOED insert
                    + "CONSTRAINT FK_DONATION_DONOR FOREIGN KEY (DonorID) REFERENCES DONOR (DonorID), "
                    + "CONSTRAINT FK_DONATION_PROJECT FOREIGN KEY (ProjectID) REFERENCES ProjectTable (ProjectID)"
                    + ")";
// FIX: If old DONATION exists without DonationType, add missing columns instead of crashing
try (ResultSet cols = conn.getMetaData().getColumns(null, null, "DONATION", null)) {
    boolean hasDonationType = false;
    boolean hasPaymentStatus = false;
    while (cols.next()) {
        String c = cols.getString("COLUMN_NAME");
        if ("DONATIONTYPE".equalsIgnoreCase(c)) hasDonationType = true;
        if ("PAYMENTSTATUS".equalsIgnoreCase(c)) hasPaymentStatus = true;
    }
    try (Statement stmt = conn.createStatement()) {
        if (!hasDonationType) {
            try { stmt.executeUpdate("ALTER TABLE DONATION ADD COLUMN DonationType VARCHAR(20) DEFAULT 'FINANCIAL'"); } catch (SQLException ignored) {}
        }
        if (!hasPaymentStatus) {
            try { stmt.executeUpdate("ALTER TABLE DONATION ADD COLUMN PaymentStatus VARCHAR(20) DEFAULT 'PENDING'"); } catch (SQLException ignored) {}
        }
        // Add the rest if missing
        try { stmt.executeUpdate("ALTER TABLE DONATION ADD COLUMN PaymentDate TIMESTAMP"); } catch (SQLException ignored) {}
        try { stmt.executeUpdate("ALTER TABLE DONATION ADD COLUMN ReceiptGenerated BOOLEAN DEFAULT FALSE"); } catch (SQLException ignored) {}
        try { stmt.executeUpdate("ALTER TABLE DONATION ADD COLUMN DateReceived DATE"); } catch (SQLException ignored) {}
    }
}
            // Check whether DONATION already exists
            try (ResultSet tables = conn.getMetaData().getTables(null, null, "DONATION", new String[]{"TABLE"})) {
                if (tables.next()) {
                    System.out.println("DONATION table already exists.");
                    return;
                }
            }
            // Create the table if it does not exist
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("DONATION table created successfully with FKs to DONOR + ProjectTable.");
            }
        }
    }

    public void seedInitialDonations() {
        try {
            // Only seed if the table is currently empty
            if (!getAllDonations().isEmpty()) {
                return;
            }
            System.out.println("Seeding initial donation records...");
            // NOTE: With FKs on, seed Donors/Projects must already exist.
            // If Donor 1 or Project SSD does not exist yet, seeding is skipped safely.
            // Real donations now come from Donor page -> DonorDonationDAOED.saveDonorAndDonation()
            // So we do NOT seed fake ProjectIDs like 201, 202 anymore.
            // If you want demo data, first create Donor 1 and Project SSD, then uncomment below:
            /*
            insertDonation(new Donation(101, 1, "SSD", "FINANCIAL", new java.math.BigDecimal("1500.00"), "PAID", Timestamp.valueOf("2026-08-01 09:56:00"), true, Date.valueOf("2026-08-01")));
            insertDonation(new Donation(102, 1, "SSF", "IN-KIND", new java.math.BigDecimal("0.00"), "PENDING", null, false, Date.valueOf("2026-08-05")));
             */
            System.out.println("Donations seeding skipped - waiting for real Donor page donations.");
        } catch (SQLException e) {
            System.out.println("Error seeding donations: " + e.getMessage());
        }
    }

    public void insertDonation(Donation donation) throws SQLException {
        String sql = "INSERT INTO DONATION (DonationID, DonorID, ProjectID, DonationType, Amount, PaymentMethod, PaymentStatus, PaymentDate, ReceiptGenerated, DonationDate, DateReceived) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donation.getDonationID());
            stmt.setInt(2, donation.getDonorID());
            // FIXED: ProjectID is String (SSD / SSF / CCC), not int. NULL = General Fund
            if (donation.getProjectID() == null || donation.getProjectID().isBlank() || donation.getProjectID().equals("General Fund")) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, donation.getProjectID());
            }
            stmt.setString(4, donation.getDonationType());
            stmt.setBigDecimal(5, donation.getAmount());
            // PaymentMethod: Donation domain does not have it, default to Card / use PaymentStatus mapping
            stmt.setString(6, "Card");
            stmt.setString(7, donation.getPaymentStatus());
            stmt.setTimestamp(8, donation.getPaymentDate());
            stmt.setBoolean(9, donation.isReceiptGenerated());
            // DonationDate = same as DateReceived for now
            stmt.setDate(10, donation.getDateReceived());
            stmt.setDate(11, donation.getDateReceived());
            stmt.executeUpdate();
        }
    }

    public Donation searchDonation(int donationID) throws SQLException {
        String sql = "SELECT * FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Donation(
                        rs.getInt("DonationID"),
                        rs.getInt("DonorID"),
                        rs.getString("ProjectID"), // FIXED: String
                        rs.getString("DonationType"),
                        rs.getBigDecimal("Amount"),
                        rs.getString("PaymentStatus"),
                        rs.getTimestamp("PaymentDate"),
                        rs.getBoolean("ReceiptGenerated"),
                        rs.getDate("DateReceived")
                );
            }
            return null;
        }
    }

    public List<Donation> getAllDonations() throws SQLException {
        List<Donation> donations = new ArrayList<>();
        String sql = "SELECT * FROM DONATION ORDER BY DonationID";
        try (Connection conn = DBConnection.derbyConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                donations.add(new Donation(
                        rs.getInt("DonationID"),
                        rs.getInt("DonorID"),
                        rs.getString("ProjectID"), // FIXED: String
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
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, donationID);
            stmt.executeUpdate();
        }
    }

    public void deleteDonation(int donationID) throws SQLException {
        String sql = "DELETE FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            stmt.executeUpdate();
        }
    }

    public int getNextDonationID() throws SQLException {
        String sql = "SELECT COALESCE(MAX(DonationID), 0) + 1 AS NextID FROM DONATION";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("NextID");
            }
        }
        return 1;
    }

    public int getLatestInKindDonationID() throws SQLException {
        String sql = "SELECT DonationID FROM DONATION WHERE UPPER(DonationType) = 'IN-KIND' ORDER BY DonationID DESC FETCH FIRST 1 ROW ONLY";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("DonationID");
            }
        }
        return -1;
    }

    public boolean isInKindDonation(int donationID) throws SQLException {
        String sql = "SELECT DonationType FROM DONATION WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
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
