package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Prepares beneficiary tables on the application's existing connection. */
public final class BeneficiarySchema {

    private static boolean initialized;

    private BeneficiarySchema() {
    }

    public static synchronized void initialize(Connection connection) throws SQLException {
        if (!initialized) {
            initializeSchema(connection);
            initialized = true;
        }
    }

    private static void initializeSchema(Connection connection) throws SQLException {
        if (!tableExists(connection, "BENEFICIARY")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE Beneficiary ("
                        + "BeneficiaryID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY, "
                        + "FirstName VARCHAR(100) NOT NULL, "
                        + "LastName VARCHAR(100) NOT NULL, "
                        + "FamilySize INTEGER NOT NULL, "
                        + "Address VARCHAR(250) NOT NULL, "
                        + "LivingSituation VARCHAR(150) NOT NULL)");
            }
        }
        if (!tableExists(connection, "NEEDS")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE Needs ("
                        + "NeedCode INTEGER PRIMARY KEY, "
                        + "Description VARCHAR(120) NOT NULL)");
            }
        }
        if (!tableExists(connection, "BENEFICIARYNEEDS")) {
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("CREATE TABLE BeneficiaryNeeds ("
                        + "BeneficiaryID INTEGER NOT NULL REFERENCES Beneficiary(BeneficiaryID), "
                        + "NeedCode INTEGER NOT NULL REFERENCES Needs(NeedCode), "
                        + "Quantity INTEGER NOT NULL, "
                        + "PRIMARY KEY (BeneficiaryID, NeedCode))");
            }
        }
        seedNeeds(connection);
    }

    private static boolean tableExists(Connection connection, String name) throws SQLException {
        DatabaseMetaData metadata = connection.getMetaData();
        try (ResultSet tables = metadata.getTables(null, connection.getSchema(), name,
                new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    private static void seedNeeds(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement();
                ResultSet rows = statement.executeQuery("SELECT COUNT(*) FROM Needs")) {
            rows.next();
            if (rows.getInt(1) != 0) {
                return;
            }
        }

        String[] descriptions = {"Blankets", "Food", "Shelter", "Clothing",
            "Education Supplies", "Medical Care", "Hygiene Products", "Winter Coats"};
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO Needs (NeedCode, Description) VALUES (?, ?)")) {
            for (int i = 0; i < descriptions.length; i++) {
                statement.setInt(1, i + 1);
                statement.setString(2, descriptions[i]);
                statement.executeUpdate();
            }
        }
    }
}
