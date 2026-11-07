package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

/** Prepares project tables on the application's existing connection. */
public final class ProjectSchema {

    private ProjectSchema() {
    }

    public static synchronized void initialize(Connection connection) throws SQLException {
        initializeSchema(connection);
    }

    private static void initializeSchema(Connection connection) throws SQLException {
        if (tableExists(connection, "PROJECTTABLE") && !projectTableMatches(connection)) {
            recreateEmptyProjectTables(connection);
        }
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
        if (tableExists(connection, "PROJECTREGISTRATION")
                && !registrationTableMatches(connection)) {
            dropEmptyRegistration(connection);
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

    private static boolean projectTableMatches(Connection connection) throws SQLException {
        return columnMatches(connection, "PROJECTTABLE", "PROJECTID", Types.VARCHAR, 20, false)
                && columnMatches(connection, "PROJECTTABLE", "NAME", Types.VARCHAR, 120, false)
                && columnMatches(connection, "PROJECTTABLE", "OWNERNAME", Types.VARCHAR, 120, true)
                && columnMatches(connection, "PROJECTTABLE", "DESCRIPTION", Types.VARCHAR, 2000, true)
                && columnMatches(connection, "PROJECTTABLE", "STATUS", Types.VARCHAR, 40, true)
                && columnMatches(connection, "PROJECTTABLE", "STARTDATE", Types.DATE, 0, true)
                && columnMatches(connection, "PROJECTTABLE", "ENDDATE", Types.DATE, 0, true)
                && columnMatches(connection, "PROJECTTABLE", "GOALAMOUNT", Types.DECIMAL, 12, true)
                && goalScaleMatches(connection)
                && (!columnExists(connection, "PROJECTTABLE", "PROGRESSPERCENT")
                    || columnMatches(connection, "PROJECTTABLE", "PROGRESSPERCENT", Types.INTEGER, 0, true))
                && primaryKeyMatches(connection, "PROJECTTABLE", "PROJECTID");
    }

    private static boolean registrationTableMatches(Connection connection) throws SQLException {
        return columnMatches(connection, "PROJECTREGISTRATION", "REGISTRATIONID", Types.INTEGER, 0, false)
                && columnMatches(connection, "PROJECTREGISTRATION", "PROJECTID", Types.VARCHAR, 20, false)
                && columnMatches(connection, "PROJECTREGISTRATION", "USERNAME", Types.VARCHAR, 120, false)
                && columnMatches(connection, "PROJECTREGISTRATION", "USEREMAIL", Types.VARCHAR, 254, false)
                && columnMatches(connection, "PROJECTREGISTRATION", "REGISTRATIONDATE", Types.DATE, 0, false)
                && primaryKeyMatches(connection, "PROJECTREGISTRATION", "REGISTRATIONID")
                && projectForeignKeyMatches(connection);
    }

    private static boolean columnMatches(Connection connection, String table, String column,
            int type, int size, boolean nullable) throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(
                null, connection.getSchema(), table, column)) {
            return columns.next()
                    && columns.getInt("DATA_TYPE") == type
                    && (size == 0 || columns.getInt("COLUMN_SIZE") == size)
                    && (columns.getInt("NULLABLE") == DatabaseMetaData.columnNullable) == nullable;
        }
    }

    private static boolean primaryKeyMatches(Connection connection, String table, String column)
            throws SQLException {
        try (ResultSet keys = connection.getMetaData().getPrimaryKeys(
                null, connection.getSchema(), table)) {
            return keys.next() && column.equalsIgnoreCase(keys.getString("COLUMN_NAME"))
                    && !keys.next();
        }
    }

    private static boolean goalScaleMatches(Connection connection) throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(
                null, connection.getSchema(), "PROJECTTABLE", "GOALAMOUNT")) {
            return columns.next() && columns.getInt("DECIMAL_DIGITS") == 2;
        }
    }

    private static boolean projectForeignKeyMatches(Connection connection) throws SQLException {
        try (ResultSet keys = connection.getMetaData().getImportedKeys(
                null, connection.getSchema(), "PROJECTREGISTRATION")) {
            while (keys.next()) {
                if ("PROJECTID".equalsIgnoreCase(keys.getString("FKCOLUMN_NAME"))
                        && "PROJECTTABLE".equalsIgnoreCase(keys.getString("PKTABLE_NAME"))
                        && "PROJECTID".equalsIgnoreCase(keys.getString("PKCOLUMN_NAME"))) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void recreateEmptyProjectTables(Connection connection) throws SQLException {
        if (!tableEmpty(connection, "PROJECTTABLE")) {
            throw new SQLException("PROJECTTABLE has an incompatible schema and saved rows. "
                    + "Back up and migrate it; the application will not delete project data.");
        }
        try (ResultSet keys = connection.getMetaData().getExportedKeys(
                null, connection.getSchema(), "PROJECTTABLE")) {
            while (keys.next()) {
                if (!"PROJECTREGISTRATION".equalsIgnoreCase(keys.getString("FKTABLE_NAME"))) {
                    throw new SQLException("PROJECTTABLE has an incompatible schema and is referenced "
                            + "by another table. Back up and migrate the linked tables together.");
                }
            }
        }
        if (tableExists(connection, "PROJECTREGISTRATION")) {
            dropEmptyRegistration(connection);
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE ProjectTable");
        }
    }

    private static void dropEmptyRegistration(Connection connection) throws SQLException {
        if (!tableEmpty(connection, "PROJECTREGISTRATION")) {
            throw new SQLException("PROJECTREGISTRATION has an incompatible schema or depends "
                    + "on an incompatible PROJECTTABLE and contains saved rows. Back up and migrate it.");
        }
        try (ResultSet keys = connection.getMetaData().getExportedKeys(
                null, connection.getSchema(), "PROJECTREGISTRATION")) {
            if (keys.next()) {
                throw new SQLException("PROJECTREGISTRATION is referenced by another table. "
                        + "Back up and migrate the linked tables together.");
            }
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE ProjectRegistration");
        }
    }

    private static boolean tableEmpty(Connection connection, String table) throws SQLException {
        try (Statement statement = connection.createStatement();
                ResultSet rows = statement.executeQuery(
                        "SELECT 1 FROM " + table + " FETCH FIRST ROW ONLY")) {
            return !rows.next();
        }
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
