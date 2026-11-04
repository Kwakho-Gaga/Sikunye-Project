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
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import za.ac.cput.sikunyecharity.DAO.ContactDAO;
import za.ac.cput.sikunyecharity.Domain.Contact;

public class ContactGUI extends JFrame implements ActionListener {

    private JPanel mainPanel, centerPanel, charityInfoPanel, emailPanel, phonePanel,
            locationPanel, hoursPanel, queryPanel, sendButtonPanel, westPanel, backButtonPanel;

    private JLabel mainHeadingLabel, subHeadingLabel;
    private JLabel charityEmailLabel, charityEmailInfoLabel;
    private JLabel charityPhoneLabel, charityPhoneInfoLabel;
    private JLabel charityLocationLabel, charityLocationInfoLabel;
    private JLabel charityHoursLabel, charityHoursInfoLabel;

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
        super("Sikunye Charity - Contact Us");
        setGUI();
    }

    public void setGUI() {
        setLayout(new BorderLayout());

        add(new HeaderPanel(this, "Contact"), BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        centerPanel = new JPanel(new GridLayout(2, 1, 0, 0));
        mainHeadingLabel = new JLabel("Get Involved with our Organisation", SwingConstants.CENTER);
        mainHeadingLabel.setFont(font2);
        mainHeadingLabel.setForeground(color1);
        mainHeadingLabel.setOpaque(true);
        mainHeadingLabel.setBackground(new Color(43, 55, 63, 200));

        subHeadingLabel = new JLabel("Interested to donate, join our project or have any query about our organisation reach us on the details below.", SwingConstants.CENTER);
        subHeadingLabel.setFont(font7);
        subHeadingLabel.setForeground(color2);
        subHeadingLabel.setOpaque(true);
        subHeadingLabel.setBackground(new Color(43, 55, 63, 200));

        centerPanel.add(mainHeadingLabel);
        centerPanel.add(subHeadingLabel);
        centerPanel.setPreferredSize(new Dimension(1280, 100));

        charityInfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        charityInfoPanel.setBackground(color1);

        charityEmailLabel = new JLabel("Email");
        charityEmailInfoLabel = new JLabel("info@sikunyecharity.org.za");
        URL emailUrl = getClass().getResource("/email.png");
        JLabel emailIcon = emailUrl != null ? new JLabel(new ImageIcon(emailUrl)) : new JLabel("✉");
        emailPanel = createPairPanel(emailIcon, charityEmailLabel, charityEmailInfoLabel);

        charityPhoneLabel = new JLabel("Phone");
        charityPhoneInfoLabel = new JLabel("+27 21 123 4567");
        URL phoneUrl = getClass().getResource("/phone.png");
        JLabel phoneIcon = phoneUrl != null ? new JLabel(new ImageIcon(phoneUrl)) : new JLabel("☎");
        phonePanel = createPairPanel(phoneIcon, charityPhoneLabel, charityPhoneInfoLabel);

        charityLocationLabel = new JLabel("Location");
        charityLocationInfoLabel = new JLabel("District Six, Cape Town 7925");
        URL locationUrl = getClass().getResource("/location.png");
        JLabel locationIcon = locationUrl != null ? new JLabel(new ImageIcon(locationUrl)) : new JLabel("📍");
        locationPanel = createPairPanel(locationIcon, charityLocationLabel, charityLocationInfoLabel);

        charityHoursLabel = new JLabel("Hours");
        charityHoursInfoLabel = new JLabel("Monday-Friday 08:00-16:00");
        URL hoursUrl = getClass().getResource("/hours.png");
        JLabel hoursIcon = hoursUrl != null ? new JLabel(new ImageIcon(hoursUrl)) : new JLabel("⏰");
        hoursPanel = createPairPanel(hoursIcon, charityHoursLabel, charityHoursInfoLabel);

        charityInfoPanel.add(emailPanel);
        charityInfoPanel.add(phonePanel);
        charityInfoPanel.add(locationPanel);
        charityInfoPanel.add(hoursPanel);

        queryPanel = new JPanel();
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        queryPanel.setBackground(color4);
        queryPanel.setBorder(new EmptyBorder(20, 40, 20, 40));

        formHeadingLabel = new JLabel("Have Inquiries or Urgent Matters? Contact Us Anytime.");
        formHeadingLabel.setFont(font1);

        userFullNameLabel = new JLabel("Full Name");
        userFullNameTextField = new JTextField(50);

        userEmailLabel = new JLabel("Email");
        userEmailTextField = new JTextField(50);

        userMobileLabel = new JLabel("Phone Number");
        userMobileTextField = new JTextField(50);

        subjectLabel = new JLabel("Subject");
        subjectCombo = new JComboBox<>(new String[]{
            "Select your query type",
            "Financial Donation",
            "Volunteer Opportunities",
            "Partnership and Collaboration",
            "Specific Project",
            "Fundraising Events"
        });

        messageLabel = new JLabel("Message");
        messageTextArea = new JTextArea(4, 50);
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
        sendMessageButton.setFont(font4);
        sendMessageButton.addActionListener(this);

        queryPanel.add(formHeadingLabel);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        queryPanel.add(createLeftAlignedPanel(userFullNameLabel, userFullNameTextField));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(createLeftAlignedPanel(userEmailLabel, userEmailTextField));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(createLeftAlignedPanel(userMobileLabel, userMobileTextField));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(createLeftAlignedPanel(subjectLabel, subjectCombo));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        queryPanel.add(createLeftAlignedPanel(messageLabel, new JScrollPane(messageTextArea)));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        queryPanel.add(sendMessageButton);

        westPanel = new JPanel();
        westPanel.setBackground(color3);
        westPanel.add(queryPanel);

        backButtonPanel = new JPanel();
        backButtonPanel.setBackground(color3);
        backButton = new JButton("Back to Home");
        backButton.setBackground(color1);
        backButton.setForeground(color2);
        backButton.addActionListener(e -> AppNavigation.open(this, "Home"));
        backButtonPanel.add(backButton);

        mainPanel.add(centerPanel);
        mainPanel.add(charityInfoPanel);
        mainPanel.add(westPanel);
        mainPanel.add(backButtonPanel);

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        add(new FooterPanel(this), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);
    }

    public void populateContactFormFields(String id, String fullName, String email, String phoneNumber, String subject, String message) {
        this.currentSelectedContact = id;
        userFullNameTextField.setText(fullName);
        userEmailTextField.setText(email);
        userMobileTextField.setText(phoneNumber);
        subjectCombo.setSelectedItem(subject);
        messageTextArea.setText(message);
        sendMessageButton.setText("Update");
    }

    public void refresh() {
        userFullNameTextField.setText("");
        userEmailTextField.setText("");
        userMobileTextField.setText("");
        subjectCombo.setSelectedIndex(0);
        messageTextArea.setText("");
        currentSelectedContact = "";
        sendMessageButton.setText("Send Message");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sendMessageButton) {
            String fullName = userFullNameTextField.getText().trim();
            String email = userEmailTextField.getText().trim();
            String mobile = userMobileTextField.getText().trim();
            String subject = subjectCombo.getSelectedItem() != null ? subjectCombo.getSelectedItem().toString() : "";
            String message = messageTextArea.getText().trim();

            if (!fullName.isEmpty() && !email.isEmpty() && !mobile.isEmpty() && !subject.equals("Select your query type") && !message.isEmpty()) {
                ContactDAO contactdao = new ContactDAO();
                try {
                    if (currentSelectedContact.isEmpty()) {
                        Contact contact = new Contact(0, fullName, email, mobile, subject, message);
                        contactdao.sendMessage(contact);
                        JOptionPane.showMessageDialog(this, "Thank you " + fullName + ", your query has been received!");
                        refresh();
                    } else {
                        int existingId = Integer.parseInt(currentSelectedContact);
                        Contact contact = new Contact(existingId, fullName, email, mobile, subject, message);
                        contactdao.updateById(contact);
                        JOptionPane.showMessageDialog(this, "Query updated successfully.");
                        refresh();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ContactGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please complete all required fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactGUI().setVisible(true));
    }
}