package za.ac.cput.sikunyecharity.DAO;

import za.ac.cput.sikunyecharity.Domain.DonationItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;

public class DonationItemDAO {

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE DONATIONITEM ("
                + "DonationItemID INT PRIMARY KEY, "
                + "DonationID INT NOT NULL, "
                + "ItemID INT NOT NULL, "
                + "Quantity INT NOT NULL, "
                + "ItemCondition VARCHAR(30), "
                + "FOREIGN KEY (DonationID) REFERENCES DONATION(DonationID)"
                + ")";
        try (Connection conn = DBConnection.derbyConnection()) {
            // FIX: DONATION must exist first, otherwise FK fails
            new DonationDAO().createTable();
            try (ResultSet tables = conn.getMetaData().getTables(null, null, "DONATIONITEM", new String[]{"TABLE"})) {
                if (tables.next()) {
                    System.out.println("DONATIONITEM table already exists.");
                    return;
                }
            }
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                System.out.println("DONATIONITEM table created successfully.");
            }
        }
    }

    // FIX: Don't seed fake DonationIDs 102,103,105 - they violate FK and break getNextDonationItemID()
    public void seedInitialDonationItems() {
        try {
            createTable();
            if (!getAllDonationItems().isEmpty()) return;
            System.out.println("DonationItem seeding skipped - waiting for real In-Kind items from DonationItemGUI.");
        } catch (SQLException e) {
            System.out.println("Error seeding donation items: " + e.getMessage());
        }
    }

    public void insertDonationItem(DonationItem item) throws SQLException {
        String sql = "INSERT INTO DONATIONITEM (DonationItemID, DonationID, ItemID, Quantity, ItemCondition) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getDonationItemID());
            stmt.setInt(2, item.getDonationID());
            stmt.setInt(3, item.getItemID());
            stmt.setInt(4, item.getQuantity());
            stmt.setString(5, item.getCondition());
            stmt.executeUpdate();
        }
    }

    public DonationItem searchDonationItem(int donationItemID) throws SQLException {
        String sql = "SELECT * FROM DONATIONITEM WHERE DonationItemID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationItemID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new DonationItem(rs.getInt("DonationItemID"), rs.getInt("DonationID"), rs.getInt("ItemID"), rs.getInt("Quantity"), rs.getString("ItemCondition"));
            }
        }
        return null;
    }

    public List<DonationItem> getAllDonationItems() throws SQLException {
        List<DonationItem> items = new ArrayList<>();
        String sql = "SELECT * FROM DONATIONITEM ORDER BY DonationItemID";
        try (Connection conn = DBConnection.derbyConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                items.add(new DonationItem(rs.getInt("DonationItemID"), rs.getInt("DonationID"), rs.getInt("ItemID"), rs.getInt("Quantity"), rs.getString("ItemCondition")));
            }
        }
        return items;
    }

    public List<DonationItem> getItemsForDonation(int donationID) throws SQLException {
        List<DonationItem> items = new ArrayList<>();
        String sql = "SELECT * FROM DONATIONITEM WHERE DonationID = ? ORDER BY DonationItemID";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new DonationItem(rs.getInt("DonationItemID"), rs.getInt("DonationID"), rs.getInt("ItemID"), rs.getInt("Quantity"), rs.getString("ItemCondition")));
                }
            }
        }
        return items;
    }

    public int getNextDonationItemID() throws SQLException {
        // FIX: ensure table exists first, so this never throws when table is missing
        createTable();
        String sql = "SELECT COALESCE(MAX(DonationItemID),0)+1 AS NEXTID FROM DONATIONITEM";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt("NEXTID");
        }
        return 1;
    }

    public boolean itemExistsForDonation(int donationID, int itemID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ? AND ItemID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            stmt.setInt(2, itemID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public void deleteDonationItem(int donationItemID) throws SQLException {
        String sql = "DELETE FROM DONATIONITEM WHERE DonationItemID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationItemID);
            stmt.executeUpdate();
        }
    }

    public void updateDonationItemCondition(int donationItemID, String newCondition) throws SQLException {
        // FIX: was "UPDATE DonationItem" - table is DONATIONITEM
        String sql = "UPDATE DONATIONITEM SET ItemCondition = ? WHERE DonationItemID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCondition);
            stmt.setInt(2, donationItemID);
            stmt.executeUpdate();
        }
    }

    // KEPT for backward compat, but NO LONGER used to block multiple items
    public boolean donationIDAlreadyUsed(int donationID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, donationID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean hasDonationItems(int donationID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, donationID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }
}
