package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Prepares project tables on the application's existing connection. */
public final class ProjectSchema {

    private ProjectSchema() {
    }

    public static synchronized void initialize(Connection connection) throws SQLException {
        initializeSchema(connection);
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
                        + "GoalAmount DECIMAL(12,2), "
                        + "ProgressPercent INTEGER)");
            }
        }
        if (!columnExists(connection, "PROJECTTABLE", "PROGRESSPERCENT")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("ALTER TABLE ProjectTable ADD COLUMN ProgressPercent INTEGER");
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
        ProjectSeeder.seed(connection);
    }

    private static boolean tableExists(Connection connection, String name) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet tables = metadata.getTables(null, connection.getSchema(), name,
                new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    private static boolean columnExists(Connection connection, String table, String column)
            throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(
                null, connection.getSchema(), table, column)) {
            return columns.next();
        }
    }

}
