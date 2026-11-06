
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

        
            String DATABASE_URL = "jdbc:derby://localhost:1527/SikunyeDatabase";
            String username = "administrator";
            String password = "password";
            Connection connection= DriverManager.getConnection(DATABASE_URL, username, password);
      
            
         
        return connection;
    }

}
