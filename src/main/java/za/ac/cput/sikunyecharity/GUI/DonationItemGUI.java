package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.Domain.DonationItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class DonationItemGUI extends JPanel {

    private JTextField donationItemIDField;
    private JTextField donationIDField;
    private JTextField itemIDField;
    private JTextField quantityField;
    private JTextField conditionField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton backButton;

    private JTable itemTable;
    private DefaultTableModel model;

    // Used by the main application to return to DonationGUI.
    private Runnable backAction;

    // -------------------- CONSTRUCTORS --------------------
    public DonationItemGUI() {
        this(0, null);
    }

    public DonationItemGUI(int donationID) {
        this(donationID, null);
    }

    public DonationItemGUI(int donationID, Runnable backAction) {

        this.backAction = backAction;

        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        createGUI();

        if (donationID > 0) {
            setDonationID(donationID);
        }

        loadDonationItems();
        loadNextDonationItemID();
    }

    // -------------------- CREATE GUI --------------------
    private void createGUI() {

        JLabel titleLabel = new JLabel(
                "DONATION ITEM MANAGEMENT",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(15, 30, 50));
        titleLabel.setBorder(
                new EmptyBorder(10, 5, 15, 5)
        );

        add(titleLabel, BorderLayout.NORTH);

        // -------------------- FORM PANEL --------------------
        JPanel formPanel = new JPanel(new GridBagLayout());

        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(350, 0));

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Record New Donated Item"
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Donation Item ID
        addFormField(formPanel, gbc, 0, "Donation Item ID:");

        donationItemIDField = new JTextField();
        donationItemIDField.setEditable(false);
        donationItemIDField.setBackground(Color.LIGHT_GRAY);

        addFormComponent(formPanel, gbc, 0, donationItemIDField);

        // Donation ID
        addFormField(formPanel, gbc, 1, "Donation ID:");

        donationIDField = new JTextField();
        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);

        addFormComponent(formPanel, gbc, 1, donationIDField);

        // Item ID
        addFormField(formPanel, gbc, 2, "Item ID:");

        itemIDField = new JTextField();

        addFormComponent(formPanel, gbc, 2, itemIDField);

        // Quantity
        addFormField(formPanel, gbc, 3, "Quantity:");

        quantityField = new JTextField();

        addFormComponent(formPanel, gbc, 3, quantityField);

        // Condition
        addFormField(formPanel, gbc, 4, "Condition:");

        conditionField = new JTextField();

        addFormComponent(formPanel, gbc, 4, conditionField);

        // -------------------- BUTTONS --------------------
        JPanel formButtonPanel = new JPanel(
                new GridLayout(2, 2, 8, 8)
        );

        formButtonPanel.setBackground(Color.WHITE);

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
        gbc.insets = new Insets(15, 10, 10, 10);

        formPanel.add(formButtonPanel, gbc);

        // -------------------- BACK BUTTON --------------------
        JPanel backButtonPanel = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        backButtonPanel.setBackground(Color.WHITE);

        backButton = new JButton("Back to Donations");

        backButtonPanel.add(backButton);

        gbc.gridy = 6;
        gbc.insets = new Insets(5, 10, 10, 10);

        formPanel.add(backButtonPanel, gbc);

        add(formPanel, BorderLayout.WEST);

        // -------------------- DONATED ITEMS TABLE --------------------
        String[] columns = {
            "DONATION ITEM ID",
            "DONATION ID",
            "ITEM ID",
            "QUANTITY",
            "CONDITION"
        };

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(model);

        itemTable.setRowHeight(28);
        itemTable.setFont(new Font("Arial", Font.PLAIN, 12));

        itemTable.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        JScrollPane tableScrollPane = new JScrollPane(itemTable);

        tableScrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Donated Items Inventory"
                )
        );

        add(tableScrollPane, BorderLayout.CENTER);

        // -------------------- BUTTON ACTIONS --------------------
        addButton.addActionListener(e -> addDonationItem());

        updateButton.addActionListener(e -> updateDonationItem());

        deleteButton.addActionListener(e -> deleteDonationItem());

        clearButton.addActionListener(e -> clearForm());

        backButton.addActionListener(e -> goBack());
    }

    // -------------------- FORM FIELD HELPERS --------------------
    private void addFormField(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String label) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        panel.add(new JLabel(label), gbc);
    }

    private void addFormComponent(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            JComponent component) {

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        component.setPreferredSize(new Dimension(150, 27));

        panel.add(component, gbc);
    }

    // -------------------- SET DONATION ID --------------------
    public void setDonationID(int donationID) {

        donationIDField.setText(String.valueOf(donationID));

        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);
    }

    // -------------------- BACK NAVIGATION --------------------
    private void goBack() {

        if (backAction != null) {

            // Main application handles CardLayout navigation.
            backAction.run();

        } else {

            // Standalone testing only.
            JFrame frame = new JFrame("Donation Management");

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(1300, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new DonationGUI());
            frame.setVisible(true);

            Window currentWindow
                    = SwingUtilities.getWindowAncestor(this);

            if (currentWindow != null) {
                currentWindow.dispose();
            }
        }
    }

    // -------------------- LOAD NEXT DONATION ITEM ID --------------------
    private void loadNextDonationItemID() {

        try {

            DonationItemDAO dao = new DonationItemDAO();

            int nextID = dao.getNextDonationItemID();

            donationItemIDField.setText(String.valueOf(nextID));

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate Donation Item ID:\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- LOAD DONATION ITEMS --------------------
    private void loadDonationItems() {

        model.setRowCount(0);

        try {

            DonationItemDAO dao = new DonationItemDAO();

            List<DonationItem> items = dao.getAllDonationItems();

            for (DonationItem item : items) {

                model.addRow(new Object[]{
                    item.getDonationItemID(),
                    item.getDonationID(),
                    item.getItemID(),
                    item.getQuantity(),
                    item.getCondition()
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading donation items:\n"
                    + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- ADD DONATION ITEM --------------------
    private void addDonationItem() {

        try {

            String donationText = donationIDField.getText().trim();
            String itemText = itemIDField.getText().trim();
            String quantityText = quantityField.getText().trim();

            String condition
                    = conditionField.getText().trim().toUpperCase();

            // Validate empty fields.
            if (donationText.isEmpty()
                    || itemText.isEmpty()
                    || quantityText.isEmpty()
                    || condition.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            int donationID = Integer.parseInt(donationText);

            int donationItemID = Integer.parseInt(
                    donationItemIDField.getText().trim()
            );

            int itemID = Integer.parseInt(itemText);

            int quantity = Integer.parseInt(quantityText);

            // Validate quantity.
            if (quantity <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than zero.",
                        "Invalid Quantity",
                        JOptionPane.WARNING_MESSAGE
                );

                quantityField.requestFocus();

                return;
            }

            DonationDAO donationDAO = new DonationDAO();

            // Confirm that the donation exists and is IN-KIND.
            if (!donationDAO.isInKindDonation(donationID)) {

                JOptionPane.showMessageDialog(
                        this,
                        "This Donation ID does not exist "
                        + "or is not an IN-KIND donation.",
                        "Invalid Donation ID",
                        JOptionPane.WARNING_MESSAGE
                );

                donationIDField.requestFocus();

                return;
            }

            DonationItemDAO dao = new DonationItemDAO();

            // Check whether this Donation ID already has an item.
            if (dao.donationIDAlreadyUsed(donationID)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Donation ID " + donationID
                        + " has already been used to capture "
                        + "a donated item.\n"
                        + "You cannot add another item to this donation.",
                        "Duplicate Donation ID",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            DonationItem newItem = new DonationItem(
                    donationItemID,
                    donationID,
                    itemID,
                    quantity,
                    condition
            );

            dao.insertDonationItem(newItem);

            JOptionPane.showMessageDialog(
                    this,
                    "Donation item added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadDonationItems();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values "
                    + "for the IDs and quantity.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );

        } catch (SQLException e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "The donated item could not be captured.\n"
                    + "Please verify the information and try again.",
                    "Donation Item Not Saved",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- UPDATE DONATION ITEM --------------------
    private void updateDonationItem() {

        int selectedRow = itemTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an item to update."
            );

            return;
        }

        int modelRow
                = itemTable.convertRowIndexToModel(selectedRow);

        int donationItemID = Integer.parseInt(
                model.getValueAt(modelRow, 0).toString()
        );

        String newCondition = JOptionPane.showInputDialog(
                this,
                "Enter the new condition:"
        );

        if (newCondition == null
                || newCondition.trim().isEmpty()) {

            return;
        }

        try {

            newCondition = newCondition.trim().toUpperCase();

            DonationItemDAO dao = new DonationItemDAO();

            dao.updateDonationItemCondition(
                    donationItemID,
                    newCondition
            );

            loadDonationItems();

            JOptionPane.showMessageDialog(
                    this,
                    "Item updated successfully!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error updating item:\n" + e.getMessage(),
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- DELETE DONATION ITEM --------------------
    private void deleteDonationItem() {

        int selectedRow = itemTable.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Select an item to delete."
            );

            return;
        }

        int modelRow
                = itemTable.convertRowIndexToModel(selectedRow);

        int donationItemID = Integer.parseInt(
                model.getValueAt(modelRow, 0).toString()
        );

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this donation item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            DonationItemDAO dao = new DonationItemDAO();

            dao.deleteDonationItem(donationItemID);

            loadDonationItems();
            loadNextDonationItemID();

            JOptionPane.showMessageDialog(
                    this,
                    "Item deleted successfully!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting item:\n" + e.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- CLEAR FORM --------------------
    private void clearForm() {

        itemIDField.setText("");
        quantityField.setText("");
        conditionField.setText("");

        // Keep the current Donation ID.
        loadNextDonationItemID();

        itemIDField.requestFocus();
    }

    // -------------------- STANDALONE TESTING --------------------
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Donation Item Management");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 700);
            frame.setLocationRelativeTo(null);

            frame.setContentPane(new DonationItemGUI());

            frame.setVisible(true);
        });
    }
}
