/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Gaga Kwakho
 */
public class DBConnection {

    public static Connection derbyConnection() throws SQLException {

        
            String DATABASE_URL = "jdbc:derby://localhost:1527/SikunyeDB";
            String username = "sikunye_use";
            String password = "iWashere@12";
            Connection connection= DriverManager.getConnection(DATABASE_URL, username, password);
      
            
         
        return connection;
    }

}
