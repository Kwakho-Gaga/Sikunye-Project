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
    private JPanel donationsPanelContainer;

    private JScrollPane pageScrollPane;

    // CardLayout navigation
    private CardLayout cardLayout;
    private JPanel contentCards;

    private DonationGUI donationGUI;
    private DonationItemGUI donationItemGUI;

    private static final String DASHBOARD_CARD = "Dashboard";
    private static final String DONATION_CARD = "Donation";
    private static final String DONATION_ITEM_CARD = "DonationItem";

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

    private JPanel footerPanel = new JPanel();
    private JPanel charityInfoFooterPanel = new JPanel();
    private JPanel quickLinksPanel = new JPanel();
    private JPanel sociallmediaLinkPanel = new JPanel();
    private JPanel rightReservedPanel = new JPanel();
    private JPanel socialIconPanel = new JPanel();

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

        // ---------------- DASHBOARD CONTENT ----------------
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(
                new BoxLayout(mainPanel, BoxLayout.Y_AXIS)
        );

        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createSummaryPanel());
        mainPanel.add(createFilterPanel());

        donationsPanelContainer = createDonationsPanel();
        mainPanel.add(donationsPanelContainer);

        mainPanel.add(createItemsPanel());

        // Make dashboard scrollable
        pageScrollPane = new JScrollPane(mainPanel);
        pageScrollPane.setBorder(null);
        pageScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // ---------------- CARDLAYOUT ----------------
        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);

        // First card: Dashboard
        contentCards.add(pageScrollPane, DASHBOARD_CARD);

        // ---------------- MAIN APPLICATION LAYOUT ----------------
        add(createNavigationBar(), BorderLayout.NORTH);
        add(contentCards, BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        // Start on Dashboard
        cardLayout.show(contentCards, DASHBOARD_CARD);
    }
// ---------------- REFRESH DASHBOARD TABLES ----------------

    private void refreshDashboardTables() {

        try {

            // Refresh donations table
            donationModel.setRowCount(0);

            DonationDAO donationDao = new DonationDAO();

            List<Donation> donations
                    = donationDao.getAllDonations();

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

            // Refresh donated items table
            itemModel.setRowCount(0);

            DonationItemDAO itemDao = new DonationItemDAO();

            List<DonationItem> items
                    = itemDao.getAllDonationItems();

            for (DonationItem i : items) {

                itemModel.addRow(new Object[]{
                    i.getDonationItemID(),
                    i.getDonationID(),
                    i.getItemID(),
                    i.getQuantity(),
                    i.getCondition()
                });
            }

            // Update summary figures
            updateSummary();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to refresh dashboard:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private JPanel createNavigationBar() {

        // Initialise the panels
        headerPanel = new JPanel();
        navLinksPanel = new JPanel();

        charityNameLabel = new JLabel("SikunyeCharity");
        charityNameLabel.setFont(font1);

        homeLabel = new JLabel("Home");
        homeLabel.setFont(font2);
        homeLabel.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        homeLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                showDashboardPage();
            }
        });

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
        donationRecievedLabel.setForeground(BLUE);
        donationRecievedLabel.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        contactLabel = new JLabel("Contact");
        contactLabel.setFont(font2);

        // DonationReceived click event
        donationRecievedLabel.addMouseListener(
                new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                // First return to the Dashboard.
                showDashboardPage();

                // Then scroll to Donations Received.
                SwingUtilities.invokeLater(() -> {

                    if (donationsPanelContainer != null
                            && pageScrollPane != null) {

                        pageScrollPane.getViewport().scrollRectToVisible(
                                donationsPanelContainer.getBounds()
                        );
                    }
                });
            }
        });

        // Header styling
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

        // Navigation links styling
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

        headerPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        headerPanel.add(charityNameLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(150, 0)));
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
    // ---------------- CARD NAVIGATION ----------------

    private void showDashboardPage() {

        refreshDashboardTables();

        cardLayout.show(contentCards, DASHBOARD_CARD);
    }

    private void showDonationPage() {

        // Create DonationGUI only once.
        if (donationGUI == null) {

            donationGUI = new DonationGUI(
                    this::showDonationItemPage
            );

            contentCards.add(donationGUI, DONATION_CARD);
        }

        cardLayout.show(contentCards, DONATION_CARD);
    }

    private void showDonationItemPage(int donationID) {

        // Remove the previous DonationItemGUI if one exists.
        if (donationItemGUI != null) {
            contentCards.remove(donationItemGUI);
        }

        // Create DonationItemGUI with the correct Donation ID.
        donationItemGUI = new DonationItemGUI(
                donationID,
                () -> showDonationPage()
        );

        contentCards.add(donationItemGUI, DONATION_ITEM_CARD);

        cardLayout.show(contentCards, DONATION_ITEM_CARD);

        contentCards.revalidate();
        contentCards.repaint();
    }

    private void showLoginDialog() {

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {
            "Username:",
            usernameField,
            "Password:",
            passwordField
        };

        int option = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Administrator Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            return;
        }

        String username = usernameField.getText().trim();

        String password = new String(
                passwordField.getPassword()
        );

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your username and password.",
                    "Login Error",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (username.equals(ADMIN_USERNAME)
                && password.equals(ADMIN_PASSWORD)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Display DonationGUI inside the dashboard window.
            showDonationPage();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
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
        // Main thin footer container
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(color5); // Keeps your nice dark theme color
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Left side: Copyright / Charity name
        JLabel rightReservedLabel = new JLabel("© 2026 SikunyeCharity. All rights reserved.");
        rightReservedLabel.setForeground(color2);
        rightReservedLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        footerPanel.add(rightReservedLabel, BorderLayout.WEST);

        // Center: Single-line horizontal quick links
        JPanel quickLinksPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        quickLinksPanel.setOpaque(false);

        String[] linkNames = {"Home", "Projects", "Beneficiaries", "Donor", "ItemsDonated", "DonationReceived", "Contact"};
        for (String name : linkNames) {
            JLabel linkLabel = new JLabel(name);
            linkLabel.setForeground(color2);
            linkLabel.setFont(new Font("Inter", Font.PLAIN, 12));
            linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            quickLinksPanel.add(linkLabel);
        }
        footerPanel.add(quickLinksPanel, BorderLayout.CENTER);

        // Right side: Small social icons
        JPanel socialIconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        socialIconPanel.setOpaque(false);

        URL facebookUrl = getClass().getResource("/Facebook.png");
        JLabel facebookIcon = (facebookUrl != null) ? new JLabel(new ImageIcon(facebookUrl)) : new JLabel("FB");
        facebookIcon.setForeground(color2);

        URL instagramUrl = getClass().getResource("/Instagram.png");
        JLabel instagramIcon = (instagramUrl != null) ? new JLabel(new ImageIcon(instagramUrl)) : new JLabel("IG");
        instagramIcon.setForeground(color2);

        URL linkedinUrl = getClass().getResource("/Linkedin.png");
        JLabel linkedinIcon = (linkedinUrl != null) ? new JLabel(new ImageIcon(linkedinUrl)) : new JLabel("IN");
        linkedinIcon.setForeground(color2);

        socialIconPanel.add(facebookIcon);
        socialIconPanel.add(instagramIcon);
        socialIconPanel.add(linkedinIcon);

        footerPanel.add(socialIconPanel, BorderLayout.EAST);

        return footerPanel;
    }

    public static void main(String[] args) {
        // 1. Initialize and create database tables FIRST before launching the UI
        try {
            DonationDAO donationDao = new DonationDAO();
            donationDao.createTable(); // Creates DONATION first

            DonationItemDAO itemDao = new DonationItemDAO();
            itemDao.createTable();     // Creates DONATIONITEM second (respects foreign key)

            donationDao.seedInitialDonations();
            itemDao.seedInitialDonationItems();

            System.out.println("Database tables verified/created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database initialization error:\n" + e.getMessage());
            return; // Stop execution if the database fails to initialize
        }

        // 2. Launch the Swing User Interface AFTER tables are guaranteed to exist
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
