package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * Sikunye Charity - Home Page
 */
public class HomeGUI extends JFrame implements ActionListener {

    private JPanel mainContentPanel;
    private JLabel heading;
    private JLabel subHeading;
    private JLabel description;
    private JButton button;

    private static final Color CYAN_BRAND = new Color(0, 192, 232);
    private static final Color PAGE_BG = new Color(245, 245, 245);
    private static final Font FONT_HEADING = new Font("Inter", Font.BOLD, 42);
    private static final Font FONT_SUB = new Font("Inter", Font.PLAIN, 22);
    private static final Font FONT_DESC = new Font("Inter", Font.PLAIN, 16);
    private static final Font FONT_BTN = new Font("Inter", Font.BOLD, 15);

    public HomeGUI() {
        super("Sikunye Charity - Home");
        setGui();
    }

    public void setGui() {
        setLayout(new BorderLayout());

        // 1. Standardized Top Header
        add(new HeaderPanel(this, "Home"), BorderLayout.NORTH);

        // 2. Center Page Content
        mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(PAGE_BG);
        mainContentPanel.setBorder(new EmptyBorder(60, 40, 60, 40));

        heading = new JLabel("Welcome to Sikunye Charity", SwingConstants.CENTER);
        heading.setFont(FONT_HEADING);
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);

        subHeading = new JLabel("Together we are one", SwingConstants.CENTER);
        subHeading.setFont(FONT_SUB);
        subHeading.setForeground(new Color(100, 100, 100));
        subHeading.setAlignmentX(Component.CENTER_ALIGNMENT);

        description = new JLabel(
                "<html><center>The name Sikunye means together in isiXhosa, reflecting the charity's core belief that communities thrive<br>"
                + "when people work together. The organisation connects donors with beneficiaries through a transparent,<br>"
                + "efficient, and accountable donation management system. Every donation is tracked from pledge to delivery,<br>"
                + "ensuring that help reaches those who need it most.</center></html>",
                SwingConstants.CENTER
        );
        description.setFont(FONT_DESC);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        button = new JButton("Get Involved") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setBackground(CYAN_BRAND);
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFont(FONT_BTN);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(160, 42));
        button.setMaximumSize(new Dimension(160, 42));
        button.addActionListener(this);

        mainContentPanel.add(heading);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainContentPanel.add(subHeading);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        mainContentPanel.add(description);
        mainContentPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainContentPanel.add(button);

        JScrollPane scrollPane = new JScrollPane(mainContentPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        // 3. Standardized Bottom Footer
        add(new FooterPanel(this), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button) {
            AppNavigation.open(this, "Items Donated");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HomeGUI home = new HomeGUI();
            home.setVisible(true);
        });
    }
}