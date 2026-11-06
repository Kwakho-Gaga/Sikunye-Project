package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.Domain.Donation;
import za.ac.cput.sikunyecharity.Domain.DonationItem;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

public class DashboardGUI extends JFrame {

    // ---------------- NAVIGATION ----------------
    private static DashboardGUI dashboardInstance;

    // ---------------- ADMIN LOGIN ----------------
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    // ---------------- COLOURS ----------------
    private final Color NAVY = new Color(15, 30, 50);
    private final Color BLUE = new Color(0, 128, 170);
    private final Color LIGHT_BLUE = new Color(235, 248, 253);
    private final Color BORDER = new Color(220, 225, 230);
    private final Color GREEN = new Color(40, 160, 110);
    private final Color ORANGE = new Color(245, 160, 60);
    private final Color PURPLE = new Color(135, 90, 200);

    // ---------------- DASHBOARD COMPONENTS ----------------
    private JLabel totalDonationsLabel;
    private JLabel completedLabel;
    private JLabel pendingLabel;
    private JLabel itemsReceivedLabel;
    private FooterPanel footerPanel;
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

    // ---------------- CARDLAYOUT ----------------
    private CardLayout cardLayout;
    private JPanel contentCards;

    private DonationGUI donationGUI;
    private DonationItemGUI donationItemGUI;

    private static final String DASHBOARD_CARD = "Dashboard";
    private static final String DONATION_CARD = "Donation";
    private static final String DONATION_ITEM_CARD = "DonationItem";

    // ---------------- FONTS ----------------
    private Font font1 = new Font("Inter", Font.BOLD, 20);
    private Font font2 = new Font("Inter", Font.PLAIN, 20);

    // ---------------- NAVIGATION COLOURS ----------------
    private Color color1 = new Color(0, 192, 232);

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public DashboardGUI() {
        dashboardInstance = this;
        setTitle("Sikunye Charity");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 850);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(createSummaryPanel());
        mainPanel.add(createFilterPanel());

        donationsPanelContainer = createDonationsPanel();
        mainPanel.add(donationsPanelContainer);
        mainPanel.add(createItemsPanel());

        pageScrollPane = new JScrollPane(mainPanel);
        pageScrollPane.setBorder(null);
        pageScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        cardLayout = new CardLayout();
        contentCards = new JPanel(cardLayout);
        contentCards.add(pageScrollPane, DASHBOARD_CARD);

        add(new HeaderPanel(this, "Donations Received"), BorderLayout.NORTH);
        add(contentCards, BorderLayout.CENTER);
        footerPanel = new FooterPanel(this);
        add(footerPanel, BorderLayout.SOUTH);

        cardLayout.show(contentCards, DASHBOARD_CARD);
        refreshDashboardTables();
    }

    public static DashboardGUI getDashboardInstance() {
        return dashboardInstance;
    }

    // =========================================================
    // DONATIONS RECEIVED NAVIGATION
    // =========================================================
    public void showDonationsReceivedPage() {
        refreshDashboardTables();
        if (footerPanel != null) footerPanel.setVisible(true);
        cardLayout.show(contentCards, DASHBOARD_CARD);
        contentCards.revalidate();
        contentCards.repaint();
        getContentPane().revalidate();
        getContentPane().repaint();

        setVisible(true);
        toFront();
        requestFocus();

        SwingUtilities.invokeLater(() -> {
            if (donationsPanelContainer != null && pageScrollPane != null) {
                pageScrollPane.getViewport().setViewPosition(new Point(0, donationsPanelContainer.getY()));
            }
        });
    }

    // =========================================================
    // ADMIN CONTROLS - PUBLIC VIEW - View | Edit ONLY, NO DELETE
    // =========================================================
    class AdminControlsRenderer extends JPanel implements TableCellRenderer {
        private JButton viewBtn = new JButton("View");
        private JButton editBtn = new JButton("Edit");

        public AdminControlsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
            setOpaque(true);
            setBackground(Color.WHITE);

            viewBtn.setFont(new Font("Arial", Font.BOLD, 10));
            viewBtn.setForeground(new Color(0, 128, 170));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            viewBtn.setFocusPainted(false);

            editBtn.setFont(new Font("Arial", Font.BOLD, 10));
            editBtn.setForeground(new Color(245, 160, 60));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            editBtn.setFocusPainted(false);

            add(viewBtn);
            add(new JLabel("|"));
            add(editBtn);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class AdminControlsEditor extends DefaultCellEditor {
        private JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        private JButton viewBtn = new JButton("View");
        private JButton editBtn = new JButton("Edit");
        private int currentViewRow;
        private String clickedAction = "";

        public AdminControlsEditor(JCheckBox checkBox) {
            super(checkBox);
            panel.setBackground(Color.WHITE);

            viewBtn.setFont(new Font("Arial", Font.BOLD, 10));
            viewBtn.setForeground(new Color(0, 128, 170));
            viewBtn.setBorderPainted(false);
            viewBtn.setContentAreaFilled(false);
            viewBtn.setFocusPainted(false);
            viewBtn.addActionListener(e -> { clickedAction = "View"; fireEditingStopped(); });

            editBtn.setFont(new Font("Arial", Font.BOLD, 10));
            editBtn.setForeground(new Color(245, 160, 60));
            editBtn.setBorderPainted(false);
            editBtn.setContentAreaFilled(false);
            editBtn.setFocusPainted(false);
            editBtn.addActionListener(e -> { clickedAction = "Edit"; fireEditingStopped(); });

            panel.add(viewBtn);
            panel.add(new JLabel("|"));
            panel.add(editBtn);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentViewRow = row;
            clickedAction = "";
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            if (!clickedAction.isEmpty()) {
                int modelRow = donationsTable.convertRowIndexToModel(currentViewRow);
                if (clickedAction.equals("View")) {
                    showDonationDetails(modelRow);
                } else if (clickedAction.equals("Edit")) {
                    // Edit still requires login - goes to login dialog
                    showLoginDialog();
                }
            }
            clickedAction = "";
            return "View | Edit";
        }
    }

    // =========================================================
    // REFRESH DASHBOARD TABLES
    // =========================================================
    private void refreshDashboardTables() {
        try {
            if (donationModel == null || itemModel == null) return;

            donationModel.setRowCount(0);
            DonationDAO donationDao = new DonationDAO();
            List<Donation> donations = donationDao.getAllDonations();
            for (Donation d : donations) {
                donationModel.addRow(new Object[]{
                    d.getDonationID(), d.getDonorID(), d.getProjectID(),
                    d.getDonationType(), d.getAmount(), d.getPaymentStatus(),
                    d.getPaymentDate(), d.isReceiptGenerated(), d.getDateReceived(),
                    "View | Edit"
                });
            }

            itemModel.setRowCount(0);
            DonationItemDAO itemDao = new DonationItemDAO();
            List<DonationItem> items = itemDao.getAllDonationItems();
            for (DonationItem i : items) {
                itemModel.addRow(new Object[]{
                    i.getDonationItemID(), i.getDonationID(), i.getItemID(),
                    i.getQuantity(), i.getCondition()
                });
            }
            updateSummary();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Unable to refresh dashboard:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================
    // SUMMARY PANEL
    // =========================================================
    private JPanel createSummaryPanel() {
        JPanel outer = new JPanel(new GridLayout(1, 4, 10, 10));
        outer.setBackground(Color.WHITE);
        outer.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        totalDonationsLabel = new JLabel("<html><center><font size='2'>TOTAL DONATIONS</font><br><font size='5'>R 25,500</font></center></html>", SwingConstants.CENTER);
        completedLabel = new JLabel("<html><center><font size='2'>COMPLETED</font><br><font size='5'>18</font></center></html>", SwingConstants.CENTER);
        pendingLabel = new JLabel("<html><center><font size='2'>PENDING</font><br><font size='5'>4</font></center></html>", SwingConstants.CENTER);
        itemsReceivedLabel = new JLabel("<html><center><font size='2'>ITEMS RECEIVED</font><br><font size='5'>590</font></center></html>", SwingConstants.CENTER);

        outer.add(createSummaryCard(totalDonationsLabel, new Color(235, 249, 253), BLUE));
        outer.add(createSummaryCard(completedLabel, new Color(237, 250, 244), GREEN));
        outer.add(createSummaryCard(pendingLabel, new Color(255, 248, 235), ORANGE));
        outer.add(createSummaryCard(itemsReceivedLabel, new Color(247, 241, 255), PURPLE));
        return outer;
    }

    private JPanel createSummaryCard(JLabel label, Color background, Color foreground) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(background);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(12, 5, 12, 5)));
        label.setForeground(foreground);
        card.add(label, BorderLayout.CENTER);
        return card;
    }

    // =========================================================
    // FILTER PANEL
    // =========================================================
    private JPanel createFilterPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(BorderFactory.createEmptyBorder(0, 20, 15, 20));
        JPanel filters = new JPanel(new GridBagLayout());
        filters.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setToolTipText("Search donations...");
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1;
        filters.add(searchField, gbc);

        donationTypeCombo = new JComboBox<>(new String[]{"All Donation Types", "Financial", "In-Kind"});
        donationTypeCombo.setPreferredSize(new Dimension(170, 35));
        gbc.gridx = 1; gbc.weightx = 0;
        filters.add(donationTypeCombo, gbc);

        paymentStatusCombo = new JComboBox<>(new String[]{"All Payment Statuses", "Paid", "Pending"});
        paymentStatusCombo.setPreferredSize(new Dimension(180, 35));
        gbc.gridx = 2;
        filters.add(paymentStatusCombo, gbc);

        JButton addDonationButton = new JButton("+ Add Donation");
        addDonationButton.setPreferredSize(new Dimension(145, 35));
        addDonationButton.setBackground(new Color(10, 125, 165));
        addDonationButton.setForeground(Color.WHITE);
        addDonationButton.setFont(new Font("Arial", Font.BOLD, 12));
        addDonationButton.setFocusPainted(false);
        addDonationButton.setBorderPainted(false);
        addDonationButton.addActionListener(e -> showLoginDialog());
        gbc.gridx = 3;
        filters.add(addDonationButton, gbc);

        outer.add(filters, BorderLayout.CENTER);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterDonations(); }
            @Override public void removeUpdate(DocumentEvent e) { filterDonations(); }
            @Override public void changedUpdate(DocumentEvent e) { filterDonations(); }
        });
        donationTypeCombo.addActionListener(e -> filterDonations());
        paymentStatusCombo.addActionListener(e -> filterDonations());
        return outer;
    }

    // =========================================================
    // DONATIONS RECEIVED TABLE - PUBLIC - View | Edit ONLY
    // =========================================================
    private JPanel createDonationsPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(BorderFactory.createEmptyBorder(5, 20, 10, 20));

        JPanel titlePanel = createSectionHeader("Donations Received", "Public donation records managed by Sikunye Charity.");
        outer.add(titlePanel, BorderLayout.NORTH);

        String[] columns = {
            "DONATION ID", "DONOR ID", "PROJECT ID", "DONATION TYPE", "AMOUNT",
            "PAYMENT STATUS", "PAYMENT DATE", "RECEIPT GENERATED", "DATE RECEIVED", "ADMIN CONTROLS"
        };

        donationModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9;
            }
        };

        try {
            DonationDAO donationDao = new DonationDAO();
            List<Donation> donations = donationDao.getAllDonations();
            for (Donation d : donations) {
                donationModel.addRow(new Object[]{
                    d.getDonationID(), d.getDonorID(), d.getProjectID(),
                    d.getDonationType(), d.getAmount(), d.getPaymentStatus(),
                    d.getPaymentDate(), d.isReceiptGenerated(), d.getDateReceived(),
                    "View | Edit"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading donations: " + e.getMessage());
        }

        donationsTable = new JTable(donationModel);
        donationsTable.setRowHeight(32);
        donationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        donationsTable.getTableHeader().setBackground(NAVY);
        donationsTable.getTableHeader().setForeground(Color.WHITE);
        donationsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
        donationsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        donationsTable.setGridColor(new Color(230, 230, 230));

        donationSorter = new TableRowSorter<>(donationModel);
        donationsTable.setRowSorter(donationSorter);

        donationsTable.getColumn("ADMIN CONTROLS").setCellRenderer(new AdminControlsRenderer());
        donationsTable.getColumn("ADMIN CONTROLS").setCellEditor(new AdminControlsEditor(new JCheckBox()));
        donationsTable.getColumn("ADMIN CONTROLS").setPreferredWidth(130);

        JScrollPane scrollPane = new JScrollPane(donationsTable);
        scrollPane.setPreferredSize(new Dimension(1100, 150));
        outer.add(scrollPane, BorderLayout.CENTER);
        return outer;
    }

    // =========================================================
    // DONATED ITEMS TABLE
    // =========================================================
    private JPanel createItemsPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(Color.WHITE);
        outer.setBorder(BorderFactory.createEmptyBorder(5, 20, 15, 20));
        JPanel titlePanel = createSectionHeader("Donated Items Inventory", "Donation items linked to donation records.");
        outer.add(titlePanel, BorderLayout.NORTH);

        String[] columns = {"DONATION ITEM ID", "DONATION ID", "ITEM ID", "QUANTITY", "CONDITION"};
        itemModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        try {
            DonationItemDAO itemDao = new DonationItemDAO();
            List<DonationItem> items = itemDao.getAllDonationItems();
            for (DonationItem i : items) {
                itemModel.addRow(new Object[]{i.getDonationItemID(), i.getDonationID(), i.getItemID(), i.getQuantity(), i.getCondition()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
        }

        itemsTable = new JTable(itemModel);
        itemsTable.setRowHeight(30);
        itemsTable.getTableHeader().setBackground(NAVY);
        itemsTable.getTableHeader().setForeground(Color.WHITE);
        itemsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
        itemsTable.setFont(new Font("Arial", Font.PLAIN, 11));
        itemsTable.setGridColor(new Color(230, 230, 230));

        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setPreferredSize(new Dimension(1100, 130));
        outer.add(scrollPane, BorderLayout.CENTER);
        return outer;
    }

    private JPanel createSectionHeader(String title, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(LIGHT_BLUE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 225, 238)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        descriptionLabel.setForeground(Color.DARK_GRAY);
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(3));
        panel.add(descriptionLabel);
        return panel;
    }

    private void filterDonations() {
        if (donationSorter == null) return;
        String search = searchField.getText().trim().toLowerCase();
        String donationType = donationTypeCombo.getSelectedItem().toString();
        String paymentStatus = paymentStatusCombo.getSelectedItem().toString();
        donationSorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            @Override
            public boolean include(RowFilter.Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                StringBuilder rowText = new StringBuilder();
                for (int i = 0; i < entry.getValueCount(); i++) rowText.append(entry.getStringValue(i)).append(" ");
                String completeRow = rowText.toString().toLowerCase();
                if (!search.isEmpty() && !completeRow.contains(search)) return false;
                if (!donationType.equals("All Donation Types")) {
                    String type = entry.getStringValue(3);
                    if (!type.equalsIgnoreCase(donationType)) return false;
                }
                if (!paymentStatus.equals("All Payment Statuses")) {
                    String status = entry.getStringValue(5);
                    if (!status.equalsIgnoreCase(paymentStatus)) return false;
                }
                return true;
            }
        });
    }

    private void showDashboardPage() {
        refreshDashboardTables();
        if (footerPanel != null) footerPanel.setVisible(true);
        cardLayout.show(contentCards, DASHBOARD_CARD);
        contentCards.revalidate();
        contentCards.repaint();
    }

    private void showDonationPage() {
        if (donationGUI == null) {
            donationGUI = new DonationGUI(this::showDonationItemPage);
            contentCards.add(donationGUI, DONATION_CARD);
        }
        footerPanel.setVisible(false);
        cardLayout.show(contentCards, DONATION_CARD);
        contentCards.revalidate();
        contentCards.repaint();
    }

    private void showDonationItemPage(int donationID) {
        footerPanel.setVisible(false);
        if (donationItemGUI != null) contentCards.remove(donationItemGUI);
        donationItemGUI = new DonationItemGUI(donationID, () -> showDonationPage());
        contentCards.add(donationItemGUI, DONATION_ITEM_CARD);
        cardLayout.show(contentCards, DONATION_ITEM_CARD);
        contentCards.revalidate();
        contentCards.repaint();
    }

    private void showLoginDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        Object[] fields = {"Username:", usernameField, "Password:", passwordField};
        int option = JOptionPane.showConfirmDialog(this, fields, "Administrator Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option != JOptionPane.OK_OPTION) return;
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your username and password.", "Login Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showDonationPage();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDonationDetails(int modelRow) {
        StringBuilder details = new StringBuilder();
        details.append("Donation ID: ").append(donationModel.getValueAt(modelRow, 0)).append("\n");
        details.append("Donor ID: ").append(donationModel.getValueAt(modelRow, 1)).append("\n");
        details.append("Project ID: ").append(donationModel.getValueAt(modelRow, 2)).append("\n");
        details.append("Donation Type: ").append(donationModel.getValueAt(modelRow, 3)).append("\n");
        details.append("Amount: ").append(donationModel.getValueAt(modelRow, 4)).append("\n");
        details.append("Payment Status: ").append(donationModel.getValueAt(modelRow, 5));
        JOptionPane.showMessageDialog(this, details.toString(), "Donation Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateSummary() {
        int totalRows = donationModel.getRowCount();
        int pending = 0; int completed = 0; int totalItemQuantity = 0;
        double totalAmount = 0;
        for (int i = 0; i < itemModel.getRowCount(); i++) {
            Object q = itemModel.getValueAt(i, 3);
            if (q != null) {
                try { totalItemQuantity += Integer.parseInt(q.toString().trim()); } catch (NumberFormatException ignored) {}
            }
        }
        for (int i = 0; i < totalRows; i++) {
            Object s = donationModel.getValueAt(i, 5);
            String status = s == null ? "" : s.toString();
            if (status.equalsIgnoreCase("Pending")) pending++;
            else if (status.equalsIgnoreCase("Paid")) completed++;
            Object a = donationModel.getValueAt(i, 4);
            String amount = a == null ? "0" : a.toString();
            amount = amount.replace("R", "").replace(",", "").trim();
            try { totalAmount += Double.parseDouble(amount); } catch (NumberFormatException ignored) {}
        }
        totalDonationsLabel.setText("<html><center><font size='2'>TOTAL DONATIONS</font><br><font size='5'>R " + String.format("%,.0f", totalAmount) + "</font></center></html>");
        completedLabel.setText("<html><center><font size='2'>COMPLETED</font><br><font size='5'>" + completed + "</font></center></html>");
        pendingLabel.setText("<html><center><font size='2'>PENDING</font><br><font size='5'>" + pending + "</font></center></html>");
        itemsReceivedLabel.setText("<html><center><font size='2'>ITEMS RECEIVED</font><br><font size='5'>" + totalItemQuantity + "</font></center></html>");
    }

    public static void main(String[] args) {
        try {
            DonationDAO donationDao = new DonationDAO();
            donationDao.createTable();
            DonationItemDAO itemDao = new DonationItemDAO();
            itemDao.createTable();
            donationDao.seedInitialDonations();
            itemDao.seedInitialDonationItems();
            System.out.println("Database tables verified/created successfully.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database initialization error:\n" + e.getMessage());
            return;
        }
        SwingUtilities.invokeLater(() -> {
            DashboardGUI dashboard = new DashboardGUI();
            dashboard.setVisible(true);
        });
    }
}
