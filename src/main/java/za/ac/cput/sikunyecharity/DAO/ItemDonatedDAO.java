package za.ac.cput.sikunyecharity.DAO;

import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.ItemDistributed;
import za.ac.cput.sikunyecharity.Domain.ItemDonatedModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDonatedDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.derbyConnection();
    }

    // FIX: Creates correct table, auto-drops old wrong table (itemID was IDENTITY PK)
    // New: inventoryID = auto PK, itemID = catalogue ID (1-5), receiptID = DonationID
    public void createTable() throws SQLException {
        try (Connection conn = getConnection()) {
            boolean tableExists = false;
            try (ResultSet rs = conn.getMetaData().getTables(null, null, "ITEMS_DONATED", new String[]{"TABLE"})) {
                tableExists = rs.next();
            }
            if (tableExists) {
                boolean hasInventoryID = false;
                try (ResultSet cols = conn.getMetaData().getColumns(null, null, "ITEMS_DONATED", "INVENTORYID")) {
                    hasInventoryID = cols.next();
                }
                if (!hasInventoryID) {
                    // Old table: itemID was IDENTITY PK - cannot INSERT catalogue ID into it, drop it
                    try (Statement st = conn.createStatement()) {
                        st.executeUpdate("DROP TABLE ITEMS_DONATED");
                        System.out.println("Dropped old ITEMS_DONATED - wrong PK, recreating with inventoryID...");
                    }
                    tableExists = false;
                }
            }
            if (!tableExists) {
                String sql = "CREATE TABLE ITEMS_DONATED ("
                        + "inventoryID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                        + "itemID INT NOT NULL, "
                        + "receiptID INT NOT NULL, "
                        + "itemName VARCHAR(100), "
                        + "quantity INT, "
                        + "itemCondition VARCHAR(30))";
                try (Statement st = conn.createStatement()) {
                    st.executeUpdate(sql);
                    System.out.println("ITEMS_DONATED table created successfully.");
                }
            }
        }
    }

    public List<ItemDonatedModel> getAllInventory() throws SQLException {
        createTable();
        List<ItemDonatedModel> list = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS_DONATED ORDER BY inventoryID";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ItemDonatedModel(
                        rs.getInt("itemID"), // catalogue ID: 2=Clothing, 4=Books
                        rs.getInt("receiptID"),
                        rs.getString("itemName"),
                        rs.getInt("quantity"),
                        rs.getString("itemCondition")
                ));
            }
        }
        return list;
    }

    public List<ItemDonatedModel> searchInventory(String keyword) throws SQLException {
        createTable();
        List<ItemDonatedModel> list = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS_DONATED WHERE LOWER(itemName) LIKE ? ORDER BY inventoryID";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword.toLowerCase() + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new ItemDonatedModel(
                            rs.getInt("itemID"),
                            rs.getInt("receiptID"),
                            rs.getString("itemName"),
                            rs.getInt("quantity"),
                            rs.getString("itemCondition")
                    ));
                }
            }
        }
        return list;
    }

    // FIX: One row PER DONATION. inventoryID is auto, we INSERT catalogue itemID explicitly.
    // No UPSERT, no check-if-exists - so Books, Clothing, Food all save as separate rows.
    public void addInventoryItem(ItemDonatedModel item) throws SQLException {
        createTable();
        String sql = "INSERT INTO ITEMS_DONATED (itemID, receiptID, itemName, quantity, itemCondition) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getItemID()); // catalogue ID from GUI: 1-5
            pstmt.setInt(2, item.getReceiptID()); // DonationID
            pstmt.setString(3, item.getItemName());
            pstmt.setInt(4, item.getQuantity());
            pstmt.setString(5, item.getItemCondition());
            pstmt.executeUpdate();
        }
    }

    // FIX: Update the ONE row for this donation, not all rows with same catalogue ID
    public void updateInventoryItem(ItemDonatedModel item) throws SQLException {
        createTable();
        String sql = "UPDATE ITEMS_DONATED SET itemName = ?, quantity = ?, itemCondition = ? WHERE itemID = ? AND receiptID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getItemName());
            pstmt.setInt(2, item.getQuantity());
            pstmt.setString(3, item.getItemCondition());
            pstmt.setInt(4, item.getItemID());
            pstmt.setInt(5, item.getReceiptID());
            pstmt.executeUpdate();
        }
    }

    // Used by Items Donated GUI Delete button - deletes by catalogue itemID
    public void deleteInventoryItem(int itemID) throws SQLException {
        createTable();
        String sql = "DELETE FROM ITEMS_DONATED WHERE itemID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemID);
            pstmt.executeUpdate();
        }
    }

    // NEW: Used by DonationGUI cascade delete - delete ONLY the row for this DonationID
    // Do NOT use deleteInventoryItem(itemID) there, it would delete ALL Clothing rows
    public void deleteInventoryByReceiptID(int receiptID) throws SQLException {
        createTable();
        String sql = "DELETE FROM ITEMS_DONATED WHERE receiptID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, receiptID);
            pstmt.executeUpdate();
        }
    }

    public List<ItemDistributed> getAllDistributions() throws SQLException {
        List<ItemDistributed> list = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS_DISTRIBUTED";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new ItemDistributed(
                        rs.getInt("distributionID"),
                        rs.getInt("itemID"),
                        rs.getInt("beneficiaryID"),
                        rs.getString("beneficiaryName"),
                        rs.getInt("distributedQuantity"),
                        rs.getDate("distributionDate")
                ));
            }
        }
        return list;
    }

    public void processDistributionTransaction(ItemDistributed dist) throws Exception {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // FIX: Total stock = SUM of all rows with this catalogue itemID (multiple donations)
            int currentStock = 0;
            String checkStockSql = "SELECT COALESCE(SUM(quantity),0) AS TOTAL FROM ITEMS_DONATED WHERE itemID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, dist.getItemID());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    currentStock = rs.getInt("TOTAL");
                }
                if (currentStock <= 0) {
                    throw new Exception("Item ID " + dist.getItemID() + " does not exist in inventory.");
                }
            }

            if (dist.getDistributedQuantity() > currentStock) {
                throw new Exception("Insufficient stock! Available stock is only: " + currentStock);
            }

            String insertDistSql = "INSERT INTO ITEMS_DISTRIBUTED (itemID, beneficiaryID, beneficiaryName, distributedQuantity, distributionDate) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertDistSql)) {
                insertStmt.setInt(1, dist.getItemID());
                insertStmt.setInt(2, dist.getBeneficiaryID());
                insertStmt.setString(3, dist.getBeneficiaryName());
                insertStmt.setInt(4, dist.getDistributedQuantity());
                insertStmt.setDate(5, new java.sql.Date(dist.getDistributionDate().getTime()));
                insertStmt.executeUpdate();
            }

            // Deduct from oldest rows first, until distributed quantity is covered
            int remaining = dist.getDistributedQuantity();
            String getRowsSql = "SELECT inventoryID, quantity FROM ITEMS_DONATED WHERE itemID = ? AND quantity > 0 ORDER BY inventoryID";
            try (PreparedStatement getStmt = conn.prepareStatement(getRowsSql)) {
                getStmt.setInt(1, dist.getItemID());
                ResultSet rs = getStmt.executeQuery();
                while (rs.next() && remaining > 0) {
                    int invId = rs.getInt("inventoryID");
                    int rowQty = rs.getInt("quantity");
                    int deduct = Math.min(rowQty, remaining);
                    String updateStockSql = "UPDATE ITEMS_DONATED SET quantity = quantity - ? WHERE inventoryID = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                        updateStmt.setInt(1, deduct);
                        updateStmt.setInt(2, invId);
                        updateStmt.executeUpdate();
                    }
                    remaining -= deduct;
                }
            }

            conn.commit();
        } catch (Exception ex) {
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    public void updateDistributionRecord(ItemDistributed dist) throws SQLException {
        String sql = "UPDATE ITEMS_DISTRIBUTED SET itemID = ?, beneficiaryID = ?, beneficiaryName = ?, distributedQuantity = ?, distributionDate = ? WHERE distributionID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, dist.getItemID());
            pstmt.setInt(2, dist.getBeneficiaryID());
            pstmt.setString(3, dist.getBeneficiaryName());
            pstmt.setInt(4, dist.getDistributedQuantity());
            pstmt.setDate(5, new java.sql.Date(dist.getDistributionDate().getTime()));
            pstmt.setInt(6, dist.getDistributionID());
            pstmt.executeUpdate();
        }
    }

    public void deleteDistributionRecord(int distID) throws SQLException {
        String sql = "DELETE FROM ITEMS_DISTRIBUTED WHERE distributionID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, distID);
            pstmt.executeUpdate();
        }
    }
}
