/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.ContactDAO;
import za.ac.cput.sikunyecharity.Domain.Contact;

/**
 *
 * @author Gaga Kwakho
 */
public class ContactTable extends JFrame
        implements ActionListener, ItemListener {

    private JPanel panelButton;
    private JPanel panelContactTable;
    private JPanel headerPanel;

    private JLabel userFullNameLabel;
    private JTextField userFullNameTextField;
    private JLabel userEmailLabel;
    private JTextField userEmailTextField;
    private JLabel userMobileLabel;
    private JTextField userMobileTextField;
    private JLabel subjectLabel;
    private JComboBox<String> subjectCombo;
    private JLabel messageLabel;
    private JTextArea messageTextArea;

    private JButton refreshButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JTable table;
    private DefaultTableModel tableModel;
    private ContactGUI contact;

    private JLabel headerLabel;
    private JLabel subHeaderLabel;

    private static final Color CYAN_PRIMARY = new Color(31, 190, 221);
    private static final Color BG_LIGHT = new Color(246, 248, 251);
    private static final Color BORDER_COLOR = new Color(218, 224, 233);
    private static final Color TEXT_DARK = new Color(35, 53, 65);

    private static final Font TITLE_FONT =
            new Font("SansSerif", Font.BOLD, 18);
    private static final Font SUBTITLE_FONT =
            new Font("SansSerif", Font.PLAIN, 12);
    private static final Font BUTTON_FONT =
            new Font("SansSerif", Font.BOLD, 14);

    private static final String DEFAULT_SUBJECT = "Select your query type";

    public ContactTable(ContactGUI contact) {
        super("Contact Table");
        this.contact = contact;

        setLayout(new BorderLayout());
        getContentPane().setBackground(BG_LIGHT);

        createHeader();
        createTable();
        createButtons();
        setGui();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void createHeader() {
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CYAN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(16, 24, 16, 24));

        headerLabel = new JLabel("Queries", SwingConstants.CENTER);
        headerLabel.setFont(TITLE_FONT);
        headerLabel.setForeground(Color.WHITE);

        subHeaderLabel = new JLabel(
                "Add,Update and Delete submittede queries",
                SwingConstants.CENTER);
        subHeaderLabel.setFont(SUBTITLE_FONT);
        subHeaderLabel.setForeground(new Color(240, 250, 255));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));

        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleBox.add(headerLabel);
        titleBox.add(Box.createRigidArea(new Dimension(0, 4)));
        titleBox.add(subHeaderLabel);

        headerPanel.add(titleBox, BorderLayout.CENTER);
    }

    private void createTable() {
        tableModel = new DefaultTableModel(
                new String[]{
                    "Contact ID",
                    "Full Name",
                    "Email",
                    "Phone Number",
                    "Subject",
                    "Message"
                }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getTableHeader().setReorderingAllowed(false);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                populateFieldsFromSelectedRow();
            }
        });

        userFullNameLabel = new JLabel("Full Name");
        userFullNameTextField = new JTextField();

        userEmailLabel = new JLabel("Email");
        userEmailTextField = new JTextField();

        userMobileLabel = new JLabel("Phone Number");
        userMobileTextField = new JTextField();

        subjectLabel = new JLabel("Subject");
        subjectCombo = new JComboBox<>();
        subjectCombo.addItem(DEFAULT_SUBJECT);
        subjectCombo.addItem("Financial Donation");
        subjectCombo.addItem("Volunteer Opportunities");
        subjectCombo.addItem("Partnership and Collaboration");
        subjectCombo.addItem("Specific Project");
        subjectCombo.addItem("Fundraising Events");
        subjectCombo.addItemListener(this);

        messageLabel = new JLabel("Message");
        messageTextArea = new JTextArea(4, 30);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
    }

    private void createButtons() {
        refreshButton = new JButton("REFRESH");
        updateButton = new JButton("UPDATE");
        deleteButton = new JButton("DELETE");

        styleButton(refreshButton, Color.GRAY);
        styleButton(updateButton, new Color(230, 150, 0));
        styleButton(deleteButton, Color.RED);

        refreshButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        panelButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panelButton.setOpaque(false);
        panelButton.add(refreshButton);
        panelButton.add(updateButton);
        panelButton.add(deleteButton);
    }

    private void styleButton(JButton button, Color background) {
        button.setFont(BUTTON_FONT);
        button.setBackground(background);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 18, 8, 18));
    }

    private void setGui() {
        panelContactTable = new JPanel(new BorderLayout(10, 10));
        panelContactTable.setBackground(BG_LIGHT);
        panelContactTable.setBorder(new EmptyBorder(16, 24, 20, 24));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        panelContactTable.add(panelButton, BorderLayout.NORTH);
        panelContactTable.add(scrollPane, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);
        add(panelContactTable, BorderLayout.CENTER);

        loadData();
    }

    private void populateFieldsFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return;
        }

        userFullNameTextField.setText(valueAt(row, 1));
        userEmailTextField.setText(valueAt(row, 2));
        userMobileTextField.setText(valueAt(row, 3));
        subjectCombo.setSelectedItem(valueAt(row, 4));
        messageTextArea.setText(valueAt(row, 5));

        if (contact != null) {
            contact.populateContactFormFields(
                    valueAt(row, 0),
                    valueAt(row, 1),
                    valueAt(row, 2),
                    valueAt(row, 3),
                    valueAt(row, 4),
                    valueAt(row, 5)
            );
        }
    }

    private String valueAt(int row, int column) {
        Object value = table.getValueAt(row, column);
        return value == null ? "" : value.toString();
    }

    private void clearFields() {
        userFullNameTextField.setText("");
        userEmailTextField.setText("");
        userMobileTextField.setText("");
        subjectCombo.setSelectedItem(DEFAULT_SUBJECT);
        messageTextArea.setText("");
        table.clearSelection();
    }

    public void refreshTable() {
        loadData();
    }

    private void loadData() {
        try {
            ContactDAO contactDAO = new ContactDAO();
            ArrayList<Contact> contactList = contactDAO.getAll();

            tableModel.setRowCount(0);

            for (Contact c : contactList) {
                tableModel.addRow(new Object[]{
                    c.getContactId(),
                    c.getFullName(),
                    c.getEmail(),
                    c.getPhoneNumber(),
                    c.getSubject(),
                    c.getMessage()
                });
            }
        } catch (Exception ex) {
            Logger.getLogger(ContactTable.class.getName())
                    .log(Level.SEVERE, "Could not load contact records", ex);

            JOptionPane.showMessageDialog(
                    this,
                    "Could not load contact records.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            clearFields();
            refreshTable();
        } else if (e.getSource() == deleteButton) {
            deleteSelectedRecord();
        } else if (e.getSource() == updateButton) {
            updateSelectedRecord();
        }
    }

    private void deleteSelectedRecord() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            return;
        }

        int contactId = Integer.parseInt(valueAt(row, 0));

        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this record?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (response != JOptionPane.YES_OPTION) {
            return;
        }

        int result = new ContactDAO().deleteById(contactId);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Record deleted successfully.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "The record could not be deleted.");
        }
    }

    private void updateSelectedRecord() {
        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
            return;
        }

        String fullName = userFullNameTextField.getText().trim();
        String email = userEmailTextField.getText().trim();
        String phone = userMobileTextField.getText().trim();
        String subject = String.valueOf(subjectCombo.getSelectedItem());
        String message = messageTextArea.getText().trim();

        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty()
                || message.isEmpty() || DEFAULT_SUBJECT.equals(subject)) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        int contactId = Integer.parseInt(valueAt(row, 0));
        Contact updatedContact =
                new Contact(contactId, fullName, email, phone, subject, message);

        int result = new ContactDAO().updateById(updatedContact);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Record updated successfully.");
            clearFields();
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "No record was updated.");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Add subject selection handling here if needed.
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            ContactDAO dao = new ContactDAO();
//            dao.createTable();

            ContactTable contactTable = new ContactTable(null);
            contactTable.setVisible(true);
        });
    }
}