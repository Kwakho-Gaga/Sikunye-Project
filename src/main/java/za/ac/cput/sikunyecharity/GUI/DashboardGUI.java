/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.Domain.Donation;
import za.ac.cput.sikunyecharity.Domain.DonationItem;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.net.URL;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author User
 */
public class DashboardGUI extends JPanel {
    
    private JPanel headerPanel, navLinksPanel;

private JLabel charityNameLabel,
        homeLabel,
        projectLabel,
        beneficiariesLabel,
        donorLabel,
        itemsDonatedLabel,
        donationRecievedLabel,
        contactLabel;






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
    
   
Font font1 = new Font("Inter", Font.BOLD, 20);
Font font2 = new Font("Inter", Font.PLAIN, 20);
Font font4 = new Font("Inter", Font.BOLD, 15);
Font font5 = new Font("Inter", Font.BOLD, 16);
Font font6 = new Font("Inter", Font.BOLD, 14);

Color color1 = new Color(0, 192, 232);
Color color2 = Color.WHITE;

Color color5 = new Color(43, 55, 63, 230);
Color color6 = new Color(142, 142, 147);
Color color7 = new Color(179, 179, 179);
    
    private JPanel footerPanel, charityInfoFooterPanel, quickLinksPanel,
        sociallmediaLinkPanel, rightReservedPanel, socialIconPanel;

private JLabel charityNameFooterLabel;
private JLabel charitySloganLabel;
private JLabel quickLinkLabel;
private JLabel homeLinkLabel;
private JLabel itemsDonatedLinkLabel;
private JLabel projectLinkLabel;
private JLabel beneficiariesLinkLabel;
private JLabel donorLinkLabel;
private JLabel donationRecievedLinkLabel;
private JLabel contactLinkLabel;
private JLabel feedbackLinkLabel;
private JLabel followUsLabel;
private JLabel rightReservedLabel;

 public DashboardGUI() {

    setLayout(new BorderLayout());
    setBackground(Color.WHITE);

    // Main content panel
    JPanel mainPanel = new JPanel();

    mainPanel.setLayout(
            new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
    );

    mainPanel.setBackground(Color.WHITE);

    // Add dashboard content
    mainPanel.add(createSummaryPanel());
    mainPanel.add(createFilterPanel());
    mainPanel.add(createDonationsPanel());
    mainPanel.add(createItemsPanel());

    // Make content scrollable
    JScrollPane scrollPane = new JScrollPane(mainPanel);

    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);

    // Add navigation bar, content and footer
    add(createNavigationBar(), BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(createFooter(), BorderLayout.SOUTH);
}
private JPanel createNavigationBar() {

    charityNameLabel = new JLabel("SikunyeCharity");
    charityNameLabel.setFont(font1);

    homeLabel = new JLabel("Home");
    homeLabel.setFont(font2);

    projectLabel = new JLabel("Projects");
    projectLabel.setFont(font2);

    beneficiariesLabel = new JLabel("Beneficiaries");
    beneficiariesLabel.setFont(font2);

    donorLabel = new JLabel("Donor");
    donorLabel.setFont(font2);

    itemsDonatedLabel = new JLabel("ItemsDonated");
    itemsDonatedLabel.setFont(font2);

    donationRecievedLabel = new JLabel("DonationReceived");
    donationRecievedLabel.setFont(font2);

    contactLabel = new JLabel("Contact");
    contactLabel.setFont(font2);

    // Navigation panel
    headerPanel.setLayout(
            new BoxLayout(headerPanel, BoxLayout.X_AXIS)
    );

    Dimension panelHeaderSize = new Dimension(1390, 60);

    headerPanel.setPreferredSize(panelHeaderSize);
    headerPanel.setMinimumSize(panelHeaderSize);
    headerPanel.setMaximumSize(panelHeaderSize);

    headerPanel.setOpaque(true);
    headerPanel.setBackground(Color.WHITE);

    headerPanel.setBorder(
            new MatteBorder(0, 0, 2, 0, color1)
    );

    navLinksPanel.setLayout(
            new FlowLayout(FlowLayout.LEADING, 18, 18)
    );

    navLinksPanel.setOpaque(false);

    navLinksPanel.add(homeLabel);
    navLinksPanel.add(projectLabel);
    navLinksPanel.add(beneficiariesLabel);
    navLinksPanel.add(donorLabel);
    navLinksPanel.add(itemsDonatedLabel);
    navLinksPanel.add(donationRecievedLabel);
    navLinksPanel.add(contactLabel);

    headerPanel.add(
            Box.createRigidArea(new Dimension(50, 0))
    );

    headerPanel.add(charityNameLabel);

    headerPanel.add(
            Box.createRigidArea(new Dimension(150, 0))
    );

    headerPanel.add(navLinksPanel);

    return headerPanel;
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

        try {
            DonationDAO donationDao = new DonationDAO();
            List<Donation> donations = donationDao.getAllDonations(); // fetch from DB
            for (Donation d : donations) {
                donationModel.addRow(new Object[]{
                    d.getDonationID(),
                    d.getDonorID(),
                    d.getProjectID(),
                    d.getDonationType(),
                    d.getAmount(),
                    d.getPaymentStatus(),
                    d.getPaymentDate(),
                    d.isReceiptGenerated(),
                    d.getDateReceived(),
                    "View | Edit"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading donations: " + e.getMessage());
        }

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

        try {
            DonationItemDAO itemDao = new DonationItemDAO();
            List<DonationItem> items = itemDao.getAllDonationItems(); // fetch from DB
            for (DonationItem i : items) {
                itemModel.addRow(new Object[]{
                    i.getDonationItemID(),
                    i.getDonationID(),
                    i.getItemID(),
                    i.getQuantity(),
                    i.getCondition()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
        }

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

        charityNameFooterLabel = new JLabel("Sikunye Charity");
charityNameFooterLabel.setForeground(color2);
charityNameFooterLabel.setFont(font1);

charitySloganLabel = new JLabel("Together we are one");
charitySloganLabel.setForeground(color2);
charitySloganLabel.setFont(font5);

quickLinkLabel = new JLabel("Quick Links");
quickLinkLabel.setForeground(color2);
quickLinkLabel.setFont(font1);

homeLinkLabel = new JLabel("Home");
homeLinkLabel.setForeground(color2);
homeLinkLabel.setFont(font4);

itemsDonatedLinkLabel = new JLabel("ItemsDonated");
itemsDonatedLinkLabel.setForeground(color2);
itemsDonatedLinkLabel.setFont(font4);

projectLinkLabel = new JLabel("Projects");
projectLinkLabel.setForeground(color2);
projectLinkLabel.setFont(font4);

beneficiariesLinkLabel = new JLabel("Beneficiaries");
beneficiariesLinkLabel.setForeground(color2);
beneficiariesLinkLabel.setFont(font4);

donorLinkLabel = new JLabel("Donor");
donorLinkLabel.setForeground(color2);
donorLinkLabel.setFont(font4);

donationRecievedLinkLabel = new JLabel("DonationsRecieved");
donationRecievedLinkLabel.setForeground(color2);
donationRecievedLinkLabel.setFont(font4);

contactLinkLabel = new JLabel("Contact");
contactLinkLabel.setForeground(color2);
contactLinkLabel.setFont(font4);

feedbackLinkLabel = new JLabel("Feedback");
feedbackLinkLabel.setForeground(color2);
feedbackLinkLabel.setFont(font4);

followUsLabel = new JLabel("Follow us");
followUsLabel.setForeground(color2);
followUsLabel.setFont(font1);

rightReservedLabel = new JLabel(
    "© 2020 SikunyeCharity.All rights reserved"
);
rightReservedLabel.setForeground(color2);
rightReservedLabel.setFont(font6);

// Charity information section
charityInfoFooterPanel.setLayout(
    new BoxLayout(charityInfoFooterPanel, BoxLayout.Y_AXIS)
);
charityInfoFooterPanel.setOpaque(false);

charityInfoFooterPanel.add(
    Box.createRigidArea(new Dimension(20, 20))
);
charityInfoFooterPanel.add(charityNameFooterLabel);

charityInfoFooterPanel.add(
    Box.createRigidArea(new Dimension(20, 20))
);
charityInfoFooterPanel.add(charitySloganLabel);


// Quick Links section
quickLinksPanel.setLayout(new GridLayout(9, 1));
quickLinksPanel.setOpaque(false);

quickLinksPanel.add(quickLinkLabel);
quickLinksPanel.add(homeLinkLabel);
quickLinksPanel.add(itemsDonatedLinkLabel);
quickLinksPanel.add(projectLinkLabel);
quickLinksPanel.add(beneficiariesLinkLabel);
quickLinksPanel.add(donorLinkLabel);
quickLinksPanel.add(donationRecievedLinkLabel);
quickLinksPanel.add(contactLinkLabel);
quickLinksPanel.add(feedbackLinkLabel);


// Social media section
sociallmediaLinkPanel.setLayout(
    new BoxLayout(sociallmediaLinkPanel, BoxLayout.Y_AXIS)
);
sociallmediaLinkPanel.setOpaque(false);

sociallmediaLinkPanel.add(followUsLabel);

socialIconPanel.setLayout(new FlowLayout());
socialIconPanel.setBorder(
    new EmptyBorder(10, 20, 10, 20)
);
socialIconPanel.setOpaque(false);


// Facebook icon
URL facebookUrl = getClass().getResource("/Facebook.png");
ImageIcon facebook = new ImageIcon(facebookUrl);
JLabel facebookIcon = new JLabel(facebook);

// Instagram icon
URL instagramUrl = getClass().getResource("/Instagram.png");
ImageIcon instagram = new ImageIcon(instagramUrl);
JLabel instagramIcon = new JLabel(instagram);

// LinkedIn icon
URL linkedinUrl = getClass().getResource("/Linkedin.png");
ImageIcon linkedin = new ImageIcon(linkedinUrl);
JLabel linkedinIcon = new JLabel(linkedin);

socialIconPanel.add(facebookIcon);
socialIconPanel.add(instagramIcon);
socialIconPanel.add(linkedinIcon);

sociallmediaLinkPanel.add(socialIconPanel);


// Copyright section
rightReservedPanel.setLayout(new FlowLayout());
rightReservedPanel.setOpaque(true);
rightReservedPanel.setBackground(color5);
rightReservedPanel.setBorder(
    new LineBorder(color6, 1)
);

rightReservedPanel.add(rightReservedLabel);


// Main footer panel
footerPanel.setLayout(new GridLayout(1, 3, 10, 0));
footerPanel.setOpaque(true);
footerPanel.setBackground(color5);
footerPanel.setBorder(
    new EmptyBorder(10, 20, 10, 20)
);

footerPanel.add(charityInfoFooterPanel);
footerPanel.add(quickLinksPanel);
footerPanel.add(sociallmediaLinkPanel);

JPanel footerContainer = new JPanel();

footerContainer.setLayout(
        new BoxLayout(footerContainer, BoxLayout.Y_AXIS)
);

footerContainer.add(footerPanel);
footerContainer.add(rightReservedPanel);

return footerContainer;

    
    }
    
   public static void main(String[] args) {

    SwingUtilities.invokeLater(() -> {

        JFrame frame = new JFrame("Sikunye Charity");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 850);
        frame.setLocationRelativeTo(null);

        frame.setContentPane(new DashboardGUI());

        frame.setVisible(true);
    });
}
}
