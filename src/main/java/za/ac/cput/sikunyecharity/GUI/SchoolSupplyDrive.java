
package za.ac.cput.sikunyecharity.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SchoolSupplyDrive extends JFrame implements ActionListener {

    private final ProjectsPage projectsPage;
    private JButton btnBack;

    // Colours
    private final Color BACKGROUND = ProjectPageStyle.BACKGROUND;
    private final Color HEADER_COLOR = ProjectPageStyle.PRIMARY;
    private final Color HEADING_COLOR = ProjectPageStyle.DARK;
    private final Color CARD_COLOR = Color.WHITE;

    public SchoolSupplyDrive() {
        this(null);
    }

    public SchoolSupplyDrive(ProjectsPage projectsPage) {
        this.projectsPage = projectsPage;
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                if (SchoolSupplyDrive.this.projectsPage != null
                        && SchoolSupplyDrive.this.projectsPage.isDisplayable()) {
                    SchoolSupplyDrive.this.projectsPage.setVisible(true);
                }
            }
        });

        // Frame

        setTitle("Sikunye Charity - School Supply Drive");
        setSize(1100, 850);
        setMinimumSize(new Dimension(750, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND);

        // Header

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(HEADER_COLOR);
        header.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("School Supply Drive");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "Providing essential school supplies to help students "
                        + "achieve their academic goals"
        );
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(title);
        header.add(Box.createVerticalStrut(10));
        header.add(subtitle);

        add(header, BorderLayout.NORTH);

        // Content panel

        JPanel content = new ProjectPageStyle.ScrollableContent();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(35, 45, 35, 45));

        // About the project

        addSectionTitle(content, "About the Project");

        String about =
                "Many children in underserved areas lack basic school "
                + "supplies, which can affect their participation, "
                + "motivation, and success in school. This project aims "
                + "to remove those barriers, promote equal learning "
                + "opportunities, and demonstrate community support "
                + "for every child's education."
                + "<br><br>"
                + "The School Supply Drive is a community effort to "
                + "collect, organize, and distribute essential school "
                + "supplies to children from low-income families. "
                + "We work with local schools, community centers, "
                + "and partners to identify children in need. "
                + "Donations come from online platforms, physical "
                + "collection points, and corporate sponsors. "
                + "Once collected, supplies are sorted, packed, "
                + "and delivered at the start of each school term. "
                + "The project also raises awareness about the "
                + "importance of education and community support.";

        addParagraph(content, about);

        addSpacing(content, 35);

        // Project goals and impacts

        addSectionTitle(content, "Project Goals and Impacts");

        JPanel goalsPanel = createCardGrid();

        goalsPanel.add(createCard(
                "Distribute Supplies to Children",
                "Ensure that 200000 children receive the tools "
                        + "they need for learning."
        ));

        goalsPanel.add(createCard(
                "Increase Community Engagement",
                "Raise awareness about educational gaps and "
                        + "encourage community involvement."
        ));

        goalsPanel.add(createCard(
                "Build Strong Partnerships",
                "Work with local organizations to sustain "
                        + "and grow the project."
        ));

        goalsPanel.add(createCard(
                "Gather Feedback",
                "Improve future efforts based on community "
                        + "and partner input."
        ));

        goalsPanel.add(createCard(
                "Foster Support",
                "Inspire ongoing giving and community solidarity."
        ));

        goalsPanel.add(createEmptyCard());

        content.add(goalsPanel);

        addSpacing(content, 40);

        // Project progress

        addSectionTitle(content, "Project Progress");

        JPanel progressPanel = createCardGrid();

        progressPanel.add(createCard(
                "Planning",
                "Partner schools and community centers identified; "
                        + "awareness campaigns launched; donation "
                        + "channels set up."
        ));

        progressPanel.add(createCard(
                "Collection",
                "Over R120000 of supplies gathered through "
                        + "donations and sponsorships."
        ));

        progressPanel.add(createCard(
                "Distribution",
                "Supplies delivered to 100000 children during "
                        + "the first wave, with plans for more."
        ));

        progressPanel.add(createCard(
                "Impacts",
                "Positive feedback received; increased "
                        + "enthusiasm among students; data "
                        + "collected on children served."
        ));

        progressPanel.add(createCard(
                "Next Step",
                "Continue collection efforts; expand to new "
                        + "communities; follow up to assess "
                        + "ongoing needs and impact."
        ));

        progressPanel.add(createEmptyCard());

        content.add(progressPanel);

        addSpacing(content, 35);

        // Back button

        btnBack = new JButton("Back");

        ProjectPageStyle.styleBackButton(btnBack);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnBack.addActionListener(this);

        content.add(btnBack);

        // Scroll pane

        JScrollPane scrollPane = new JScrollPane(content);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.getVerticalScrollBar().setUnitIncrement(18);

        scrollPane.getViewport().setBackground(BACKGROUND);

        // Add scroll pane to frame

        add(scrollPane, BorderLayout.CENTER);
        add(ProjectPageStyle.createFooter(), BorderLayout.SOUTH);

    }

    // Section title

    private void addSectionTitle(JPanel panel, String text) {

        JLabel heading = new JLabel(text);

        heading.setFont(ProjectPageStyle.SECTION_FONT);
        heading.setForeground(HEADING_COLOR);

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(heading);
        panel.add(Box.createVerticalStrut(20));
    }

    // Paragraph

    private void addParagraph(JPanel panel, String text) {

        panel.add(ProjectPageStyle.createParagraph(text, 10));
    }

    // Card grid

    private JPanel createCardGrid() {

        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));

        grid.setBackground(BACKGROUND);
        grid.setAlignmentX(Component.CENTER_ALIGNMENT);

        grid.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE)
        );

        return grid;
    }

    // Create card

    private JPanel createCard(String heading, String description) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_COLOR);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 225, 235), 1
                ),
                new EmptyBorder(25, 22, 25, 22)
        ));

        JLabel cardTitle = new JLabel(
                "<html><div style='width:150px; text-align:left;'>"
                        + heading
                        + "</div></html>"
        );

        cardTitle.setFont(new Font("Arial", Font.BOLD, 16));
        cardTitle.setForeground(HEADING_COLOR);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cardDescription = new JLabel(
                "<html><div style='width:150px;"
                        + "text-align:left;"
                        + "line-height:150%;'>"
                        + description
                        + "</div></html>"
        );

        cardDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        cardDescription.setForeground(ProjectPageStyle.MUTED);
        cardDescription.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(cardTitle);
        card.add(Box.createVerticalStrut(18));
        card.add(cardDescription);

        return card;
    }

    // Empty card

    private JPanel createEmptyCard() {

        JPanel empty = new JPanel();
        empty.setOpaque(false);

        return empty;
    }

    // Spacing

    private void addSpacing(JPanel panel, int height) {

        panel.add(Box.createVerticalStrut(height));
    }

    // Main method

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            if (projectsPage == null) {
                new ProjectsPage().setVisible(true);
            } else {
                projectsPage.setVisible(true);
            }
            dispose();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SchoolSupplyDrive().setVisible(true);
            }
        });
    }
}
