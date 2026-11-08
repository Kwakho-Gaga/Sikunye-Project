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

    // FIXED: now holds donationId + donationType too
    public static final class DonationLink {
        private final int donationId;
        private final int donorId;
        private final String projectId;
        private final String donationType;

        public DonationLink(int donationId, int donorId, String projectId, String donationType) {
            this.donationId = donationId;
            this.donorId = donorId;
            this.projectId = projectId;
            this.donationType = donationType;
        }
        public int getDonationId() { return donationId; }
        public int getDonorId() { return donorId; }
        public String getProjectId() { return projectId; }
        public String getDonationType() { return donationType; }
    }

    private static final String DB_SCHEMA = "ADMINISTRATOR";

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
        + "DonationID INT NOT NULL PRIMARY KEY, "
        + "DonorID INT NOT NULL, "
        + "ProjectID VARCHAR(20), "
        + "DonationType VARCHAR(20) DEFAULT 'FINANCIAL', "
        + "Amount DECIMAL(12,2) NOT NULL, "
        + "PaymentMethod VARCHAR(30) DEFAULT 'Card', "
        + "PaymentStatus VARCHAR(20) DEFAULT 'PENDING', "
        + "PaymentDate TIMESTAMP, "
        + "ReceiptGenerated BOOLEAN DEFAULT FALSE, "
        + "DonationDate DATE, "
        + "DateReceived DATE, "
        + "CONSTRAINT FK_DONATION_DONOR FOREIGN KEY (DonorID) REFERENCES DONOR (DonorID), "
        + "CONSTRAINT FK_DONATION_PROJECT FOREIGN KEY (ProjectID) REFERENCES ProjectTable (ProjectID))";

    private static final String INSERT_DONOR =
            "INSERT INTO DONOR "
            + "(DONORID, FIRSTNAME, LASTNAME, EMAIL, PHONENUMBER, "
            + "STREETADDRESS, CITY, PROVINCE, POSTALCODE) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    // 10 cols, 9 ? + 'PAID' literal = 10 values
    // 1=DonationID 2=DonorID 3=Amount 4=PaymentMethod 5=DonationDate 6=ProjectID 7=DonationType 8=PaymentDate 9=DateReceived
    private static final String INSERT_DONATION =
        "INSERT INTO DONATION "
        + "(DonationID, DonorID, Amount, PaymentMethod, DonationDate, ProjectID, "
        + "DonationType, PaymentStatus, PaymentDate, DateReceived) "
        + "VALUES (?, ?, ?, ?, ?, ?, ?, 'PAID', ?, ?)";

    public List<Project> getProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT ProjectID, Name FROM ProjectTable ORDER BY Name";
        try (Connection connection = openConnection()) {
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

    public void createTablesIfNotExist() throws SQLException {
        try (Connection connection = openConnection()) {
            createTablesIfMissing(connection);
        }
    }

    private void createTablesIfMissing(Connection connection) throws SQLException {
        createTableIfMissing(connection, "DONOR", CREATE_DONOR_TABLE);
        createTableIfMissing(connection, "DONATION", CREATE_DONATION_TABLE);
        ensureDonationProjectColumn(connection);
    }

    private void createTableIfMissing(Connection connection, String tableName, String createSql) throws SQLException {
        if (tableExists(connection, tableName)) return;
        try (PreparedStatement statement = connection.prepareStatement(createSql)) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            if (!tableExists(connection, tableName)) throw ex;
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet tables = metadata.getTables(null, DB_SCHEMA, tableName.toUpperCase(Locale.ROOT), new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    // OLD callers (FINANCIAL only) still work - delegates to new method
    public DonationLink saveDonorAndDonation(
            String firstName, String lastName, String email, String phoneNumber,
            String streetAddress, String city, String province, String postalCode,
            double amount, String paymentMethod, LocalDate donationDate, String projectId
    ) throws SQLException {
        return saveDonorAndDonation(firstName, lastName, email, phoneNumber, streetAddress, city, province, postalCode, amount, paymentMethod, donationDate, projectId, "FINANCIAL");
    }

    // NEW: with donationType - ONLY ONE real implementation, no duplicate
    public DonationLink saveDonorAndDonation(
            String firstName, String lastName, String email, String phoneNumber,
            String streetAddress, String city, String province, String postalCode,
            double amount, String paymentMethod, LocalDate donationDate, String projectId,
            String donationType
    ) throws SQLException {
        return saveDonorAndDonation(firstName, lastName, email, phoneNumber,
                streetAddress, city, province, postalCode, amount, paymentMethod,
                donationDate, projectId, donationType, null, 0);
    }

    public DonationLink saveDonorAndDonation(
            String firstName, String lastName, String email, String phoneNumber,
            String streetAddress, String city, String province, String postalCode,
            double amount, String paymentMethod, LocalDate donationDate, String projectId,
            String donationType, String itemName, int itemQuantity
    ) throws SQLException {
        if (donationType == null || donationType.isBlank()) donationType = "FINANCIAL";
        donationType = donationType.toUpperCase();
        if ("IN-KIND".equals(donationType)
                && (itemName == null || itemName.isBlank() || itemQuantity <= 0)) {
            throw new IllegalArgumentException("Choose an item and quantity for an in-kind donation.");
        }
        if ("FINANCIAL".equals(donationType) && amount <= 0) {
            throw new IllegalArgumentException("Donation amount must be greater than zero.");
        }
        if ("IN-KIND".equals(donationType)) {
            amount = 0;
            paymentMethod = "In-Kind";
        }
        try (Connection connection = openConnection()) {
            createTablesIfMissing(connection);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            try {
                int donorId = nextId(connection, "DONOR", "DONORID");
                insertDonor(connection, donorId, firstName, lastName, email, phoneNumber, streetAddress, city, province, postalCode);
                int donationId = nextId(connection, "DONATION", "DONATIONID");
                insertDonation(connection, donationId, donorId, amount, paymentMethod, donationDate, projectId, donationType);
                        // DELETED: Do NOT insert into ITEMS_DONATED here.
                // DonorDonationGUI_ED already saves it ONCE with:
                // ItemID = catalogue ID (1-5), Condition = "Good"
                // Keeping this insert caused the double row:
                // ItemID 8 / "Not specified" + ItemID 9 / "Good" for same ReceiptID    
                connection.commit();
                return new DonationLink(donationId, donorId, projectId, donationType);
            } catch (SQLException ex) {
                try { connection.rollback(); } catch (SQLException re) { ex.addSuppressed(re); }
                throw ex;
            }
        }
    }

    private Connection openConnection() throws SQLException {
        return DBConnection.derbyConnection();
    }

    private int nextId(Connection connection, String table, String idColumn) throws SQLException {
        String sql = "SELECT COALESCE(MAX(" + idColumn + "), 0) + 1 FROM " + table;
        try (PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()) {
            result.next();
            return result.getInt(1);
        }
    }

    private void ensureDonationProjectColumn(Connection connection) throws SQLException {
        if (!columnExists(connection, "DONATION", "PROJECTID")) {
            try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE DONATION ADD COLUMN PROJECTID VARCHAR(20)")) {
                statement.executeUpdate();
            } catch (SQLException ex) {
                if (!columnExists(connection, "DONATION", "PROJECTID")) throw ex;
            }
        }
        if (!foreignKeyExists(connection, "DONATION", "FK_DONATION_PROJECT")) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "ALTER TABLE DONATION ADD CONSTRAINT FK_DONATION_PROJECT FOREIGN KEY (PROJECTID) REFERENCES ProjectTable (ProjectID)")) {
                statement.executeUpdate();
            } catch (SQLException ex) {
                if (!foreignKeyExists(connection, "DONATION", "FK_DONATION_PROJECT")) throw ex;
            }
        }
    }

    private boolean columnExists(Connection connection, String tableName, String columnName) throws SQLException {
        try (ResultSet columns = connection.getMetaData().getColumns(null, DB_SCHEMA, tableName.toUpperCase(Locale.ROOT), columnName.toUpperCase(Locale.ROOT))) {
            return columns.next();
        }
    }

    private boolean foreignKeyExists(Connection connection, String tableName, String keyName) throws SQLException {
        try (ResultSet keys = connection.getMetaData().getImportedKeys(null, DB_SCHEMA, tableName.toUpperCase(Locale.ROOT))) {
            while (keys.next()) {
                boolean namedProjectKey = keyName.equalsIgnoreCase(keys.getString("FK_NAME"));
                boolean projectIdReference = "PROJECTID".equalsIgnoreCase(keys.getString("FKCOLUMN_NAME"))
                        && "PROJECTTABLE".equalsIgnoreCase(keys.getString("PKTABLE_NAME"))
                        && "PROJECTID".equalsIgnoreCase(keys.getString("PKCOLUMN_NAME"));
                if (namedProjectKey || projectIdReference) return true;
            }
        }
        return false;
    }

    private void insertDonor(Connection connection, int donorId, String firstName, String lastName, String email, String phoneNumber, String streetAddress, String city, String province, String postalCode) throws SQLException {
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

    private void insertDonation(Connection connection, int donationId, int donorId, double amount, String paymentMethod, LocalDate donationDate, String projectId, String donationType) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_DONATION)) {
            statement.setInt(1, donationId);
            statement.setInt(2, donorId);
            statement.setDouble(3, amount);
            statement.setString(4, paymentMethod);
            statement.setDate(5, java.sql.Date.valueOf(donationDate));
            if (projectId == null || projectId.isBlank()) statement.setNull(6, Types.VARCHAR);
            else statement.setString(6, projectId);
            statement.setString(7, donationType.toUpperCase());
            // FIXED: PaymentDate = DonationDate + current time
            statement.setTimestamp(8, java.sql.Timestamp.valueOf(donationDate.atTime(java.time.LocalTime.now())));
            statement.setDate(9, java.sql.Date.valueOf(donationDate));
            statement.executeUpdate();
        }
    }
}
