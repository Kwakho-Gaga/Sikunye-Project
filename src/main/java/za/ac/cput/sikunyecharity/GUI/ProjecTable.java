/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.ProjectDonationDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectRegistrationDAO;
import za.ac.cput.sikunyecharity.Domain.Project;
import za.ac.cput.sikunyecharity.Domain.ProjectDonationRecord;
import za.ac.cput.sikunyecharity.Domain.ProjectRegistration;

/**
 *
 * @author agime
 */
public class ProjecTable extends JFrame implements ActionListener {

    private JTextField txtProjectID;
    private JTextField txtProjectName;
    private JTextField txtOwnerName;
    private JTextField txtDescription;
    private JTextField txtStatus;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTextField txtGoalAmount;

    private JTextField txtUserName;
    private JTextField txtUserEmail;

    // Table
    private JTable tblProjects;
    private JTable tblDonations;

    private DefaultTableModel projectTableModel;
    private DefaultTableModel donationTableModel;

    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ProjectDonationDAO donationDAO = new ProjectDonationDAO();
    private final ProjectRegistrationDAO registrationDAO = new ProjectRegistrationDAO();
    private final ProjectsPage projectsPage;

    private JButton btnRegister;
    private JButton btnClear;
    private JButton btnRefresh;
    private JButton btnBack;

    // Constructor
    public ProjecTable() {
        this(null);
    }

    public ProjecTable(ProjectsPage projectsPage) {
        this.projectsPage = projectsPage;

        setTitle("Sikunye Charity - Project Donations");

        setSize(1200, 700);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        createInterface();

        loadProjects();
        loadDonations(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                if (ProjecTable.this.projectsPage != null) {
                    ProjecTable.this.projectsPage.setVisible(true);
                }
            }
        });
    }

    // Create interface
    private void createInterface() {

        // Top form
        JPanel formPanel
                = new JPanel(new GridLayout(1, 2, 20, 5));

        // Left panel - labels
        JPanel labelPanel
                = new JPanel(new GridLayout(10, 1, 5, 5));

        labelPanel.add(
                new JLabel("Project ID:")
        );

        labelPanel.add(
                new JLabel("Project Name:")
        );

        labelPanel.add(
                new JLabel("Owner Name:")
        );

        labelPanel.add(
                new JLabel("Description:")
        );

        labelPanel.add(
                new JLabel("Status:")
        );

        labelPanel.add(
                new JLabel("Start Date:")
        );

        labelPanel.add(
                new JLabel("End Date:")
        );

        labelPanel.add(
                new JLabel("Goal Amount:")
        );

        labelPanel.add(
                new JLabel("User Name:")
        );

        labelPanel.add(
                new JLabel("User Email:")
        );

        // Right panel - textfields
        JPanel fieldPanel
                = new JPanel(new GridLayout(10, 1, 5, 5));

        txtProjectID
                = new JTextField();
        txtProjectID.setEditable(false);

        txtProjectName
                = new JTextField();

        txtOwnerName
                = new JTextField();

        txtDescription
                = new JTextField();

        txtStatus
                = new JTextField();

        txtStartDate
                = new JTextField();

        txtEndDate
                = new JTextField();

        txtGoalAmount
                = new JTextField();

        txtUserName
                = new JTextField();

        txtUserEmail
                = new JTextField();

        fieldPanel.add(txtProjectID);
        fieldPanel.add(txtProjectName);
        fieldPanel.add(txtOwnerName);
        fieldPanel.add(txtDescription);
        fieldPanel.add(txtStatus);
        fieldPanel.add(txtStartDate);
        fieldPanel.add(txtEndDate);
        fieldPanel.add(txtGoalAmount);
        fieldPanel.add(txtUserName);
        fieldPanel.add(txtUserEmail);

        // Add panels
        formPanel.add(labelPanel);
        formPanel.add(fieldPanel);

        add(
                formPanel,
                BorderLayout.NORTH
        );

        // Button panel
        JPanel buttonPanel
                = new JPanel();

        btnRegister
                = new JButton("Register");

        btnClear
                = new JButton("Clear");

        btnRefresh
                = new JButton("Refresh");

        btnBack
                = new JButton("Back to Projects");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);

        // Table
        String[] columns = {
            "Project ID",
            "Project Name",
            "Owner Name",
            "Description",
            "Status",
            "Start Date",
            "End Date",
            "Goal Amount"
        };

        projectTableModel
                = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(
                    int row,
                    int column) {

                return false;
            }
        };

        tblProjects
                = new JTable(projectTableModel);

        JScrollPane scrollPane
                = new JScrollPane(tblProjects);

        String[] donationColumns = {
            "Donation ID", "Donor ID", "Project ID", "Amount (R)",
            "Payment Method", "Donation Date"
        };
        donationTableModel = new DefaultTableModel(donationColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDonations = new JTable(donationTableModel);
        JPanel donationPanel = new JPanel(new BorderLayout());
        donationPanel.add(new JLabel("Donations (select a project to filter)"), BorderLayout.NORTH);
        donationPanel.add(new JScrollPane(tblDonations), BorderLayout.CENTER);

        JSplitPane tables = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                scrollPane, donationPanel);
        tables.setResizeWeight(0.45);

        // Bottom
        JPanel bottomPanel
                = new JPanel(new BorderLayout());

        // Buttons ABOVE JTable
        bottomPanel.add(
                buttonPanel,
                BorderLayout.NORTH
        );

        // Project and donation tables below buttons
        bottomPanel.add(
                tables,
                BorderLayout.CENTER
        );

        add(
                bottomPanel,
                BorderLayout.CENTER
        );

        // Events
        btnRegister.addActionListener(this);

        btnClear.addActionListener(this);

        btnRefresh.addActionListener(this);

        btnBack.addActionListener(this);

        // When user clicks a project
        tblProjects.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    loadSelectedProject();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == btnRegister) {
            registerUser();
        } else if (source == btnClear) {
            clearFields();
        } else if (source == btnRefresh) {
            loadProjects();
            if (selectedProjectId() == null) {
                loadDonations(null);
            }
        } else if (source == btnBack) {
            dispose();
        }
    }

    // Load projects from database
    private void loadProjects() {

        String projectId = selectedProjectId();
        projectTableModel.setRowCount(0);
        donationTableModel.setRowCount(0);

        try {
            List<Project> projects = projectDAO.getAllProjects();
            for (Project project : projects) {
                projectTableModel.addRow(new Object[]{
                    project.getProjectId(),
                    project.getName(),
                    project.getOwnerName(),
                    project.getDescription(),
                    project.getStatus(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getGoalAmount()
                });
            }
            if (projectId != null) {
                for (int row = 0; row < projectTableModel.getRowCount(); row++) {
                    if (projectId.equals(cellText(row, 0))) {
                        tblProjects.setRowSelectionInterval(row, row);
                        break;
                    }
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading projects:\n"
                    + e.getMessage()
            );
        }
    }

    // Select project from jtable
    private void loadSelectedProject() {

        int row
                = tblProjects.getSelectedRow();

        if (row == -1) {

            return;
        }

        txtProjectID.setText(cellText(row, 0));

        txtProjectName.setText(
                cellText(row, 1)
        );

        txtOwnerName.setText(
                cellText(row, 2)
        );

        txtDescription.setText(
                cellText(row, 3)
        );

        txtStatus.setText(
                cellText(row, 4)
        );

        txtStartDate.setText(
                cellText(row, 5)
        );

        txtEndDate.setText(
                cellText(row, 6)
        );

        txtGoalAmount.setText(
                cellText(row, 7)
        );
        loadDonations(cellText(row, 0));
    }

    private void loadDonations(String projectId) {
        donationTableModel.setRowCount(0);
        try {
            List<ProjectDonationRecord> donations = donationDAO.getDonations(projectId);
            for (ProjectDonationRecord donation : donations) {
                donationTableModel.addRow(new Object[]{
                    donation.getDonationId(),
                    donation.getDonorId(),
                    donation.getProjectId() == null
                            ? "General Fund" : donation.getProjectId(),
                    donation.getAmount(),
                    donation.getPaymentMethod(),
                    donation.getDonationDate()
                });
            }
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this,
                    "Error loading project donations:\n" + exception.getMessage());
        }
    }

    private String cellText(int row, int column) {
        Object value = projectTableModel.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private String selectedProjectId() {
        int row = tblProjects.getSelectedRow();
        if (row < 0) {
            return null;
        }
        return cellText(tblProjects.convertRowIndexToModel(row), 0);
    }

    private boolean validUserDetails(String name, String email) {
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name and email.");
            return false;
        }
        if (!email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return false;
        }
        return true;
    }

    // Register user to project
    private void registerUser() {

        String projectID = selectedProjectId();

        String userName
                = txtUserName.getText().trim();

        String userEmail
                = txtUserEmail.getText().trim();

        if (projectID == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project."
            );

            return;
        }

        if (!validUserDetails(userName, userEmail)) {
            return;
        }

        try {
            registrationDAO.register(
                    new ProjectRegistration(projectID, userName, userEmail)
            );

            JOptionPane.showMessageDialog(
                    this,
                    "You have successfully registered "
                    + "for this project!"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed:\n"
                    + e.getMessage()
            );
        }
    }

    // Clear
    private void clearFields() {

        txtProjectID.setText("");
        txtProjectName.setText("");
        txtOwnerName.setText("");
        txtDescription.setText("");
        txtStatus.setText("");
        txtStartDate.setText("");
        txtEndDate.setText("");
        txtGoalAmount.setText("");

        txtUserName.setText("");
        txtUserEmail.setText("");

        tblProjects.clearSelection();
        loadDonations(null);
    }

    // Main
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (DashboardGUI.requestAdminAccess(null)) {
                    new ProjecTable().setVisible(true);
                }
            }
        });
    }
}
