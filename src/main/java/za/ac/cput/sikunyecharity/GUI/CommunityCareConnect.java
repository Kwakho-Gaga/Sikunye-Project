/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CommunityCareConnect extends JFrame implements ActionListener {

    private final ProjectsPage projectsPage;
    private JButton btnBack;

    public CommunityCareConnect() {
        this(null);
    }

    public CommunityCareConnect(ProjectsPage projectsPage) {
        this.projectsPage = projectsPage;
        addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent e) {
                if (CommunityCareConnect.this.projectsPage != null
                        && CommunityCareConnect.this.projectsPage.isDisplayable()) {
                    CommunityCareConnect.this.projectsPage.setVisible(true);
                }
            }
        });

        setTitle("Sikunye Charity - Community Care Connect");
        setSize(1000, 850);
        setMinimumSize(new Dimension(780, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Main panel

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(ProjectPageStyle.BACKGROUND);

        // Project header

        JPanel header = new JPanel();
        header.setBackground(ProjectPageStyle.PRIMARY);
        header.setPreferredSize(new Dimension(100, 100));

        header.setLayout(new GridLayout(2, 1));

        JLabel title = new JLabel(
                "Community Care Connect",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "<html><div style='width:620px; text-align:center;'>"
                + "A community-led initiative to provide accessible healthcare services and resources to underserved communities."
                + "</div></html>", SwingConstants.CENTER
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);

        header.add(title);
        header.add(subtitle);

        mainPanel.add(header, BorderLayout.NORTH);

        // Content panel

        JPanel content = new ProjectPageStyle.ScrollableContent();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ProjectPageStyle.BACKGROUND);

        content.setBorder(new EmptyBorder(32, 45, 38, 45));

        // About the project

        JLabel aboutTitle = new JLabel("About the Project");
        aboutTitle.setFont(ProjectPageStyle.SECTION_FONT);
        aboutTitle.setForeground(ProjectPageStyle.DARK);
        aboutTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(aboutTitle);

        content.add(Box.createVerticalStrut(20));

        JTextArea aboutText = ProjectPageStyle.createParagraph(
                        "Community Care Connect is a program dedicated to strengthening community support "
                        + "systems through outreach, engagement, and resource sharing. The project aims to build "
                        + "connections among community members, local organizations, and service providers to "
                        + "address social, emotional, and health needs.", 6
        );

        content.add(aboutText);

        content.add(Box.createVerticalStrut(40));

        // Programmes section

        JLabel programmesTitle = new JLabel("Programmes");
        programmesTitle.setFont(
                ProjectPageStyle.SECTION_FONT
        );

        programmesTitle.setForeground(
                ProjectPageStyle.DARK
        );

        programmesTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(programmesTitle);

        content.add(Box.createVerticalStrut(25));

        // Programme container

        JPanel programmesPanel = new JPanel();
        programmesPanel.setBackground(
                ProjectPageStyle.BACKGROUND
        );

        programmesPanel.setLayout(
                new GridLayout(2, 2, 25, 25)
        );

        programmesPanel.setBorder(
                new EmptyBorder(25, 25, 25, 25)
        );

        // Programme 1

        JPanel outreachPanel = new JPanel();
        outreachPanel.setBackground(Color.WHITE);

        outreachPanel.setLayout(
                new BoxLayout(
                        outreachPanel,
                        BoxLayout.Y_AXIS
                )
        );

        outreachPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel outreachTitle = new JLabel(
                "Engagement & Outreach"
        );

        outreachTitle.setFont(new Font("Arial", Font.BOLD, 16));
        outreachTitle.setForeground(ProjectPageStyle.DARK);

        outreachTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel outreachDescription = wrappedLabel(
                "Building relationships within the community to "
                        + "identify needs and foster collaboration."
        );

        JLabel outreach1 = wrappedLabel(
                "Community dialogues"
        );

        JLabel outreach2 = wrappedLabel(
                "Home visits (planned)"
        );

        JLabel outreach3 = wrappedLabel(
                "Focus groups (planned)"
        );

        outreachPanel.add(outreachTitle);
        outreachPanel.add(Box.createVerticalStrut(15));
        outreachPanel.add(outreachDescription);
        outreachPanel.add(Box.createVerticalStrut(10));
        outreachPanel.add(outreach1);
        outreachPanel.add(Box.createVerticalStrut(8));
        outreachPanel.add(outreach2);
        outreachPanel.add(Box.createVerticalStrut(8));
        outreachPanel.add(outreach3);

        // Programme 2

        JPanel supportPanel = new JPanel();

        supportPanel.setBackground(Color.WHITE);

        supportPanel.setLayout(
                new BoxLayout(
                        supportPanel,
                        BoxLayout.Y_AXIS
                )
        );

        supportPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel supportTitle = new JLabel(
                "Support & Resources"
        );

        supportTitle.setFont(new Font("Arial", Font.BOLD, 16));
        supportTitle.setForeground(ProjectPageStyle.DARK);

        supportTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel supportDescription = wrappedLabel(
                "Connecting community members to services "
                        + "and support networks."
        );

        JLabel support1 = wrappedLabel(
                "Referrals to health and social services (planned)"
        );

        JLabel support2 = wrappedLabel(
                "Support groups and community meetings (planned)"
        );

        supportPanel.add(supportTitle);
        supportPanel.add(Box.createVerticalStrut(15));
        supportPanel.add(supportDescription);
        supportPanel.add(Box.createVerticalStrut(15));
        supportPanel.add(support1);
        supportPanel.add(Box.createVerticalStrut(10));
        supportPanel.add(support2);

        // Programme 3

        JPanel psychoPanel = new JPanel();

        psychoPanel.setBackground(Color.WHITE);

        psychoPanel.setLayout(
                new BoxLayout(
                        psychoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        psychoPanel.setBorder(
                new EmptyBorder(15, 20, 15, 20)
        );

        JLabel psychoTitle = new JLabel("Psycho");

        psychoTitle.setFont(new Font("Arial", Font.BOLD, 16));
        psychoTitle.setForeground(ProjectPageStyle.DARK);

        psychoTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel psychoDescription = wrappedLabel(
                "Focusing on mental and emotional health to help "
                        + "individuals understand and manage their feelings "
                        + "and personality."
        );

        JLabel psycho1 = wrappedLabel(
                "Counselling (individual and group)"
        );

        JLabel psycho2 = wrappedLabel(
                "Support groups"
        );

        JLabel psycho3 = wrappedLabel(
                "Life Skills through football"
        );

        psychoPanel.add(psychoTitle);
        psychoPanel.add(Box.createVerticalStrut(10));
        psychoPanel.add(psychoDescription);
        psychoPanel.add(Box.createVerticalStrut(10));
        psychoPanel.add(psycho1);
        psychoPanel.add(Box.createVerticalStrut(8));
        psychoPanel.add(psycho2);
        psychoPanel.add(Box.createVerticalStrut(8));
        psychoPanel.add(psycho3);

        // Empty fourth space
        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        programmesPanel.add(outreachPanel);
        programmesPanel.add(supportPanel);
        programmesPanel.add(psychoPanel);
        programmesPanel.add(emptyPanel);

        content.add(programmesPanel);

        content.add(Box.createVerticalStrut(40));

        // Project progress

        JLabel progressTitle = new JLabel(
                "Project Progress"
        );

        progressTitle.setFont(
                ProjectPageStyle.SECTION_FONT
        );

        progressTitle.setForeground(
                ProjectPageStyle.DARK
        );

        progressTitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        content.add(progressTitle);

        content.add(Box.createVerticalStrut(25));

        JPanel progressPanel = new JPanel();

        progressPanel.setBackground(
                ProjectPageStyle.BACKGROUND
        );

        progressPanel.setLayout(
                new GridLayout(2, 2, 25, 25)
        );

        // Planning

        JPanel planningPanel = new JPanel();

        planningPanel.setBackground(Color.WHITE);

        planningPanel.setLayout(
                new BoxLayout(
                        planningPanel,
                        BoxLayout.Y_AXIS
                )
        );

        planningPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel planningTitle = new JLabel("Planning");

        planningTitle.setFont(new Font("Arial", Font.BOLD, 16));
        planningTitle.setForeground(ProjectPageStyle.DARK);

        planningTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel planning1 = wrappedLabel(
                "Community mapping and needs assessment underway."
        );

        JLabel planning2 = wrappedLabel(
                "Building partnerships with local organizations and stakeholders."
        );

        JLabel planning3 = wrappedLabel(
                "Developing outreach and engagement strategies."
        );

        planningPanel.add(planningTitle);
        planningPanel.add(Box.createVerticalStrut(20));
        planningPanel.add(planning1);
        planningPanel.add(Box.createVerticalStrut(15));
        planningPanel.add(planning2);
        planningPanel.add(Box.createVerticalStrut(15));
        planningPanel.add(planning3);

        // Current status

        JPanel statusPanel = new JPanel();

        statusPanel.setBackground(Color.WHITE);

        statusPanel.setLayout(
                new BoxLayout(
                        statusPanel,
                        BoxLayout.Y_AXIS
                )
        );

        statusPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel statusTitle = new JLabel(
                "Current Status"
        );

        statusTitle.setFont(new Font("Arial", Font.BOLD, 16));
        statusTitle.setForeground(ProjectPageStyle.DARK);

        statusTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusText = wrappedLabel(
                "Less than 40% complete<br><br>"
                        + "Actively in planning and development."
        );

        statusPanel.add(statusTitle);
        statusPanel.add(Box.createVerticalStrut(20));
        statusPanel.add(statusText);

        // Next step

        JPanel nextPanel = new JPanel();

        nextPanel.setBackground(Color.WHITE);

        nextPanel.setLayout(
                new BoxLayout(
                        nextPanel,
                        BoxLayout.Y_AXIS
                )
        );

        nextPanel.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JLabel nextTitle = new JLabel(
                "Next Step"
        );

        nextTitle.setFont(new Font("Arial", Font.BOLD, 16));
        nextTitle.setForeground(ProjectPageStyle.DARK);

        nextTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nextText = wrappedLabel(
                "Finalize program design and activities.<br><br>"
                        + "Begin community outreach and awareness campaigns.<br><br>"
                        + "Start pilot activities once planning is complete."
        );

        nextPanel.add(nextTitle);
        nextPanel.add(Box.createVerticalStrut(15));
        nextPanel.add(nextText);

        JPanel emptyProgressPanel = new JPanel();
        emptyProgressPanel.setOpaque(false);

        progressPanel.add(planningPanel);
        progressPanel.add(statusPanel);
        progressPanel.add(nextPanel);
        progressPanel.add(emptyProgressPanel);

        content.add(progressPanel);

        content.add(Box.createVerticalStrut(30));

        // Back button

        btnBack = new JButton("Back");

        ProjectPageStyle.styleBackButton(btnBack);

        btnBack.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

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

        // Make scrolling smoother
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Remove the default scroll pane border
        scrollPane.setBorder(null);

        // Add the scroll pane to the center of the main panel
        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Footer

        mainPanel.add(ProjectPageStyle.createFooter(), BorderLayout.SOUTH);

        add(mainPanel);

    }

    private JLabel wrappedLabel(String text) {
        JLabel label = new JLabel("<html><div style='width:220px;'>" + text + "</div></html>");
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(ProjectPageStyle.MUTED);
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
                new CommunityCareConnect().setVisible(true);
            }
        });
    }
}
