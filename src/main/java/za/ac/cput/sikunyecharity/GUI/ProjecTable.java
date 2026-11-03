/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
//import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectRegistrationDAO;
//import za.ac.cput.sikunyecharity.Domain.Donation;
import za.ac.cput.sikunyecharity.Domain.Project;
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

    private DefaultTableModel projectTableModel;

    private final ProjectDAO projectDAO = new ProjectDAO();
    private final ProjectRegistrationDAO registrationDAO = new ProjectRegistrationDAO();
    //private final DonationDAO donationDAO = new DonationDAO();

    private JButton btnRegister;
    private JButton btnDonate;
    private JButton btnClear;
    private JButton btnRefresh;

    // Constructor
    public ProjecTable() {

        setTitle("Sikunye Charity - Projects");

        setSize(1200, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        createInterface();

        loadProjects();
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

        btnDonate
                = new JButton("Donate");

        btnClear
                = new JButton("Clear");

        btnRefresh
                = new JButton("Refresh");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnDonate);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

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

        // Bottom
        JPanel bottomPanel
                = new JPanel(new BorderLayout());

        // Buttons ABOVE JTable
        bottomPanel.add(
                buttonPanel,
                BorderLayout.NORTH
        );

        // JTable BELOW buttons
        bottomPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        add(
                bottomPanel,
                BorderLayout.CENTER
        );

        // Events
        btnRegister.addActionListener(this);

        btnDonate.addActionListener(this);

        btnClear.addActionListener(this);

        btnRefresh.addActionListener(this);

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
        } else if (source == btnDonate) {
            makeDonation();
        } else if (source == btnClear) {
            clearFields();
        } else if (source == btnRefresh) {
            loadProjects();
        }
    }

    // Load projects from database
    private void loadProjects() {

        projectTableModel.setRowCount(0);

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

    // Make donation
    private void makeDonation() {

        String projectID = selectedProjectId();

        String userName
                = txtUserName.getText().trim();

        String userEmail
                = txtUserEmail.getText().trim();

        if (projectID == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a project first."
            );

            return;
        }

        if (!validUserDetails(userName, userEmail)) {
            return;
        }

        String amountText
                = JOptionPane.showInputDialog(
                        this,
                        "Enter donation amount (R):"
                );

        if (amountText == null
                || amountText.trim().isEmpty()) {

            return;
        }

        try {

            BigDecimal amount
                    = new BigDecimal(
                            amountText.trim()
                    );

            if (amount.compareTo(
                    BigDecimal.ZERO) <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Donation must be greater than R0."
                );

                return;
            }

            if (amount.scale() > 2 || amount.precision() - amount.scale() > 10) {
                JOptionPane.showMessageDialog(this,
                        "Enter an amount with at most two decimal places and ten whole digits.");
                return;
            }

            saveDonation(
                    projectID,
                    userName,
                    userEmail,
                    amount
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid amount."
            );
        }
    }

    // Save donation
    private void saveDonation(
            String projectID,
            String userName,
            String userEmail,
            BigDecimal amount) {

        try {
           // donationDAO.saveDonation(
                //    new Donation(projectID, userName, userEmail, amount)
            //);

            JOptionPane.showMessageDialog(
                    this,
                    "Donation of R" + amount + " was recorded. No payment was processed."
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Donation failed:\n"
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
    }

    // Main
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProjecTable().setVisible(true);
            }
        });
    }
}
