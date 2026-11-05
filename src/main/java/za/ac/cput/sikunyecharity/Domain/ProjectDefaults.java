package za.ac.cput.sikunyecharity.Domain;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

/** The three permanent projects shown on every Projects page. */
public final class ProjectDefaults {

    private ProjectDefaults() {
    }

    public static boolean isDefault(String id) {
        return "SSD".equalsIgnoreCase(id) || "SSF".equalsIgnoreCase(id)
                || "CCC".equalsIgnoreCase(id);
    }

    public static List<Project> all() {
        return Arrays.asList(
                new Project("SSD", "School Supply Drive", "Mr Gime",
                        "Providing essential school supplies to help students in need achieve their academic goals.",
                        "In progress", Date.valueOf("2020-12-20"), Date.valueOf("2027-01-20"), null, 58),
                new Project("SSF", "Share and Serve Food", "Mr Gime",
                        "Provides underserved families with meals, helping to fight hunger and build stronger communities.",
                        "In progress", Date.valueOf("2024-01-20"), Date.valueOf("2029-01-08"), null, 44),
                new Project("CCC", "Community Care Connect", "Mr Gime",
                        "A community-led initiative to provide accessible healthcare services and resources to vulnerable populations, bridging the gap in healthcare for underserved communities.",
                        "Planned", Date.valueOf("2026-07-02"), Date.valueOf("2028-01-10"), null, 36));
    }
}
