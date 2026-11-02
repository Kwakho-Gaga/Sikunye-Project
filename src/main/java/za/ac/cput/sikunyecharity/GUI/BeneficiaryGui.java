package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import za.ac.cput.sikunyecharity.DAO.BeneficiaryDAO;
import za.ac.cput.sikunyecharity.Domain.Beneficiary;

public class BeneficiaryGui extends JFrame implements ActionListener {

    private final BeneficiariesMenu menu;
    private final BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
    private JTextField txtBeneficiaryID;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtFamilySize;
    private JTextField txtAddress;
    private JTextField txtLivingSituation;
    private JButton btnAdd;
    private JButton btnView;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JButton btnBack;
    private JTable tblBeneficiaries;
    private DefaultTableModel tableModel;

    public BeneficiaryGui(BeneficiariesMenu menu) {
        super("Sikunye Charity - Beneficiary Management");
        this.menu = menu;
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setOpaque(false);
        JPanel form = new JPanel(new GridLayout(6, 2, 18, 9));
        form.setOpaque(false);
        txtBeneficiaryID = new JTextField();
        txtBeneficiaryID.setEditable(false);
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtFamilySize = new JTextField();
        txtAddress = new JTextField();
        txtLivingSituation = new JTextField();
        form.add(BeneficiaryPageStyle.label("Beneficiary ID"));
        form.add(txtBeneficiaryID);
        form.add(BeneficiaryPageStyle.label("First Name"));
        form.add(txtFirstName);
        form.add(BeneficiaryPageStyle.label("Last Name"));
        form.add(txtLastName);
        form.add(BeneficiaryPageStyle.label("Family Size"));
        form.add(txtFamilySize);
        form.add(BeneficiaryPageStyle.label("Address"));
        form.add(txtAddress);
        form.add(BeneficiaryPageStyle.label("Living Situation"));
        form.add(txtLivingSituation);
        top.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actions.setOpaque(false);
        btnAdd = button("Add", new Color(39, 189, 114), actions);
        btnView = button("View", new Color(31, 151, 204), actions);
        btnUpdate = button("Update", new Color(207, 160, 18), actions);
        btnDelete = button("Delete", new Color(208, 80, 75), actions);
        btnClear = button("Clear", new Color(112, 126, 136), actions);
        top.add(actions, BorderLayout.SOUTH);
        content.add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name",
            "Family Size", "Address", "Living Situation"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblBeneficiaries = new JTable(tableModel);
        tblBeneficiaries.setRowHeight(28);
        tblBeneficiaries.setAutoCreateRowSorter(true);
        tblBeneficiaries.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    showSelectedBeneficiary();
                }
            }
        });
        content.add(new JScrollPane(tblBeneficiaries), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.setOpaque(false);
        btnBack = button("Back to Beneficiaries", BeneficiaryPageStyle.DARK, bottom);
        content.add(bottom, BorderLayout.SOUTH);
        BeneficiaryPageStyle.setup(this, "Manage Beneficiaries",
                "Add people, review their details, and maintain their records.", content);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (BeneficiaryGui.this.menu != null) {
                    BeneficiaryGui.this.menu.setVisible(true);
                }
            }
        });
        loadBeneficiaries();
    }

    public BeneficiaryGui() {
        this(null);
    }

    private JButton button(String title, Color color, JPanel panel) {
        JButton button = new JButton(title);
        BeneficiaryPageStyle.styleButton(button, color);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    private void loadBeneficiaries() {
        try {
            tableModel.setRowCount(0);
            for (Beneficiary beneficiary : beneficiaryDAO.getAll()) {
                tableModel.addRow(new Object[]{beneficiary.getBeneficiaryId(),
                    beneficiary.getFirstName(), beneficiary.getLastName(),
                    beneficiary.getFamilySize(), beneficiary.getAddress(),
                    beneficiary.getLivingSituation()});
            }
            clearFields();
        } catch (SQLException ex) {
            showError("Could not load beneficiaries", ex);
        }
    }

    private void showSelectedBeneficiary() {
        int selected = tblBeneficiaries.getSelectedRow();
        if (selected < 0) {
            return;
        }
        int row = tblBeneficiaries.convertRowIndexToModel(selected);
        txtBeneficiaryID.setText(tableModel.getValueAt(row, 0).toString());
        txtFirstName.setText(tableModel.getValueAt(row, 1).toString());
        txtLastName.setText(tableModel.getValueAt(row, 2).toString());
        txtFamilySize.setText(tableModel.getValueAt(row, 3).toString());
        txtAddress.setText(tableModel.getValueAt(row, 4).toString());
        txtLivingSituation.setText(tableModel.getValueAt(row, 5).toString());
    }

    private Beneficiary formBeneficiary(boolean existing) {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String address = txtAddress.getText().trim();
        String situation = txtLivingSituation.getText().trim();
        if (firstName.isEmpty() || lastName.isEmpty() || address.isEmpty()
                || situation.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete all beneficiary details.");
            return null;
        }
        if (firstName.length() > 100 || lastName.length() > 100
                || address.length() > 250 || situation.length() > 150) {
            JOptionPane.showMessageDialog(this, "One or more fields are too long.");
            return null;
        }
        int familySize;
        try {
            familySize = Integer.parseInt(txtFamilySize.getText().trim());
            if (familySize < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Family size must be a positive whole number.");
            return null;
        }
        if (existing && txtBeneficiaryID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a beneficiary first.");
            return null;
        }
        int id = existing ? Integer.parseInt(txtBeneficiaryID.getText()) : 0;
        return new Beneficiary(id, firstName, lastName, familySize,
                address, situation);
    }

    private void addBeneficiary() {
        Beneficiary beneficiary = formBeneficiary(false);
        if (beneficiary == null) return;
        try {
            beneficiaryDAO.add(beneficiary);
            loadBeneficiaries();
            clearFields();
            JOptionPane.showMessageDialog(this, "Beneficiary added.");
        } catch (SQLException ex) {
            showError("Could not add beneficiary", ex);
        }
    }

    private void updateBeneficiary() {
        Beneficiary beneficiary = formBeneficiary(true);
        if (beneficiary == null) return;
        try {
            if (beneficiaryDAO.update(beneficiary)) {
                loadBeneficiaries();
                clearFields();
                JOptionPane.showMessageDialog(this, "Beneficiary updated.");
            } else {
                JOptionPane.showMessageDialog(this, "Beneficiary no longer exists.");
            }
        } catch (SQLException ex) {
            showError("Could not update beneficiary", ex);
        }
    }

    private void deleteBeneficiary() {
        if (txtBeneficiaryID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Select a beneficiary first.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete this beneficiary and their assigned needs?",
                "Confirm deletion", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        try {
            if (beneficiaryDAO.delete(Integer.parseInt(txtBeneficiaryID.getText()))) {
                loadBeneficiaries();
                clearFields();
                JOptionPane.showMessageDialog(this, "Beneficiary deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Beneficiary no longer exists.");
            }
        } catch (SQLException ex) {
            showError("Could not delete beneficiary", ex);
        }
    }

    private void clearFields() {
        tblBeneficiaries.clearSelection();
        txtBeneficiaryID.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtFamilySize.setText("");
        txtAddress.setText("");
        txtLivingSituation.setText("");
    }

    private void showError(String title, SQLException ex) {
        JOptionPane.showMessageDialog(this, title + ":\n" + ex.getMessage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAdd) {
            addBeneficiary();
        } else if (source == btnView) {
            loadBeneficiaries();
        } else if (source == btnUpdate) {
            updateBeneficiary();
        } else if (source == btnDelete) {
            deleteBeneficiary();
        } else if (source == btnClear) {
            clearFields();
        } else if (source == btnBack) {
            if (menu == null) {
                new BeneficiariesMenu().setVisible(true);
            } else {
                menu.setVisible(true);
            }
            dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BeneficiaryGui().setVisible(true);
            }
        });
    }
}
