package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 * ContactGUI - Updated with stylish, full-height info cards & aligned layout.
 *
 * @author Gaga Kwakho
 */
public class ContactGUI extends JFrame implements ActionListener, ItemListener {

    private JPanel mainPanel, centerPanel, charityInfoPanel, emailPanel, phonePanel,
            locationPanel, hoursPanel, queryPanel, sendButtonPanel, formContainerPanel;
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

    // Design Tokens & Colors
    Font font1 = new Font("Inter", Font.BOLD, 22);
    Font font2 = new Font("Inter", Font.BOLD, 16);
    Font font3 = new Font("Inter", Font.PLAIN, 12);
    Font font4 = new Font("Inter", Font.BOLD, 13);
    Font font7 = new Font("Inter", Font.PLAIN, 13);

    Color color1 = new Color(0, 192, 232);       // Signature Cyan Accent
    Color color2 = Color.WHITE;                  // Pure White
    Color color3 = new Color(245, 239, 247);     // Soft Body Background
    Color color4 = Color.WHITE;                  // Card Background
    Color color5 = new Color(43, 55, 63);        // Dark Banner Background
    Color color7 = new Color(215, 220, 225);     // Light Gray Border

    /**
     * Creates stylish, rounded white cards with cyan top accents and enough height for text.
     */
    private JPanel createInfoCard(JLabel icon, JLabel titleLabel, JLabel infoLabel) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Card White Background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                // Subtle Outer Border
                g2.setColor(new Color(220, 225, 230));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

                // Stylish Top Cyan Accent Bar
                g2.setColor(color1);
                g2.fillRoundRect(0, 0, getWidth(), 5, 16, 16);
                g2.fillRect(0, 3, getWidth(), 2);

                g2.dispose();
            }
        };

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(10, 14, 10, 14));

        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Inter", Font.BOLD, 13));
        titleLabel.setForeground(new Color(30, 40, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        infoLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        infoLabel.setForeground(new Color(90, 100, 110));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 3)));
        card.add(infoLabel);

        // Increased height to 95px so text never clips
        Dimension cardSize = new Dimension(245, 95);
        card.setPreferredSize(cardSize);
        card.setMinimumSize(cardSize);
        card.setMaximumSize(cardSize);

        return card;
    }

    private JPanel createFormRow(JLabel label, JComponent inputField, int width, int height) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        label.setFont(font4);
        label.setForeground(new Color(45, 55, 65));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        Dimension fieldSize = new Dimension(width, height);
        inputField.setPreferredSize(fieldSize);
        inputField.setMaximumSize(fieldSize);
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);

        row.add(label);
        row.add(Box.createRigidArea(new Dimension(0, 3)));
        row.add(inputField);

        Dimension rowSize = new Dimension(width, height + 22);
        row.setPreferredSize(rowSize);
        row.setMaximumSize(rowSize);

        return row;
    }

    /**
     * Inline Footer Panel with aligned column components.
     */
    private JPanel createInlineFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(27, 38, 46));
        footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        // Top 4-Column Layout
        JPanel mainContentPanel = new JPanel(new GridLayout(1, 4, 30, 0));
        mainContentPanel.setOpaque(false);
        mainContentPanel.setBorder(new EmptyBorder(30, 50, 25, 50));

        // Column 1: Organization Summary
        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        col1.setOpaque(false);

        JLabel title1 = new JLabel("Sikunye Charity");
        title1.setFont(new Font("Inter", Font.BOLD, 16));
        title1.setForeground(Color.WHITE);
        title1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel tag1 = new JLabel("Together we are one");
        tag1.setFont(new Font("Inter", Font.BOLD, 12));
        tag1.setForeground(color1);
        tag1.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea desc1 = new JTextArea("Connecting donors with local beneficiaries through transparent and accountable donation management.");
        desc1.setFont(new Font("Inter", Font.PLAIN, 11));
        desc1.setForeground(new Color(160, 174, 186));
        desc1.setLineWrap(true);
        desc1.setWrapStyleWord(true);
        desc1.setEditable(false);
        desc1.setOpaque(false);
        desc1.setAlignmentX(Component.LEFT_ALIGNMENT);
        desc1.setMaximumSize(new Dimension(250, 48));

        JLabel npo1 = new JLabel("NPO Reg: 2020/104/001");
        npo1.setFont(new Font("Inter", Font.PLAIN, 11));
        npo1.setForeground(new Color(160, 174, 186));
        npo1.setAlignmentX(Component.LEFT_ALIGNMENT);

        col1.add(title1);
        col1.add(Box.createRigidArea(new Dimension(0, 4)));
        col1.add(tag1);
        col1.add(Box.createRigidArea(new Dimension(0, 8)));
        col1.add(desc1);
        col1.add(Box.createRigidArea(new Dimension(0, 8)));
        col1.add(npo1);

        // Column 2: Quick Links
        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        col2.setOpaque(false);

        JLabel title2 = new JLabel("Quick Links");
        title2.setFont(new Font("Inter", Font.BOLD, 16));
        title2.setForeground(Color.WHITE);
        title2.setAlignmentX(Component.LEFT_ALIGNMENT);

        col2.add(title2);
        col2.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] links = {"Home", "Projects", "Beneficiaries", "Items Donated", "Contact", "Feedback"};
        for (String link : links) {
            JLabel linkLbl = new JLabel(link);
            linkLbl.setFont(new Font("Inter", Font.PLAIN, 11));
            linkLbl.setForeground(new Color(160, 174, 186));
            linkLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            linkLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            linkLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { linkLbl.setForeground(color1); }
                @Override
                public void mouseExited(MouseEvent e) { linkLbl.setForeground(new Color(160, 174, 186)); }
                @Override
                public void mouseClicked(MouseEvent e) { AppNavigation.open(ContactGUI.this, link.replace(" ", "")); }
            });
            col2.add(linkLbl);
            col2.add(Box.createRigidArea(new Dimension(0, 4)));
        }

        // Column 3: Get In Touch
        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        col3.setOpaque(false);

        JLabel title3 = new JLabel("Get In Touch");
        title3.setFont(new Font("Inter", Font.BOLD, 16));
        title3.setForeground(Color.WHITE);
        title3.setAlignmentX(Component.LEFT_ALIGNMENT);

        col3.add(title3);
        col3.add(Box.createRigidArea(new Dimension(0, 10)));

        String[][] contactDetails = {
            {"✉", "info@sikunyecharity.org.za"},
            {"☎", "+27 21 123 4567"},
            {"📍", "District Six, Cape Town 7925"},
            {"⏰", "Mon - Fri: 08:00 - 16:00"}
        };
        for (String[] item : contactDetails) {
            JPanel r = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
            r.setOpaque(false);
            r.setAlignmentX(Component.LEFT_ALIGNMENT);
            r.setMaximumSize(new Dimension(250, 20));

            JLabel ic = new JLabel(item[0]);
            ic.setFont(new Font("Inter", Font.PLAIN, 11));
            ic.setForeground(new Color(160, 174, 186));

            JLabel tx = new JLabel(item[1]);
            tx.setFont(new Font("Inter", Font.PLAIN, 11));
            tx.setForeground(new Color(160, 174, 186));

            r.add(ic);
            r.add(tx);
            col3.add(r);
            col3.add(Box.createRigidArea(new Dimension(0, 6)));
        }

        // Column 4: Follow Our Journey
        JPanel col4 = new JPanel();
        col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));
        col4.setOpaque(false);

        JLabel title4 = new JLabel("Follow Our Journey");
        title4.setFont(new Font("Inter", Font.BOLD, 16));
        title4.setForeground(Color.WHITE);
        title4.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub4 = new JLabel("Stay updated on our latest community developments.");
        sub4.setFont(new Font("Inter", Font.PLAIN, 11));
        sub4.setForeground(new Color(160, 174, 186));
        sub4.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel socialP = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        socialP.setOpaque(false);
        socialP.setAlignmentX(Component.LEFT_ALIGNMENT);
        socialP.setMaximumSize(new Dimension(200, 25));

        String[] socs = {"f", "📷", "in"};
        for (String s : socs) {
            JLabel socLbl = new JLabel(s);
            socLbl.setFont(new Font("Inter", Font.BOLD, 14));
            socLbl.setForeground(Color.WHITE);
            socLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            socLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { socLbl.setForeground(color1); }
                @Override
                public void mouseExited(MouseEvent e) { socLbl.setForeground(Color.WHITE); }
            });
            socialP.add(socLbl);
        }

        JButton reviewsBtn = new JButton("★ View All Reviews") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(color1);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        reviewsBtn.setFont(new Font("Inter", Font.BOLD, 11));
        reviewsBtn.setForeground(color1);
        reviewsBtn.setBackground(new Color(27, 38, 46));
        reviewsBtn.setFocusPainted(false);
        reviewsBtn.setContentAreaFilled(false);
        reviewsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        reviewsBtn.setPreferredSize(new Dimension(150, 32));
        reviewsBtn.setAlignmentX(Component.LEFT_ALIGNMENT);

        col4.add(title4);
        col4.add(Box.createRigidArea(new Dimension(0, 6)));
        col4.add(sub4);
        col4.add(Box.createRigidArea(new Dimension(0, 10)));
        col4.add(socialP);
        col4.add(Box.createRigidArea(new Dimension(0, 14)));
        col4.add(reviewsBtn);

        mainContentPanel.add(col1);
        mainContentPanel.add(col2);
        mainContentPanel.add(col3);
        mainContentPanel.add(col4);

        // Bottom Copyright Bar
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBackground(new Color(16, 23, 29));
        bottomBar.setBorder(new EmptyBorder(12, 50, 12, 50));

        JLabel copyLabel = new JLabel("© 2026 Sikunye Charity. All rights reserved.");
        copyLabel.setFont(new Font("Inter", Font.PLAIN, 11));
        copyLabel.setForeground(new Color(160, 174, 186));

        JLabel rightTagline = new JLabel("Empowering Communities Across South Africa");
        rightTagline.setFont(new Font("Inter", Font.PLAIN, 11));
        rightTagline.setForeground(color1);

        bottomBar.add(copyLabel, BorderLayout.WEST);
        bottomBar.add(rightTagline, BorderLayout.EAST);

        footerPanel.add(mainContentPanel, BorderLayout.CENTER);
        footerPanel.add(bottomBar, BorderLayout.SOUTH);

        return footerPanel;
    }

    public ContactGUI() {
        super("Sikunye Charity - Contact");

        mainPanel = new JPanel();
        centerPanel = new JPanel();
        charityInfoPanel = new JPanel();
        queryPanel = new JPanel();
        sendButtonPanel = new JPanel();
        formContainerPanel = new JPanel();

        // Banner Headings
        mainHeadingLabel = new JLabel("Get Involved with our Organisation", SwingConstants.CENTER);
        mainHeadingLabel.setFont(font1);
        mainHeadingLabel.setForeground(color1);
        mainHeadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subHeadingLabel = new JLabel("Interested to donate, join our project or have any query about our organisation reach us on the details below.", SwingConstants.CENTER);
        subHeadingLabel.setFont(font7);
        subHeadingLabel.setForeground(color2);
        subHeadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Info Cards Labels
        charityEmailLabel = new JLabel("Email");
        charityEmailInfoLabel = new JLabel("info@sikunyecharity.org.za");

        charityPhoneLabel = new JLabel("Phone");
        charityPhoneInfoLabel = new JLabel("+27 21 123 4567");

        charityLocationLabel = new JLabel("Location");
        charityLocationInfoLabel = new JLabel("District Six, Cape Town 7925");

        charityHoursLabel = new JLabel("Hours");
        charityHoursInfoLabel = new JLabel("Monday-Friday 08:00-16:00");

        // Form Controls
        formHeadingLabel = new JLabel("Have Inquiries or Urgent Matters? Contact Us Anytime.", SwingConstants.CENTER);
        formHeadingLabel.setFont(font2);
        formHeadingLabel.setForeground(new Color(30, 40, 50));
        formHeadingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userFullNameLabel = new JLabel("Full Name");
        userFullNameTextField = new JTextField();

        userEmailLabel = new JLabel("Email");
        userEmailTextField = new JTextField();

        userMobileLabel = new JLabel("Phone Number");
        userMobileTextField = new JTextField();

        subjectLabel = new JLabel("Subject");
        subjectCombo = new JComboBox<>();
        subjectCombo.setBackground(Color.WHITE);
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        sendMessageButton.setBackground(color1);
        sendMessageButton.setForeground(color2);
        sendMessageButton.setContentAreaFilled(false);
        sendMessageButton.setFont(font4);
        sendMessageButton.setFocusPainted(false);
        sendMessageButton.setBorder(BorderFactory.createEmptyBorder());
        sendMessageButton.addActionListener(this);

        backButton = new JButton("Back home") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        backButton.setBackground(new Color(90, 100, 110));
        backButton.setForeground(color2);
        backButton.setContentAreaFilled(false);
        backButton.setFont(font4);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.addActionListener(e -> AppNavigation.open(this, "Home"));

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Subject_Id");
        tableModel.addColumn("User_Name");
        tableModel.addColumn("User_Email");
        tableModel.addColumn("User_Mobile");
        tableModel.addColumn("Status");
    }

    public void setGUI() {
        this.setTitle("Contact");
        setLayout(new BorderLayout());

        // Universal Top Navigation Bar
        add(new HeaderPanel(this, "Contact"), BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 1. Dark Banner Header (Full Width)
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(color5);
        centerPanel.setBorder(new EmptyBorder(18, 20, 18, 20));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        centerPanel.add(mainHeadingLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        centerPanel.add(subHeadingLabel);

        // 2. Cyan Info Bar (Full Width, Stylish Centered Cards)
        charityInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 18, 12));
        charityInfoPanel.setOpaque(true);
        charityInfoPanel.setBackground(color1);
        charityInfoPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        charityInfoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        charityInfoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 125));

        URL emailUrl = getClass().getResource("/email.png");
        JLabel emailIcon = emailUrl != null ? new JLabel(new ImageIcon(emailUrl)) : new JLabel("✉");

        URL phoneUrl = getClass().getResource("/phone.png");
        JLabel phoneIcon = phoneUrl != null ? new JLabel(new ImageIcon(phoneUrl)) : new JLabel("☎");

        URL locationUrl = getClass().getResource("/location.png");
        JLabel locationIcon = locationUrl != null ? new JLabel(new ImageIcon(locationUrl)) : new JLabel("📍");

        URL hoursUrl = getClass().getResource("/hours.png");
        JLabel hoursIcon = hoursUrl != null ? new JLabel(new ImageIcon(hoursUrl)) : new JLabel("⏰");

        emailPanel = createInfoCard(emailIcon, charityEmailLabel, charityEmailInfoLabel);
        phonePanel = createInfoCard(phoneIcon, charityPhoneLabel, charityPhoneInfoLabel);
        locationPanel = createInfoCard(locationIcon, charityLocationLabel, charityLocationInfoLabel);
        hoursPanel = createInfoCard(hoursIcon, charityHoursLabel, charityHoursInfoLabel);

        charityInfoPanel.add(emailPanel);
        charityInfoPanel.add(phonePanel);
        charityInfoPanel.add(locationPanel);
        charityInfoPanel.add(hoursPanel);

        // 3. Contact Form Card (Centered inside formContainerPanel)
        queryPanel.setLayout(new BoxLayout(queryPanel, BoxLayout.Y_AXIS));
        queryPanel.setOpaque(true);
        queryPanel.setBackground(color4);
        queryPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 225, 230), 1, true),
            new EmptyBorder(18, 30, 18, 30)
        ));
        queryPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        int formWidth = 520;
        int inputHeight = 30;

        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        JScrollPane messageScroll = new JScrollPane(messageTextArea);

        queryPanel.add(formHeadingLabel);
        queryPanel.add(Box.createRigidArea(new Dimension(0, 14)));
        queryPanel.add(createFormRow(userFullNameLabel, userFullNameTextField, formWidth, inputHeight));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        queryPanel.add(createFormRow(userEmailLabel, userEmailTextField, formWidth, inputHeight));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        queryPanel.add(createFormRow(userMobileLabel, userMobileTextField, formWidth, inputHeight));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        queryPanel.add(createFormRow(subjectLabel, subjectCombo, formWidth, inputHeight));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        queryPanel.add(createFormRow(messageLabel, messageScroll, formWidth, 60));
        queryPanel.add(Box.createRigidArea(new Dimension(0, 14)));

        // Action Buttons Row
        sendButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 14, 0));
        sendButtonPanel.setOpaque(false);
        sendButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sendMessageButton.setPreferredSize(new Dimension(150, 34));
        backButton.setPreferredSize(new Dimension(130, 34));

        sendButtonPanel.add(sendMessageButton);
        sendButtonPanel.add(backButton);
        queryPanel.add(sendButtonPanel);

        // Form Container Panel (Full Width, Centered Content)
        formContainerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        formContainerPanel.setOpaque(true);
        formContainerPanel.setBackground(color3);
        formContainerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formContainerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));
        formContainerPanel.add(queryPanel);

        // Inline Footer Panel
        JPanel inlineFooter = createInlineFooterPanel();

        // Assemble Content Stack into mainPanel
        mainPanel.add(centerPanel);
        mainPanel.add(charityInfoPanel);
        mainPanel.add(formContainerPanel);
        mainPanel.add(inlineFooter);

        // Wrap in Scroll Pane
        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        this.getContentPane().add(scroll, BorderLayout.CENTER);

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
        sendMessageButton.setText("Send Message");
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