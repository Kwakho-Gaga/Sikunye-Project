package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        return listProjects("SELECT ProjectID, Name, OwnerName, Description, Status, "
                + "StartDate, EndDate, GoalAmount, ProgressPercent "
                + "FROM ProjectTable ORDER BY StartDate, Name");
    }

    public List<Project> getVisibleProjects() throws SQLException {
        return listProjects("SELECT ProjectID, Name, OwnerName, Description, Status, "
                + "StartDate, EndDate, GoalAmount, ProgressPercent "
                + "FROM ProjectTable WHERE Status IS NULL OR UPPER(Status) <> 'ARCHIVED' "
                + "ORDER BY StartDate, Name");
    }

    private List<Project> listProjects(String sql) throws SQLException {
        List<Project> projects = new ArrayList<>();

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
                        result.getBigDecimal("GoalAmount"),
                        result.getInt("ProgressPercent")));
            }
        }
        return projects;
    }

    public void addProject(Project project) throws SQLException {
        String sql = "INSERT INTO ProjectTable "
                + "(ProjectID, Name, OwnerName, Description, Status, StartDate, "
                + "EndDate, GoalAmount, ProgressPercent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            setProjectFields(statement, project);
            statement.executeUpdate();
        }
    }

    public void updateProject(Project project) throws SQLException {
        String sql = "UPDATE ProjectTable SET Name = ?, OwnerName = ?, Description = ?, "
                + "Status = ?, StartDate = ?, EndDate = ?, GoalAmount = ?, "
                + "ProgressPercent = ? WHERE ProjectID = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getName());
            statement.setString(2, project.getOwnerName());
            statement.setString(3, project.getDescription());
            statement.setString(4, project.getStatus());
            statement.setDate(5, project.getStartDate());
            statement.setDate(6, project.getEndDate());
            statement.setBigDecimal(7, project.getGoalAmount());
            statement.setInt(8, project.getProgressPercent());
            statement.setString(9, project.getProjectId());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Project not found: " + project.getProjectId());
            }
        }
    }

    public void archiveProject(String projectId) throws SQLException {
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE ProjectTable SET Status = 'Archived' WHERE ProjectID = ?")) {
            statement.setString(1, projectId);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Project not found: " + projectId);
            }
        }
    }

    private void setProjectFields(PreparedStatement statement, Project project)
            throws SQLException {
        statement.setString(1, project.getProjectId());
        statement.setString(2, project.getName());
        statement.setString(3, project.getOwnerName());
        statement.setString(4, project.getDescription());
        statement.setString(5, project.getStatus());
        statement.setDate(6, project.getStartDate());
        statement.setDate(7, project.getEndDate());
        statement.setBigDecimal(8, project.getGoalAmount());
        statement.setInt(9, project.getProgressPercent());
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
