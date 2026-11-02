package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.DBConection.ProjectSchema;
import za.ac.cput.sikunyecharity.Domain.Project;

public class ProjectDAO {

    public List<Project> getAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT ProjectID, Name, OwnerName, "
                + "Description, Status, StartDate, "
                + "EndDate, GoalAmount "
                + "FROM ProjectTable";

        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql)) {
            while (result.next()) {
                projects.add(new Project(
                        result.getString("ProjectID"),
                        result.getString("Name"),
                        result.getString("OwnerName"),
                        result.getString("Description"),
                        result.getString("Status"),
                        result.getDate("StartDate"),
                        result.getDate("EndDate"),
                        result.getBigDecimal("GoalAmount")));
            }
        }
        return projects;
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
