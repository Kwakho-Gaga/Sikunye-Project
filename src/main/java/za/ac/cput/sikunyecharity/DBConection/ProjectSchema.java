package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/** Prepares project tables on the application's existing connection. */
public final class ProjectSchema {

    private static boolean initialized;

    private ProjectSchema() {
    }

    public static synchronized void initialize(Connection connection) throws SQLException {
        if (!initialized) {
            initializeSchema(connection);
            initialized = true;
        }
    }

    private static void initializeSchema(Connection connection) throws SQLException {
        if (!tableExists(connection, "PROJECTTABLE")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE ProjectTable ("
                        + "ProjectID VARCHAR(20) PRIMARY KEY, "
                        + "Name VARCHAR(120) NOT NULL, "
                        + "OwnerName VARCHAR(120), "
                        + "Description VARCHAR(2000), "
                        + "Status VARCHAR(40), "
                        + "StartDate DATE, EndDate DATE, "
                        + "GoalAmount DECIMAL(12,2))");
            }
        }
        if (!tableExists(connection, "PROJECTREGISTRATION")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE ProjectRegistration ("
                        + "RegistrationID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                        + "ProjectID VARCHAR(20) NOT NULL REFERENCES ProjectTable(ProjectID), "
                        + "UserName VARCHAR(120) NOT NULL, "
                        + "UserEmail VARCHAR(254) NOT NULL, "
                        + "RegistrationDate DATE NOT NULL)");
            }
        }
        seedProjects(connection);
    }

    private static boolean tableExists(Connection connection, String name) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet tables = metadata.getTables(null, connection.getSchema(), name,
                new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    private static void seedProjects(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
                ResultSet rows = statement.executeQuery("SELECT COUNT(*) FROM ProjectTable")) {
            rows.next();
            if (rows.getInt(1) != 0) {
                return;
            }
        }
        String sql = "INSERT INTO ProjectTable "
                + "(ProjectID, Name, OwnerName, Description, Status, StartDate, EndDate, GoalAmount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            addProject(statement, "SSD", "School Supply Drive", "Mr Gime",
                    "Providing essential school supplies to help students in need achieve their academic goals.",
                    "In progress", "2020-12-20", "2027-01-20");
            addProject(statement, "SSF", "Share and Serve Food", "Mr Gime",
                    "Providing meals to underserved families and helping to fight hunger.",
                    "In progress", "2024-01-20", "2029-01-08");
            addProject(statement, "CCC", "Community Care Connect", "Mr Gime",
                    "Providing accessible healthcare services and resources to underserved communities.",
                    "Planned", "2026-07-02", "2028-01-10");
        }
    }

    private static void addProject(PreparedStatement statement, String id, String name,
            String owner, String description, String status, String start, String end)
            throws SQLException {
        statement.setString(1, id);
        statement.setString(2, name);
        statement.setString(3, owner);
        statement.setString(4, description);
        statement.setString(5, status);
        statement.setDate(6, java.sql.Date.valueOf(LocalDate.parse(start)));
        statement.setDate(7, java.sql.Date.valueOf(LocalDate.parse(end)));
        statement.setNull(8, java.sql.Types.DECIMAL);
        statement.executeUpdate();
    }

}
