package za.ac.cput.sikunyecharity.DAO;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.cput.sikunyecharity.DBConection.DBConnection;
import za.ac.cput.sikunyecharity.Domain.ProjectDonationRecord;

public class ProjectDonationDAO {

    public List<ProjectDonationRecord> getDonations(String projectId)
            throws SQLException {
        List<ProjectDonationRecord> donations = new ArrayList<>();
        try (Connection connection = DBConnection.derbyConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            if (!hasTable(metadata, connection, "DONATION")) {
                return donations;
            }
            String donorId = hasColumn(metadata, connection, "DONATION", "DONORID")
                    ? "DONORID" : "CAST(NULL AS INTEGER) AS DONORID";
            String payment = hasColumn(metadata, connection, "DONATION", "PAYMENTMETHOD")
                    ? "PAYMENTMETHOD" : "CAST(NULL AS VARCHAR(30)) AS PAYMENTMETHOD";
            String sql = "SELECT DONATIONID, " + donorId + ", PROJECTID, AMOUNT, "
                    + payment + ", DONATIONDATE FROM DONATION "
                    + (projectId == null ? "" : "WHERE PROJECTID = ? ")
                    + "ORDER BY DONATIONDATE DESC, DONATIONID DESC";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                if (projectId != null) {
                    statement.setString(1, projectId);
                }
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        donations.add(new ProjectDonationRecord(
                                result.getInt("DONATIONID"),
                                result.getInt("DONORID"),
                                result.getString("PROJECTID"),
                                result.getBigDecimal("AMOUNT"),
                                result.getString("PAYMENTMETHOD"),
                                result.getDate("DONATIONDATE")));
                    }
                }
            }
        }
        return donations;
    }

    private boolean hasTable(DatabaseMetaData metadata, Connection connection,
            String name) throws SQLException {
        try (ResultSet tables = metadata.getTables(null, connection.getSchema(),
                name, new String[]{"TABLE"})) {
            return tables.next();
        }
    }

    private boolean hasColumn(DatabaseMetaData metadata, Connection connection,
            String table, String column) throws SQLException {
        try (ResultSet columns = metadata.getColumns(null, connection.getSchema(),
                table, column)) {
            return columns.next();
        }
    }
}
