/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.DAO;

import za.ac.cput.sikunyecharity.Domain.DonationItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class DonationItemDAO {

    public void insertDonationItem(DonationItem item) throws SQLException {
        String sql = "INSERT INTO DONATIONITEM (DonationItemID, DonationID, ItemID, Quantity, Condition) VALUES (?, ?, ?, ?, ?)";
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
                return new DonationItem(
                        rs.getInt("DonationItemID"),
                        rs.getInt("DonationID"),
                        rs.getInt("ItemID"),
                        rs.getInt("Quantity"),
                        rs.getString("Condition")
                );
            }
        }
        return null;
    }

    public List<DonationItem> getAllDonationItems() throws SQLException {
        List<DonationItem> items = new ArrayList<>();
        String sql = "SELECT * FROM DONATIONITEM";
        try (Connection conn = DBConnection.derbyConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                items.add(new DonationItem(
                        rs.getInt("DonationItemID"),
                        rs.getInt("DonationID"),
                        rs.getInt("ItemID"),
                        rs.getInt("Quantity"),
                        rs.getString("Condition")
                ));
            }
        }
        return items;
    }
    public int getNextDonationItemID() throws SQLException {

    String sql =
        "SELECT COALESCE(MAX(DONATIONITEMID),0)+1 AS NEXTID " +
        "FROM DONATIONITEM";

    try (Connection conn = DBConnection.derbyConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getInt("NEXTID");
        }
    }

    return 1;
}
    public boolean itemExistsForDonation(int donationID, int itemID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ? AND ItemID = ?";
        
        try (Connection conn = DBConnection.derbyConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, donationID);
            stmt.setInt(2, itemID);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
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
        String sql = "UPDATE DonationItem SET condition = ? WHERE donationItemID = ?";
        try (Connection conn = DBConnection.derbyConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCondition);
            stmt.setInt(2, donationItemID);
            stmt.executeUpdate();
        }
    }
    public boolean donationIDAlreadyUsed(int donationID) throws SQLException {
    String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ?";

    try (Connection conn = DBConnection.derbyConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, donationID);

        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() && rs.getInt(1) > 0;
        }
    }
}
    public boolean hasDonationItems(int donationID) throws SQLException {
    String sql = "SELECT COUNT(*) FROM DONATIONITEM WHERE DonationID = ?";

    try (Connection conn = DBConnection.derbyConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, donationID);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
    }

    return false;
}
}
