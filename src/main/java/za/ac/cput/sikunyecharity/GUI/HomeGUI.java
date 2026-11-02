/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

/**
 *
 * @author Gaga Kwakho
 */
public class HomeGUI extends JFrame implements ActionListener {

    private JPanel charityInfoFooterPanel, quickLinksPanel, sociallmediaLinkPanel,
            socialIconPanel, rightReservedPanel, footerPanel, mainPanel, headerPanel,
            headingPanel, subHeadingPanel, descriptionPanel, buttonPanel, homePanel, charityNamePanel;

    private JLabel charityNameLabel,
            homeLabel,
            projectLabel,
            donorLabel,
            itemsDonatedLabel,
            donationRecievedLabel,
            contactLabel;

    private JLabel heading;
    private JLabel subHeading;
    private JLabel description;
    private JButton button;

    private JLabel charityNameFooterLabel;
    private JLabel charitySloganLabel;
    private JLabel quickLinkLabel;
    private JLabel homeLinkLabel;
    private JLabel itemsDonatedLinkLabel;
    private JLabel projectLinkLabel;
    private JLabel donorLinkLabel;
    private JLabel donationRecievedLinkLabel;
    private JLabel contactLinkLabel;
    private JLabel feedbackLinkLabel;
    private JLabel followUsLabel;
    private JLabel rightReservedLabel;

    Font font1 = new Font("Inter", Font.BOLD, 20);
    Font font8 = new Font("Inter", Font.BOLD, 50);

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

    private JPanel createPairPanel(JLabel heading, JLabel subHeading) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(heading);
        panel.add(subHeading);

        return panel;
    }

    public HomeGUI() {
        homePanel = new JPanel();
        headerPanel = new JPanel();
        charityNamePanel = new JPanel();
        charityInfoFooterPanel = new JPanel();
        headingPanel = new JPanel();
        subHeadingPanel = new JPanel();
        descriptionPanel = new JPanel();
        buttonPanel = new JPanel();
        quickLinksPanel = new JPanel();
        sociallmediaLinkPanel = new JPanel();
        socialIconPanel = new JPanel();
        rightReservedPanel = new JPanel();
        footerPanel = new JPanel();
        mainPanel = new JPanel();
        charityNameLabel = new JLabel("SikunyeCharity");
        charityNameLabel.setFont(font1);

        homeLabel = new JLabel("Home");
        homeLabel.setFont(font2);
        projectLabel = new JLabel("Projects");
        projectLabel.setFont(font2);
        donorLabel = new JLabel("Donor");
        donorLabel.setFont(font2);
        itemsDonatedLabel = new JLabel("ItemsDonated");
        itemsDonatedLabel.setFont(font2);
        donationRecievedLabel = new JLabel("DonationRecieved");
        donationRecievedLabel.setFont(font2);
        contactLabel = new JLabel("Contact");

        contactLabel.setFont(font2);

        heading = new JLabel("Welcome to Sikunye Charity");
        heading.setFont(font8);

        subHeading = new JLabel("Together we are one");
        subHeading.setFont(font2);

        description = new JLabel(
                "<html>The name Sikunye means together in isiXhosa, reflecting the charity's core belief that communities thrive<br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;when people work together. The organisation connects donors with beneficiaries through a transparent,<br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;efficient, and accountable donation management system.Every donation is tracked from pledge to delivery,<br>"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ensuring that help reaches those who need it most.</html>"
        );
        description.setFont(font7);
        button = new JButton("Get Involved") {
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
        button.setBackground(color1);
        button.setForeground(color2);
        button.setContentAreaFilled(false);
        button.setFont(font4);

        button.setBorder(BorderFactory.createEmptyBorder());
        button.addActionListener(this);

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
        homeLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        homeLinkLabel.setForeground(color2);
        homeLinkLabel.setFont(font4);

        itemsDonatedLinkLabel = new JLabel("ItemsDonated");
        itemsDonatedLinkLabel.setForeground(color2);
        itemsDonatedLinkLabel.setFont(font4);
        projectLinkLabel = new JLabel("Projects");
        projectLinkLabel.setForeground(color2);
        projectLinkLabel.setFont(font4);
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
                //feedback.setLocationRelativeTo(ContactGUI.this);
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

    public void setGui() {
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        Dimension panelHeaderSize = new Dimension(1390, 60);
        headerPanel.setPreferredSize(panelHeaderSize);
        headerPanel.setMinimumSize(panelHeaderSize);
        headerPanel.setMaximumSize(panelHeaderSize);
        headerPanel.setOpaque(true);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        headerPanel.setBorder(new MatteBorder(0, 0, 2, 0, color1));

        mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 32, 18));
        mainPanel.setOpaque(false);
        mainPanel.add(homeLabel);
        mainPanel.add(projectLabel);
        mainPanel.add(donorLabel);
        mainPanel.add(itemsDonatedLabel);
        mainPanel.add(donationRecievedLabel);
        mainPanel.add(contactLabel);
        //charityNamePanel.setLayout(new FlowLayout(FlowLayout.LEFT,15,18));
//       charityNamePanel.add(charityNameLabel);
//charityNamePanel.setOpaque(false);
//       
//       headerPanel.add(charityNamePanel);
        headerPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        headerPanel.add(charityNameLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(365, 0)));
        headerPanel.add(mainPanel);

        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.setBackground(color4);
        homePanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        homePanel.add(headerPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 65)));

        homePanel.add(headingPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        homePanel.add(subHeadingPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        homePanel.add(descriptionPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 40)));
        homePanel.add(buttonPanel);
        homePanel.add(Box.createRigidArea(new Dimension(0, 65)));

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(button);
        buttonPanel.setOpaque(false);
        button.setPreferredSize(new Dimension(140, 40));
        subHeadingPanel.setLayout(new FlowLayout());
        subHeadingPanel.add(subHeading);
        subHeadingPanel.setOpaque(false);
        descriptionPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        descriptionPanel.add(description);
        descriptionPanel.setOpaque(false);

        headingPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        headingPanel.add(heading);
        headingPanel.setOpaque(false);

        charityInfoFooterPanel.setLayout(new BoxLayout(charityInfoFooterPanel, BoxLayout.Y_AXIS));
        charityInfoFooterPanel.setOpaque(false);
        charityInfoFooterPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        charityInfoFooterPanel.add(charityNameFooterLabel);
        charityInfoFooterPanel.add(Box.createRigidArea(new Dimension(20, 20)));
        charityInfoFooterPanel.add(charitySloganLabel);

        quickLinksPanel.setLayout(new GridLayout(8, 1));
        quickLinksPanel.setOpaque(false);
        quickLinksPanel.add(quickLinkLabel);
        quickLinksPanel.add(homeLinkLabel);
        quickLinksPanel.add(itemsDonatedLinkLabel);
        quickLinksPanel.add(projectLinkLabel);
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
        URL facebookUrl = getClass().getResource("/facebook.png"
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

        homePanel.add(footerPanel);
        homePanel.add(rightReservedPanel);

        JScrollPane scroll = new JScrollPane(homePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        this.getContentPane().add(scroll);
    }

    public static void main(String[] args) {

        HomeGUI home = new HomeGUI();
        home.setGui();
        home.setVisible(true);
        home.setSize(1000, 2000);
        home.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
