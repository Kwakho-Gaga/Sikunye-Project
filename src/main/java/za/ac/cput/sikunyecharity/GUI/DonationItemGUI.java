/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author User
 */
 
public class DonationItemGUI extends JFrame {

    private JTextField donationItemIDField;
    private JTextField donationIDField;
    private JTextField itemIDField;
    private JTextField quantityField;
    private JTextField conditionField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;

    private JTable itemTable;
    private DefaultTableModel model;

    // =========================================================
    // CONSTRUCTOR WITH DONATION ID
    // =========================================================
    public DonationItemGUI(int donationID) {

        this();

        donationIDField.setText(String.valueOf(donationID));
        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);

        donationItemIDField.setEditable(false);
        donationItemIDField.setBackground(Color.LIGHT_GRAY);

        //loadNextDonationItemID();
    }

    // =========================================================
    // MAIN CONSTRUCTOR
    // =========================================================
    public DonationItemGUI() {

        // IMPORTANT:
        // Set the JFrame properties BEFORE adding components.
        setTitle("Donation Item Management");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // =====================================================
        // TITLE
        // =====================================================

        JLabel titleLabel = new JLabel(
                "DONATION ITEM MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        titleLabel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        );

        add(titleLabel, BorderLayout.NORTH);

        // =====================================================
        // FORM PANEL
        // =====================================================

        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Record New Donated Item"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(7, 10, 7, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // =====================================================
        // DONATION ITEM ID
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        formPanel.add(
                new JLabel("Donation Item ID:"),
                gbc
        );

        donationItemIDField = new JTextField();

        donationItemIDField.setEditable(false);
        donationItemIDField.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 1;

        formPanel.add(
                donationItemIDField,
                gbc
        );

        // =====================================================
        // DONATION ID
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                new JLabel("Donation ID:"),
                gbc
        );

        donationIDField = new JTextField();

        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);

        gbc.gridx = 1;

        formPanel.add(
                donationIDField,
                gbc
        );

        // =====================================================
        // ITEM ID
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 2;

        formPanel.add(
                new JLabel("Item ID:"),
                gbc
        );

        itemIDField = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                itemIDField,
                gbc
        );

        // =====================================================
        // QUANTITY
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 3;

        formPanel.add(
                new JLabel("Quantity:"),
                gbc
        );

        quantityField = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                quantityField,
                gbc
        );

        // =====================================================
        // CONDITION
        // =====================================================

        gbc.gridx = 0;
        gbc.gridy = 4;

        formPanel.add(
                new JLabel("Condition:"),
                gbc
        );

        conditionField = new JTextField();

        gbc.gridx = 1;

        formPanel.add(
                conditionField,
                gbc
        );

        // =====================================================
        // BUTTONS
        // =====================================================

        JPanel formButtonPanel = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        10,
                        5
                )
        );

        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        clearButton = new JButton("Clear");

        formButtonPanel.add(addButton);
        formButtonPanel.add(updateButton);
        formButtonPanel.add(deleteButton);
        formButtonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;

        formPanel.add(
                formButtonPanel,
                gbc
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JPanel backButtonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        JButton backButton = new JButton("Back");

        backButton.addActionListener(e -> {
            new DonationGUI().setVisible(true);
            dispose();
        });

        backButtonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;

        formPanel.add(
                backButtonPanel,
                gbc
        );

        // Add form panel to the left side
        add(
                formPanel,
                BorderLayout.WEST
        );

        // =====================================================
        // INVENTORY TABLE
        // =====================================================

        String[] columns = {
            "DONATION ITEM ID",
            "DONATION ID",
            "ITEM ID",
            "QUANTITY",
            "CONDITION"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column
            ) {
                return false;
            }
        };

        itemTable = new JTable(model);

        JScrollPane tableScrollPane =
                new JScrollPane(itemTable);

        tableScrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Donated Items Inventory"
                )
        );

        add(
                tableScrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        addButton.addActionListener(
                e -> addDonationItem()
        );

        //updateButton.addActionListener(
        //        e -> updateDonationItem()
        //);

        deleteButton.addActionListener(
                e -> deleteDonationItem()
        );

        clearButton.addActionListener(
                e -> clearForm()
        );

        // =====================================================
        // DATABASE METHODS
        // =====================================================

        //loadDonationItems();
        //loadNextDonationItemID();

        // IMPORTANT:
        // setVisible(true) is NOT placed here.
        // The JFrame will be made visible in main().
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            DonationItemGUI myGUI =
                    new DonationItemGUI();

            myGUI.setVisible(true);
        });
    }

//    private void loadNextDonationItemID() {
//        try {
//            DonationItemDAO dao = new DonationItemDAO();
//
//            int nextID = dao.getNextDonationItemID();
//
//            donationItemIDField.setText(String.valueOf(nextID));
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Unable to generate Donation Item ID:\n"
//                    + e.getMessage(),
//                    "Database Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

//    private void loadDonationItems() {
//        model.setRowCount(0);
//
//        try {
//            DonationItemDAO dao = new DonationItemDAO();
//            List<DonationItem> items = dao.getAllDonationItems();
//
//            for (DonationItem item : items) {
//                model.addRow(new Object[]{
//                    item.getDonationItemID(),
//                    item.getDonationID(),
//                    item.getItemID(),
//                    item.getQuantity(),
//                    item.getCondition()
//                });
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error loading donation items:\n" + e.getMessage(),
//                    "Database Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

    // =========================================================
    // ADD DONATION ITEM
    // =========================================================

    private void addDonationItem() {

//        try {
//            String donationText = donationIDField.getText().trim();
//            String itemText = itemIDField.getText().trim();
//            String quantityText = quantityField.getText().trim();
//            String condition = conditionField.getText().trim().toUpperCase();
//
//            // Validate empty fields
//            if (donationText.isEmpty()
//                    || itemText.isEmpty()
//                    || quantityText.isEmpty()
//                    || condition.isEmpty()) {
//
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Please complete all fields.",
//                        "Missing Information",
//                        JOptionPane.WARNING_MESSAGE
//                );
//                return;
//            }
//
//            int donationID = Integer.parseInt(donationText);
//            int donationItemID = Integer.parseInt(
//                    donationItemIDField.getText().trim()
//            );
//            int itemID = Integer.parseInt(itemText);
//            int quantity = Integer.parseInt(quantityText);
//
//            if (quantity <= 0) {
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Quantity must be greater than zero.",
//                        "Invalid Quantity",
//                        JOptionPane.WARNING_MESSAGE
//                );
//                quantityField.requestFocus();
//                return;
//            }
//
//            DonationDAO donationDAO = new DonationDAO();
//
//            // Confirm that the donation exists and is IN-KIND
//            if (!donationDAO.isInKindDonation(donationID)) {
//                JOptionPane.showMessageDialog(
//                        this,
//                        "This Donation ID does not exist or is not an IN-KIND donation.",
//                        "Invalid Donation ID",
//                        JOptionPane.WARNING_MESSAGE
//                );
//                donationIDField.requestFocus();
//                return;
//            }
//
//            DonationItemDAO dao = new DonationItemDAO();
//
//            // Check whether this Donation ID already has an item
//            if (dao.donationIDAlreadyUsed(donationID)) {
//                JOptionPane.showMessageDialog(
//                        this,
//                        "Donation ID " + donationID
//                        + " has already been used to capture a donated item.\n"
//                        + "You cannot add another item to this donation.",
//                        "Duplicate Donation ID",
//                        JOptionPane.WARNING_MESSAGE
//                );
//                return;
//            }
//
//            DonationItem newItem = new DonationItem(
//                    donationItemID,
//                    donationID,
//                    itemID,
//                    quantity,
//                    condition
//            );
//
//            dao.insertDonationItem(newItem);
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Donation item added successfully!",
//                    "Success",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//
//            clearForm();
//            loadDonationItems();
//
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Please enter valid numeric values for the IDs and quantity.",
//                    "Invalid Input",
//                    JOptionPane.WARNING_MESSAGE
//            );
//
//        } catch (SQLException e) {
//
//            // Print the technical error in the NetBeans Output window
//            e.printStackTrace();
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "The donated item could not be captured.\n"
//                    + "Please verify the information and try again.",
//                    "Donation Item Not Saved",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
    }

//    private void updateDonationItem() {
//
//        int selectedRow = itemTable.getSelectedRow();
//
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Select an item to update."
//            );
//            return;
//        }
//
//        int donationItemID = Integer.parseInt(
//                model.getValueAt(selectedRow, 0).toString()
//        );
//
//        String newCondition = JOptionPane.showInputDialog(
//                this,
//                "Enter the new condition:"
//        );
//
//        if (newCondition == null || newCondition.trim().isEmpty()) {
//            return;
//        }
//
//        try {
//            newCondition = newCondition.trim().toUpperCase();
//
//            DonationItemDAO dao = new DonationItemDAO();
//
//            dao.updateDonationItemCondition(
//                    donationItemID,
//                    newCondition
//            );
//
//            loadDonationItems();
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Item updated successfully!"
//            );
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error updating item:\n" + e.getMessage(),
//                    "Update Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }

    // =========================================================
    // DELETE DONATION ITEM
    // =========================================================

    private void deleteDonationItem() {

        int selectedRow =
                itemTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an item to delete."
            );

            return;
        }

        int donationItemID =
                Integer.parseInt(
                        model.getValueAt(
                                selectedRow,
                                0
                        ).toString()
                );

        int confirmation =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this donation item?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

//        try {
//            DonationItemDAO dao = new DonationItemDAO();
//
//            dao.deleteDonationItem(donationItemID);
//
//            loadDonationItems();
//            loadNextDonationItemID();
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Item deleted successfully!"
//            );
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Error deleting item:\n" + e.getMessage(),
//                    "Delete Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
    }

    // =========================================================
    // CLEAR FORM
    // =========================================================

    private void clearForm() {

        itemIDField.setText("");
        quantityField.setText("");
        conditionField.setText("");

        // Keep the current Donation ID.
        //loadNextDonationItemID();

        itemIDField.requestFocus();
    }
}
