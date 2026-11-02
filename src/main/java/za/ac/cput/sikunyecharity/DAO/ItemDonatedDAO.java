package za.ac.cput.mavenproject6.dao;

import za.ac.cput.mavenproject6.connection.DBConnection;
import za.ac.cput.mavenproject6.domain.ItemDistributed;
import za.ac.cput.mavenproject6.domain.ItemDonatedModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDonatedDAO {

    private Connection getConnection() throws SQLException {
        return DBConnection.derbyConnection(); 
    }

    public List<ItemDonatedModel> getAllInventory() throws SQLException {
        List<ItemDonatedModel> list = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS_DONATED";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
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
        return list;
    }

    public List<ItemDonatedModel> searchInventory(String keyword) throws SQLException {
        List<ItemDonatedModel> list = new ArrayList<>();
        String sql = "SELECT * FROM ITEMS_DONATED WHERE LOWER(itemName) LIKE ?";
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

    public void addInventoryItem(ItemDonatedModel item) throws SQLException {
        String sql = "INSERT INTO ITEMS_DONATED (receiptID, itemName, quantity, itemCondition) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getReceiptID());
            pstmt.setString(2, item.getItemName());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setString(4, item.getItemCondition());
            pstmt.executeUpdate();
        }
    }

    public void updateInventoryItem(ItemDonatedModel item) throws SQLException {
        String sql = "UPDATE ITEMS_DONATED SET receiptID = ?, itemName = ?, quantity = ?, itemCondition = ? WHERE itemID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getReceiptID());
            pstmt.setString(2, item.getItemName());
            pstmt.setInt(3, item.getQuantity());
            pstmt.setString(4, item.getItemCondition());
            pstmt.setInt(5, item.getItemID());
            pstmt.executeUpdate();
        }
    }

    public void deleteInventoryItem(int itemID) throws SQLException {
        String sql = "DELETE FROM ITEMS_DONATED WHERE itemID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemID);
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

            int currentStock = 0;
            String checkStockSql = "SELECT quantity FROM ITEMS_DONATED WHERE itemID = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkStockSql)) {
                checkStmt.setInt(1, dist.getItemID());
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    currentStock = rs.getInt("quantity");
                } else {
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

            String updateStockSql = "UPDATE ITEMS_DONATED SET quantity = quantity - ? WHERE itemID = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                updateStmt.setInt(1, dist.getDistributedQuantity());
                updateStmt.setInt(2, dist.getItemID());
                updateStmt.executeUpdate();
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