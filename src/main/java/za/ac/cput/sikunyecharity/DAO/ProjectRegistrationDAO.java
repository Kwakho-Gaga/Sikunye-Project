package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.ProjectSchema;
import za.ac.cput.sikunyecharity.Domain.ProjectRegistration;

public class ProjectRegistrationDAO {

    public void register(ProjectRegistration registration) throws SQLException {
        String sql = "INSERT INTO ProjectRegistration "
                + "(ProjectID, UserName, UserEmail, RegistrationDate) "
                + "VALUES (?, ?, ?, CURRENT_DATE)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, registration.getProjectId());
            statement.setString(2, registration.getUserName());
            statement.setString(3, registration.getUserEmail());
            statement.executeUpdate();
        }
    }

    private Connection getConnection() throws SQLException {
        Connection connection = DBConnection.derbyConnection();
        try {
            ProjectSchema.initialize(connection);
            return connection;
        } catch (SQLException ex) {
            connection.close();
            throw ex;
        }
    }
}
