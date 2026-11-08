package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.Domain.Donation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.IntConsumer;

public class DonationGUI extends JPanel {

    private JTextField donationIDField;
    private JTextField donorIDField;
    private JTextField projectIDField;
    private JTextField amountField;
    private JTextField paymentDateField;
    private JTextField dateReceivedField;
    private JComboBox<String> donationTypeCombo;
    private JComboBox<String> paymentStatusCombo;
    private JComboBox<String> receiptGeneratedCombo;
    private JButton addButton, clearButton, refreshButton, deleteButton;
    private JTable donationTable;
    private DefaultTableModel model;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private IntConsumer openDonationItemPage;
    private boolean autoLinkedMode = false; // true when opened from Donor page

    public DonationGUI() {
        this(null);
    }

    public DonationGUI(IntConsumer openDonationItemPage) {
        this.openDonationItemPage = openDonationItemPage;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new javax.swing.border.EmptyBorder(15, 15, 15, 15));
        createGUI();
        loadDonations();
        loadNextDonationID();
        updatePaymentDateState();
        setFocusable(true);
        setupEnterKey();
    }

    // NEW CONSTRUCTOR - Called from Donor page with auto-generated IDs
    public DonationGUI(int donationId, int donorId, String projectId, double amount) {
        this((IntConsumer) null);
        this.autoLinkedMode = true;
        donationIDField.setText(String.valueOf(donationId));
        donorIDField.setText(String.valueOf(donorId));
        projectIDField.setText(projectId == null ? "General Fund" : projectId);
        amountField.setText(String.valueOf(amount));
        dateReceivedField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
        lockAutoLinkedFields();
        loadDonations(); // JTable shows the just-saved donation
    }

    // Locks the 3 IDs so user cannot break Donation -> Donor / Project link
    private void lockAutoLinkedFields() {
        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);
        donorIDField.setEditable(false);
        donorIDField.setBackground(Color.LIGHT_GRAY);
        projectIDField.setEditable(false);
        projectIDField.setBackground(Color.LIGHT_GRAY);
        addButton.setText("Refresh / View Donation");
    }

    private void createGUI() {
        JLabel titleLabel = new JLabel("DONATION MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(15, 30, 50));
        titleLabel.setBorder(new javax.swing.border.EmptyBorder(10, 5, 15, 5));
        add(titleLabel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Record New Donation"));
        formPanel.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        addFormField(formPanel, gbc, 0, "Donation ID:");
        donationIDField = new JTextField();
        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);
        addFormComponent(formPanel, gbc, 0, donationIDField);
        addFormField(formPanel, gbc, 1, "Donor ID:");
        donorIDField = new JTextField();
        addFormComponent(formPanel, gbc, 1, donorIDField);
        addFormField(formPanel, gbc, 2, "Project ID:");
        projectIDField = new JTextField();
        addFormComponent(formPanel, gbc, 2, projectIDField);
        addFormField(formPanel, gbc, 3, "Donation Type:");
        donationTypeCombo = new JComboBox<>(new String[]{"FINANCIAL", "IN-KIND"});
        addFormComponent(formPanel, gbc, 3, donationTypeCombo);
        addFormField(formPanel, gbc, 4, "Amount:");
        amountField = new JTextField();
        addFormComponent(formPanel, gbc, 4, amountField);
        addFormField(formPanel, gbc, 5, "Payment Status:");
        paymentStatusCombo = new JComboBox<>(new String[]{"PENDING", "PAID"});
        addFormComponent(formPanel, gbc, 5, paymentStatusCombo);
        addFormField(formPanel, gbc, 6, "Payment Date:");
        paymentDateField = new JTextField();
        paymentDateField.setToolTipText("Format: yyyy-MM-dd");
        addFormComponent(formPanel, gbc, 6, paymentDateField);
        addFormField(formPanel, gbc, 7, "Receipt Generated:");
        receiptGeneratedCombo = new JComboBox<>(new String[]{"FALSE", "TRUE"});
        addFormComponent(formPanel, gbc, 7, receiptGeneratedCombo);
        addFormField(formPanel, gbc, 8, "Date Received:");
        dateReceivedField = new JTextField();
        dateReceivedField.setToolTipText("Format: yyyy-MM-dd");
        addFormComponent(formPanel, gbc, 8, dateReceivedField);
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        formButtonPanel.setBackground(Color.WHITE);
        addButton = new JButton("Add Donation");
        clearButton = new JButton("Clear");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");
        formButtonPanel.add(addButton);
        formButtonPanel.add(clearButton);
        formButtonPanel.add(refreshButton);
        formButtonPanel.add(deleteButton);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(formButtonPanel, gbc);
        JLabel instructionLabel = new JLabel("<html><center>Press ENTER to continue to Donation Item Management</center></html>");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 10;
        gbc.insets = new Insets(12, 10, 7, 10);
        formPanel.add(instructionLabel, gbc);
        add(formPanel, BorderLayout.WEST);
        String[] columns = {"DONATION ID", "DONOR ID", "PROJECT ID", "DONATION TYPE", "AMOUNT", "PAYMENT STATUS", "PAYMENT DATE", "RECEIPT GENERATED", "DATE RECEIVED", "DELETE"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9;
            }
        };
        donationTable = new JTable(model);
        donationTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        donationTable.setRowHeight(30);
        for (int i = 0; i < columns.length; i++) {
            donationTable.getColumnModel().getColumn(i).setPreferredWidth(i < 3 ? 100 : i < 6 ? 120 : i == 9 ? 90 : 150);
        }
        donationTable.getColumn("DELETE").setCellRenderer(new ButtonRenderer());
        donationTable.getColumn("DELETE").setCellEditor(new ButtonEditor(new JCheckBox()));
        JScrollPane tableScrollPane = new JScrollPane(donationTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Donations Received"));
        add(tableScrollPane, BorderLayout.CENTER);
        addButton.addActionListener(e -> addDonation());
        clearButton.addActionListener(e -> clearForm());
        refreshButton.addActionListener(e -> {
            loadDonations();
            if (!autoLinkedMode) {
                loadNextDonationID();
            }
        });
        deleteButton.addActionListener(e -> deleteDonation());
        donationTypeCombo.addActionListener(e -> updatePaymentDateState());
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
            setText("Delete");
            setBackground(new Color(220, 53, 69));
            setForeground(Color.WHITE);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private int currentRow;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton("Delete");
            button.setOpaque(true);
            button.setBackground(new Color(220, 53, 69));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentRow = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                deleteDonationByRow(currentRow);
            }
            isPushed = false;
            return "Delete";
        }
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
    }

    private void addFormComponent(JPanel panel, GridBagConstraints gbc, int row, JComponent component) {
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        component.setPreferredSize(new Dimension(150, 27));
        panel.add(component, gbc);
    }

    private void setupEnterKey() {
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke("ENTER"), "goToDonationItem");
        getActionMap().put("goToDonationItem", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openDonationItemManagement();
            }
        });
    }

    private void openDonationItemManagement() {
        try {
            DonationDAO dao = new DonationDAO();
            int donationID = dao.getLatestInKindDonationID();
            if (donationID <= 0) {
                JOptionPane.showMessageDialog(this, "Please add an IN-KIND donation first.", "No IN-KIND Donation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (openDonationItemPage != null) {
                openDonationItemPage.accept(donationID);
            } else {
                DonationItemGUI itemGUI = new DonationItemGUI(donationID);
                JFrame frame = new JFrame("Donation Item Management");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(1100, 650);
                frame.setLocationRelativeTo(null);
                frame.setContentPane(itemGUI);
                frame.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not retrieve an IN-KIND donation ID:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDonations() {
        model.setRowCount(0);
        try {
            DonationDAO dao = new DonationDAO();
            List<Donation> donations = dao.getAllDonations();
            for (Donation d : donations) {
                model.addRow(new Object[]{d.getDonationID(), d.getDonorID(), d.getProjectID(), d.getDonationType(), d.getAmount(), d.getPaymentStatus(), d.getPaymentDate(), d.isReceiptGenerated(), d.getDateReceived(), "Delete"});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading donations:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNextDonationID() {
        try {
            DonationDAO dao = new DonationDAO();
            donationIDField.setText(String.valueOf(dao.getNextDonationID()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Could not determine the next Donation ID:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updatePaymentDateState() {
        String selectedType = (String) donationTypeCombo.getSelectedItem();
        if ("FINANCIAL".equals(selectedType)) {
            paymentDateField.setEnabled(true);
            paymentDateField.setBackground(Color.WHITE);
        } else {
            paymentDateField.setText("");
            paymentDateField.setEnabled(false);
            paymentDateField.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void addDonation() {
        // In auto-linked mode, donation already exists - just refresh JTable, don't insert duplicate
        if (autoLinkedMode) {
            loadDonations();
            JOptionPane.showMessageDialog(this, "Donation " + donationIDField.getText() + " is already saved and linked to Donor " + donorIDField.getText() + " + Project " + projectIDField.getText(), "Already Saved", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            int donationID = Integer.parseInt(donationIDField.getText().trim());
            String donorText = donorIDField.getText().trim();
            if (donorText.isEmpty()) {
                showWarning("Please enter the Donor ID.");
                donorIDField.requestFocus();
                return;
            }
            int donorID = Integer.parseInt(donorText);
            String projectID = projectIDField.getText().trim(); // FIXED: String, not int - SSD, SSF, CCC
            if (projectID.isEmpty() || projectID.equals("General Fund")) {
                projectID = null;
            }
            String donationType = (String) donationTypeCombo.getSelectedItem();
            String amountText = amountField.getText().trim();
            if (amountText.isEmpty()) {
                showWarning("Please enter the donation amount.");
                amountField.requestFocus();
                return;
            }
            BigDecimal amount = new BigDecimal(amountText);
            if (amount.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Amount cannot be negative.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                amountField.requestFocus();
                return;
            }
            String paymentStatus = (String) paymentStatusCombo.getSelectedItem();
            Timestamp paymentDate = null;
            if ("FINANCIAL".equals(donationType)) {
                String paymentDateText = paymentDateField.getText().trim();
                if (paymentDateText.isEmpty()) {
                    showWarning("Please enter the Payment Date for a financial donation.");
                    paymentDateField.requestFocus();
                    return;
                }
                if (!isValidDate(paymentDateText)) {
                    showWarning("Payment Date must use the format:\nyyyy-MM-dd");
                    paymentDateField.requestFocus();
                    return;
                }
                paymentDate = Timestamp.valueOf(paymentDateText + " " + new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
            }
            boolean receiptGenerated = Boolean.parseBoolean((String) receiptGeneratedCombo.getSelectedItem());
            String dateReceivedText = dateReceivedField.getText().trim();
            if (dateReceivedText.isEmpty()) {
                showWarning("Please enter the Date Received.");
                dateReceivedField.requestFocus();
                return;
            }
            if (!isValidDate(dateReceivedText)) {
                showWarning("Date Received must use the format:\nyyyy-MM-dd");
                dateReceivedField.requestFocus();
                return;
            }
            Date dateReceived = Date.valueOf(dateReceivedText);
            // NOTE: Your Domain Donation must have ProjectID as String - change int to String there too
            Donation donation = new Donation(donationID, donorIdSafe(donorID), projectID, donationType, amount, paymentStatus, paymentDate, receiptGenerated, dateReceived);
            DonationDAO dao = new DonationDAO();
            dao.insertDonation(donation);
            JOptionPane.showMessageDialog(this, "Donation " + donationID + " was recorded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearInputFields();
            loadDonations();
            loadNextDonationID();
            if ("IN-KIND".equalsIgnoreCase(donationType)) {
                if (openDonationItemPage != null) {
                    openDonationItemPage.accept(donationID);
                } else {
                    openDonationItemManagement();
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Donor ID and Donation ID must be valid whole numbers.\n\nAmount must be a valid number.", "Invalid Number", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error recording donation:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int donorIdSafe(int id) {
        return id;
    }

    private void deleteDonation() {
        int selectedRow = donationTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a donation to delete.", "No Donation Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        deleteDonationByRow(selectedRow);
    }

    private void deleteDonationByRow(int row) {
        int donationID = (int) model.getValueAt(row, 0);
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete Donation ID " + donationID + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            DonationItemDAO itemDAO = new DonationItemDAO();
            if (itemDAO.hasDonationItems(donationID)) {
                JOptionPane.showMessageDialog(this, "Cannot delete this donation.\n\nThis IN-KIND donation has Donation Items linked to it.\nPlease delete the related Donation Item(s) first.", "Cannot Delete Donation", JOptionPane.WARNING_MESSAGE);
                return;
            }
            DonationDAO dao = new DonationDAO();
            dao.deleteDonation(donationID);
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Donation deleted successfully!");
            loadNextDonationID();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error deleting donation: " + ex.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidDate(String date) {
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private void clearForm() {
        clearInputFields();
        if (!autoLinkedMode) {
            loadNextDonationID();
        }
    }

    private void clearInputFields() {
        // Do NOT clear locked IDs in auto-linked mode
        if (!autoLinkedMode) {
            donorIDField.setText("");
            projectIDField.setText("");
        }
        amountField.setText("");
        paymentDateField.setText("");
        dateReceivedField.setText("");
        donationTypeCombo.setSelectedItem("FINANCIAL");
        paymentStatusCombo.setSelectedItem("PENDING");
        receiptGeneratedCombo.setSelectedItem("FALSE");
        if (!autoLinkedMode) {
            donorIDField.requestFocus();
        }
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Missing Information", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Donation Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new DonationGUI());
            frame.setVisible(true);
        });
    }
}
