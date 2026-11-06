package za.ac.cput.sikunyecharity.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame; // <-- FIX 1: you were missing this
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HeaderPanel extends JPanel {

    private static final Color CYAN_ACTIVE = new Color(65, 186, 207);
    private static final Color TEXT_DARK = new Color(24, 24, 24);

    private static final Font BRAND_FONT = new Font("SansSerif", Font.BOLD, 17);
    private static final Font NAV_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font ACTIVE_NAV_FONT = new Font("SansSerif", Font.BOLD, 16);

    // Use this constructor when you are inside a JPanel like DonationGUI
    public HeaderPanel(String activePage) {
        this(null, activePage);
    }

    public HeaderPanel(JFrame currentFrame, String activePage) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(0, 70, 0, 35));
        setPreferredSize(new Dimension(1180, 76));
        setMinimumSize(new Dimension(900, 76));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 1;

        c.gridx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;

        JLabel brand = new JLabel("Sikunye Charity");
        brand.setFont(BRAND_FONT);
        brand.setForeground(TEXT_DARK);
        add(brand, c);

        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        linksPanel.setOpaque(false);

        String[] displayNames = {
            "Home",
            "Projects",
            "Beneficiaries",
            "Donor",
            "Items Donated",
            "Donations Received",
            "Contact"
        };

        for (String displayName : displayNames) {
            JLabel link = new JLabel(displayName);
            boolean isActive = isActivePage(activePage, displayName);

            if (isActive) {
                link.setFont(ACTIVE_NAV_FONT);
                link.setForeground(CYAN_ACTIVE);
            } else {
                link.setFont(NAV_FONT);
                link.setForeground(TEXT_DARK);
            }

            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // FIX 2: Don't pass currentFrame, let AppNavigation find the window itself
            AppNavigation.link(link, displayName);

            linksPanel.add(link);
        }

        c.gridx = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.EAST;
        add(linksPanel, c);
    }

    private boolean isActivePage(String activePage, String destination) {
        if (activePage == null || destination == null) return false;
        String active = activePage.replace(" ", "").toLowerCase();
        String dest = destination.replace(" ", "").toLowerCase();
        active = active.replace("recieved", "received");
        dest = dest.replace("recieved", "received");
        return active.equals(dest);
    }
}
