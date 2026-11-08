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
        // FIX 1: Use null schema = check ALL schemas, not just connection.getSchema()
        // This stops APP vs ADMINISTRATOR creating 2 different ProjectTables
        if (!tableExists(connection, "PROJECTTABLE")) {
            createProjectTable(connection);
        } else if (!hasPrimaryKey(connection, "PROJECTTABLE", "PROJECTID")) {
            // FIX 2: Old table exists but without PK - drop and recreate
            // This is why you kept getting FK_DONATION_PROJECT error
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DROP TABLE ProjectTable");
            }
            createProjectTable(connection);
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

    private static void createProjectTable(Connection connection) throws SQLException {
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

    private static boolean hasPrimaryKey(Connection connection, String table, String column) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        // FIX: null schema = check all schemas
        try (ResultSet keys = metadata.getPrimaryKeys(null, null, table)) {
            while (keys.next()) {
                if (column.equalsIgnoreCase(keys.getString("COLUMN_NAME"))) return true;
            }
        }
        return false;
    }

    private static boolean tableExists(Connection connection, String name) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        // FIX: null schema = check all schemas
        try (ResultSet tables = metadata.getTables(null, null, name,
                new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    private static boolean columnExists(Connection connection, String table, String column)
            throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(
                null, null, table, column)) {
            return columns.next();
        }
    }
}
