package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.ContactDAO;
import za.ac.cput.sikunyecharity.Domain.Contact;

/**
 * @author Gaga Kwakho
 */
public class ContactGUI extends JFrame implements ActionListener, ItemListener {

    private JPanel mainPanel, centerPanel, charityInfoPanel, emailPanel, phonePanel,
            locationPanel, hoursPanel, queryPanel, sendButtonPanel, westPanel, backButtonPanel;
    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel mainHeadingLabel;
    private JLabel subHeadingLabel;

    private JLabel charityEmailLabel;
    private JLabel charityEmailInfoLabel;
    private JLabel charityPhoneLabel;
    private JLabel charityPhoneInfoLabel;
    private JLabel charityLocationLabel;
    private JLabel charityLocationInfoLabel;
    private JLabel charityHoursLabel;
    private JLabel charityHoursInfoLabel;

    private JLabel formHeadingLabel;
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
    private JButton sendMessageButton;

    private JButton backButton;
    private String currentSelectedContact = "";

    Font font1 = new Font("Inter", Font.BOLD, 20);
    Font font2 = new Font("Inter", Font.PLAIN, 20);
    Font font3 = new Font("Inter", Font.PLAIN, 13);
    Font font4 = new Font("Inter", Font.BOLD, 15);
    Font font7 = new Font("Inter", Font.PLAIN, 17);

    Color color1 = new Color(0, 192, 232);
    Color color2 = Color.WHITE;
    Color color3 = new Color(245, 239, 247);
    Color color4 = new Color(245, 245, 245);
    Color color7 = new Color(179, 179, 179);

    private JPanel createPairPanel(JLabel icon, JLabel label, JLabel infoLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(icon);
        panel.add(label);
        panel.add(infoLabel);
        return panel;
    }

    private JPanel createLeftAlignedPanel(JLabel label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);
        panel.setOpaque(false);
        return panel;
    }

    public ContactGUI() {
        super("Sikunye Charity - Contact");

        mainPanel = new JPanel();
        centerPanel = new JPanel();
        charityInfoPanel = new JPanel();
        emailPanel = new JPanel();
        phonePanel = new JPanel();
        locationPanel = new JPanel();
        hoursPanel = new JPanel();
        queryPanel = new JPanel();
        sendButtonPanel = new JPanel();
        westPanel = new JPanel();
        backButtonPanel = new JPanel();

        mainHeadingLabel = new JLabel("Get Involved with our Organisation");
        mainHeadingLabel.setFont(font2);
        mainHeadingLabel.setForeground(color1);
        mainHeadingLabel.setOpaque(true);
        mainHeadingLabel.setBackground(new Color(43, 55, 63, 153));
        mainHeadingLabel.setBorder(new EmptyBorder(0, 450, 0, 400));

        subHeadingLabel = new JLabel("Interested to donate, join our project or "
                + "have any query about our organisation reach us on the details below.");
        subHeadingLabel.setFont(font7);
        subHeadingLabel.setForeground(color2);
        subHeadingLabel.setOpaque(true);
        subHeadingLabel.setBackground(new Color(43, 55, 63, 153));
        subHeadingLabel.setBorder(new EmptyBorder(0, 350, 10, 200));

        charityEmailLabel = new JLabel("Email");
        charityEmailLabel.setFont(font3);
        charityEmailLabel.setOpaque(true);
        charityEmailLabel.setBackground(Color.WHITE);
        charityEmailLabel.setBorder(new EmptyBorder(20, 75, 10, 75));
        Dimension charityEmailSize = new Dimension(200, 55);
        charityEmailLabel.setPreferredSize(charityEmailSize);
        charityEmailLabel.setMinimumSize(charityEmailSize);
        charityEmailLabel.setMaximumSize(charityEmailSize);

        charityEmailInfoLabel = new JLabel("info@sikunyecharity.org.za");
        charityEmailInfoLabel.setFont(font3);
        charityEmailInfoLabel.setOpaque(true);
        charityEmailInfoLabel.setBackground(Color.WHITE);
        charityEmailInfoLabel.setBorder(new EmptyBorder(20, 15, 20, 10));
        Dimension charityEmailInfoSize = new Dimension(200, 65);
        charityEmailInfoLabel.setPreferredSize(charityEmailInfoSize);
        charityEmailInfoLabel.setMinimumSize(charityEmailInfoSize);
        charityEmailInfoLabel.setMaximumSize(charityEmailInfoSize);

        charityPhoneLabel = new JLabel("Phone");
        charityPhoneLabel.setFont(font3);
        charityPhoneLabel.setOpaque(true);
        charityPhoneLabel.setBackground(Color.WHITE);
        charityPhoneLabel.setBorder(new EmptyBorder(20, 75, 20, 89));
        Dimension charityPhoneSize = new Dimension(200, 42);

        charityPhoneInfoLabel = new JLabel("+27 21 123 4567");
        charityPhoneInfoLabel.setFont(font3);
        charityPhoneInfoLabel.setOpaque(true);
        charityPhoneInfoLabel.setBackground(Color.WHITE);
        charityPhoneInfoLabel.setBorder(new EmptyBorder(15, 45, 20, 10));
        Dimension charityPhoneInfoSize = new Dimension(200, 42);
        charityPhoneInfoLabel.setPreferredSize(charityPhoneInfoSize);
        charityPhoneInfoLabel.setMinimumSize(charityPhoneInfoSize);
        charityPhoneInfoLabel.setMaximumSize(charityPhoneInfoSize);

        charityLocationLabel = new JLabel("Location");
        charityLocationLabel.setFont(font3);
        charityLocationLabel.setOpaque(true);
        charityLocationLabel.setBackground(Color.WHITE);
        charityLocationLabel.setBorder(new EmptyBorder(20, 75, 20, 75));
        Dimension charityLocationSize = new Dimension(200, 50);
        charityLocationLabel.setPreferredSize(charityLocationSize);
        charityLocationLabel.setMinimumSize(charityLocationSize);
        charityLocationLabel.setMaximumSize(charityLocationSize);

        charityLocationInfoLabel = new JLabel("District Six ,Cape Town 7925");
        charityLocationInfoLabel.setFont(font3);
        charityLocationInfoLabel.setOpaque(true);
        charityLocationInfoLabel.setBackground(Color.WHITE);
        charityLocationInfoLabel.setBorder(new EmptyBorder(20, 13, 20, 10));
        Dimension charityLocationInfoSize = new Dimension(200, 50);
        charityLocationInfoLabel.setPreferredSize(charityLocationInfoSize);
        charityLocationInfoLabel.setMinimumSize(charityLocationInfoSize);
        charityLocationInfoLabel.setMaximumSize(charityLocationInfoSize);

        charityHoursLabel = new JLabel("Hours");
        charityHoursLabel.setFont(font3);
        charityHoursLabel.setOpaque(true);
        charityHoursLabel.setBackground(Color.WHITE);
        charityHoursLabel.setBorder(new EmptyBorder(20, 75, 20, 75));
        Dimension charityHoursSize = new Dimension(200, 50);
        charityHoursLabel.setPreferredSize(charityHoursSize);
        charityHoursLabel.setMinimumSize(charityHoursSize);
        charityHoursLabel.setMaximumSize(charityHoursSize);

        charityHoursInfoLabel = new JLabel("Monday-Friday 08:00-16:00");
        charityHoursInfoLabel.setFont(font3);
        charityHoursInfoLabel.setOpaque(true);
        charityHoursInfoLabel.setBackground(Color.WHITE);
        charityHoursInfoLabel.setBorder(new EmptyBorder(20, 17, 20, 10));
        Dimension charityHoursInfoSize = new Dimension(200, 50);
        charityHoursInfoLabel.setPreferredSize(charityHoursInfoSize);
        charityHoursInfoLabel.setMinimumSize(charityHoursInfoSize);
        charityHoursInfoLabel.setMaximumSize(charityHoursInfoSize);

        formHeadingLabel = new JLabel("Have Inquiries or Urgent Matters? Contact Us Anytime.");
        userFullNameLabel = new JLabel("Full Name");
        userFullNameTextField = new JTextField(50);

        userEmailLabel = new JLabel("Email");
        userEmailTextField = new JTextField(50);

        userMobileLabel = new JLabel("Phone Number");
        userMobileTextField = new JTextField(50);

        subjectLabel = new JLabel("Subject");
        subjectCombo = new JComboBox<>();
        subjectCombo.setBackground(color2);
        subjectCombo.addItem("Select your query type");
        subjectCombo.addItem("Financial Donation");
        subjectCombo.addItem("Volunteer Opportunities");
        subjectCombo.addItem("Partnership and Collaboration");
        subjectCombo.addItem("Specific Project");
        subjectCombo.addItem("Fundraising Events");

        messageLabel = new JLabel("Message");
        messageTextArea = new JTextArea();
        messageTextArea.setBorder(new LineBorder(color7, 1));

        sendMessageButton = new JButton("Send Message") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        sendMessageButton.setBackground(color1);
        sendMessageButton.setForeground(color2);
        sendMessageButton.setContentAreaFilled(false);
        sendMessageButton.setFont(font4);
        sendMessageButton.setBorder(BorderFactory.createEmptyBorder());
        sendMessageButton.addActionListener(this);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Subject_Id");
        tableModel.addColumn("User_Name");
        tableModel.addColumn("User_Email");
        tableModel.addColumn("User_Mobile");
        tableModel.addColumn("Status");

        backButton = new JButton("Manage Quiries")
          {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        
        backButton.addActionListener(e -> {
    JTextField usernameField = new JTextField();
    JPasswordField passwordField = new JPasswordField();

    Object[] fields = {
        "Username:", usernameField,
        "Password:", passwordField
    };

    int result = JOptionPane.showConfirmDialog(
            this,
            fields,
            "Admin Login",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
    );

    if (result != JOptionPane.OK_OPTION) {
        return; // User cancelled
    }

    String username = usernameField.getText().trim();
    String password = new String(passwordField.getPassword());

    if (!username.equals("admin") || !password.equals("1234")) {
        JOptionPane.showMessageDialog(
                this,
                "Incorrect username or password.",
                "Login failed",
                JOptionPane.ERROR_MESSAGE
        );
        return;
    }
     

    ContactTable contactTable = new ContactTable(this);
    contactTable.setGui();
    contactTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    contactTable.setSize(1000, 700);
    contactTable.setLocationRelativeTo(this);
    contactTable.setVisible(true);
});
        
        
        backButton.setBackground(color1);
        backButton.setForeground(color2);
         backButton.setContentAreaFilled(false);
        backButton.setFont(font4);
       backButton.setBorder(BorderFactory.createEmptyBorder());
       
    }

    public void setGUI() {
        this.setTitle("Contact");
        setLayout(new BorderLayout());

        // 1. Universal Top Navigation Bar
        add(new HeaderPanel(this, "Contact"), BorderLayout.NORTH);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);

        centerPanel.setLayout(new GridLayout(2, 1, 0, 0));
        Dimension panelCenterSize = new Dimension(1390, 100);
        centerPanel.setPreferredSize(panelCenterSize);
        centerPanel.setMinimumSize(panelCenterSize);
        centerPanel.setMaximumSize(panelCenterSize);
        centerPanel.add(mainHeadingLabel);
        centerPanel.add(subHeadingLabel);

        charityInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        charityInfoPanel.setBackground(color1);
        charityInfoPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        Dimension panelCharityInfoSize = new Dimension(1390, 200);
        charityInfoPanel.setPreferredSize(panelCharityInfoSize);
        charityInfoPanel.setMinimumSize(panelCharityInfoSize);
        charityInfoPanel.setMaximumSize(panelCharityInfoSize);

        URL emailUrl = getClass().getResource("/email.png");
        JLabel emailIcon = emailUrl != null ? new JLabel(new ImageIcon(emailUrl)) : new JLabel("✉");
        emailIcon.setFont(font3);
        emailIcon.setOpaque(true);
        emailIcon.setBackground(Color.WHITE);
        emailIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        emailPanel = createPairPanel(emailIcon, charityEmailLabel, charityEmailInfoLabel);

        URL phoneUrl = getClass().getResource("/phone.png");
        JLabel phoneIcon = phoneUrl != null ? new JLabel(new ImageIcon(phoneUrl)) : new JLabel("☎");
        phoneIcon.setFont(font3);
        phoneIcon.setOpaque(true);
        phoneIcon.setBackground(Color.WHITE);
        phoneIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        phonePanel = createPairPanel(phoneIcon, charityPhoneLabel, charityPhoneInfoLabel);

        URL locationUrl = getClass().getResource("/location.png");
        JLabel locationIcon = locationUrl != null ? new JLabel(new ImageIcon(locationUrl)) : new JLabel("📍");
        locationIcon.setFont(font3);
        locationIcon.setOpaque(true);
        locationIcon.setBackground(Color.WHITE);
        locationIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        locationPanel = createPairPanel(locationIcon, charityLocationLabel, charityLocationInfoLabel);

        URL hoursUrl = getClass().getResource("/hours.png");
        JLabel hoursIcon = hoursUrl != null ? new JLabel(new ImageIcon(hoursUrl)) : new JLabel("⏰");
        hoursIcon.setFont(font3);
        hoursIcon.setOpaque(true);
        hoursIcon.setBackground(Color.WHITE);
        hoursIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        hoursPanel = createPairPanel(hoursIcon, charityHoursLabel, charityHoursInfoLabel);

        charityInfoPanel.add(emailPanel);
        charityInfoPanel.add(phonePanel);
        charityInfoPanel.add(locationPanel);
        charityInfoPanel.add(hoursPanel);

        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        queryPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        queryPanel.setOpaque(true);
        queryPanel.setBackground(color4);
        queryPanel.setBorder(new EmptyBorder(10, 0, 85, 35));
        Dimension panelQuerySize = new Dimension(700, 600);
        queryPanel.setPreferredSize(panelQuerySize);
        queryPanel.setMinimumSize(panelQuerySize);
        queryPanel.setMaximumSize(panelQuerySize);

        Dimension myCustomSize = new Dimension(600, 40);

        queryPanel.add(formHeadingLabel);
        formHeadingLabel.setPreferredSize(myCustomSize);
        formHeadingLabel.setMinimumSize(myCustomSize);
        formHeadingLabel.setBorder(new EmptyBorder(0, 100, 0, 0));

        queryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        queryPanel.add(createLeftAlignedPanel(userFullNameLabel, userFullNameTextField));
        userFullNameLabel.setOpaque(false);
        userFullNameTextField.setPreferredSize(myCustomSize);
        userFullNameTextField.setMinimumSize(myCustomSize);
        userFullNameTextField.setMaximumSize(myCustomSize);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        queryPanel.add(createLeftAlignedPanel(userEmailLabel, userEmailTextField));
        userEmailLabel.setOpaque(false);
        userEmailTextField.setPreferredSize(myCustomSize);
        userEmailTextField.setMinimumSize(myCustomSize);
        userEmailTextField.setMaximumSize(myCustomSize);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        queryPanel.add(createLeftAlignedPanel(userMobileLabel, userMobileTextField));
        userMobileLabel.setOpaque(false);
        userMobileTextField.setPreferredSize(myCustomSize);
        userMobileTextField.setMinimumSize(myCustomSize);
        userMobileTextField.setMaximumSize(myCustomSize);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        queryPanel.add(createLeftAlignedPanel(subjectLabel, subjectCombo));
        subjectCombo.setPreferredSize(myCustomSize);
        subjectCombo.setMinimumSize(myCustomSize);
        subjectCombo.setMaximumSize(myCustomSize);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(messageLabel);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        JScrollPane message = new JScrollPane(messageTextArea);
        Dimension scrollPaneSize = new Dimension(600, 70);
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setOpaque(true);
        messageTextArea.setBackground(color4);
        message.setPreferredSize(scrollPaneSize);
        message.setMinimumSize(scrollPaneSize);
        message.setMaximumSize(scrollPaneSize);
        message.getVerticalScrollBar().setUnitIncrement(16);
        message.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        message.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        message.setOpaque(false);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(createLeftAlignedPanel(messageLabel, message));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        sendButtonPanel.setLayout(new FlowLayout());
        sendButtonPanel.setOpaque(false);
        Dimension buttonSize = new Dimension(175, 40);
        sendMessageButton.setPreferredSize(buttonSize);
        sendMessageButton.setMinimumSize(buttonSize);
        sendMessageButton.setMaximumSize(buttonSize);
        queryPanel.add(sendMessageButton);
        sendButtonPanel.add(sendMessageButton);
        queryPanel.add(sendButtonPanel);

        westPanel.setOpaque(true);
        westPanel.setBackground(color3);
        westPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        westPanel.add(queryPanel);

        backButtonPanel.setOpaque(true);
        backButtonPanel.setBackground(color3);
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.setFont(font2);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButtonPanel.add(backButton);

        mainPanel.add(centerPanel);
        mainPanel.add(charityInfoPanel);
        mainPanel.add(westPanel);
        mainPanel.add(backButtonPanel);

        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        this.getContentPane().add(scroll, BorderLayout.CENTER);

        // 2. Universal Bottom Footer
        add(new FooterPanel(this), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);
    }

    public void refresh() {
        userFullNameTextField.setText("");
        userEmailTextField.setText("");
        userMobileTextField.setText("");
        subjectCombo.setSelectedItem("Select your query type");
        messageTextArea.setText("");
        userFullNameTextField.requestFocus();
        currentSelectedContact = "";
        sendMessageButton.setText("Submit");
    }

    public void refreshTable() {
        ContactDAO contactDAO = new ContactDAO();
        try {
            ArrayList<Contact> contactList = contactDAO.getAllContact();
            tableModel.setRowCount(0);

            for (Contact c : contactList) {
                Object[] row = {
                    c.getContactId(),
                    c.getFullName(),
                    c.getEmail(),
                    c.getPhoneNumber(),
                    c.getSubject(),
                    c.getMessage()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error refreshing data");
        }
    }

    public void populateContactFormFields(String id, String fullName, String email, String phoneNumber, String subject, String message) {
        this.currentSelectedContact = id;
        userFullNameTextField.setText(fullName);
        userFullNameTextField.setForeground(Color.BLACK);

        userEmailTextField.setText(email);
        userEmailTextField.setForeground(Color.BLACK);

        userMobileTextField.setText(phoneNumber);

        subjectCombo.setSelectedItem(subject);
        subjectCombo.setForeground(Color.BLACK);

        messageTextArea.setText(message);
        messageTextArea.setForeground(Color.BLACK);

        sendMessageButton.setText("Update");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendMessageButton) {
            String fullName = userFullNameTextField.getText();
            String email = userEmailTextField.getText();
            String mobile = userMobileTextField.getText();
            String subject = (subjectCombo.getSelectedItem() != null)
                    ? subjectCombo.getSelectedItem().toString()
                    : "";
            String message = messageTextArea.getText();

            if (!fullName.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !subject.equals("Select your query type") && !message.isEmpty()) {
                ContactDAO contactdao = new ContactDAO();
                try {
                    if (currentSelectedContact.isEmpty()) {
                        Contact contact = new Contact(0, fullName, email, mobile, subject, message);
                        contactdao.sendMessage(contact);
                        JOptionPane.showMessageDialog(null, "Thank you " + fullName + " your QUERY has been received we will get back to you soon!!");
                        refresh();
                    } else {
                        int existingId = Integer.parseInt(currentSelectedContact);
                        Contact contact = new Contact(existingId, fullName, email, mobile, subject, message);
                        contactdao.updateById(contact);
                        JOptionPane.showMessageDialog(null, "Query updated successfully.");
                        refresh();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ContactGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Message not sent please enter all required fields");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactGUI contact = new ContactGUI();
            contact.setGUI();
            contact.setVisible(true);
        });
    }
}