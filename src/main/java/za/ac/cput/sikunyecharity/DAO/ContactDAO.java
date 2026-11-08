/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.Contact;
import za.ac.cput.sikunyecharity.Domain.Feedback;

/**
 *
 * @author Gaga Kwakho
 */
public class ContactDAO {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public ContactDAO() {

        try {
            this.connection = DBConnection.derbyConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void createTable() {
  String dropTableSQL = "DROP TABLE IF EXISTS Contact CASCADE";
        String table = "CREATE TABLE Contact ("
                + "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "Full_Name VARCHAR(100), "
                + "Email VARCHAR(50), "
                + "Phone_Number VARCHAR(50), "
                + "Subject VARCHAR(100), "
                + "Query VARCHAR(500)"
                + ")";
        try {
            statement = this.connection.createStatement();
          //  statement.executeUpdate(dropTableSQL);
            System.out.println("Existing table dropped.");
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.statement.executeUpdate(table);
            System.out.println("Table successfully created");
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            if (statement != null) {

            }

            try {
                statement.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void sendMessage(Contact contact) throws SQLException {
        String sql = "INSERT INTO Contact (Full_Name, Email, Phone_Number, Subject, Query) VALUES (?, ?, ?, ?, ?)";

        // Try-with-resources handles closing statements automatically and securely
        this.preparedStatement = this.connection.prepareStatement(sql);
        this.connection.setAutoCommit(true);

        preparedStatement.setString(1, contact.getFullName());
        preparedStatement.setString(2, contact.getEmail());
        preparedStatement.setString(3, contact.getPhoneNumber());
        preparedStatement.setString(4, contact.getSubject());
        preparedStatement.setString(5, contact.getMessage());

        int ok = preparedStatement.executeUpdate();
        if (ok > 0) {
            System.out.println("Database successfully saved the row.");
        }

    }

    public ArrayList<Contact> getAll() throws SQLException {

        ArrayList<Contact> contactList = new ArrayList();
        String getAll = "SELECT * FROM contact";
        try {
            preparedStatement = this.connection.prepareStatement(getAll);
        } catch (SQLException ex) {
            Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet rs;
        rs = preparedStatement.executeQuery();
        if (rs != null) {
            try {
                while (rs.next()) {

                    try {
                        System.out.println("DataBase record:" + rs.getInt(1)
                                + "" + rs.getString(2)
                                + "" + rs.getString(3)
                                + "" + rs.getString(4)
                                + "" + rs.getString(5)
                                + "" + rs.getString(6)
                        );
                    } catch (SQLException ex) {
                        Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        contactList.add(new Contact(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6)
                        ));
                    } catch (SQLException ex) {
                        Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

                if (preparedStatement != null) {
                    preparedStatement.close();

                }

            }
        }

        return contactList;
    }

    public int deleteById(int contactId) {
    int rowsAffected = 0;
    String sql = "DELETE FROM Contact WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, contactId);   
        rowsAffected = pstmt.executeUpdate(); 
    } catch (SQLException ex) {
        // handle exception
        Logger.getLogger(Contact.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rowsAffected; 
}
    
   public ArrayList<Contact> getAllContact() {
    ArrayList<Contact> contact = new ArrayList<>();
           String sql = "SELECT * FROM contact";

    try (Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            Contact c = new Contact(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6)
            );
            contact.add(c);
        }
    } catch (SQLException ex) {
   JOptionPane.showInternalMessageDialog(null, ex);
    }
    
    return contact;
} 

public int updateById(Contact contact) {
    int rowsAffected = 0;
    String sql = "UPDATE Contact SET Full_Name = ?, Email = ?, Phone_Number = ?, Subject = ?, Query= ? WHERE id = ?";
    try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setString(1, contact.getFullName());
        pstmt.setString(2, contact.getEmail());
        pstmt.setString(3, contact.getPhoneNumber());
        pstmt.setString(4, contact.getSubject());
       pstmt.setString(5, contact.getMessage());
        pstmt.setInt(6, contact.getContactId());
       
        
        
        
        rowsAffected = pstmt.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(ContactDAO.class.getName()).log(Level.SEVERE, null, ex);
    }
    return rowsAffected;
}





}
