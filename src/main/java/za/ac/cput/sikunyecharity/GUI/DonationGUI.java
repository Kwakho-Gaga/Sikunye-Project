/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;


import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
/**
 *
 * @author User
 */
    

public class DonationGUI extends JFrame {

    private JTextField donationIDField;
    private JTextField donorIDField;
    private JTextField projectIDField;
    private JTextField amountField;
    private JTextField paymentDateField;
    private JTextField dateReceivedField;
    private JComboBox<String> donationTypeCombo;
    private JComboBox<String> paymentStatusCombo;
    private JComboBox<String> receiptGeneratedCombo;
    private JButton addButton;
    private JButton clearButton;
    private JButton refreshButton;
    private JButton deleteButton;
    private JTable donationTable;
    private DefaultTableModel model;
    private final SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd");

    public DonationGUI() {

        setTitle("Donation Management");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel
                = new JLabel("DONATION MANAGEMENT", SwingConstants.CENTER);

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        add(titleLabel, BorderLayout.NORTH);
        JPanel formPanel = new JPanel(
                new GridBagLayout()
        );

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Record New Donation"
                )
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.insets
                = new Insets(7, 10, 7, 10);

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                new JLabel("Donation ID:"),
                gbc
        );

        donationIDField
                = new JTextField();

        donationIDField.setEditable(false);

        donationIDField.setBackground(
                Color.LIGHT_GRAY
        );

        gbc.gridx = 1;

        formPanel.add(
                donationIDField,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Donor ID:"),
                gbc
        );

        donorIDField
                = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                donorIDField,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Project ID:"),
                gbc
        );

        projectIDField
                = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                projectIDField,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Donation Type:"),
                gbc
        );

        donationTypeCombo
                = new JComboBox<>(
                        new String[]{
                            "FINANCIAL",
                            "IN-KIND"
                        }
                );

        gbc.gridx = 1;

        formPanel.add(
                donationTypeCombo,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Amount:"),
                gbc
        );

        amountField
                = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                amountField,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 5;

        formPanel.add(
                new JLabel("Payment Status:"),
                gbc
        );

        paymentStatusCombo
                = new JComboBox<>(
                        new String[]{
                            "PENDING",
                            "PAID"
                        }
                );

        gbc.gridx = 1;

        formPanel.add(
                paymentStatusCombo,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 6;

        formPanel.add(
                new JLabel("Payment Date:"),
                gbc
        );

        paymentDateField
                = new JTextField();

        paymentDateField.setToolTipText(
                "Format: yyyy-MM-dd"
        );

        gbc.gridx = 1;

        formPanel.add(
                paymentDateField,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 7;

        formPanel.add(
                new JLabel("Receipt Generated:"),
                gbc
        );

        receiptGeneratedCombo
                = new JComboBox<>(
                        new String[]{
                            "FALSE",
                            "TRUE"
                        }
                );

        gbc.gridx = 1;

        formPanel.add(
                receiptGeneratedCombo,
                gbc
        );
        gbc.gridx = 0;
        gbc.gridy = 8;

        formPanel.add(
                new JLabel("Date Received:"),
                gbc
        );

        dateReceivedField
                = new JTextField();

        dateReceivedField.setToolTipText(
                "Format: yyyy-MM-dd"
        );

        gbc.gridx = 1;

        formPanel.add(
                dateReceivedField,
                gbc
        );
        JPanel formButtonPanel
                = new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                5
                        )
                );

        addButton = new JButton("Add Donation");
        clearButton = new JButton("Clear");
        refreshButton = new JButton("Refresh");
        deleteButton = new JButton("Delete");

        formButtonPanel.add(addButton);
        formButtonPanel.add(clearButton);
        formButtonPanel.add(refreshButton);
        formButtonPanel.add(deleteButton);
        JLabel instructionLabel = new JLabel("Press ENTER to continue to Donation Item Management");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        instructionLabel.setForeground(Color.DARK_GRAY);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        formPanel.add(instructionLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;

        gbc.gridwidth = 2;

        formPanel.add(
                formButtonPanel,
                gbc
        );

        add(
                formPanel,
                BorderLayout.WEST
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
            "DATE RECEIVED"

        };

        model
                = new DefaultTableModel(
                        columns,
                        0
                ) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {

                return false;
            }
        };

        donationTable
                = new JTable(model);

        donationTable.setAutoResizeMode(
                JTable.AUTO_RESIZE_OFF
        );
        donationTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        donationTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        donationTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        donationTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        donationTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        donationTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        donationTable.getColumnModel().getColumn(6).setPreferredWidth(150);
        donationTable.getColumnModel().getColumn(7).setPreferredWidth(150);
        donationTable.getColumnModel().getColumn(8).setPreferredWidth(150);
        JScrollPane tableScrollPane
                = new JScrollPane(
                        donationTable
                );

        tableScrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Donations Received"
                )
        );

        add(
                tableScrollPane,
                BorderLayout.CENTER
        );

//        addButton.addActionListener(
//                e -> addDonation()
//        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        refreshButton.addActionListener(
                e -> {

                    loadDonations();
                    //loadNextDonationID();

                }
        );

        deleteButton.addActionListener(e -> {
            int selectedRow = donationTable.getSelectedRow();

            if (selectedRow >= 0) {
                int donationID = (int) model.getValueAt(selectedRow, 0);

                int confirmation = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete Donation ID "
                        + donationID + "?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation != JOptionPane.YES_OPTION) {
                    return;
                }

//                try {
//
//                    DonationItemDAO itemDAO = new DonationItemDAO();
//
//                    if (itemDAO.hasDonationItems(donationID)) {
//                        JOptionPane.showMessageDialog(
//                                this,
//                                "Cannot delete this donation.\n\n"
//                                + "This IN-KIND donation has Donation Items linked to it.\n"
//                                + "Please delete the related Donation Item(s) first.",
//                                "Cannot Delete Donation",
//                                JOptionPane.WARNING_MESSAGE
//                        );
//                        return;
//                    }
//
//                    DonationDAO dao = new DonationDAO();
//                    dao.deleteDonation(donationID);
//
//                    model.removeRow(selectedRow);
//
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Donation deleted successfully!"
//                    );
//
//                } catch (Exception ex) {
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Error deleting donation: " + ex.getMessage(),
//                            "Delete Error",
//                            JOptionPane.ERROR_MESSAGE
//                    );
//                }

            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a donation to delete.",
                        "No Donation Selected",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });
      

        donationTypeCombo.addActionListener(
                e -> updatePaymentDateState()
        );

        loadDonations();

        //loadNextDonationID();

        updatePaymentDateState();

        JRootPane rootPane = this.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ENTER"), "goToDonationItem");
rootPane.getActionMap().put("goToDonationItem",
        new AbstractAction() {

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

//        try {
//
//            DonationDAO dao = new DonationDAO();
//
//            int donationID = dao.getLatestInKindDonationID();
//
//            if (donationID <= 0) {
//
//                JOptionPane.showMessageDialog(
//                        DonationGUI.this,
//                        "Please add an IN-KIND donation first.",
//                        "No IN-KIND Donation",
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                return;
//            }
//
////            DonationItemGUI itemGUI =
////                    new DonationItemGUI(donationID);
////
////            itemGUI.setVisible(true);
//
//            dispose();
//
//        } catch (Exception ex) {
//
//            JOptionPane.showMessageDialog(
//                    DonationGUI.this,
//                    "Could not retrieve an IN-KIND donation ID:\n"
//                    + ex.getMessage(),
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
    }
});





        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void loadDonations() {

//        model.setRowCount(0);
//
//        try {
//
//            DonationDAO dao
//                    = new DonationDAO();
//
//            List<Donation> donations
//                    = dao.getAllDonations();
//
//            for (Donation d : donations) {
//
//                model.addRow(
//                        new Object[]{
//                            d.getDonationID(),
//                            d.getDonorID(),
//                            d.getProjectID(),
//                            d.getDonationType(),
//                            d.getAmount(),
//                            d.getPaymentStatus(),
//                            d.getPaymentDate(),
//                            d.isReceiptGenerated(),
//                            d.getDateReceived()
//
//                        }
//                );
//            }
//
//        } catch (SQLException e) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error loading donations:\n"
//                    + e.getMessage(),
//                    "Database Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
    }

//    private void loadNextDonationID() {
//
//        try {
//
//            DonationDAO dao
//                    = new DonationDAO();
//
//            int nextID
//                    = dao.getNextDonationID();
//
//            donationIDField.setText(
//                    String.valueOf(nextID)
//            );
//
//        } catch (SQLException e) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Could not determine the next Donation ID:\n"
//                    + e.getMessage(),
//                    "Database Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

    private void updatePaymentDateState() {

        String selectedType
                = (String) donationTypeCombo.getSelectedItem();

        if ("FINANCIAL".equals(selectedType)) {

            paymentDateField.setEnabled(true);

            paymentDateField.setBackground(
                    Color.WHITE
            );

        } else {

            paymentDateField.setText("");

            paymentDateField.setEnabled(false);

            paymentDateField.setBackground(
                    Color.LIGHT_GRAY
            );
        }
    }

//    private void addDonation() {
//
//        try {
//            int donationID
//                    = Integer.parseInt(
//                            donationIDField
//                                    .getText()
//                                    .trim()
//                    );
//            String donorText
//                    = donorIDField
//                            .getText()
//                            .trim();
//
//            if (donorText.isEmpty()) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Please enter the Donor ID.",
//                        "Missing Information",
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                donorIDField.requestFocus();
//
//                return;
//            }
//
//            int donorID
//                    = Integer.parseInt(donorText);
//
//            String projectText
//                    = projectIDField
//                            .getText()
//                            .trim();
//
//            if (projectText.isEmpty()) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Please enter the Project ID.",
//                        "Missing Information",
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                projectIDField.requestFocus();
//
//                return;
//            }
//
//            int projectID
//                    = Integer.parseInt(projectText);
//            String donationType
//                    = (String) donationTypeCombo
//                            .getSelectedItem();
//            String amountText
//                    = amountField
//                            .getText()
//                            .trim();
//
//            if (amountText.isEmpty()) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Please enter the donation amount.",
//                        "Missing Information",
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                amountField.requestFocus();
//
//                return;
//            }
//
//            BigDecimal amount
//                    = new BigDecimal(amountText);
//
//            if (amount.compareTo(
//                    BigDecimal.ZERO
//            ) < 0) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Amount cannot be negative.",
//                        "Invalid Amount",
//                        JOptionPane.ERROR_MESSAGE
//                );
//
//                amountField.requestFocus();
//
//                return;
//            }
//
//            String paymentStatus
//                    = (String) paymentStatusCombo
//                            .getSelectedItem();
//
//            Timestamp paymentDate = null;
//
//            if ("FINANCIAL".equals(donationType)) {
//
//                String paymentDateText
//                        = paymentDateField
//                                .getText()
//                                .trim();
//
//                if (paymentDateText.isEmpty()) {
//
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Please enter the Payment Date "
//                            + "for a financial donation.",
//                            "Missing Payment Date",
//                            JOptionPane.WARNING_MESSAGE
//                    );
//
//                    paymentDateField.requestFocus();
//
//                    return;
//                }
//
//                if (!isValidDate(paymentDateText)) {
//
//                    JOptionPane.showMessageDialog(
//                            this,
//                            "Payment Date must use the format:\n"
//                            + "yyyy-MM-dd",
//                            "Invalid Date",
//                            JOptionPane.ERROR_MESSAGE
//                    );
//
//                    paymentDateField.requestFocus();
//
//                    return;
//                }
//
//                paymentDate
//                        = Timestamp.valueOf(
//                                paymentDateText
//                                + " "
//                                + new SimpleDateFormat("HH:mm:ss")
//                                        .format(new java.util.Date())
//                        );
//            }
//
//            String receiptText
//                    = (String) receiptGeneratedCombo
//                            .getSelectedItem();
//
//            boolean receiptGenerated
//                    = Boolean.parseBoolean(
//                            receiptText
//                    );
//
//            String dateReceivedText
//                    = dateReceivedField
//                            .getText()
//                            .trim();
//
//            if (dateReceivedText.isEmpty()) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Please enter the Date Received.",
//                        "Missing Information",
//                        JOptionPane.WARNING_MESSAGE
//                );
//
//                dateReceivedField.requestFocus();
//
//                return;
//            }
//
//            if (!isValidDate(dateReceivedText)) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Date Received must use the format:\n"
//                        + "yyyy-MM-dd",
//                        "Invalid Date",
//                        JOptionPane.ERROR_MESSAGE
//                );
//
//                dateReceivedField.requestFocus();
//
//                return;
//            }
//
//            Date dateReceived
//                    = Date.valueOf(
//                            dateReceivedText
//                    );
//            Donation donation
//                    = new Donation(
//                            donationID,
//                            donorID,
//                            projectID,
//                            donationType,
//                            amount,
//                            paymentStatus,
//                            paymentDate,
//                            receiptGenerated,
//                            dateReceived
//                    );
//
//            DonationDAO dao
//                    = new DonationDAO();
//
//            dao.insertDonation(donation);
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Donation " + donationID
//                    + " was recorded successfully!",
//                    "Success",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//
//            clearInputFields();
//            loadDonations();
//            loadNextDonationID();
//
//        } catch (NumberFormatException e) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Donor ID, Project ID and Donation ID "
//                    + "must be valid whole numbers.\n\n"
//                    + "Amount must be a valid number.",
//                    "Invalid Number",
//                    JOptionPane.ERROR_MESSAGE
//            );
//
//        } catch (SQLException e) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Database error:\n"
//                    + e.getMessage(),
//                    "Database Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//
//        } catch (Exception e) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error recording donation:\n"
//                    + e.getMessage(),
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

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

        //loadNextDonationID();
    }

    private void clearInputFields() {
        donorIDField.setText("");
        projectIDField.setText("");
        amountField.setText("");
        paymentDateField.setText("");
        dateReceivedField.setText("");
        donationTypeCombo.setSelectedItem(
                "FINANCIAL"
        );
        paymentStatusCombo.setSelectedItem(
                "PENDING"
        );
        receiptGeneratedCombo.setSelectedItem(
                "FALSE"
        );
        donorIDField.requestFocus();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DonationGUI gui
                    = new DonationGUI();

            gui.setVisible(true);
        });
    }
}
