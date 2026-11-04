package za.ac.cput.sikunyecharity.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class HeaderPanel extends JPanel {

    private static final Color CYAN_ACTIVE = new Color(65, 186, 207);
    private static final Color TEXT_DARK = new Color(24, 24, 24);
    private static final Font BRAND_FONT = new Font("SansSerif", Font.BOLD, 17);
    private static final Font NAV_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font ACTIVE_NAV_FONT = new Font("SansSerif", Font.BOLD, 16);

    public HeaderPanel(JFrame currentFrame, String activePage) {
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(0, 70, 0, 35));
        setPreferredSize(new Dimension(1180, 76));
        setMinimumSize(new Dimension(900, 76));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weighty = 1;

        // Brand Title
        c.gridx = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        JLabel brand = new JLabel("Sikunye Charity");
        brand.setFont(BRAND_FONT);
        brand.setForeground(TEXT_DARK);
        add(brand, c);

        // Right Navigation Links
        JPanel linksPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        linksPanel.setOpaque(false);

        String[] items = {"Home", "Projects", "Beneficiaries", "Donor", "ItemsDonated", "DonationsRecieved", "Contact"};

        for (String item : items) {
            JLabel link = new JLabel(item);

            boolean isActive = item.equalsIgnoreCase(activePage) 
                    || (activePage.equalsIgnoreCase("Items Donated") && item.equalsIgnoreCase("ItemsDonated"));

            if (isActive) {
                link.setFont(ACTIVE_NAV_FONT);
                link.setForeground(CYAN_ACTIVE);
            } else {
                link.setFont(NAV_FONT);
                link.setForeground(TEXT_DARK);
            }

            link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Attach navigation event handler
            AppNavigation.link(link, currentFrame, item);

            linksPanel.add(link);
        }

        c.gridx = 1;
        c.weightx = 0;
        c.anchor = GridBagConstraints.EAST;
        add(linksPanel, c);
    }
}