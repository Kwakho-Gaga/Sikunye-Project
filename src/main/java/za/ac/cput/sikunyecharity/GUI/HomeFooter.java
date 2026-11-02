package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/** The HomeGUI footer design shared with Beneficiaries. */
final class HomeFooter {

    private static final Color DARK = new Color(43, 55, 63);
    private static final Font HEADING = new Font("Inter", Font.BOLD, 20);
    private static final Font LINK = new Font("Inter", Font.BOLD, 15);

    private HomeFooter() {
    }

    static JPanel create(JFrame current) {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(DARK);
        footer.setPreferredSize(new Dimension(1180, 275));
        footer.setMinimumSize(new Dimension(900, 275));
        footer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 275));

        JPanel columns = new JPanel(new GridLayout(1, 3, 10, 0));
        columns.setBackground(DARK);
        columns.setBorder(new EmptyBorder(10, 20, 10, 20));
        columns.add(brandColumn());
        columns.add(quickLinks(current));
        columns.add(socialColumn());
        footer.add(columns, BorderLayout.CENTER);

        JLabel copyright = new JLabel("© 2020 SikunyeCharity. All rights reserved",
                JLabel.CENTER);
        copyright.setFont(new Font("Inter", Font.BOLD, 14));
        copyright.setForeground(Color.WHITE);
        copyright.setBorder(BorderFactory.createLineBorder(new Color(142, 142, 147)));
        copyright.setPreferredSize(new Dimension(100, 38));
        footer.add(copyright, BorderLayout.SOUTH);
        return footer;
    }

    private static JPanel brandColumn() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        JLabel name = new JLabel("Sikunye Charity");
        name.setFont(HEADING);
        name.setForeground(Color.WHITE);
        panel.add(name);
        panel.add(Box.createRigidArea(new Dimension(20, 20)));
        JLabel slogan = new JLabel("Together we are one");
        slogan.setFont(new Font("Inter", Font.BOLD, 16));
        slogan.setForeground(Color.WHITE);
        panel.add(slogan);
        return panel;
    }

    private static JPanel quickLinks(JFrame current) {
        JPanel panel = new JPanel(new GridLayout(9, 1));
        panel.setOpaque(false);
        addText(panel, "Quick Links", HEADING, null, current);
        addText(panel, "Home", LINK, "Home", current);
        addText(panel, "ItemsDonated", LINK, null, current);
        addText(panel, "Projects", LINK, "Projects", current);
        addText(panel, "Beneficiaries", LINK, "Beneficiaries", current);
        addText(panel, "Donor", LINK, null, current);
        addText(panel, "DonationsRecieved", LINK, null, current);
        addText(panel, "Contact", LINK, "Contact", current);
        addText(panel, "Feedback", LINK, "Feedback", current);
        return panel;
    }

    private static void addText(JPanel panel, String text, Font font,
            String destination, JFrame current) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        if (current != null && destination != null) {
            AppNavigation.link(label, current, destination);
        }
        panel.add(label);
    }

    private static JPanel socialColumn() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel heading = new JLabel("Follow us");
        heading.setFont(HEADING);
        heading.setForeground(Color.WHITE);
        panel.add(heading);
        JPanel icons = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        icons.setOpaque(false);
        addIcon(icons, "/Facebook.png");
        addIcon(icons, "/Instagram.png");
        addIcon(icons, "/Linkedin.png");
        panel.add(icons);
        return panel;
    }

    private static void addIcon(JPanel panel, String resource) {
        URL url = HomeFooter.class.getResource(resource);
        if (url != null) {
            panel.add(new JLabel(new ImageIcon(url)));
        }
    }
}
