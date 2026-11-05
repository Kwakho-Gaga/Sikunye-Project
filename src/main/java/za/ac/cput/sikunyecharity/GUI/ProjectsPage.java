package za.ac.cput.sikunyecharity.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ProjectsPage extends JFrame implements ActionListener {

    private static final Color CYAN = new Color(31, 190, 221);
    private static final Color HERO_BLUE = new Color(111, 204, 229);
    private static final Color PAGE_BACKGROUND = new Color(248, 244, 250);
    private static final Color TEXT = new Color(24, 24, 24);
    private BufferedImage referenceImage;
    private JButton btnSchoolProject;
    private JButton btnFoodProject;
    private JButton btnCareProject;
    private JButton btnBack;

    public ProjectsPage() {
        super("Sikunye Charity - Projects");
        loadReferenceImage();

        JPanel page = new PagePanel();
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));
        page.setBackground(PAGE_BACKGROUND);
        page.add(createNavigation());
        page.add(createHero());
        page.add(createProjectsSection());
        page.add(createFooter());

        JScrollPane scrollPane = new JScrollPane(page);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        setContentPane(scrollPane);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(980, 700));
        setSize(1180, 900);
        setLocationRelativeTo(null);
    }

    private JPanel createNavigation() {
        JPanel navigation = new JPanel(new GridBagLayout());
        navigation.setBackground(Color.WHITE);
        navigation.setBorder(new EmptyBorder(0, 70, 0, 35));
        navigation.setPreferredSize(new Dimension(1180, 76));
        navigation.setMinimumSize(new Dimension(900, 76));
        navigation.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 1;
        c.anchor = GridBagConstraints.WEST;
        JLabel brand = new JLabel("Sikunye Charity");
        brand.setFont(new Font("SansSerif", Font.BOLD, 17));
        c.gridx = 0;
        c.weightx = 1;
        navigation.add(brand, c);

        JPanel links = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        links.setOpaque(false);
        String[] items = {"Home", "Projects", "Beneficiaries", "Donor",
            "ItemsDonated", "DonationsRecieved", "Contact"};

        for (String item : items) {
            JLabel link = new JLabel(item);
            link.setFont(new Font("SansSerif", Font.PLAIN, 16));
            
            // Highlight Projects as active page tab
            boolean isActive = "Projects".equalsIgnoreCase(item);
            link.setForeground(isActive ? new Color(65, 186, 207) : TEXT);
            if (isActive) {
                link.setFont(new Font("SansSerif", Font.BOLD, 16));
            }
            
            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            // Connect ALL navigation links to AppNavigation
            AppNavigation.link(link, this, item);
            links.add(link);
        }

        c.gridx = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.EAST;
        navigation.add(links, c);
        return navigation;
    }

    private JPanel createHero() {
        JPanel hero = new JPanel();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBackground(HERO_BLUE);
        hero.setBorder(new EmptyBorder(11, 20, 10, 20));
        hero.setPreferredSize(new Dimension(1180, 70));
        hero.setMinimumSize(new Dimension(900, 70));
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        hero.add(centeredLabel("Our Projects", new Font("SansSerif", Font.BOLD, 20), Color.WHITE));
        hero.add(Box.createVerticalStrut(7));
        hero.add(centeredLabel("Discover the meaningful projects our NGO is dedicated to, creating a positive impact in communities.",
                new Font("SansSerif", Font.PLAIN, 12), Color.WHITE));
        return hero;
    }

    private JPanel createProjectsSection() {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(PAGE_BACKGROUND);
        section.setBorder(new EmptyBorder(13, 30, 25, 30));
        section.setPreferredSize(new Dimension(1180, 535));
        section.setMinimumSize(new Dimension(900, 535));
        section.setMaximumSize(new Dimension(Integer.MAX_VALUE, 535));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setOpaque(false);
        toolbar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        JLabel overview = new JLabel("Projects Overview");
        overview.setFont(new Font("SansSerif", Font.BOLD, 17));
        toolbar.add(overview, BorderLayout.WEST);
        toolbar.add(createSearchField(), BorderLayout.EAST);
        section.add(toolbar);
        section.add(Box.createVerticalStrut(9));

        JPanel cards = new JPanel(new GridLayout(1, 3, 54, 0));
        cards.setOpaque(false);
        cards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 380));
        cards.add(createProjectColumn("School Supply Drive", 0,
                "Providing essential school supplies to help students in need achieve their academic goals.",
                "20 December 2020-20 January 2027", "Mr Gime", "In progress", 58));
        cards.add(createProjectColumn("Share and Serve Food", 1,
                "Provides underserved families with meals, helping to fight hunger and build stronger communities.",
                "20 January 2024-08 January 2029", "Mr Gime", "In Progress", 44));
        cards.add(createProjectColumn("Community Care Connect", 2,
                "A community-led initiative to provide accessible healthcare services and resources to vulnerable populations, bridging the gap in healthcare for underserved communities.",
                "02 July 2026-10 January 2028", "Mr Gime", "Planned", 36));
        section.add(cards);
        section.add(Box.createVerticalStrut(22));

        JPanel backRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        backRow.setOpaque(false);
        btnBack = new FlatButton("Back", 118, 36, 0);
        btnBack.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnBack.addActionListener(this);
        backRow.add(btnBack);
        section.add(backRow);
        return section;
    }

    private JPanel createSearchField() {
        JPanel search = new RoundedPanel(18, Color.WHITE);
        search.setLayout(new BorderLayout(6, 0));
        search.setBorder(new EmptyBorder(4, 15, 4, 12));
        search.setPreferredSize(new Dimension(250, 30));
        JTextField field = new JTextField("search projects");
        field.setFont(new Font("SansSerif", Font.PLAIN, 12));
        field.setForeground(new Color(205, 205, 205));
        field.setBorder(null);
        field.setOpaque(false);
        field.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                if ("search projects".equals(field.getText())) {
                    field.setText("");
                    field.setForeground(TEXT);
                }
            }
            @Override public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    field.setText("search projects");
                    field.setForeground(new Color(205, 205, 205));
                }
            }
        });
        JLabel icon = new JLabel("\u2315");
        icon.setFont(new Font("SansSerif", Font.BOLD, 16));
        search.add(field, BorderLayout.CENTER);
        search.add(icon, BorderLayout.EAST);
        return search;
    }

    private JPanel createProjectColumn(String title, int imageNumber, String description,
            String timeline, String owner, String status, int progress) {
        JPanel column = new JPanel();
        column.setOpaque(false);
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(12, 20, 14, 20));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel cardTitle = centeredLabel(title, new Font("SansSerif", Font.BOLD, 14), CYAN);
        cardTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));
        card.add(cardTitle);
        card.add(Box.createVerticalStrut(17));
        JLabel picture = new JLabel();
        picture.setAlignmentX(Component.CENTER_ALIGNMENT);
        picture.setHorizontalAlignment(SwingConstants.CENTER);
        picture.setPreferredSize(new Dimension(240, 132));
        picture.setMinimumSize(new Dimension(180, 110));
        picture.setMaximumSize(new Dimension(Integer.MAX_VALUE, 132));
        Image projectImage = getProjectImage(imageNumber);
        if (projectImage != null) {
            picture.setIcon(new ImageIcon(projectImage.getScaledInstance(240, 132, Image.SCALE_SMOOTH)));
        } else {
            picture.setOpaque(true);
            picture.setBackground(new Color(225, 225, 225));
            picture.setText("Project image");
        }
        card.add(picture);
        card.add(Box.createVerticalStrut(7));

        JLabel desc = new JLabel(html(description, 270));
        desc.setFont(new Font("SansSerif", Font.PLAIN, 10));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(desc);
        card.add(Box.createVerticalStrut(8));
        card.add(detailLabel("Timeline:", timeline));
        card.add(Box.createVerticalStrut(8));
        card.add(detailLabel("Owner:", owner));
        card.add(Box.createVerticalStrut(8));
        card.add(detailLabel("Status:", status));
        card.add(Box.createVerticalStrut(8));

        JPanel progressRow = new JPanel(new BorderLayout(8, 0));
        progressRow.setOpaque(false);
        progressRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel progressLabel = new JLabel("Progress:");
        progressLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        progressRow.add(progressLabel, BorderLayout.WEST);
        progressRow.add(new ProgressBar(progress), BorderLayout.CENTER);
        progressRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 16));
        card.add(progressRow);

        column.add(card);
        column.add(Box.createVerticalStrut(10));
        JButton btnViewProject = new FlatButton("View project  \u279C", 105, 25, 18);
        btnViewProject.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnViewProject.addActionListener(this);
        if (imageNumber == 0) {
            btnSchoolProject = btnViewProject;
        } else if (imageNumber == 1) {
            btnFoodProject = btnViewProject;
        } else {
            btnCareProject = btnViewProject;
        }
        column.add(btnViewProject);
        return column;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnSchoolProject) {
            openProject(0);
        } else if (source == btnFoodProject) {
            openProject(1);
        } else if (source == btnCareProject) {
            openProject(2);
        } else if (source == btnBack) {
            AppNavigation.open(this, "Home");
        }
    }

    private void openProject(int projectNumber) {
        JFrame detailPage;
        switch (projectNumber) {
            case 0:
                detailPage = new SchoolSupplyDrive(this);
                break;
            case 1:
                detailPage = new ShareAndServeFood(this);
                break;
            case 2:
                detailPage = new CommunityCareConnect(this);
                break;
            default:
                throw new IllegalArgumentException("Unknown project: " + projectNumber);
        }
        setVisible(false);
        detailPage.setVisible(true);
    }

    private JPanel createFooter() {
        return new FooterPanel(this);
    }

    private JLabel detailLabel(String name, String value) {
        JLabel label = new JLabel("<html>" + name + value + "</html>");
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel centeredLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private String html(String text, int width) {
        return "<html><div style='width:" + width + "px'>" + text + "</div></html>";
    }

    private void loadReferenceImage() {
        try (InputStream stream = ProjectsPage.class.getResourceAsStream("/images/projects-reference.jpg")) {
            if (stream != null) referenceImage = ImageIO.read(stream);
        } catch (IOException exception) {
            referenceImage = null;
        }
    }

    private Image getProjectImage(int imageNumber) {
        if (referenceImage == null) return null;
        int[][] crops = {{61, 259, 237, 132}, {426, 258, 240, 133}, {835, 264, 175, 116}};
        int[] crop = crops[imageNumber];
        return referenceImage.getSubimage(crop[0], crop[1], crop[2], crop[3]);
    }

    private static final class RoundedPanel extends JPanel {
        private final int arc;
        private final Color fill;
        RoundedPanel(int arc, Color fill) { this.arc = arc; this.fill = fill; setOpaque(false); }
        @Override protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(fill);
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g.setColor(new Color(215, 215, 215));
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static final class PagePanel extends JPanel implements Scrollable {
        @Override public Dimension getPreferredScrollableViewportSize() { return getPreferredSize(); }
        @Override public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) { return 18; }
        @Override public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) { return 120; }
        @Override public boolean getScrollableTracksViewportWidth() { return true; }
        @Override public boolean getScrollableTracksViewportHeight() { return false; }
    }

    private static final class FlatButton extends JButton {
        private final int arc;
        FlatButton(String text, int width, int height, int arc) {
            super(text);
            this.arc = arc;
            setPreferredSize(new Dimension(width, height));
            setMaximumSize(new Dimension(width, height));
            setMinimumSize(new Dimension(width, height));
            setFont(new Font("SansSerif", Font.PLAIN, 11));
            setForeground(Color.WHITE);
            setBackground(CYAN);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        @Override protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(getModel().isRollover() ? new Color(18, 169, 201) : getBackground());
            g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static final class ProgressBar extends JPanel {
        private final int progress;
        ProgressBar(int progress) {
            this.progress = progress;
            setOpaque(false);
            setPreferredSize(new Dimension(170, 12));
            setMinimumSize(new Dimension(80, 12));
        }
        @Override protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int y = (getHeight() - 8) / 2;
            g.setColor(new Color(215, 215, 215));
            g.fillRoundRect(0, y, getWidth(), 8, 8, 8);
            g.setColor(CYAN);
            g.fillRoundRect(0, y, Math.max(8, getWidth() * progress / 100), 8, 8, 8);
            g.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProjectsPage().setVisible(true));
    }
}