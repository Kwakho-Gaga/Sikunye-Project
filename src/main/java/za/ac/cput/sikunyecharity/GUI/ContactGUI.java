/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import static java.awt.PageAttributes.MediaType.C0;
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
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import static javax.swing.text.StyleConstants.Bold;
import za.ac.cput.sikunyecharity.DAO.ContactDAO;
import za.ac.cput.sikunyecharity.Domain.Contact;
import za.ac.cput.sikunyecharity.Domain.Feedback;

/**
 *
 * @author Gaga Kwakho
 */
public class ContactGUI extends JFrame implements ActionListener, ItemListener {

    private JPanel mainPanel, headerPanel, centerPanel, charityInfoPanel, emailPanel, phonePanel,
            locationPanel, hoursPanel, queryPanel, sendButtonPanel, westPanel, footerPanel,
            backButtonPanel, charityInfoFooterPanel, quickLinksPanel, sociallmediaLinkPanel,
            rightReservedPanel, socialIconPanel, navBarPanel;
    private JTable table;
    private DefaultTableModel tableModel;

    private JLabel charityNameLabel,
            homeLabel,
            projectLabel,
            beneficiariesLabel,
            donorLabel,
            itemsDonatedLabel,
            donationRecievedLabel,
            contactLabel;

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
    private JComboBox subjectCombo;
    private JTextField subjectTextField;
    private JLabel messageLabel;
    private JTextArea messageTextArea;
    private JButton sendMessageButton;
    private int submissionId;

    private JButton backButton;

    private JLabel charityNameFooterLabel;
    private JLabel charitySloganLabel;
    private JLabel quickLinkLabel;
    private JLabel homeLinkLabel;
    private JLabel itemsDonatedLinkLabel;
    private JLabel projectLinkLabel;
    private JLabel beneficiariesLinkLabel;
    private JLabel donorLinkLabel;
    private JLabel donationRecievedLinkLabel;
    private JLabel contactLinkLabel;
    private JLabel feedbackLinkLabel;
    private JLabel followUsLabel;
    private JLabel rightReservedLabel;
    private String currentSelectedContact = "";

    Font font1 = new Font("Inter", Font.BOLD, 20);
    Font font2 = new Font("Inter", Font.PLAIN, 20);
    Font font3 = new Font("Inter", Font.PLAIN, 13);
    Font font4 = new Font("Inter", Font.BOLD, 15);
    Font font5 = new Font("Inter", Font.BOLD, 16);
    Font font6 = new Font("Inter", Font.BOLD, 14);
    Font font7 = new Font("Inter", Font.PLAIN, 17);

    Color color1 = new Color(0, 192, 232);
    Color color2 = Color.WHITE;
    Color color3 = new Color(245, 239, 247);
    Color color4 = new Color(245, 245, 245);
    // footer
    Color color5 = new Color(43, 55, 63, 230);
    //strokein footer
    Color color6 = new Color(142, 142, 147);
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
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // small space between label and field
        panel.add(field);
        panel.setOpaque(false);
        return panel;
    }

    public ContactGUI() {

        mainPanel = new JPanel();
        headerPanel = new JPanel();
        navBarPanel = new JPanel();
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
        charityInfoFooterPanel = new JPanel();
        quickLinksPanel = new JPanel();
        sociallmediaLinkPanel = new JPanel();
        socialIconPanel = new JPanel();
        rightReservedPanel = new JPanel();
        footerPanel = new JPanel();

        charityNameLabel = new JLabel("SikunyeCharity");
        charityNameLabel.setFont(font1);

        homeLabel = new JLabel("Home");
        homeLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                homeLinkLabel.setForeground(color1);

                HomeGUI home = new HomeGUI();
                home.setExtendedState(JFrame.MAXIMIZED_BOTH);
                home.setVisible(true);
            }
        });
        homeLabel.setFont(font2);
        projectLabel = new JLabel("Projects");
        projectLabel.setFont(font2);
        beneficiariesLabel = new JLabel("Beneficiaries");
        beneficiariesLabel.setFont(font2);
        AppNavigation.link(beneficiariesLabel, this, "Beneficiaries");
        donorLabel = new JLabel("Donor");
        donorLabel.setFont(font2);
        itemsDonatedLabel = new JLabel("ItemsDonated");
        itemsDonatedLabel.setFont(font2);
        donationRecievedLabel = new JLabel("DonationRecieved");
        donationRecievedLabel.setFont(font2);
        contactLabel = new JLabel("Contact");

        contactLabel.setFont(font2);

        mainHeadingLabel = new JLabel("" + "Get Involved  with our Organisation");
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

        charityEmailInfoLabel.setPreferredSize(charityPhoneSize);
        charityEmailInfoLabel.setMinimumSize(charityPhoneSize);
        charityEmailInfoLabel.setMaximumSize(charityPhoneSize);
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
        subjectCombo = new JComboBox();
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

        backButton = new JButton("Back");
        backButton.setBackground(color1);
        backButton.setForeground(color2);

        charityNameFooterLabel = new JLabel("Sikunye Charity");
        charityNameFooterLabel.setForeground(color2);
        charityNameFooterLabel.setFont(font1);
        charitySloganLabel = new JLabel("Together we are one");
        charitySloganLabel.setForeground(color2);
        charitySloganLabel.setFont(font5);
        quickLinkLabel = new JLabel("Quick Links");
        quickLinkLabel.setForeground(color2);
        quickLinkLabel.setFont(font1);
        homeLinkLabel = new JLabel("Home");
        homeLinkLabel.setForeground(color2);
        homeLinkLabel.setFont(font4);
        itemsDonatedLinkLabel = new JLabel("ItemsDonated");
        itemsDonatedLinkLabel.setForeground(color2);
        itemsDonatedLinkLabel.setFont(font4);
        projectLinkLabel = new JLabel("Projects");
        projectLinkLabel.setForeground(color2);
        projectLinkLabel.setFont(font4);
        beneficiariesLinkLabel = new JLabel("Beneficiaries");
        beneficiariesLinkLabel.setForeground(color2);
        beneficiariesLinkLabel.setFont(font4);
        AppNavigation.link(beneficiariesLinkLabel, this, "Beneficiaries");
        donorLinkLabel = new JLabel("Donor");
        donorLinkLabel.setForeground(color2);
        donorLinkLabel.setFont(font4);
        donationRecievedLinkLabel = new JLabel("DonationsRecieved");
        donationRecievedLinkLabel.setForeground(color2);
        donationRecievedLinkLabel.setFont(font4);
        contactLinkLabel = new JLabel("Contact");
        contactLinkLabel.setForeground(color2);
        contactLinkLabel.setFont(font4);
        feedbackLinkLabel = new JLabel("Feedback");
        feedbackLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        feedbackLinkLabel.setForeground(color2);
        feedbackLinkLabel.setFont(font4);

        feedbackLinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                feedbackLinkLabel.setForeground(color1);

                FeedbackGUI feedback = new FeedbackGUI();
                // Keep this line only if the FeedbackGUI constructor doesn't call setGui().
                feedback.setGui();

                feedback.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                feedback.setSize(800, 700);
                feedback.setLocationRelativeTo(ContactGUI.this);
                feedback.setVisible(true);
            }
        });
        feedbackLinkLabel.setForeground(color2);
        feedbackLinkLabel.setFont(font4);
        followUsLabel = new JLabel("Follow us");
        followUsLabel.setForeground(color2);
        followUsLabel.setFont(font1);
        rightReservedLabel = new JLabel("© 2020 SikunyeCharity.All rights reserved");
        rightReservedLabel.setForeground(color2);
        rightReservedLabel.setFont(font6);

    }

    public void setGUI() {
        this.setTitle("Contact");

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        mainPanel.setPreferredSize(new Dimension(1390, 1552));

        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        Dimension navBarSize = new Dimension(1390, 60);
        navBarPanel.setPreferredSize(navBarSize);
        navBarPanel.setMinimumSize(navBarSize);
        navBarPanel.setMaximumSize(navBarSize);
        navBarPanel.setOpaque(true);
        navBarPanel.setBackground(Color.WHITE);
        navBarPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        navBarPanel.setBorder(new MatteBorder(0, 0, 2, 0, color1));

        headerPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 18, 18));
        Dimension panelHeaderSize = new Dimension(1390, 60);

        headerPanel.setPreferredSize(panelHeaderSize);
        headerPanel.setMinimumSize(panelHeaderSize);
        headerPanel.setMaximumSize(panelHeaderSize);
        headerPanel.setOpaque(true);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        headerPanel.add(homeLabel);
        headerPanel.add(projectLabel);
        headerPanel.add(beneficiariesLabel);
        headerPanel.add(donorLabel);
        headerPanel.add(itemsDonatedLabel);
        headerPanel.add(donationRecievedLabel);
        headerPanel.add(contactLabel);
        navBarPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        navBarPanel.add(charityNameLabel);
        navBarPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        navBarPanel.add(headerPanel);
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

        URL emailUrl = getClass().getResource("/email.png"
        );
        ImageIcon email = new ImageIcon(emailUrl);
        JLabel emailIcon = new JLabel(email);
        emailIcon.setFont(font3);
        emailIcon.setOpaque(true);
        emailIcon.setBackground(Color.WHITE);
        emailIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        emailPanel = createPairPanel(emailIcon, charityEmailLabel, charityEmailInfoLabel);

        URL phoneUrl = getClass().getResource("/phone.png"
        );
        ImageIcon phone = new ImageIcon(phoneUrl);
        JLabel phoneIcon = new JLabel(phone);
        phoneIcon.setFont(font3);
        phoneIcon.setOpaque(true);
        phoneIcon.setBackground(Color.WHITE);
        phoneIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        phonePanel = createPairPanel(phoneIcon, charityPhoneLabel, charityPhoneInfoLabel);

        URL locationUrl = getClass().getResource("/location.png"
        );
        ImageIcon location = new ImageIcon(locationUrl);
        JLabel locationIcon = new JLabel(location);
        locationIcon.setFont(font3);
        locationIcon.setOpaque(true);
        locationIcon.setBackground(Color.WHITE);
        locationIcon.setBorder(new EmptyBorder(12, 75, 0, 95));
        locationPanel = createPairPanel(locationIcon, charityLocationLabel, charityLocationInfoLabel);

        URL hoursUrl = getClass().getResource("/hours.png"
        );
        ImageIcon hours = new ImageIcon(hoursUrl);
        JLabel hoursIcon = new JLabel(hours);
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
        backButton.setPreferredSize(new Dimension(140, 40));
        backButton.setFont(font2);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButtonPanel.add(backButton);

        charityInfoFooterPanel.setLayout(new BoxLayout(charityInfoFooterPanel, BoxLayout.Y_AXIS));
        charityInfoFooterPanel.setOpaque(false);
        charityInfoFooterPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        charityInfoFooterPanel.add(charityNameFooterLabel);
        charityInfoFooterPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        charityInfoFooterPanel.add(charitySloganLabel);

        quickLinksPanel.setLayout(new GridLayout(9, 1));
        quickLinksPanel.setOpaque(false);
        quickLinksPanel.add(quickLinkLabel);
        quickLinksPanel.add(homeLinkLabel);
        quickLinksPanel.add(itemsDonatedLinkLabel);
        quickLinksPanel.add(projectLinkLabel);
        quickLinksPanel.add(beneficiariesLinkLabel);
        quickLinksPanel.add(donorLinkLabel);
        quickLinksPanel.add(donationRecievedLinkLabel);
        quickLinksPanel.add(contactLinkLabel);
        quickLinksPanel.add(feedbackLinkLabel);

        sociallmediaLinkPanel.setLayout(new BoxLayout(sociallmediaLinkPanel, BoxLayout.Y_AXIS));
        sociallmediaLinkPanel.setOpaque(false);
        sociallmediaLinkPanel.add(followUsLabel);
        socialIconPanel.setLayout(new FlowLayout());
        socialIconPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        socialIconPanel.setOpaque(false);
        URL facebookUrl = getClass().getResource("/Facebook.png"
        );
        ImageIcon facebook = new ImageIcon(facebookUrl);
        JLabel facebookIcon = new JLabel(facebook);
        URL instagramUrl = getClass().getResource("/Instagram.png"
        );
        ImageIcon instagram = new ImageIcon(instagramUrl);
        JLabel instagramIcon = new JLabel(instagram);
        URL linkedinUrl = getClass().getResource("/Linkedin.png"
        );
        ImageIcon linkedin = new ImageIcon(linkedinUrl);
        JLabel linkedinIcon = new JLabel(linkedin);
        socialIconPanel.add(facebookIcon);
        socialIconPanel.add(instagramIcon);
        socialIconPanel.add(linkedinIcon);
        sociallmediaLinkPanel.add(socialIconPanel);

        rightReservedPanel.setLayout(new FlowLayout());
        rightReservedPanel.setOpaque(true);
        rightReservedPanel.setBackground(color5);
        rightReservedPanel.setBorder(new LineBorder(color6, 1));
        rightReservedPanel.add(rightReservedLabel);

        footerPanel.setLayout(new GridLayout(1, 3, 10, 0));
        footerPanel.setOpaque(true);
        footerPanel.setBackground(color5);
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
        footerPanel.add(charityInfoFooterPanel);
        footerPanel.add(quickLinksPanel);
        footerPanel.add(sociallmediaLinkPanel);

        // panelMain.setLayout(new BorderLayout());
        mainPanel.add(navBarPanel);
        mainPanel.add(centerPanel);
        mainPanel.add(charityInfoPanel);

        mainPanel.add(westPanel);
        mainPanel.add(backButtonPanel);
        mainPanel.add(footerPanel);
        mainPanel.add(rightReservedPanel);

        JScrollPane scroll = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.getContentPane().add(scroll);
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
            // Fetch latest data from the database
            ArrayList<Contact> contactList = contactDAO.getAllContact();

            // Clear existing data in the table
            tableModel.setRowCount(0);

            // Populate table with new data
            for (Contact c : contactList) {
                Object[] row = {
                    c.getContactId(), // assuming getContactId() returns the contact's ID
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
                        JOptionPane.showMessageDialog(null, "Thank you " + fullName + " your QUERY has been recieved we will get back to you soon!!");
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void main(String[] args) {
        ContactGUI contact = new ContactGUI();
        contact.setGUI();
        contact.setVisible(true);
        contact.setSize(1000, 2000);
        contact.pack();

    }
}
