package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.Project;

/**
 * Esona Dyomfana 240949072
 */
public class DonorDonationDAOED {

    public static final class DonationLink {
        private final int donorId;
        private final String projectId;

        private DonationLink(int donorId, String projectId) {
            this.donorId = donorId;
            this.projectId = projectId;
        }

        public int getDonorId() {
            return donorId;
        }

        public String getProjectId() {
            return projectId;
        }
    }

    private static final String DB_SCHEMA = "ADMINISTRATOR";

    private static final String CREATE_PROJECT_TABLE =
            "CREATE TABLE ProjectTable ("
            + "ProjectID VARCHAR(20) PRIMARY KEY, "
            + "Name VARCHAR(120) NOT NULL, "
            + "OwnerName VARCHAR(120), "
            + "Description VARCHAR(2000), "
            + "Status VARCHAR(40), "
            + "StartDate DATE, "
            + "EndDate DATE, "
            + "GoalAmount DECIMAL(12,2))";

    private static final String CREATE_DONOR_TABLE =
            "CREATE TABLE DONOR ("
            + "DONORID INT NOT NULL PRIMARY KEY, "
            + "FIRSTNAME VARCHAR(100) NOT NULL, "
            + "LASTNAME VARCHAR(100) NOT NULL, "
            + "EMAIL VARCHAR(255) NOT NULL, "
            + "PHONENUMBER VARCHAR(30) NOT NULL, "
            + "STREETADDRESS VARCHAR(255) NOT NULL, "
            + "CITY VARCHAR(100) NOT NULL, "
            + "PROVINCE VARCHAR(100) NOT NULL, "
            + "POSTALCODE VARCHAR(20) NOT NULL)";

    private static final String CREATE_DONATION_TABLE =
            "CREATE TABLE DONATION ("
            + "DONATIONID INT NOT NULL PRIMARY KEY, "
            + "DONORID INT NOT NULL, "
            + "AMOUNT DECIMAL(12, 2) NOT NULL, "
            + "PAYMENTMETHOD VARCHAR(30) NOT NULL, "
            + "DONATIONDATE DATE NOT NULL, "
            + "PROJECTID VARCHAR(20), "
            + "CONSTRAINT FK_DONATION_DONOR FOREIGN KEY (DONORID) "
            + "REFERENCES DONOR (DONORID), "
            + "CONSTRAINT FK_DONATION_PROJECT FOREIGN KEY (PROJECTID) "
            + "REFERENCES ProjectTable (ProjectID))";

    private static final String INSERT_DONOR =
            "INSERT INTO DONOR "
            + "(DONORID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, "
            + "STREETADDRESS, CITY, PROVINCE, POSTALCODE) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_DONATION =
            "INSERT INTO DONATION "
            + "(DONATIONID, DONORID, AMOUNT, PAYMENTMETHOD, DONATIONDATE, PROJECTID) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    public List<Project> getProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT ProjectID, Name FROM ProjectTable ORDER BY Name";
        try (Connection connection = openConnection()) {
            ensureProjectTable(connection);
            try (PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet rows = statement.executeQuery()) {
                while (rows.next()) {
                    projects.add(new Project(
                            rows.getString("ProjectID"),
                            rows.getString("Name"),
                            null, null, null, null, null, null));
                }
            }
        }
        return projects;
    }

    private void ensureProjectTable(Connection connection) throws SQLException {
        createTableIfMissing(connection, "PROJECTTABLE", CREATE_PROJECT_TABLE);

        try (PreparedStatement countStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM ProjectTable");
                ResultSet result = countStatement.executeQuery()) {
            result.next();
            if (result.getInt(1) != 0) {
                return;
            }
        }

        String insertSql = "INSERT INTO ProjectTable "
                + "(ProjectID, Name, OwnerName, Description, Status, StartDate, EndDate, GoalAmount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
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

    private void addProject(PreparedStatement statement, String projectId, String name,
            String owner, String description, String status, String startDate, String endDate)
            throws SQLException {
        statement.setString(1, projectId);
        statement.setString(2, name);
        statement.setString(3, owner);
        statement.setString(4, description);
        statement.setString(5, status);
        statement.setDate(6, java.sql.Date.valueOf(startDate));
        statement.setDate(7, java.sql.Date.valueOf(endDate));
        statement.setNull(8, Types.DECIMAL);
        statement.executeUpdate();
    }

    public void createTablesIfNotExist() throws SQLException {
        try (Connection connection = openConnection()) {
            createTablesIfMissing(connection);
        }
    }

    private void createTablesIfMissing(Connection connection) throws SQLException {
        ensureProjectTable(connection);
        createTableIfMissing(connection, "DONOR", CREATE_DONOR_TABLE);
        createTableIfMissing(connection, "DONATION", CREATE_DONATION_TABLE);
        ensureDonationProjectColumn(connection);
    }

    private void createTableIfMissing(
            Connection connection,
            String tableName,
            String createSql
    ) throws SQLException {
        if (tableExists(connection, tableName)) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(createSql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            if (!tableExists(connection, tableName)) {
                throw ex;
            }
        }
    }

    private boolean tableExists(Connection connection, String tableName)
            throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet tables = metadata.getTables(
                null,
                DB_SCHEMA,
                tableName.toUpperCase(Locale.ROOT),
                new String[]{"TABLE"}
        )) {
            return tables.next();
        }
    }

    public DonationLink saveDonorAndDonation(
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String streetAddress,
            String city,
            String province,
            String postalCode,
            double amount,
            String paymentMethod,
            LocalDate donationDate,
            String projectId
    ) throws SQLException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Donation amount must be greater than zero.");
        }

        try (Connection connection = openConnection()) {
            createTablesIfMissing(connection);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            try {
                int donorId = nextId(connection, "DONOR", "DONORID");
                insertDonor(
                        connection,
                        donorId,
                        firstName,
                        lastName,
                        email,
                        phoneNumber,
                        streetAddress,
                        city,
                        province,
                        postalCode
                );

                int donationId = nextId(connection, "DONATION", "DONATIONID");
                insertDonation(
                        connection,
                        donationId,
                        donorId,
                        amount,
                        paymentMethod,
                        donationDate,
                        projectId
                );

                connection.commit();
                return new DonationLink(donorId, projectId);
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    ex.addSuppressed(rollbackException);
                }
                throw ex;
            }
        }
    }

    private Connection openConnection() throws SQLException {
        return DBConnection.derbyConnection();
    }

    private int nextId(Connection connection, String table, String idColumn)
            throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + idColumn + "), 0) + 1 FROM " + table;
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            result.next();
            return result.getInt(1);
        }
    }

    private void ensureDonationProjectColumn(Connection connection) throws SQLException {
        if (!columnExists(connection, "DONATION", "PROJECTID")) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "ALTER TABLE DONATION ADD COLUMN PROJECTID VARCHAR(20)")) {
                statement.executeUpdate();
            } catch (SQLException ex) {
                if (!columnExists(connection, "DONATION", "PROJECTID")) {
                    throw ex;
                }
            }
        }

        if (!foreignKeyExists(connection, "DONATION", "FK_DONATION_PROJECT")) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "ALTER TABLE DONATION ADD CONSTRAINT FK_DONATION_PROJECT "
                    + "FOREIGN KEY (PROJECTID) REFERENCES ProjectTable (ProjectID)")) {
                statement.executeUpdate();
            } catch (SQLException ex) {
                if (!foreignKeyExists(connection, "DONATION", "FK_DONATION_PROJECT")) {
                    throw ex;
                }
            }
        }
    }

    private boolean columnExists(Connection connection, String tableName, String columnName)
            throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(
                null, DB_SCHEMA, tableName.toUpperCase(Locale.ROOT),
                columnName.toUpperCase(Locale.ROOT))) {
            return columns.next();
        }
    }

    private boolean foreignKeyExists(Connection connection, String tableName, String keyName)
            throws SQLException {
        try (ResultSet keys = connection.getMetaData().getImportedKeys(
                null, DB_SCHEMA, tableName.toUpperCase(Locale.ROOT))) {
            while (keys.next()) {
                boolean namedProjectKey = keyName.equalsIgnoreCase(keys.getString("FK_NAME"));
                boolean projectIdReference = "PROJECTID".equalsIgnoreCase(
                        keys.getString("FKCOLUMN_NAME"))
                        && "PROJECTTABLE".equalsIgnoreCase(keys.getString("PKTABLE_NAME"))
                        && "PROJECTID".equalsIgnoreCase(keys.getString("PKCOLUMN_NAME"));
                if (namedProjectKey || projectIdReference) {
                    return true;
                }
            }
        }
        return false;
    }

    private void insertDonor(
            Connection connection,
            int donorId,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String streetAddress,
            String city,
            String province,
            String postalCode
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DONOR)) {
            statement.setInt(1, donorId);
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, email);
            statement.setString(5, phoneNumber);
            statement.setString(6, streetAddress);
            statement.setString(7, city);
            statement.setString(8, province);
            statement.setString(9, postalCode);
            statement.executeUpdate();
        }
    }

    private void insertDonation(
            Connection connection,
            int donationId,
            int donorId,
            double amount,
            String paymentMethod,
            LocalDate donationDate,
            String projectId
    ) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DONATION)) {
            statement.setInt(1, donationId);
            statement.setInt(2, donorId);
            statement.setDouble(3, amount);
            statement.setString(4, paymentMethod);
            statement.setDate(5, java.sql.Date.valueOf(donationDate));
            if (projectId == null || projectId.isBlank()) {
                statement.setNull(6, Types.VARCHAR);
            } else {
                statement.setString(6, projectId);
            }
            statement.executeUpdate();
        }
    }
}