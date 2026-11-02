package za.ac.cput.sikunyecharity.GUI;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ShareAndServeFood extends JFrame implements ActionListener {

    private final ProjectsPage projectsPage;
    private JButton btnBack;

    private static final Color BACKGROUND = ProjectPageStyle.BACKGROUND;
    private static final Color PRIMARY = ProjectPageStyle.PRIMARY;
    private static final Color DARK = ProjectPageStyle.DARK;
    private static final Color MUTED = ProjectPageStyle.MUTED;
    private static final Color CARD = Color.WHITE;
    private static final Font BODY_FONT = new Font("Arial", Font.PLAIN, 14);
    private static final Font HEADING_FONT = ProjectPageStyle.SECTION_FONT;

    public ShareAndServeFood() {
        this(null);
    }

    public ShareAndServeFood(ProjectsPage projectsPage) {
        this.projectsPage = projectsPage;
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                if (ShareAndServeFood.this.projectsPage != null
                        && ShareAndServeFood.this.projectsPage.isDisplayable()) {
                    ShareAndServeFood.this.projectsPage.setVisible(true);
                }
            }
        });
        setTitle("Sikunye Charity | Share and Serve Food");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1100, 850);
        setMinimumSize(new Dimension(760, 600));
        setLocationRelativeTo(null);

        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BACKGROUND);

        // Header / navigation area
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY);
        header.setBorder(new EmptyBorder(20, 45, 20, 45));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));

        JLabel brandName = new JLabel("SIKUNYE CHARITY");
        brandName.setFont(new Font("Arial", Font.BOLD, 13));
        brandName.setForeground(new Color(225, 248, 255));

        JLabel pageTitle = new JLabel("Share and Serve Food");
        pageTitle.setFont(new Font("Arial", Font.BOLD, 28));
        pageTitle.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Providing meals, restoring dignity, and building stronger communities.");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        brand.add(brandName);
        brand.add(Box.createVerticalStrut(8));
        brand.add(pageTitle);
        brand.add(Box.createVerticalStrut(6));
        brand.add(subtitle);
        header.add(brand, BorderLayout.WEST);

        JLabel badge = new JLabel("COMMUNITY PROJECT");
        badge.setFont(new Font("Arial", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setBorder(new LineBorder(new Color(255, 255, 255, 150), 1, true));
        badge.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 247, 255), 1, true),
                new EmptyBorder(9, 13, 9, 13)));
        header.add(badge, BorderLayout.EAST);
        page.add(header, BorderLayout.NORTH);

        // Scrollable page content
        JPanel content = new ProjectPageStyle.ScrollableContent();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(32, 45, 38, 45));

        content.add(sectionHeading("About the Project",
                "A community initiative focused on food security and care."));
        content.add(Box.createVerticalStrut(14));

        JPanel aboutCard = createCard();
        JTextArea aboutText = ProjectPageStyle.createParagraph(
                "Many community members lack access to reliable, nutritious food due to economic hardship. "
                + "This project aims to address hunger, promote dignity, and build a caring community where no one goes hungry."
                + "<br><br>"
                + "Share and Serve Food is a community initiative to provide nutritious meals to those in need. "
                + "We organize food parcels and operate soup kitchens to support vulnerable populations. "
                + "The project collaborates with local partners to distribute food directly to individuals and families facing food insecurity. "
                + "Our goal is to ensure everyone has access to healthy, hot meals and to foster community care.",
                9);
        aboutCard.add(aboutText);
        content.add(aboutCard);

        content.add(Box.createVerticalStrut(32));
        content.add(sectionHeading("Project Goals & Impact",
                "The objectives guiding our work in the community."));
        content.add(Box.createVerticalStrut(14));

        JPanel goalsGrid = new JPanel(new GridLayout(0, 3, 16, 16));
        goalsGrid.setOpaque(false);
        goalsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        goalsGrid.add(createInfoCard("01", "Distribute Food to Those in Need",
                "Provide food parcels and hot meals to over 50000 people in the community."));
        goalsGrid.add(createInfoCard("02", "Promote Community Support",
                "Encourage local volunteerism and participation in food sharing efforts."));
        goalsGrid.add(createInfoCard("03", "Build Strong Partnerships",
                "Work with local food banks, shelters, and community centers to sustain the initiative."));
        goalsGrid.add(createInfoCard("04", "Gather Feedback",
                "Improve services based on community and recipient feedback."));
        goalsGrid.add(createInfoCard("05", "Inspire Ongoing Support",
                "Cultivate a caring community that actively supports food security."));

        content.add(goalsGrid);
        content.add(Box.createVerticalStrut(32));

        content.add(sectionHeading("Project Progress",
                "An overview of planning, current activities, impact, and next steps."));
        content.add(Box.createVerticalStrut(14));

        JPanel progressGrid = new JPanel(new GridLayout(0, 2, 16, 16));
        progressGrid.setOpaque(false);
        progressGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        progressGrid.add(createInfoCard("01", "Planning",
                "Partner organizations identified.\n\nFood sourcing and logistics planned."));
        progressGrid.add(createInfoCard("02", "Activities",
                "Parcel distribution to families and individuals.\n\nRunning soup kitchens to serve hot meals to those in need."));
        progressGrid.add(createInfoCard("03", "Impact",
                "Positive feedback from recipients and increased community awareness."));
        progressGrid.add(createInfoCard("04", "Next Steps",
                "Expand food distribution efforts, increase volunteer participation, and continue gathering community feedback to improve services."));

        content.add(progressGrid);
        content.add(Box.createVerticalStrut(28));

        btnBack = new JButton("Back");
        ProjectPageStyle.styleBackButton(btnBack);
        btnBack.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnBack.addActionListener(this);
        content.add(btnBack);

        // Scroll pane: content can extend beyond the visible window
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane.getViewport().setBackground(BACKGROUND);
        page.add(scrollPane, BorderLayout.CENTER);

        // Footer
        page.add(ProjectPageStyle.createFooter(), BorderLayout.SOUTH);

        setContentPane(page);
    }

    private JPanel sectionHeading(String title, String subtitle) {
        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADING_FONT);
        titleLabel.setForeground(DARK);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(BODY_FONT);
        subtitleLabel.setForeground(MUTED);

        heading.add(titleLabel);
        heading.add(Box.createVerticalStrut(6));
        heading.add(subtitleLabel);
        heading.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        return heading;
    }

    private JPanel createCard() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(226, 232, 238), 1, true),
                new EmptyBorder(22, 24, 22, 24)));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private JPanel createInfoCard(String number, String title, String description) {
        JPanel card = createCard();

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 12));
        numberLabel.setForeground(PRIMARY);
        numberLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JLabel titleLabel = new JLabel("<html><div style='width:150px;'>" + title + "</div></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(DARK);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descriptionLabel = wrappedLabel(description.replace("\n", "<br>"), 150);
        descriptionLabel.setForeground(MUTED);
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(numberLabel);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(descriptionLabel);
        return card;
    }

    private JLabel wrappedLabel(String text, int width) {
        JLabel label = new JLabel("<html><div style='width:" + width + "px; line-height:1.5;'>"
                + text + "</div></html>");
        label.setFont(BODY_FONT);
        label.setForeground(MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

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
                new ShareAndServeFood().setVisible(true);
            }
        });
    }
}
