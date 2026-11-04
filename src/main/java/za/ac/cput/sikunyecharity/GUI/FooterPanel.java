package za.ac.cput.sikunyecharity.GUI;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

/** Modern Universal Footer Panel for Sikunye Charity */
public class FooterPanel extends JPanel {

    private static final Color FOOTER_BG = new Color(30, 41, 51);
    private static final Color STRIP_BG = new Color(20, 28, 35);
    private static final Color CYAN_ACCENT = new Color(31, 190, 221);
    private static final Color TEXT_NORMAL = new Color(220, 225, 230);
    private static final Color TEXT_MUTED = new Color(160, 172, 184);
    private static final Color BORDER_LINE = new Color(45, 58, 70);

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Font TEXT_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font BOLD_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 12);

    public FooterPanel() {
        this(null);
    }

    public FooterPanel(JFrame currentFrame) {
        setLayout(new BorderLayout());
        setBackground(FOOTER_BG);

        // --- Top Main Content Grid (4 Columns) ---
        JPanel gridPanel = new JPanel(new GridLayout(1, 4, 30, 0));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(new EmptyBorder(35, 50, 35, 50));

        // Column 1: Organization & Mission
        JPanel col1 = createColumnPanel();

        JLabel lblName = createLeftLabel("Sikunye Charity", TITLE_FONT, Color.WHITE);
        JLabel lblSlogan = createLeftLabel("Together we are one", BOLD_FONT, CYAN_ACCENT);
        JLabel lblDesc = createLeftLabel("<html>Connecting donors with local beneficiaries through transparent and accountable donation management.</html>", SMALL_FONT, TEXT_MUTED);
        JLabel lblNpo = createLeftLabel("NPO Reg: 2020/104/001", SMALL_FONT, TEXT_MUTED);

        col1.add(lblName);
        col1.add(Box.createRigidArea(new Dimension(0, 6)));
        col1.add(lblSlogan);
        col1.add(Box.createRigidArea(new Dimension(0, 10)));
        col1.add(lblDesc);
        col1.add(Box.createRigidArea(new Dimension(0, 10)));
        col1.add(lblNpo);

        // Column 2: Quick Links
        JPanel col2 = createColumnPanel();

        JLabel lblQuick = createLeftLabel("Quick Links", TITLE_FONT, Color.WHITE);
        col2.add(lblQuick);
        col2.add(Box.createRigidArea(new Dimension(0, 12)));

        String[] linkNames = {"Home", "Projects", "Beneficiaries", "Items Donated", "Contact", "Feedback"};
        for (String linkName : linkNames) {
            JLabel lblLink = createLeftLabel(linkName, TEXT_FONT, TEXT_MUTED);
            lblLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            lblLink.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    lblLink.setForeground(CYAN_ACCENT);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    lblLink.setForeground(TEXT_MUTED);
                }
            });

            if (currentFrame != null) {
                AppNavigation.link(lblLink, currentFrame, linkName);
            }

            col2.add(lblLink);
            col2.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        // Column 3: Contact Info
        JPanel col3 = createColumnPanel();

        JLabel lblContact = createLeftLabel("Get In Touch", TITLE_FONT, Color.WHITE);
        JLabel lblEmail = createLeftLabel("✉ info@sikunyecharity.org.za", TEXT_FONT, TEXT_MUTED);
        JLabel lblPhone = createLeftLabel("☎ +27 21 123 4567", TEXT_FONT, TEXT_MUTED);
        JLabel lblAddress = createLeftLabel("📍 District Six, Cape Town 7925", TEXT_FONT, TEXT_MUTED);
        JLabel lblHours = createLeftLabel("⏰ Mon - Fri: 08:00 - 16:00", TEXT_FONT, TEXT_MUTED);

        col3.add(lblContact);
        col3.add(Box.createRigidArea(new Dimension(0, 12)));
        col3.add(lblEmail);
        col3.add(Box.createRigidArea(new Dimension(0, 6)));
        col3.add(lblPhone);
        col3.add(Box.createRigidArea(new Dimension(0, 6)));
        col3.add(lblAddress);
        col3.add(Box.createRigidArea(new Dimension(0, 6)));
        col3.add(lblHours);

        // Column 4: Follow Us & Social Media
        JPanel col4 = createColumnPanel();

        JLabel lblSocial = createLeftLabel("Follow Our Journey", TITLE_FONT, Color.WHITE);
        JLabel lblSocialSub = createLeftLabel("Stay updated on our latest community developments.", SMALL_FONT, TEXT_MUTED);

        JPanel socialRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        socialRow.setOpaque(false);
        socialRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel fbIcon = createSocialIcon("/Facebook.png", "[FB]", "Facebook");
        JLabel igIcon = createSocialIcon("/Instagram.png", "[IG]", "Instagram");
        JLabel inIcon = createSocialIcon("/Linkedin.png", "[IN]", "LinkedIn");

        socialRow.add(fbIcon);
        socialRow.add(Box.createRigidArea(new Dimension(14, 0)));
        socialRow.add(igIcon);
        socialRow.add(Box.createRigidArea(new Dimension(14, 0)));
        socialRow.add(inIcon);

        col4.add(lblSocial);
        col4.add(Box.createRigidArea(new Dimension(0, 12)));
        col4.add(lblSocialSub);
        col4.add(Box.createRigidArea(new Dimension(0, 14)));
        col4.add(socialRow);

        gridPanel.add(col1);
        gridPanel.add(col2);
        gridPanel.add(col3);
        gridPanel.add(col4);

        // --- Bottom Copyright Strip ---
        JPanel bottomStrip = new JPanel(new BorderLayout());
        bottomStrip.setOpaque(true);
        bottomStrip.setBackground(STRIP_BG);
        bottomStrip.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_LINE));
        bottomStrip.setBorder(new EmptyBorder(14, 50, 14, 50));

        JLabel lblRights = createLeftLabel("© 2026 Sikunye Charity. All rights reserved.", SMALL_FONT, TEXT_MUTED);
        JLabel lblTag = new JLabel("Empowering Communities Across South Africa");
        lblTag.setFont(SMALL_FONT);
        lblTag.setForeground(CYAN_ACCENT);

        bottomStrip.add(lblRights, BorderLayout.WEST);
        bottomStrip.add(lblTag, BorderLayout.EAST);

        add(gridPanel, BorderLayout.CENTER);
        add(bottomStrip, BorderLayout.SOUTH);
    }

    private JPanel createColumnPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentY(Component.TOP_ALIGNMENT);
        return panel;
    }

    private JLabel createLeftLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JLabel createSocialIcon(String iconPath, String fallbackText, String platformName) {
        JLabel iconLabel = new JLabel();
        iconLabel.setToolTipText("Visit Sikunye Charity on " + platformName);
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        iconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        URL imgUrl = getClass().getResource(iconPath);

        if (imgUrl != null) {
            ImageIcon rawIcon = new ImageIcon(imgUrl);
            Image scaledImage = rawIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            
            // Generate standard (White/Light Muted) and hover (Cyan) icons dynamically
            ImageIcon normalIcon = tintIcon(scaledImage, TEXT_NORMAL);
            ImageIcon cyanHoverIcon = tintIcon(scaledImage, CYAN_ACCENT);

            iconLabel.setIcon(normalIcon);

            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    iconLabel.setIcon(cyanHoverIcon);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    iconLabel.setIcon(normalIcon);
                }
            });
        } else {
            // Text fallback styling
            iconLabel.setText(fallbackText);
            iconLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            iconLabel.setForeground(TEXT_MUTED);

            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    iconLabel.setForeground(CYAN_ACCENT);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    iconLabel.setForeground(TEXT_MUTED);
                }
            });
        }

        return iconLabel;
    }

    /** Helper method to recolor transparent PNG icons to specified Color */
    private ImageIcon tintIcon(Image srcImage, Color color) {
        int w = srcImage.getWidth(null);
        int h = srcImage.getHeight(null);
        if (w <= 0 || h <= 0) return new ImageIcon(srcImage);

        BufferedImage tintedBuffer = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tintedBuffer.createGraphics();
        g2.drawImage(srcImage, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.setColor(color);
        g2.fillRect(0, 0, w, h);
        g2.dispose();

        return new ImageIcon(tintedBuffer);
    }
}