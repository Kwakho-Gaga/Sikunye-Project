package za.ac.cput.sikunyecharity.DBConection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/** Adds the three original campaigns without overwriting later admin changes. */
public final class ProjectSeeder {

    private ProjectSeeder() {
    }

    public static void seed(Connection connection) throws SQLException {
        addIfMissing(connection, "SSD", "School Supply Drive", "Mr Gime",
                "Providing essential school supplies to help students in need achieve their academic goals.",
                "In progress", "2020-12-20", "2027-01-20", 58);
        addIfMissing(connection, "SSF", "Share and Serve Food", "Mr Gime",
                "Provides underserved families with meals, helping to fight hunger and build stronger communities.",
                "In progress", "2024-01-20", "2029-01-08", 44);
        addIfMissing(connection, "CCC", "Community Care Connect", "Mr Gime",
                "A community-led initiative to provide accessible healthcare services and resources to vulnerable populations, bridging the gap in healthcare for underserved communities.",
                "Planned", "2026-07-02", "2028-01-10", 36);
    }

    private static void addIfMissing(Connection connection, String id, String name,
            String owner, String description, String status, String start, String end,
            int progress) throws SQLException {
        try (PreparedStatement exists = connection.prepareStatement(
                "SELECT ProjectID FROM ProjectTable WHERE ProjectID = ?")) {
            exists.setString(1, id);
            try (ResultSet row = exists.executeQuery()) {
                if (row.next()) {
                    try (PreparedStatement update = connection.prepareStatement(
                            "UPDATE ProjectTable SET ProgressPercent = ? "
                            + "WHERE ProjectID = ? AND ProgressPercent IS NULL")) {
                        update.setInt(1, progress);
                        update.setString(2, id);
                        update.executeUpdate();
                    }
                    return;
                }
            }
        }

        String sql = "INSERT INTO ProjectTable "
                + "(ProjectID, Name, OwnerName, Description, Status, StartDate, "
                + "EndDate, GoalAmount, ProgressPercent) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, owner);
            statement.setString(4, description);
            statement.setString(5, status);
            statement.setDate(6, java.sql.Date.valueOf(LocalDate.parse(start)));
            statement.setDate(7, java.sql.Date.valueOf(LocalDate.parse(end)));
            statement.setNull(8, java.sql.Types.DECIMAL);
            statement.setInt(9, progress);
            statement.executeUpdate();
        }
    }
}
