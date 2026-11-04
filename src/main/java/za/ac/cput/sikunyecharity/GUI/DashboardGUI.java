/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class DashboardGUI extends JPanel {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    private final Color NAVY = new Color(15, 30, 50);
    private final Color BLUE = new Color(0, 128, 170);
    private final Color LIGHT_BLUE = new Color(235, 248, 253);
    private final Color BORDER = new Color(220, 225, 230);
    private final Color TEXT = new Color(35, 45, 55);
    private final Color GREEN = new Color(40, 160, 110);
    private final Color ORANGE = new Color(245, 160, 60);
    private final Color PURPLE = new Color(135, 90, 200);

    private JLabel totalDonationsLabel;
    private JLabel completedLabel;
    private JLabel pendingLabel;
    private JLabel itemsReceivedLabel;
    private JTable donationsTable;
    private JTable itemsTable;
    private DefaultTableModel donationModel;
    private DefaultTableModel itemModel;
    private TableRowSorter<DefaultTableModel> donationSorter;
    private JTextField searchField;
    private JComboBox<String> donationTypeCombo;
    private JComboBox<String> paymentStatusCombo;

    public DashboardGUI() {

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Main dashboard content
    JPanel mainPanel = new JPanel();

    mainPanel.setLayout(new BoxLayout(
            mainPanel,
            BoxLayout.Y_AXIS
    ));

    mainPanel.setBackground(Color.WHITE);

    // Dashboard sections
    mainPanel.add(createSummaryPanel());
    mainPanel.add(createFilterPanel());
    mainPanel.add(createDonationsPanel());
    mainPanel.add(createItemsPanel());

    // Scroll the dashboard content
    JScrollPane pageScrollPane = new JScrollPane(mainPanel);

    pageScrollPane.setBorder(null);

    pageScrollPane.getVerticalScrollBar()
            .setUnitIncrement(16);

    add(pageScrollPane, BorderLayout.CENTER);
}

    private JPanel createNavigationBar() {

        JPanel panel
                = new JPanel(new BorderLayout());

        panel.setBackground(Color.WHITE);

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0, BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 20, 12, 20
                        )
                )
        );
        JLabel logo
                = new JLabel("Sikunye Charity");

        logo.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        18
                )
        );

        logo.setForeground(Color.BLACK);

        panel.add(
                logo,
                BorderLayout.WEST
        );
        JPanel navigation
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                25,
                                0
                        )
                );

        navigation.setBackground(Color.WHITE);

        String[] links = {
            "Home",
            "Projects",
            "Donor",
            "ItemsDonated",
            "Donations Received",
            "Contact"
        };

        for (String link : links) {

            JLabel navLabel
                    = new JLabel(link);

            navLabel.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            12
                    )
            );

            if (link.equals(
                    "Donations Received"
            )) {

                navLabel.setForeground(BLUE);

                navLabel.setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                12
                        )
                );

            } else {

                navLabel.setForeground(
                        Color.DARK_GRAY
                );
            }

            navigation.add(navLabel);
        }

        panel.add(
                navigation,
                BorderLayout.EAST
        );

        return panel;
    }
    

    private JPanel createSummaryPanel() {

        JPanel outer
                = new JPanel(
                        new GridLayout(
                                1,
                                4,
                                10,
                                10
                        )
                );

        outer.setBackground(Color.WHITE);

        outer.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 20, 10, 20
                )
        );
        totalDonationsLabel
                = new JLabel(
                        "<html><center>"
                        + "<font size='2'>TOTAL DONATIONS</font><br>"
                        + "<font size='5'>R 25,500</font>"
                        + "</center></html>",
                        SwingConstants.CENTER
                );
        completedLabel
                = new JLabel(
                        "<html><center>"
                        + "<font size='2'>COMPLETED</font><br>"
                        + "<font size='5'>18</font>"
                        + "</center></html>",
                        SwingConstants.CENTER
                );
        pendingLabel
                = new JLabel(
                        "<html><center>"
                        + "<font size='2'>PENDING</font><br>"
                        + "<font size='5'>4</font>"
                        + "</center></html>",
                        SwingConstants.CENTER
                );
        itemsReceivedLabel
                = new JLabel(
                        "<html><center>"
                        + "<font size='2'>ITEMS RECEIVED</font><br>"
                        + "<font size='5'>590</font>"
                        + "</center></html>",
                        SwingConstants.CENTER
                );

        outer.add(
                createSummaryCard(
                        totalDonationsLabel,
                        new Color(235, 249, 253),
                        BLUE
                )
        );

        outer.add(
                createSummaryCard(
                        completedLabel,
                        new Color(237, 250, 244),
                        GREEN
                )
        );

        outer.add(
                createSummaryCard(
                        pendingLabel,
                        new Color(255, 248, 235),
                        ORANGE
                )
        );

        outer.add(
                createSummaryCard(
                        itemsReceivedLabel,
                        new Color(247, 241, 255),
                        PURPLE
                )
        );

        return outer;
    }

    private JPanel createSummaryCard(
            JLabel label,
            Color background,
            Color foreground
    ) {

        JPanel card
                = new JPanel(
                        new BorderLayout()
                );

        card.setBackground(background);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                12, 5, 12, 5
                        )
                )
        );

        label.setForeground(foreground);

        card.add(
                label,
                BorderLayout.CENTER
        );

        return card;
    }

    private JPanel createFilterPanel() {

        JPanel outer
                = new JPanel(
                        new BorderLayout()
                );

        outer.setBackground(Color.WHITE);

        outer.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 20, 15, 20
                )
        );

        JPanel filters
                = new JPanel(
                        new GridBagLayout()
                );

        filters.setBackground(Color.WHITE);

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.insets
                = new Insets(
                        0,
                        5,
                        0,
                        5
                );

        gbc.fill
                = GridBagConstraints.HORIZONTAL;
        searchField
                = new JTextField();

        searchField.setPreferredSize(
                new Dimension(
                        300,
                        35
                )
        );

        searchField.setToolTipText(
                "Search donations..."
        );

        searchField.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                5,
                                10,
                                5,
                                10
                        )
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;

        filters.add(
                searchField,
                gbc
        );
        donationTypeCombo
                = new JComboBox<>(
                        new String[]{
                            "All Donation Types",
                            "Financial",
                            "In-Kind"
                        }
                );

        donationTypeCombo.setPreferredSize(
                new Dimension(
                        170,
                        35
                )
        );

        gbc.gridx = 1;
        gbc.weightx = 0;

        filters.add(
                donationTypeCombo,
                gbc
        );
        paymentStatusCombo
                = new JComboBox<>(
                        new String[]{
                            "All Payment Statuses",
                            "Paid",
                            "Pending"
                        }
                );

        paymentStatusCombo.setPreferredSize(
                new Dimension(
                        180,
                        35
                )
        );

        gbc.gridx = 2;

        filters.add(
                paymentStatusCombo,
                gbc
        );
        JButton addDonationButton
                = new JButton("+ Add Donation");

        addDonationButton.setPreferredSize(
                new Dimension(
                        145,
                        35
                )
        );

        addDonationButton.setBackground(
                new Color(
                        10,
                        125,
                        165
                )
        );

        addDonationButton.setForeground(
                Color.WHITE
        );

        addDonationButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        12
                )
        );
        addDonationButton.setFocusPainted(false);
        addDonationButton.setBorderPainted(false);
        addDonationButton.addActionListener(
                e -> showLoginDialog()
        );

        gbc.gridx = 3;

        filters.add(
                addDonationButton,
                gbc
        );

        outer.add(
                filters,
                BorderLayout.CENTER
        );

        searchField.getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                    @Override
                    public void insertUpdate(
                            DocumentEvent e
                    ) {
                        filterDonations();
                    }

                    @Override
                    public void removeUpdate(
                            DocumentEvent e
                    ) {
                        filterDonations();
                    }

                    @Override
                    public void changedUpdate(
                            DocumentEvent e
                    ) {
                        filterDonations();
                    }
                }
                );

        donationTypeCombo.addActionListener(
                e -> filterDonations()
        );

        paymentStatusCombo.addActionListener(
                e -> filterDonations()
        );

        return outer;
    }

    private JPanel createDonationsPanel() {

        JPanel outer
                = new JPanel(
                        new BorderLayout()
                );

        outer.setBackground(Color.WHITE);

        outer.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 20, 10, 20
                )
        );

        JPanel titlePanel
                = createSectionHeader(
                        "Donations Received",
                        "Public donation records managed by Sikunye Charity."
                );

        outer.add(
                titlePanel,
                BorderLayout.NORTH
        );

        String[] columns = {
            "DONATION ID",
            "DONOR ID",
            "PROJECT ID",
            "DONATION TYPE",
            "AMOUNT",
            "PAYMENT STATUS",
            "PAYMENT DATE",
            "RECEIPT GENERATED",
            "DATE RECEIVED",
            "ADMIN CONTROLS"
        };
        donationModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

//        try {
//            DonationDAO donationDao = new DonationDAO();
//            List<Donation> donations = donationDao.getAllDonations(); // fetch from DB
//            for (Donation d : donations) {
//                donationModel.addRow(new Object[]{
//                    d.getDonationID(),
//                    d.getDonorID(),
//                    d.getProjectID(),
//                    d.getDonationType(),
//                    d.getAmount(),
//                    d.getPaymentStatus(),
//                    d.getPaymentDate(),
//                    d.isReceiptGenerated(),
//                    d.getDateReceived(),
//                    "View | Edit"
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error loading donations: " + e.getMessage());
//        }

        donationsTable
                = new JTable(
                        donationModel
                );

        donationsTable.setRowHeight(32);

        donationsTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_ALL_COLUMNS
        );

        donationsTable.getTableHeader()
                .setBackground(NAVY);

        donationsTable.getTableHeader()
                .setForeground(Color.WHITE);

        donationsTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                10
                        )
                );

        donationsTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        donationsTable.setGridColor(
                new Color(
                        230,
                        230,
                        230
                )
        );
        donationSorter
                = new TableRowSorter<>(
                        donationModel
                );

        donationsTable.setRowSorter(
                donationSorter
        );

        donationsTable.addMouseListener(
                new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(
                    java.awt.event.MouseEvent e
            ) {

                int row
                        = donationsTable.rowAtPoint(
                                e.getPoint()
                        );

                int column
                        = donationsTable.columnAtPoint(
                                e.getPoint()
                        );

                if (row >= 0 && column == 9) {

                    showDonationDetails(row);
                }
            }
        }
        );

        JScrollPane scrollPane
                = new JScrollPane(
                        donationsTable
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        1100,
                        150
                )
        );

        outer.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return outer;
    }

    private JPanel createItemsPanel() {

        JPanel outer
                = new JPanel(
                        new BorderLayout()
                );

        outer.setBackground(Color.WHITE);

        outer.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 20, 15, 20
                )
        );

        JPanel titlePanel
                = createSectionHeader(
                        "Donated Items Inventory",
                        "Donation items linked to donation records."
                );

        outer.add(
                titlePanel,
                BorderLayout.NORTH
        );

        String[] columns = {
            "DONATION ITEM ID",
            "DONATION ID",
            "ITEM ID",
            "QUANTITY",
            "CONDITION"
        };

        itemModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

//        try {
//            DonationItemDAO itemDao = new DonationItemDAO();
//            List<DonationItem> items = itemDao.getAllDonationItems(); // fetch from DB
//            for (DonationItem i : items) {
//                itemModel.addRow(new Object[]{
//                    i.getDonationItemID(),
//                    i.getDonationID(),
//                    i.getItemID(),
//                    i.getQuantity(),
//                    i.getCondition()
//                });
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
//        }

        itemsTable
                = new JTable(
                        itemModel
                );

        itemsTable.setRowHeight(30);

        itemsTable.getTableHeader()
                .setBackground(NAVY);

        itemsTable.getTableHeader()
                .setForeground(Color.WHITE);

        itemsTable.getTableHeader()
                .setFont(
                        new Font(
                                "Arial",
                                Font.BOLD,
                                10
                        )
                );

        itemsTable.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        11
                )
        );

        itemsTable.setGridColor(
                new Color(
                        230,
                        230,
                        230
                )
        );

        JScrollPane scrollPane
                = new JScrollPane(
                        itemsTable
                );

        scrollPane.setPreferredSize(
                new Dimension(
                        1100,
                        130
                )
        );

        outer.add(
                scrollPane,
                BorderLayout.CENTER
        );

        return outer;
    }

    private JPanel createSectionHeader(
            String title,
            String description
    ) {

        JPanel panel
                = new JPanel();

        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );

        panel.setBackground(
                LIGHT_BLUE
        );

        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        190,
                                        225,
                                        238
                                )
                        ),
                        BorderFactory.createEmptyBorder(
                                8,
                                12,
                                8,
                                12
                        )
                )
        );

        JLabel titleLabel
                = new JLabel(title);

        titleLabel.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        16
                )
        );

        JLabel descriptionLabel
                = new JLabel(description);

        descriptionLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        descriptionLabel.setForeground(
                Color.DARK_GRAY
        );

        panel.add(titleLabel);

        panel.add(
                Box.createVerticalStrut(3)
        );

        panel.add(descriptionLabel);

        return panel;
    }

    private void filterDonations() {

        if (donationSorter == null) {
            return;
        }

        String search
                = searchField
                        .getText()
                        .trim()
                        .toLowerCase();

        String donationType
                = donationTypeCombo
                        .getSelectedItem()
                        .toString();

        String paymentStatus
                = paymentStatusCombo
                        .getSelectedItem()
                        .toString();

        donationSorter.setRowFilter(
                new RowFilter<
                        DefaultTableModel, Integer>() {

            @Override
            public boolean include(
                    RowFilter.Entry<
                            ? extends DefaultTableModel, ? extends Integer> entry
            ) {

                StringBuilder rowText
                        = new StringBuilder();

                for (int i = 0;
                        i < entry.getValueCount();
                        i++) {

                    rowText.append(
                            entry.getStringValue(i)
                    );

                    rowText.append(" ");
                }

                String completeRow
                        = rowText
                                .toString()
                                .toLowerCase();

                if (!search.isEmpty()
                        && !completeRow.contains(
                                search
                        )) {

                    return false;
                }

                if (!donationType.equals(
                        "All Donation Types"
                )) {

                    String type
                            = entry.getStringValue(3);

                    if (!type.equals(
                            donationType
                    )) {

                        return false;
                    }
                }

                if (!paymentStatus.equals(
                        "All Payment Statuses"
                )) {

                    String status
                            = entry.getStringValue(5);

                    if (!status.equals(
                            paymentStatus
                    )) {

                        return false;
                    }
                }

                return true;
            }
        }
        );
    }

    private void showLoginDialog() {
        if (requestAdminAccess(this)) {
            DonationGUI donationGUI = new DonationGUI();
            donationGUI.setVisible(true);
        }
    }

    public static boolean requestAdminAccess(Component parent) {

        JTextField usernameField
                = new JTextField();

        JPasswordField passwordField
                = new JPasswordField();

        Object[] fields = {
            "Username:",
            usernameField,
            "Password:",
            passwordField
        };

        int option
                = JOptionPane.showConfirmDialog(
                        parent,
                        fields,
                        "Administrator Login",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

        if (option != JOptionPane.OK_OPTION) {
            return false;
        }

        String username
                = usernameField
                        .getText()
                        .trim();

        String password
                = new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Please enter your username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return false;
        }
        if (username.equals(ADMIN_USERNAME)
                && password.equals(ADMIN_PASSWORD)) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } else {

            JOptionPane.showMessageDialog(
                    parent,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return false;
    }

    private void showDonationDetails(int row) {

        int modelRow
                = donationsTable
                        .convertRowIndexToModel(
                                row
                        );

        StringBuilder details
                = new StringBuilder();

        details.append(
                "Donation ID: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        0
                )
        );

        details.append("\n");

        details.append(
                "Donor ID: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        1
                )
        );

        details.append("\n");

        details.append(
                "Project ID: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        2
                )
        );

        details.append("\n");

        details.append(
                "Donation Type: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        3
                )
        );

        details.append("\n");

        details.append(
                "Amount: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        4
                )
        );

        details.append("\n");

        details.append(
                "Payment Status: "
        );

        details.append(
                donationModel.getValueAt(
                        modelRow,
                        5
                )
        );

        JOptionPane.showMessageDialog(
                this,
                details.toString(),
                "Donation Details",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateSummary() {

        int totalRows
                = donationModel.getRowCount();

        int pending = 0;
        int completed = 0;

        double totalAmount = 0;

        for (int i = 0;
                i < totalRows;
                i++) {

            String status
                    = donationModel.getValueAt(
                            i,
                            5
                    ).toString();

            if (status.equalsIgnoreCase(
                    "Pending"
            )) {

                pending++;

            } else if (status.equalsIgnoreCase(
                    "Paid"
            )) {

                completed++;
            }

            String amount
                    = donationModel.getValueAt(
                            i,
                            4
                    ).toString();

            amount
                    = amount
                            .replace(
                                    "R",
                                    ""
                            )
                            .replace(
                                    ",",
                                    ""
                            )
                            .trim();

            try {

                totalAmount
                        += Double.parseDouble(
                                amount
                        );

            } catch (NumberFormatException ignored) {
            }
        }

        totalDonationsLabel.setText(
                "<html><center>"
                + "<font size='2'>TOTAL DONATIONS</font><br>"
                + "<font size='5'>R "
                + String.format(
                        "%,.0f",
                        totalAmount
                )
                + "</font>"
                + "</center></html>"
        );

        completedLabel.setText(
                "<html><center>"
                + "<font size='2'>COMPLETED</font><br>"
                + "<font size='5'>"
                + completed
                + "</font>"
                + "</center></html>"
        );

        pendingLabel.setText(
                "<html><center>"
                + "<font size='2'>PENDING</font><br>"
                + "<font size='5'>"
                + pending
                + "</font>"
                + "</center></html>"
        );
    }

    private JPanel createFooter() {

        JPanel footer
                = new JPanel(
                        new GridLayout(
                                1,
                                3,
                                30,
                                0
                        )
                );

        footer.setBackground(NAVY);

        footer.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );

        JPanel about
                = new JPanel();

        about.setLayout(
                new BoxLayout(
                        about,
                        BoxLayout.Y_AXIS
                )
        );

        about.setOpaque(false);

        JLabel aboutTitle
                = new JLabel(
                        "Sikunye Charity"
                );

        aboutTitle.setForeground(
                Color.WHITE
        );

        aboutTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JLabel aboutText
                = new JLabel(
                        "<html>Sikunye Charity connects communities through<br>"
                        + "compassion, transparency, and support. Every<br>"
                        + "donation is tracked responsibly to ensure help<br>"
                        + "reaches those who need it most.</html>"
                );

        aboutText.setForeground(
                new Color(
                        220,
                        225,
                        230
                )
        );

        aboutText.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        about.add(aboutTitle);

        about.add(
                Box.createVerticalStrut(6)
        );

        about.add(aboutText);

        JPanel links
                = new JPanel();

        links.setLayout(
                new BoxLayout(
                        links,
                        BoxLayout.Y_AXIS
                )
        );

        links.setOpaque(false);

        JLabel linksTitle
                = new JLabel(
                        "Quick Links"
                );

        linksTitle.setForeground(
                Color.WHITE
        );

        linksTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        links.add(linksTitle);

        String[] quickLinks = {
            "Home",
            "Projects",
            "Donor",
            "ItemsDonated",
            "Donations Received"

        };

        for (String link : quickLinks) {

            JLabel label
                    = new JLabel(
                            "> " + link
                    );

            label.setForeground(
                    new Color(
                            220,
                            225,
                            230
                    )
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            10
                    )
            );

            links.add(label);
        }

        JPanel contact
                = new JPanel();

        contact.setLayout(
                new BoxLayout(
                        contact,
                        BoxLayout.Y_AXIS
                )
        );

        contact.setOpaque(false);

        JLabel contactTitle
                = new JLabel(
                        "Contact Information"
                );

        contactTitle.setForeground(
                Color.WHITE
        );

        contactTitle.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        JLabel email
                = new JLabel(
                        "Email: help@sikunye.org"
                );

        JLabel phone
                = new JLabel(
                        "Phone: +27 71 234 5678"
                );

        JLabel location
                = new JLabel(
                        "Location: Johannesburg, South Africa"
                );

        for (JLabel label
                : new JLabel[]{
                    email,
                    phone,
                    location
                }) {

            label.setForeground(
                    new Color(
                            220,
                            225,
                            230
                    )
            );

            label.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            10
                    )
            );
        }

        contact.add(contactTitle);

        contact.add(
                Box.createVerticalStrut(6)
        );

        contact.add(email);
        contact.add(phone);
        contact.add(location);

        footer.add(about);
        footer.add(links);
        footer.add(contact);

        return footer;
    }

    public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {

        JFrame frame = new JFrame("Sikunye Charity Dashboard");

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.setSize(1200, 850);

        frame.setLocationRelativeTo(null);

        frame.setContentPane(new DashboardGUI());

        frame.setVisible(true);
    });
}
}
