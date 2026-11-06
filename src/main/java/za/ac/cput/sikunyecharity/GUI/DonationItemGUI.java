package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.Domain.DonationItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
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
        setBorder(new javax.swing.border.EmptyBorder(15, 15, 15, 15));
        createGUI();

        if (donationID > 0) {
            setDonationID(donationID);
        }

        loadDonationItems();
        loadNextDonationItemID();
    }

    // -------------------- CREATE GUI - MATCHES DonationGUI STYLE --------------------
    private void createGUI() {
        JLabel titleLabel = new JLabel("DONATION ITEM MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(15, 30, 50));
        titleLabel.setBorder(new javax.swing.border.EmptyBorder(10, 5, 15, 5));
        add(titleLabel, BorderLayout.NORTH);

        // --- LEFT FORM PANEL - same as DonationGUI ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Record Donated Item"));
        formPanel.setPreferredSize(new Dimension(340, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 10, 7, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addFormField(formPanel, gbc, 0, "Donation Item ID:");
        donationItemIDField = new JTextField();
        donationItemIDField.setEditable(false);
        donationItemIDField.setBackground(Color.LIGHT_GRAY);
        addFormComponent(formPanel, gbc, 0, donationItemIDField);

        addFormField(formPanel, gbc, 1, "Donation ID:");
        donationIDField = new JTextField();
        donationIDField.setEditable(false);
        donationIDField.setBackground(Color.LIGHT_GRAY);
        addFormComponent(formPanel, gbc, 1, donationIDField);

        addFormField(formPanel, gbc, 2, "Item ID:");
        itemIDField = new JTextField();
        addFormComponent(formPanel, gbc, 2, itemIDField);

        addFormField(formPanel, gbc, 3, "Quantity:");
        quantityField = new JTextField();
        addFormComponent(formPanel, gbc, 3, quantityField);

        addFormField(formPanel, gbc, 4, "Condition:");
        conditionField = new JTextField();
        addFormComponent(formPanel, gbc, 4, conditionField);

        // Buttons - same layout as DonationGUI
        JPanel formButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        formButtonPanel.setBackground(Color.WHITE);
        addButton = new JButton("Add Item");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");
        backButton = new JButton("Back to Donation");

        formButtonPanel.add(addButton);
        formButtonPanel.add(updateButton);
        formButtonPanel.add(deleteButton);
        formButtonPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2; gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(formButtonPanel, gbc);

        gbc.gridy = 6; gbc.insets = new Insets(12, 10, 7, 10);
        formPanel.add(backButton, gbc);

        JLabel instructionLabel = new JLabel("<html><center>Select a row to Update or Delete.<br>Click Delete in the table to delete that item.</center></html>");
        instructionLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        instructionLabel.setForeground(Color.DARK_GRAY);
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7;
        formPanel.add(instructionLabel, gbc);

        add(formPanel, BorderLayout.WEST);

        // --- TABLE - same as DonationGUI, with DELETE on each row ---
        String[] columns = {"DONATION ITEM ID", "DONATION ID", "ITEM ID", "QUANTITY", "CONDITION", "DELETE"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only DELETE column clickable
            }
        };

        itemTable = new JTable(model);
        itemTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        itemTable.setRowHeight(30);

        itemTable.getColumnModel().getColumn(0).setPreferredWidth(140);
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(110);
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(110);
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(150);
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(90);

        itemTable.getColumn("DELETE").setCellRenderer(new ButtonRenderer());
        itemTable.getColumn("DELETE").setCellEditor(new ButtonEditor(new JCheckBox()));

        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Donated Items Inventory"));
        add(tableScrollPane, BorderLayout.CENTER);

        // -------------------- BUTTON ACTIONS --------------------
        addButton.addActionListener(e -> addDonationItem());
        updateButton.addActionListener(e -> updateDonationItem());
        deleteButton.addActionListener(e -> deleteDonationItem());
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> goBack());
    }

    // ===== RENDERER FOR DELETE BUTTON - same as DonationGUI =====
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

    // ===== EDITOR FOR DELETE BUTTON - same as DonationGUI =====
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
                deleteDonationItemByRow(currentRow);
            }
            isPushed = false;
            return "Delete";
        }
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label) {
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);
    }

    private void addFormComponent(JPanel panel, GridBagConstraints gbc, int row, JComponent component) {
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 1;
        component.setPreferredSize(new Dimension(150, 27));
        panel.add(component, gbc);
    }

    // -------------------- SET DONATION ID --------------------
    public void setDonationID(int donationID) {
        donationIDField.setText(String.valueOf(donationID));
        donationIDField.setEditable(false);
    }

    // -------------------- BACK NAVIGATION --------------------
    private void goBack() {
        if (backAction != null) {
            backAction.run();
        } else {
            Window currentWindow = SwingUtilities.getWindowAncestor(this);
            JFrame currentFrame = (currentWindow instanceof JFrame) ? (JFrame) currentWindow : null;
            AppNavigation.open(currentFrame, "Donor");
        }
    }

    // -------------------- LOAD NEXT DONATION ITEM ID --------------------
    private void loadNextDonationItemID() {
        try {
            DonationItemDAO dao = new DonationItemDAO();
            int nextID = dao.getNextDonationItemID();
            donationItemIDField.setText(String.valueOf(nextID));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Unable to generate Donation Item ID:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
                    item.getCondition(),
                    "Delete"
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading donation items:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------- ADD DONATION ITEM --------------------
    private void addDonationItem() {
        try {
            String donationText = donationIDField.getText().trim();
            String itemText = itemIDField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String condition = conditionField.getText().trim().toUpperCase();

            if (donationText.isEmpty() || itemText.isEmpty() || quantityText.isEmpty() || condition.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please complete all fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int donationID = Integer.parseInt(donationText);
            int donationItemID = Integer.parseInt(donationItemIDField.getText().trim());
            int itemID = Integer.parseInt(itemText);
            int quantity = Integer.parseInt(quantityText);

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than zero.", "Invalid Quantity", JOptionPane.WARNING_MESSAGE);
                quantityField.requestFocus();
                return;
            }

            DonationDAO donationDAO = new DonationDAO();
            if (!donationDAO.isInKindDonation(donationID)) {
                JOptionPane.showMessageDialog(this, "This Donation ID does not exist or is not an IN-KIND donation.", "Invalid Donation ID", JOptionPane.WARNING_MESSAGE);
                donationIDField.requestFocus();
                return;
            }

            DonationItemDAO dao = new DonationItemDAO();
            if (dao.donationIDAlreadyUsed(donationID)) {
                JOptionPane.showMessageDialog(this, "Donation ID " + donationID + " has already been used to capture a donated item.\nYou cannot add another item to this donation.", "Duplicate Donation ID", JOptionPane.WARNING_MESSAGE);
                return;
            }

            DonationItem newItem = new DonationItem(donationItemID, donationID, itemID, quantity, condition);
            dao.insertDonationItem(newItem);

            JOptionPane.showMessageDialog(this, "Donation item added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDonationItems();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for the IDs and quantity.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "The donated item could not be captured.\nPlease verify the information and try again.", "Donation Item Not Saved", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------- UPDATE DONATION ITEM --------------------
    private void updateDonationItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to update.");
            return;
        }
        int donationItemID = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
        String newCondition = JOptionPane.showInputDialog(this, "Enter the new condition:");
        if (newCondition == null || newCondition.trim().isEmpty()) return;
        try {
            newCondition = newCondition.trim().toUpperCase();
            DonationItemDAO dao = new DonationItemDAO();
            dao.updateDonationItemCondition(donationItemID, newCondition);
            loadDonationItems();
            JOptionPane.showMessageDialog(this, "Item updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating item:\n" + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------- DELETE DONATION ITEM - via form button --------------------
    private void deleteDonationItem() {
        int selectedRow = itemTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to delete.");
            return;
        }
        deleteDonationItemByRow(selectedRow);
    }

    // -------------------- DELETE DONATION ITEM - via table DELETE button --------------------
    private void deleteDonationItemByRow(int row) {
        int donationItemID = Integer.parseInt(model.getValueAt(row, 0).toString());
        int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this donation item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirmation != JOptionPane.YES_OPTION) return;
        try {
            DonationItemDAO dao = new DonationItemDAO();
            dao.deleteDonationItem(donationItemID);
            loadDonationItems();
            loadNextDonationItemID();
            JOptionPane.showMessageDialog(this, "Item deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting item:\n" + e.getMessage(), "Delete Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------------------- CLEAR FORM --------------------
    private void clearForm() {
        itemIDField.setText("");
        quantityField.setText("");
        conditionField.setText("");
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
