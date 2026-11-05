/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.ProjectDonationDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectDAO;
import za.ac.cput.sikunyecharity.DAO.ProjectRegistrationDAO;
import za.ac.cput.sikunyecharity.Domain.Project;
import za.ac.cput.sikunyecharity.Domain.ProjectDefaults;
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
    private JTextField txtProgress;

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
    private JButton btnAddProject;
    private JButton btnUpdateProject;
    private JButton btnDeleteProject;
    private JButton btnArchiveProject;
    private JButton btnUnarchiveProject;
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

        createInterface();

        loadProjects();
        loadDonations(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent event) {
                if (ProjecTable.this.projectsPage != null) {
                    ProjecTable.this.projectsPage.refreshProjects();
                    ProjecTable.this.projectsPage.setVisible(true);
                }
            }
        });
    }

    // Create interface
    private void createInterface() {

        // Top form
        JPanel content = new JPanel(new BorderLayout(0, 18));
        content.setBorder(new EmptyBorder(25, 35, 25, 35));
        JPanel top = new JPanel(new BorderLayout(0, 15));
        top.setOpaque(false);
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 18, 9));
        formPanel.setOpaque(false);

        // Left panel - labels
        JPanel labelPanel
                = new JPanel(new GridLayout(11, 1, 9, 9));
        labelPanel.setOpaque(false);

        labelPanel.add(
                BeneficiaryPageStyle.label("Project ID:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Project Name:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Owner Name:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Description:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Status:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Start Date:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("End Date:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("Goal Amount:")
        );

        labelPanel.add(BeneficiaryPageStyle.label("Progress (%):"));

        labelPanel.add(
                BeneficiaryPageStyle.label("User Name:")
        );

        labelPanel.add(
                BeneficiaryPageStyle.label("User Email:")
        );

        // Right panel - textfields
        JPanel fieldPanel
                = new JPanel(new GridLayout(11, 1, 9, 9));
        fieldPanel.setOpaque(false);

        txtProjectID
                = new JTextField();
        txtProjectID.setEditable(true);

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

        txtProgress
                = new JTextField("0");

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
        fieldPanel.add(txtProgress);
        fieldPanel.add(txtUserName);
        fieldPanel.add(txtUserEmail);

        // Add panels
        formPanel.add(labelPanel);
        formPanel.add(fieldPanel);

        top.add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);

        btnRegister
                = new JButton("Register");

        btnAddProject = new JButton("Add Project");
        btnUpdateProject = new JButton("Update Project");
        btnDeleteProject = new JButton("Delete Project");
        btnArchiveProject = new JButton("Archive Project");
        btnUnarchiveProject = new JButton("Unarchive Project");

        btnClear
                = new JButton("Clear");

        btnRefresh
                = new JButton("Refresh");

        btnBack
                = new JButton("Back to Projects");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnAddProject);
        buttonPanel.add(btnUpdateProject);
        buttonPanel.add(btnDeleteProject);
        buttonPanel.add(btnArchiveProject);
        buttonPanel.add(btnUnarchiveProject);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        BeneficiaryPageStyle.styleButton(btnRegister, BeneficiaryPageStyle.DARK);
        BeneficiaryPageStyle.styleButton(btnAddProject, new Color(39, 189, 114));
        BeneficiaryPageStyle.styleButton(btnUpdateProject, new Color(207, 160, 18));
        BeneficiaryPageStyle.styleButton(btnDeleteProject, new Color(208, 80, 75));
        BeneficiaryPageStyle.styleButton(btnArchiveProject, new Color(208, 80, 75));
        BeneficiaryPageStyle.styleButton(btnUnarchiveProject, new Color(31, 151, 204));
        BeneficiaryPageStyle.styleButton(btnClear, new Color(112, 126, 136));
        BeneficiaryPageStyle.styleButton(btnRefresh, new Color(31, 151, 204));
        BeneficiaryPageStyle.styleButton(btnBack, BeneficiaryPageStyle.DARK);
        top.add(buttonPanel, BorderLayout.SOUTH);
        content.add(top, BorderLayout.NORTH);

        // Table
        String[] columns = {
            "Project ID",
            "Project Name",
            "Owner Name",
            "Description",
            "Status",
            "Start Date",
            "End Date",
            "Goal Amount", "Progress (%)"
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
        tblProjects.setRowHeight(28);
        tblProjects.setAutoCreateRowSorter(true);

        JScrollPane scrollPane
                = new JScrollPane(tblProjects);
        JPanel projectPanel = new JPanel(new BorderLayout());
        projectPanel.add(BeneficiaryPageStyle.label("Projects (including archived)"), BorderLayout.NORTH);
        projectPanel.add(scrollPane, BorderLayout.CENTER);

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
        tblDonations.setRowHeight(28);
        tblDonations.setAutoCreateRowSorter(true);
        JPanel donationPanel = new JPanel(new BorderLayout());
        donationPanel.add(BeneficiaryPageStyle.label("Donations (select a project to filter)"), BorderLayout.NORTH);
        donationPanel.add(new JScrollPane(tblDonations), BorderLayout.CENTER);

        JSplitPane tables = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                projectPanel, donationPanel);
        tables.setResizeWeight(0.45);
        tables.setPreferredSize(new Dimension(1050, 350));

        content.add(tables, BorderLayout.CENTER);
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottom.setOpaque(false);
        bottom.add(btnBack);
        content.add(bottom, BorderLayout.SOUTH);
        content.setPreferredSize(new Dimension(1080,
                top.getPreferredSize().height + 470));
        JScrollPane pageScroll = new JScrollPane(content);
        pageScroll.setBorder(null);
        pageScroll.getVerticalScrollBar().setUnitIncrement(18);
        JPanel scrollContent = new JPanel(new BorderLayout());
        scrollContent.add(pageScroll, BorderLayout.CENTER);
        BeneficiaryPageStyle.setup(this, "Manage Projects",
                "Add projects, review donations, and maintain their records.", scrollContent);

        // Events
        btnRegister.addActionListener(this);
        btnAddProject.addActionListener(this);
        btnUpdateProject.addActionListener(this);
        btnDeleteProject.addActionListener(this);
        btnArchiveProject.addActionListener(this);
        btnUnarchiveProject.addActionListener(this);

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
        } else if (source == btnAddProject) {
            addProject();
        } else if (source == btnUpdateProject) {
            updateProject();
        } else if (source == btnDeleteProject) {
            deleteProject();
        } else if (source == btnArchiveProject) {
            archiveProject();
        } else if (source == btnUnarchiveProject) {
            unarchiveProject();
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
                    project.getGoalAmount(),
                    project.getProgressPercent()
                });
            }
            if (projectId != null) {
                for (int row = 0; row < projectTableModel.getRowCount(); row++) {
                    if (projectId.equals(cellText(row, 0))) {
                        int viewRow = tblProjects.convertRowIndexToView(row);
                        tblProjects.setRowSelectionInterval(viewRow, viewRow);
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

        int row = tblProjects.getSelectedRow();

        if (row == -1) {

            return;
        }
        row = tblProjects.convertRowIndexToModel(row);

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
        txtProgress.setText(cellText(row, 8));
        txtProjectID.setEditable(false);
        loadDonations(cellText(row, 0));
    }

    private void loadDonations(String projectId) {
        donationTableModel.setRowCount(0);
        try {
            List<ProjectDonationRecord> donations = donationDAO.getDonations(projectId);
            for (ProjectDonationRecord donation : donations) {
                donationTableModel.addRow(new Object[]{
                    donation.getDonationId(),
                    donation.getDonorId() == 0 ? "" : donation.getDonorId(),
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

    private Project projectFromFields() {
        String id = txtProjectID.getText().trim().toUpperCase(Locale.ROOT);
        String name = txtProjectName.getText().trim();
        String owner = txtOwnerName.getText().trim();
        String description = txtDescription.getText().trim();
        String status = txtStatus.getText().trim();
        if (id.isEmpty() || name.isEmpty() || status.isEmpty()) {
            throw new IllegalArgumentException("Project ID, name and status are required.");
        }
        if (id.length() > 20 || name.length() > 120 || owner.length() > 120
                || description.length() > 2000 || status.length() > 40) {
            throw new IllegalArgumentException("One of the project fields is too long.");
        }

        Date start = parseDate(txtStartDate.getText().trim());
        Date end = parseDate(txtEndDate.getText().trim());
        if (start != null && end != null && end.before(start)) {
            throw new IllegalArgumentException("End date must be after the start date.");
        }

        BigDecimal goal = null;
        String goalText = txtGoalAmount.getText().trim();
        if (!goalText.isEmpty()) {
            try {
                goal = new BigDecimal(goalText);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Goal amount must be a number.");
            }
            if (goal.signum() < 0 || goal.scale() > 2 || goal.precision() - goal.scale() > 10) {
                throw new IllegalArgumentException("Goal amount must be positive with at most two decimals.");
            }
        }

        int progress;
        try {
            progress = Integer.parseInt(txtProgress.getText().trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Progress must be a whole number from 0 to 100.");
        }
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("Progress must be between 0 and 100.");
        }
        return new Project(id, name, owner, description, status, start, end, goal, progress);
    }

    private Date parseDate(String text) {
        if (text.isEmpty()) {
            return null;
        }
        try {
            return Date.valueOf(LocalDate.parse(text));
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Use yyyy-MM-dd for dates.");
        }
    }

    private void addProject() {
        if (selectedProjectId() != null) {
            JOptionPane.showMessageDialog(this, "Click Clear before adding a new project.");
            return;
        }
        try {
            Project project = projectFromFields();
            projectDAO.addProject(project);
            loadProjects();
            selectProject(project.getProjectId());
            JOptionPane.showMessageDialog(this, "Project added.");
        } catch (IllegalArgumentException | java.sql.SQLException exception) {
            JOptionPane.showMessageDialog(this, "Could not add project:\n" + exception.getMessage());
        }
    }

    private void updateProject() {
        if (selectedProjectId() == null) {
            JOptionPane.showMessageDialog(this, "Select a project to update.");
            return;
        }
        try {
            projectDAO.updateProject(projectFromFields());
            loadProjects();
            JOptionPane.showMessageDialog(this, "Project updated.");
        } catch (IllegalArgumentException | java.sql.SQLException exception) {
            JOptionPane.showMessageDialog(this, "Could not update project:\n" + exception.getMessage());
        }
    }

    private void deleteProject() {
        String id = selectedProjectId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select a project to delete.");
            return;
        }
        if (ProjectDefaults.isDefault(id)) {
            JOptionPane.showMessageDialog(this, "The three original projects are permanent.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Delete " + id + " permanently?", "Delete Project",
                JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            projectDAO.deleteProject(id);
            loadProjects();
            clearFields();
            JOptionPane.showMessageDialog(this, "Project deleted.");
        } catch (java.sql.SQLException exception) {
            JOptionPane.showMessageDialog(this,
                    "Could not delete project. A linked donation or registration may exist.\n"
                    + exception.getMessage());
        }
    }

    private void archiveProject() {
        String id = selectedProjectId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select a project to archive.");
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "Archive " + id + "? It will disappear from the public projects page.",
                "Archive Project", JOptionPane.YES_NO_OPTION);
        if (answer != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            projectDAO.archiveProject(id);
            loadProjects();
            JOptionPane.showMessageDialog(this, "Project archived.");
        } catch (java.sql.SQLException exception) {
            JOptionPane.showMessageDialog(this, "Could not archive project:\n" + exception.getMessage());
        }
    }

    private void unarchiveProject() {
        String id = selectedProjectId();
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Select an archived project to unarchive.");
            return;
        }
        int row = tblProjects.convertRowIndexToModel(tblProjects.getSelectedRow());
        if (!"Archived".equalsIgnoreCase(cellText(row, 4))) {
            JOptionPane.showMessageDialog(this, "This project is not archived.");
            return;
        }
        String status = JOptionPane.showInputDialog(this,
                "Status after unarchiving:", "In progress");
        if (status == null) {
            return;
        }
        status = status.trim();
        if (status.isEmpty() || status.length() > 40
                || "Archived".equalsIgnoreCase(status)) {
            JOptionPane.showMessageDialog(this, "Enter a status other than Archived (up to 40 characters).");
            return;
        }
        try {
            projectDAO.unarchiveProject(id, status);
            loadProjects();
            JOptionPane.showMessageDialog(this, "Project unarchived.");
        } catch (java.sql.SQLException exception) {
            JOptionPane.showMessageDialog(this, "Could not unarchive project:\n" + exception.getMessage());
        }
    }

    private void selectProject(String id) {
        for (int row = 0; row < projectTableModel.getRowCount(); row++) {
            if (id.equals(cellText(row, 0))) {
                int viewRow = tblProjects.convertRowIndexToView(row);
                tblProjects.setRowSelectionInterval(viewRow, viewRow);
                return;
            }
        }
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
        txtProgress.setText("0");

        txtUserName.setText("");
        txtUserEmail.setText("");

        tblProjects.clearSelection();
        txtProjectID.setEditable(true);
        loadDonations(null);
    }

    // Main
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (ProjectAdminLogin.requestAccess(null)) {
                    new ProjecTable().setVisible(true);
                }
            }
        });
    }
}
