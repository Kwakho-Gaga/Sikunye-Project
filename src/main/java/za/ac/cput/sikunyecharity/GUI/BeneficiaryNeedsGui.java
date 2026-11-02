package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.BeneficiaryDAO;
import za.ac.cput.sikunyecharity.DAO.BeneficiaryNeedsDAO;
import za.ac.cput.sikunyecharity.DAO.NeedsDAO;
import za.ac.cput.sikunyecharity.Domain.Beneficiary;
import za.ac.cput.sikunyecharity.Domain.BeneficiaryNeed;
import za.ac.cput.sikunyecharity.Domain.Need;

public class BeneficiaryNeedsGui extends JFrame implements ActionListener {

    private final BeneficiariesMenu menu;
    private final BeneficiaryDAO beneficiaryDAO = new BeneficiaryDAO();
    private final NeedsDAO needsDAO = new NeedsDAO();
    private final BeneficiaryNeedsDAO beneficiaryNeedsDAO = new BeneficiaryNeedsDAO();
    private JComboBox<Beneficiary> cmbBeneficiary;
    private JComboBox<Need> cmbNeed;
    private JSpinner spnQuantity;
    private JList<BeneficiaryNeed> lstAssigned;
    private DefaultListModel<BeneficiaryNeed> assignedModel;
    private JTable tblBeneficiaries;
    private DefaultTableModel tableModel;
    private JButton btnAssign;
    private JButton btnRemove;
    private JButton btnRefresh;
    private JButton btnBack;
    private boolean loading;

    public BeneficiaryNeedsGui(BeneficiariesMenu menu) {
        super("Sikunye Charity - Beneficiary Needs");
        this.menu = menu;
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBorder(new EmptyBorder(25, 35, 25, 35));

        JPanel top = new JPanel(new BorderLayout(0, 12));
        top.setOpaque(false);
        JPanel form = new JPanel(new GridLayout(2, 4, 12, 7));
        form.setOpaque(false);
        cmbBeneficiary = new JComboBox<>();
        cmbNeed = new JComboBox<>();
        spnQuantity = new JSpinner(new SpinnerNumberModel(1, 1, 1000000, 1));
        form.add(BeneficiaryPageStyle.label("Select Beneficiary"));
        form.add(BeneficiaryPageStyle.label("Select Need"));
        form.add(BeneficiaryPageStyle.label("Quantity"));
        form.add(new javax.swing.JLabel());
        form.add(cmbBeneficiary);
        form.add(cmbNeed);
        form.add(spnQuantity);
        form.add(new javax.swing.JLabel());
        top.add(form, BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actions.setOpaque(false);
        btnAssign = button("Assign", new java.awt.Color(39, 189, 114), actions);
        btnRemove = button("Remove Selected", new java.awt.Color(208, 80, 75), actions);
        btnRefresh = button("Refresh", BeneficiaryPageStyle.CYAN, actions);
        top.add(actions, BorderLayout.SOUTH);
        content.add(top, BorderLayout.NORTH);

        assignedModel = new DefaultListModel<>();
        lstAssigned = new JList<>(assignedModel);
        JScrollPane assignedScroll = new JScrollPane(lstAssigned);
        assignedScroll.setBorder(javax.swing.BorderFactory.createTitledBorder("Assigned Needs"));

        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name",
            "Family Size", "Living Situation"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblBeneficiaries = new JTable(tableModel);
        tblBeneficiaries.setRowHeight(27);
        tblBeneficiaries.setAutoCreateRowSorter(true);
        JScrollPane beneficiaryScroll = new JScrollPane(tblBeneficiaries);
        beneficiaryScroll.setBorder(javax.swing.BorderFactory.createTitledBorder("Beneficiary List"));

        JSplitPane lists = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                assignedScroll, beneficiaryScroll);
        lists.setResizeWeight(0.55);
        lists.setBorder(null);
        content.add(lists, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.setOpaque(false);
        btnBack = button("Back to Beneficiaries", BeneficiaryPageStyle.DARK, bottom);
        content.add(bottom, BorderLayout.SOUTH);

        cmbBeneficiary.addActionListener(this);
        tblBeneficiaries.getSelectionModel().addListSelectionListener(
                new javax.swing.event.ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && tblBeneficiaries.getSelectedRow() >= 0) {
                    int row = tblBeneficiaries.convertRowIndexToModel(
                            tblBeneficiaries.getSelectedRow());
                    int id = (Integer) tableModel.getValueAt(row, 0);
                    selectBeneficiary(id);
                }
            }
        });

        BeneficiaryPageStyle.setup(this, "Beneficiary Needs",
                "Assign the support each beneficiary needs.", content);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (BeneficiaryNeedsGui.this.menu != null) {
                    BeneficiaryNeedsGui.this.menu.setVisible(true);
                }
            }
        });
        loadData();
    }

    public BeneficiaryNeedsGui() {
        this(null);
    }

    private JButton button(String title, java.awt.Color color, JPanel panel) {
        JButton button = new JButton(title);
        BeneficiaryPageStyle.styleButton(button, color);
        button.addActionListener(this);
        panel.add(button);
        return button;
    }

    private void loadData() {
        Beneficiary selected = (Beneficiary) cmbBeneficiary.getSelectedItem();
        int selectedId = selected == null ? -1 : selected.getBeneficiaryId();
        try {
            loading = true;
            cmbBeneficiary.removeAllItems();
            tableModel.setRowCount(0);
            for (Beneficiary beneficiary : beneficiaryDAO.getAll()) {
                cmbBeneficiary.addItem(beneficiary);
                tableModel.addRow(new Object[]{beneficiary.getBeneficiaryId(),
                    beneficiary.getFirstName(), beneficiary.getLastName(),
                    beneficiary.getFamilySize(), beneficiary.getLivingSituation()});
            }
            cmbNeed.removeAllItems();
            for (Need need : needsDAO.getAll()) {
                cmbNeed.addItem(need);
            }
            selectBeneficiary(selectedId);
            loading = false;
            loadAssignedNeeds();
        } catch (SQLException ex) {
            loading = false;
            showError("Could not load beneficiary needs", ex);
        }
    }

    private void selectBeneficiary(int id) {
        for (int i = 0; i < cmbBeneficiary.getItemCount(); i++) {
            if (cmbBeneficiary.getItemAt(i).getBeneficiaryId() == id) {
                cmbBeneficiary.setSelectedIndex(i);
                return;
            }
        }
    }

    private void loadAssignedNeeds() {
        assignedModel.clear();
        Beneficiary beneficiary = (Beneficiary) cmbBeneficiary.getSelectedItem();
        if (beneficiary == null) return;
        try {
            for (BeneficiaryNeed need : beneficiaryNeedsDAO.getForBeneficiary(
                    beneficiary.getBeneficiaryId())) {
                assignedModel.addElement(need);
            }
        } catch (SQLException ex) {
            showError("Could not load assigned needs", ex);
        }
    }

    private void assignNeed() {
        Beneficiary beneficiary = (Beneficiary) cmbBeneficiary.getSelectedItem();
        Need need = (Need) cmbNeed.getSelectedItem();
        if (beneficiary == null || need == null) {
            JOptionPane.showMessageDialog(this,
                    "Select a beneficiary and a need first.");
            return;
        }
        int quantity = (Integer) spnQuantity.getValue();
        try {
            beneficiaryNeedsDAO.assign(beneficiary.getBeneficiaryId(),
                    need.getCode(), quantity);
            loadAssignedNeeds();
            JOptionPane.showMessageDialog(this, "Need assigned.");
        } catch (SQLException ex) {
            showError("Could not assign need", ex);
        }
    }

    private void removeNeed() {
        BeneficiaryNeed selected = lstAssigned.getSelectedValue();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select an assigned need to remove.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Remove " + selected.getNeedDescription() + " from this beneficiary?",
                "Confirm removal", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) return;
        try {
            beneficiaryNeedsDAO.remove(selected.getBeneficiaryId(),
                    selected.getNeedCode());
            loadAssignedNeeds();
        } catch (SQLException ex) {
            showError("Could not remove need", ex);
        }
    }

    private void showError(String title, SQLException ex) {
        JOptionPane.showMessageDialog(this, title + ":\n" + ex.getMessage());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnAssign) {
            assignNeed();
        } else if (source == btnRemove) {
            removeNeed();
        } else if (source == btnRefresh) {
            loadData();
        } else if (source == cmbBeneficiary && !loading) {
            loadAssignedNeeds();
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
                new BeneficiaryNeedsGui().setVisible(true);
            }
        });
    }
}
