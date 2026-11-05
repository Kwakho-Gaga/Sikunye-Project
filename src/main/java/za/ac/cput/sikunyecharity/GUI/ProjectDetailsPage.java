package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import za.ac.cput.sikunyecharity.Domain.Project;

public class ProjectDetailsPage extends JFrame {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public ProjectDetailsPage(Project project, ProjectsPage projectsPage) {
        setTitle("Sikunye Charity | " + project.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 850);
        setMinimumSize(new Dimension(760, 600));
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                if (projectsPage != null && projectsPage.isDisplayable()) {
                    projectsPage.setVisible(true);
                }
            }
        });

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(ProjectPageStyle.BACKGROUND);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(ProjectPageStyle.PRIMARY);
        header.setBorder(new EmptyBorder(20, 45, 20, 45));
        JLabel brand = new JLabel("SIKUNYE CHARITY");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 13));
        JLabel title = new JLabel(project.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        header.add(brand);
        header.add(Box.createVerticalStrut(8));
        header.add(title);
        page.add(header, BorderLayout.NORTH);

        JPanel content = new ProjectPageStyle.ScrollableContent();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ProjectPageStyle.BACKGROUND);
        content.setBorder(new EmptyBorder(32, 45, 38, 45));
        JLabel heading = new JLabel("About the Project");
        heading.setFont(ProjectPageStyle.SECTION_FONT);
        heading.setForeground(ProjectPageStyle.DARK);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(heading);
        content.add(Box.createVerticalStrut(18));
        content.add(ProjectPageStyle.createParagraph(
                project.getDescription() == null ? "" : project.getDescription(), 6));
        content.add(Box.createVerticalStrut(28));
        content.add(detail("Owner: " + value(project.getOwnerName())));
        content.add(detail("Status: " + value(project.getStatus())));
        content.add(detail("Timeline: " + date(project.getStartDate())
                + " - " + date(project.getEndDate())));
        content.add(detail("Goal amount: " + (project.getGoalAmount() == null
                ? "Not set" : "R" + project.getGoalAmount())));
        content.add(detail("Progress: " + project.getProgressPercent() + "%"));
        content.add(Box.createVerticalStrut(24));
        JButton back = new JButton("Back to Projects");
        ProjectPageStyle.styleBackButton(back);
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.addActionListener(event -> dispose());
        content.add(back);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        page.add(scrollPane, BorderLayout.CENTER);
        page.add(ProjectPageStyle.createFooter(), BorderLayout.SOUTH);
        setContentPane(page);
    }

    private JLabel detail(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(ProjectPageStyle.DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(0, 0, 12, 0));
        return label;
    }

    private String value(String text) {
        return text == null || text.isBlank() ? "Not set" : text;
    }

    private String date(Date date) {
        return date == null ? "TBA" : date.toLocalDate().format(DATE_FORMAT);
    }
}
