package za.ac.cput.sikunyecharity.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class FooterPanel extends JPanel {

    private static final Color FOOTER_BG = new Color(43, 55, 63);
    private static final Color CYAN_ACCENT = new Color(65, 186, 207);
    private static final Color BORDER_COLOR = new Color(142, 142, 147);
    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font TEXT_FONT = new Font("SansSerif", Font.PLAIN, 14);
    private static final Font BOLD_FONT = new Font("SansSerif", Font.BOLD, 14);

    public FooterPanel() {
        this(null);
    }

    public FooterPanel(JFrame currentFrame) {
        setLayout(new BorderLayout());
        setBackground(FOOTER_BG);

        // 3-Column Footer Content
        JPanel topFooter = new JPanel(new GridLayout(1, 3, 20, 0));
        topFooter.setOpaque(false);
        topFooter.setBorder(new EmptyBorder(20, 50, 20, 50));

        // Column 1: Organization Branding
        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        col1.setOpaque(false);

        JLabel lblName = new JLabel("Sikunye Charity");
        lblName.setFont(TITLE_FONT);
        lblName.setForeground(Color.WHITE);

        JLabel lblSlogan = new JLabel("Together we are one");
        lblSlogan.setFont(BOLD_FONT);
        lblSlogan.setForeground(CYAN_ACCENT);

        col1.add(lblName);
        col1.add(Box.createRigidArea(new Dimension(0, 8)));
        col1.add(lblSlogan);

        // Column 2: Quick Links
        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        col2.setOpaque(false);

        JLabel lblQuick = new JLabel("Quick Links");
        lblQuick.setFont(TITLE_FONT);
        lblQuick.setForeground(Color.WHITE);
        col2.add(lblQuick);
        col2.add(Box.createRigidArea(new Dimension(0, 8)));

        String[] links = {"Home", "Projects", "Beneficiaries", "ItemsDonated", "Contact"};
        for (String linkName : links) {
            JLabel lblLink = new JLabel(linkName);
            lblLink.setFont(TEXT_FONT);
            lblLink.setForeground(Color.WHITE);
            lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (currentFrame != null) {
                AppNavigation.link(lblLink, currentFrame, linkName);
            }
            col2.add(lblLink);
            col2.add(Box.createRigidArea(new Dimension(0, 4)));
        }

        // Column 3: Contact Details
        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        col3.setOpaque(false);

        JLabel lblContactHeader = new JLabel("Contact Us");
        lblContactHeader.setFont(TITLE_FONT);
        lblContactHeader.setForeground(Color.WHITE);
        col3.add(lblContactHeader);
        col3.add(Box.createRigidArea(new Dimension(0, 8)));

        JLabel lblEmail = new JLabel("info@sikunyecharity.org.za");
        lblEmail.setFont(TEXT_FONT);
        lblEmail.setForeground(Color.WHITE);

        JLabel lblPhone = new JLabel("+27 21 123 4567");
        lblPhone.setFont(TEXT_FONT);
        lblPhone.setForeground(Color.WHITE);

        col3.add(lblEmail);
        col3.add(Box.createRigidArea(new Dimension(0, 4)));
        col3.add(lblPhone);

        topFooter.add(col1);
        topFooter.add(col2);
        topFooter.add(col3);

        // Bottom Copyright Strip
        JPanel bottomStrip = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomStrip.setOpaque(true);
        bottomStrip.setBackground(new Color(30, 40, 46));
        bottomStrip.setBorder(new LineBorder(BORDER_COLOR, 1));

        JLabel lblRights = new JLabel("© SikunyeCharity. All rights reserved");
        lblRights.setFont(TEXT_FONT);
        lblRights.setForeground(Color.WHITE);
        bottomStrip.add(lblRights);

        add(topFooter, BorderLayout.CENTER);
        add(bottomStrip, BorderLayout.SOUTH);
    }
}