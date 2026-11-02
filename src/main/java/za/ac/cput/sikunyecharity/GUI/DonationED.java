/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Gaga Kwakho
 */
public class DonationED extends JFrame {

    private static final String DB_URL =
            "jdbc:derby://localhost:1527/SikunyeDB";

    private static final String DB_USER =
            "sikunye_use";

    private static final String DB_PASSWORD =
            "iWashere@12";

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField postalCodeField;

    private JComboBox<String> provinceBox;

    private JRadioButton r50Button;
    private JRadioButton r100Button;

    private JTextField customAmountField;

    private JRadioButton cardButton;
    private JRadioButton eftButton;

    private JPanel contentPanel;

    private final Color navy =
            new Color(62, 72, 80);

    private final Color blue =
            new Color(39, 190, 216);

    private final Color lightGray =
            new Color(242, 237, 246);

    private final Color darkGray =
            new Color(111, 202, 222);

    public DonationED() {

        setTitle("Sikunye Charity");

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setSize(1000, 700);

        setMinimumSize(
                new Dimension(850, 600)
        );

        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        createInterface();
    }

    private void createInterface() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                Color.WHITE
        );

        JPanel topSpace =
                new JPanel();

        topSpace.setPreferredSize(
                new Dimension(1, 1)
        );

        topSpace.setBackground(
                Color.WHITE
        );

        mainPanel.add(
                topSpace,
                BorderLayout.NORTH
        );

        mainPanel.add(
                createNavigationBar(),
                BorderLayout.CENTER
        );

        setContentPane(
                mainPanel
        );
    }

    private JPanel createNavigationBar() {

        JPanel container =
                new JPanel(
                        new BorderLayout()
                );

        container.setBackground(
                Color.WHITE
        );

        JPanel navigation =
                new JPanel(
                        new BorderLayout()
                );

        navigation.setBorder(
                new EmptyBorder(
                        12,
                        30,
                        12,
                        24
                )
        );

        navigation.setBackground(
                Color.WHITE
        );

        JPanel logoPanel =
                new JPanel(
                        new BorderLayout()
                );

        logoPanel.setOpaque(false);

        JLabel logoText =
                new JLabel("Sikunye Charity");

        logoText.setForeground(new Color(35, 35, 35));
        logoText.setFont(new Font("Arial", Font.BOLD, 14));

        logoPanel.add(
                logoText,
                BorderLayout.CENTER
        );

        navigation.add(
                logoPanel,
                BorderLayout.WEST
        );

        JPanel menu =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                16,
                                5
                        )
                );

        menu.setOpaque(false);

        JButton homeButton =
                createNavButton("Home", false);

        JButton projectsButton =
                createNavButton("Projects", false);

        JButton donateButton =
                createNavButton("Donor", true);

        JButton itemsDonatedButton =
                createNavButton("ItemsDonated", false);

        JButton donationsReceivedButton =
                createNavButton("DonationsRecieved", false);

        JButton contactButton =
                createNavButton("Contact", false);

        menu.add(homeButton);
        menu.add(projectsButton);
        menu.add(donateButton);
        menu.add(itemsDonatedButton);
        menu.add(donationsReceivedButton);
        menu.add(contactButton);

        navigation.add(
                menu,
                BorderLayout.EAST
        );

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBackground(
                new Color(246, 240, 248)
        );

        showDonatePage();

        container.add(
                navigation,
                BorderLayout.NORTH
        );

        container.add(
                contentPanel,
                BorderLayout.CENTER
        );

        homeButton.addActionListener(
                e -> showHomePage()
        );

        projectsButton.addActionListener(
                e -> showProjectsPage()
        );

        donateButton.addActionListener(
                e -> showDonatePage()
        );

        contactButton.addActionListener(
                e -> showContactPage()
        );

        itemsDonatedButton.addActionListener(
                e -> openLinkedPage(
                        "za.ac.cput.sikunyecharity.GUI.ItemsDonatedGUI",
                        "za.ac.cput.sikunyecharity.GUI.ItemsDonatedPage",
                        "za.ac.cput.sikunyecharity.GUI.ItemsDonated"
                )
        );

        donationsReceivedButton.addActionListener(
                e -> openLinkedPage(
                        "za.ac.cput.sikunyecharity.GUI.DonationsRecievedGUI",
                        "za.ac.cput.sikunyecharity.GUI.DonationsReceivedGUI",
                        "za.ac.cput.sikunyecharity.GUI.DonationsRecievedPage",
                        "za.ac.cput.sikunyecharity.GUI.DonationsReceivedPage"
                )
        );

        return container;
    }

    private JButton createNavButton(
            String text,
            boolean active
    ) {

        JButton button =
                new JButton(text);

        button.setForeground(active ? new Color(111, 202, 222) : new Color(35, 35, 35));

        button.setBackground(Color.WHITE);

        button.setBorderPainted(false);

        button.setFocusPainted(false);

        button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    private void showDonatePage() {

        JPanel page =
                new JPanel(
                        new BorderLayout()
                );

        page.setBackground(
                new Color(246, 240, 248)
        );

        page.add(
                createHero(),
                BorderLayout.NORTH
        );

        page.add(
                createDonationForm(),
                BorderLayout.CENTER
        );

        page.add(
                createFooter(),
                BorderLayout.SOUTH
        );

        contentPanel.removeAll();

        contentPanel.add(
                page,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();

        contentPanel.repaint();
    }

    private JPanel createHero() {

    JPanel hero =
            new JPanel(
                    new BorderLayout()
            );

    hero.setPreferredSize(
            new Dimension(
                    1,
                    155
            )
    );

    hero.setBackground(
            darkGray
    );

    JPanel textPanel =
            new JPanel();

    textPanel.setOpaque(false);

    textPanel.setLayout(
            new BoxLayout(
                    textPanel,
                    BoxLayout.Y_AXIS
            )
    );

    textPanel.setBorder(
            new EmptyBorder(
                    12,
                    25,
                    8,
                    10
            )
    );

    JLabel title =
            new JLabel(
                    "<html>"
                    + "BECOME A <font color='#28C7DE'>DONER</font>"
                    + "</html>"
            );

    title.setForeground(
            Color.WHITE
    );

    title.setFont(
            new Font(
                    "Arial",
                    Font.PLAIN,
                    30
            )
    );

    JLabel description =
            new JLabel(
                    "<html>"
                    + "YOUR SUPPORT HELPS SIKUNYE CHARITY BRING HOPE,<br>"
                    + "DIGNITY AND OPPORTUNITIES TO THOSE IN NEED."
                    + "</html>"
            );

    description.setForeground(
            Color.WHITE
    );

    description.setFont(
            new Font(
                    "Arial",
                    Font.PLAIN,
                    9
            )
    );

    textPanel.add(title);

    textPanel.add(
            Box.createVerticalStrut(5)
    );

    textPanel.add(description);

    textPanel.add(
            Box.createVerticalStrut(10)
    );

    JPanel information =
            new JPanel(
                    new GridLayout(
                            1,
                            3,
                            15,
                            0
                    )
            );

    information.setOpaque(false);

    information.add(
            createInfoBox(
                    "✋",
                    "MAKE AN IMPACT",
                    "YOUR DONATION CHANGES LIVES."
            )
    );

    information.add(
            createInfoBox(
                    "◎",
                    "100% SECURE",
                    "YOUR INFORMATION IS SAFE WITH US."
            )
    );

    information.add(
            createInfoBox(
                    "♙",
                    "TRANSPARENCY",
                    "WE ARE COMMITTED TO ACCOUNTABILITY."
            )
    );

    textPanel.add(
            information
    );

    hero.add(
            textPanel,
            BorderLayout.CENTER
    );

    JLabel imageLabel =
            new JLabel();

    imageLabel.setHorizontalAlignment(
            SwingConstants.CENTER
    );

    java.net.URL imageURL =
            getClass().getResource(
                    "/za/ac/cput/sikunyecharity/"
                    + "two-little-girls-hugging-in-black-and-white-photo.jpg"
            );

    if (imageURL != null) {

        ImageIcon imageIcon =
                new ImageIcon(imageURL);

        Image image =
                imageIcon.getImage();

        Image resized =
                image.getScaledInstance(
                        350,
                        155,
                        Image.SCALE_SMOOTH
                );

        imageLabel.setIcon(
                new ImageIcon(resized)
        );
    }

    hero.add(
            imageLabel,
            BorderLayout.EAST
    );

    return hero;
}

    private JPanel createInfoBox(
            String icon,
            String title,
            String text
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                7,
                                0
                        )
                );

        panel.setOpaque(false);

        JLabel iconLabel =
                new JLabel(icon);

        iconLabel.setForeground(
                blue
        );

        iconLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        22
                )
        );

        panel.add(
                iconLabel,
                BorderLayout.WEST
        );

        JLabel textLabel =
                new JLabel(
                        "<html>"
                        + "<b>"
                        + title
                        + "</b><br>"
                        + "<font size='1'>"
                        + text
                        + "</font>"
                        + "</html>"
                );

        textLabel.setForeground(
                Color.WHITE
        );

        textLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        panel.add(
                textLabel,
                BorderLayout.CENTER
        );

        return panel;
    }

    private JPanel createDonationForm() {

        JPanel outer =
                new JPanel(
                        new BorderLayout()
                );

        outer.setBackground(
                new Color(246, 240, 248)
        );

        outer.setBorder(
                new EmptyBorder(
                        0,
                        65,
                        0,
                        65
                )
        );

        JPanel form =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                0,
                                0
                        )
                );

        form.setBackground(
                Color.WHITE
        );

        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                Color.WHITE,
                                1
                        ),
                        new EmptyBorder(
                                10,
                                20,
                                10,
                                20
                        )
                )
        );

        form.add(
                createDonorSection()
        );

        form.add(
                createDonationSection()
        );

        outer.add(
                form,
                BorderLayout.CENTER
        );

        return outer;
    }

    private JPanel createDonorSection() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                5,
                                5
                        )
                );

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        0,
                        1,
                        Color.DARK_GRAY
                )
        );

        JLabel heading =
                new JLabel(
                        "♙   DONER INFORMATION"
                );

        heading.setForeground(
                Color.BLACK
        );

        heading.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        panel.add(
                heading,
                BorderLayout.NORTH
        );

        JPanel fields =
                new JPanel(
                        new GridLayout(
                                0,
                                1,
                                4,
                                4
                        )
                );

        fields.setBackground(
                Color.WHITE
        );

        JPanel names =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                12,
                                0
                        )
                );

        names.setBackground(
                Color.WHITE
        );

        firstNameField =
                createField(
                        "FIRST NAME*",
                        "ENTER YOUR FIRST NAME"
                );

        lastNameField =
                createField(
                        "LAST NAME*",
                        "ENTER YOUR LAST NAME"
                );

        names.add(
                createLabeledField(
                        "FIRST NAME*",
                        firstNameField
                )
        );

        names.add(
                createLabeledField(
                        "LAST NAME*",
                        lastNameField
                )
        );

        fields.add(names);

        emailField =
                createField(
                        "EMAIL ADDRESS*",
                        "ENTER YOUR EMAIL ADDRESS"
                );

        fields.add(
                createLabeledField(
                        "EMAIL ADDRESS*",
                        emailField
                )
        );

        phoneField =
                createField(
                        "PHONE NUMBER*",
                        "ENTER YOUR PHONE NUMBER E.G. 0821234567"
                );

        fields.add(
                createLabeledField(
                        "PHONE NUMBER*",
                        phoneField
                )
        );

        addressField =
                createField(
                        "ADDRESS*",
                        "STREET ADDRESS"
                );

        fields.add(
                createLabeledField(
                        "ADDRESS*",
                        addressField
                )
        );

        JPanel location =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        location.setBackground(
                Color.WHITE
        );

        cityField =
                createField(
                        "",
                        "CITY / STATE / PROVINCE"
                );

        postalCodeField =
                createField(
                        "",
                        "POSTAL CODE"
                );

        location.add(
                cityField
        );

        location.add(
                postalCodeField
        );

        fields.add(location);

        provinceBox =
                new JComboBox<>(
                        new String[]{
                                "Select Province",
                                "Eastern Cape",
                                "Free State",
                                "Gauteng",
                                "KwaZulu-Natal",
                                "Limpopo",
                                "Mpumalanga",
                                "Northern Cape",
                                "North West",
                                "Western Cape"
                        }
                );

        provinceBox.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        fields.add(
                provinceBox
        );

        panel.add(
                fields,
                BorderLayout.CENTER
        );

        JLabel required =
                new JLabel(
                        "* REQUIRED FIELDS"
                );

        required.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        required.setForeground(
                Color.GRAY
        );

        panel.add(
                required,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private JPanel createDonationSection() {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        panel.setBackground(
                Color.WHITE
        );

        panel.setBorder(
                new EmptyBorder(
                        0,
                        18,
                        0,
                        0
                )
        );

        JPanel content =
                new JPanel();

        content.setBackground(
                Color.WHITE
        );

        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel amountTitle =
                new JLabel(
                        "▣   DONATION AMOUNT"
                );

        amountTitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        content.add(
                amountTitle
        );

        content.add(
                Box.createVerticalStrut(10)
        );

        JPanel amountButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        amountButtons.setBackground(
                Color.WHITE
        );

        r50Button =
                new JRadioButton(
                        "R50"
                );

        r100Button =
                new JRadioButton(
                        "R100"
                );

        r50Button.setBackground(
                lightGray
        );

        r100Button.setBackground(
                lightGray
        );

        r50Button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        r100Button.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        ButtonGroup amountGroup =
                new ButtonGroup();

        amountGroup.add(
                r50Button
        );

        amountGroup.add(
                r100Button
        );

        amountButtons.add(
                r50Button
        );

        amountButtons.add(
                Box.createHorizontalStrut(15)
        );

        amountButtons.add(
                r100Button
        );

        content.add(
                amountButtons
        );

        content.add(
                Box.createVerticalStrut(8)
        );

        JLabel customLabel =
                new JLabel(
                        "CUSTOM AMOUNT"
                );

        customLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        8
                )
        );

        content.add(
                customLabel
        );

        JPanel custom =
                new JPanel(
                        new BorderLayout()
                );

        custom.setBackground(
                Color.WHITE
        );

        JLabel rand =
                new JLabel(
                        "  R  "
                );

        rand.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        10
                )
        );

        customAmountField =
                new JTextField(
                        "ENTER AMOUNT"
                );

        customAmountField.setForeground(
                Color.GRAY
        );

        customAmountField.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        customAmountField.addFocusListener(
                new FocusAdapter() {

                    @Override
                    public void focusGained(
                            FocusEvent e
                    ) {

                        if (
                                customAmountField
                                        .getText()
                                        .equals(
                                                "ENTER AMOUNT"
                                        )
                        ) {

                            customAmountField.setText("");

                            customAmountField.setForeground(
                                    Color.BLACK
                            );
                        }
                    }

                    @Override
                    public void focusLost(
                            FocusEvent e
                    ) {

                        if (
                                customAmountField
                                        .getText()
                                        .trim()
                                        .isEmpty()
                        ) {

                            customAmountField.setText(
                                    "ENTER AMOUNT"
                            );

                            customAmountField.setForeground(
                                    Color.GRAY
                            );
                        }
                    }
                }
        );

        custom.add(
                rand,
                BorderLayout.WEST
        );

        custom.add(
                customAmountField,
                BorderLayout.CENTER
        );

        content.add(
                custom
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        JLabel paymentTitle =
                new JLabel(
                        "▣   PAYMENT METHOD"
                );

        paymentTitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        10
                )
        );

        content.add(
                paymentTitle
        );

        content.add(
                Box.createVerticalStrut(10)
        );

        JPanel payment =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        payment.setBackground(
                Color.WHITE
        );

        cardButton =
                new JRadioButton(
                        "CARD\nCREDIT/DEBIT CARD"
                );

        eftButton =
                new JRadioButton(
                        "EFT\nBANK TRANSFER"
                );

        cardButton.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        eftButton.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        cardButton.setBackground(
                new Color(248, 248, 248)
        );

        eftButton.setBackground(
                new Color(248, 248, 248)
        );

        cardButton.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        eftButton.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        ButtonGroup paymentGroup =
                new ButtonGroup();

        paymentGroup.add(
                cardButton
        );

        paymentGroup.add(
                eftButton
        );

        payment.add(
                cardButton
        );

        payment.add(
                eftButton
        );

        content.add(
                payment
        );

        content.add(
                Box.createVerticalStrut(15)
        );

        JButton donateButton =
                new JButton(
                        "<html>"
                        + "♡  REGISTER & DONATE"
                        + "<br>"
                        + "<font size='1'>"
                        + "JOIN US IN MAKING A DIFFERENCE"
                        + "</font>"
                        + "</html>"
                );

        donateButton.setForeground(
        Color.WHITE
);

donateButton.setBackground(
        blue
);

donateButton.setOpaque(
        true
);

donateButton.setContentAreaFilled(
        true
);

donateButton.setBorderPainted(
        false
);

donateButton.setFocusPainted(
        false
);
        donateButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        donateButton.setPreferredSize(
                new Dimension(
                        300,
                        55
                )
        );

        donateButton.setMinimumSize(
                new Dimension(
                        300,
                        55
                )
        );

        donateButton.setMaximumSize(
                new Dimension(
                        300,
                        55
                )
        );

        donateButton.addActionListener(
                e -> registerAndDonate()
        );

        JScrollPane contentScroll =
                new JScrollPane(
                        content,
                        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
                );

        contentScroll.setBorder(
                BorderFactory.createEmptyBorder()
        );

        panel.add(
                contentScroll,
                BorderLayout.CENTER
        );

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                0,
                                5
                        )
                );

        buttonPanel.setBackground(
                Color.WHITE
        );

        buttonPanel.add(
                donateButton
        );

        panel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        return panel;
    }

    private JTextField createField(
            String title,
            String placeholder
    ) {

        JTextField field =
                new JTextField();

        field.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        9
                )
        );

        field.setBackground(
                lightGray
        );

        field.setBorder(
                BorderFactory.createEmptyBorder(
                        6,
                        8,
                        6,
                        8
                )
        );

        field.setToolTipText(
                placeholder
        );

        return field;
    }

    private JPanel createLabeledField(
            String label,
            JTextField field
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                2
                        )
                );

        panel.setBackground(
                Color.WHITE
        );

        if (!label.isEmpty()) {

            JLabel title =
                    new JLabel(
                            label
                    );

            title.setFont(
                    new Font(
                            "Arial",
                            Font.PLAIN,
                            8
                    )
            );

            panel.add(
                    title,
                    BorderLayout.NORTH
            );
        }

        panel.add(
                field,
                BorderLayout.CENTER
        );

        return panel;
    }

    private void registerAndDonate() {

        String firstName =
                firstNameField
                        .getText()
                        .trim();

        String lastName =
                lastNameField
                        .getText()
                        .trim();

        String email =
                emailField
                        .getText()
                        .trim();

        String phone =
                phoneField
                        .getText()
                        .trim();

        String address =
                addressField
                        .getText()
                        .trim();

        String city =
                cityField
                        .getText()
                        .trim();

        String province =
                (String)
                        provinceBox
                                .getSelectedItem();

        String postalCode =
                postalCodeField
                        .getText()
                        .trim();

        if (
                firstName.isEmpty()
                || lastName.isEmpty()
                || email.isEmpty()
                || phone.isEmpty()
                || address.isEmpty()
                || city.isEmpty()
                || postalCode.isEmpty()
                || province.equals(
                        "Select Province"
                )
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please complete all required donor information.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        double amount = 0;

        if (r50Button.isSelected()) {

            amount = 50;

        } else if (r100Button.isSelected()) {

            amount = 100;

        } else {

            String custom =
                    customAmountField
                            .getText()
                            .trim();

            if (
                    custom.isEmpty()
                    || custom.equals(
                            "ENTER AMOUNT"
                    )
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select R50, R100, or enter a custom amount.",
                        "Donation Amount",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

            try {

                amount =
                        Double.parseDouble(
                                custom
                        );

            } catch (
                    NumberFormatException ex
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid donation amount.",
                        "Invalid Amount",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }
        }

        if (amount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Donation amount must be greater than R0.",
                    "Invalid Amount",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String paymentMethod = "";

        if (cardButton.isSelected()) {

            paymentMethod = "Card";

        } else if (eftButton.isSelected()) {

            paymentMethod = "EFT";

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a payment method.",
                    "Payment Method",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try (
                Connection connection =
                        getConnection()
        ) {

            connection.setAutoCommit(false);

            try {

                int donorID =
                        getNextDonorId(
                                connection
                        );

                String donorSQL =
                        "INSERT INTO DONOR "
                        + "(donorID, firstName, lastName, email, "
                        + "phoneNumber, streetAddress, city, "
                        + "province, postalCode) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                try (
                        PreparedStatement donorStatement =
                                connection.prepareStatement(
                                        donorSQL
                                )
                ) {

                    donorStatement.setInt(
                            1,
                            donorID
                    );

                    donorStatement.setString(
                            2,
                            firstName
                    );

                    donorStatement.setString(
                            3,
                            lastName
                    );

                    donorStatement.setString(
                            4,
                            email
                    );

                    donorStatement.setString(
                            5,
                            phone
                    );

                    donorStatement.setString(
                            6,
                            address
                    );

                    donorStatement.setString(
                            7,
                            city
                    );

                    donorStatement.setString(
                            8,
                            province
                    );

                    donorStatement.setString(
                            9,
                            postalCode
                    );

                    donorStatement.executeUpdate();
                }

                int donationID =
                        getNextDonationId(
                                connection
                        );

                String donationSQL =
                        "INSERT INTO DONATION "
                        + "(donationID, donorID, amount, "
                        + "paymentMethod, donationDate) "
                        + "VALUES (?, ?, ?, ?, ?)";

                try (
                        PreparedStatement donationStatement =
                                connection.prepareStatement(
                                        donationSQL
                                )
                ) {

                    donationStatement.setInt(
                            1,
                            donationID
                    );

                    donationStatement.setInt(
                            2,
                            donorID
                    );

                    donationStatement.setDouble(
                            3,
                            amount
                    );

                    donationStatement.setString(
                            4,
                            paymentMethod
                    );

                    donationStatement.setDate(
                            5,
                            Date.valueOf(
                                    LocalDate.now()
                            )
                    );

                    donationStatement.executeUpdate();
                }

                connection.commit();

                JOptionPane.showMessageDialog(
                        this,
                        "Thank you, "
                        + firstName
                        + "!\n\n"
                        + "Your registration and donation "
                        + "have been saved successfully.\n\n"
                        + "Donation Amount: R"
                        + String.format(
                                "%.2f",
                                amount
                        ),
                        "Thank You!",
                        JOptionPane.INFORMATION_MESSAGE
                );

                clearForm();

            } catch (SQLException ex) {

                connection.rollback();

                throw ex;
            }

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not complete registration:\n"
                    + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getNextDonorId(
            Connection connection
    ) throws SQLException {

        String sql =
                "SELECT COALESCE(MAX(donorID), 0) + 1 "
                + "FROM DONOR";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        );

                ResultSet results =
                        statement.executeQuery()
        ) {

            results.next();

            return results.getInt(1);
        }
    }

    private int getNextDonationId(
            Connection connection
    ) throws SQLException {

        String sql =
                "SELECT COALESCE(MAX(donationID), 0) + 1 "
                + "FROM DONATION";

        try (
                PreparedStatement statement =
                        connection.prepareStatement(
                                sql
                        );

                ResultSet results =
                        statement.executeQuery()
        ) {

            results.next();

            return results.getInt(1);
        }
    }

    private void clearForm() {

        firstNameField.setText("");

        lastNameField.setText("");

        emailField.setText("");

        phoneField.setText("");

        addressField.setText("");

        cityField.setText("");

        postalCodeField.setText("");

        provinceBox.setSelectedIndex(0);

        r50Button.setSelected(false);

        r100Button.setSelected(false);

        cardButton.setSelected(false);

        eftButton.setSelected(false);

        customAmountField.setText(
                "ENTER AMOUNT"
        );

        customAmountField.setForeground(
                Color.GRAY
        );
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setBorder(new EmptyBorder(12, 26, 0, 26));
        footer.setBackground(navy);

        JPanel columns = new JPanel(new GridLayout(1, 3, 24, 0));
        columns.setOpaque(false);
        columns.setBorder(new EmptyBorder(0, 0, 12, 0));

        JPanel brand = new JPanel();
        brand.setOpaque(false);
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Sikunye Charity");
        JLabel tagline = new JLabel("Together we are one");
        styleFooterLabel(name, true);
        styleFooterLabel(tagline, false);
        brand.add(name);
        brand.add(Box.createVerticalStrut(14));
        brand.add(tagline);

        JPanel quickLinks = new JPanel();
        quickLinks.setOpaque(false);
        quickLinks.setLayout(new BoxLayout(quickLinks, BoxLayout.Y_AXIS));
        JLabel linksTitle = new JLabel("Quick Links");
        styleFooterLabel(linksTitle, true);
        quickLinks.add(linksTitle);
        quickLinks.add(Box.createVerticalStrut(4));
        for (String label : new String[]{
                "Home", "ItemsDonated", "Projects", "Beneficiaries",
                "Donor", "DonationsRecieved", "Contact", "Feedback"
        }) {
            JLabel link = new JLabel(label);
            styleFooterLabel(link, false);
            quickLinks.add(link);
            quickLinks.add(Box.createVerticalStrut(2));
        }

        JPanel follow = new JPanel();
        follow.setOpaque(false);
        follow.setLayout(new BoxLayout(follow, BoxLayout.Y_AXIS));
        JLabel followTitle = new JLabel("Follow us");
        JLabel icons = new JLabel("f   ◎   in");
        styleFooterLabel(followTitle, true);
        styleFooterLabel(icons, false);
        icons.setFont(new Font("Arial", Font.BOLD, 18));
        follow.add(followTitle);
        follow.add(Box.createVerticalStrut(10));
        follow.add(icons);

        columns.add(brand);
        columns.add(quickLinks);
        columns.add(follow);

        JPanel copyrightRow = new JPanel(new BorderLayout());
        copyrightRow.setOpaque(false);
        copyrightRow.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(115, 124, 132)),
                new EmptyBorder(8, 0, 8, 0)));
        JLabel copyright = new JLabel(
                "© 2020 SikunyeCharity.All rights reserved",
                SwingConstants.CENTER
        );
        styleFooterLabel(copyright, false);
        copyrightRow.add(copyright, BorderLayout.CENTER);

        footer.add(columns);
        footer.add(copyrightRow);
        return footer;
    }

    private void styleFooterLabel(JLabel label, boolean heading) {
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", heading ? Font.BOLD : Font.PLAIN, heading ? 14 : 12));
    }

    private void showHomePage() {
        HomeGUI home = new HomeGUI();
        home.setGui();
        home.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        home.setExtendedState(JFrame.MAXIMIZED_BOTH);
        home.setLocationRelativeTo(null);
        home.setVisible(true);
    }

    private void showProjectsPage() {
        openLinkedPage(
                "za.ac.cput.sikunyecharity.GUI.ProjectsGUI",
                "za.ac.cput.sikunyecharity.GUI.ProjectGUI",
                "za.ac.cput.sikunyecharity.GUI.ProjectsPage",
                "za.ac.cput.sikunyecharity.GUI.ProjectPage"
        );
    }

    private void showContactPage() {
        ContactGUI contact = new ContactGUI();
        contact.setGUI();
        contact.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contact.setExtendedState(JFrame.MAXIMIZED_BOTH);
        contact.setLocationRelativeTo(null);
        contact.setVisible(true);
    }

    private void openLinkedPage(String... classNames) {
        for (String className : classNames) {
            try {
                Class<?> pageClass = Class.forName(className);
                Object instance = pageClass.getDeclaredConstructor().newInstance();

                if (!(instance instanceof JFrame)) {
                    throw new ClassCastException(className + " is not a JFrame page");
                }

                JFrame page = (JFrame) instance;
                initializePage(pageClass, page);
                page.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                page.setExtendedState(JFrame.MAXIMIZED_BOTH);
                page.setLocationRelativeTo(null);
                page.setVisible(true);
                return;

            } catch (ClassNotFoundException ex) {
                // Try the next supported class name for the Projects page.
            } catch (ReflectiveOperationException | ClassCastException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Could not open " + className + ": " + ex.getMessage(),
                        "Page Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "The Projects page class is not in this folder yet. "
                + "When it is added as ProjectsGUI or ProjectGUI, this link will open it.",
                "Projects Page Not Found",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void initializePage(Class<?> pageClass, JFrame page)
            throws ReflectiveOperationException {

        for (String methodName : new String[]{"setGui", "setGUI"}) {
            try {
                pageClass.getMethod(methodName).invoke(page);
                return;
            } catch (NoSuchMethodException ex) {
                // Some page classes build their interface in the constructor.
            }
        }
    }

 private Connection getConnection() throws SQLException {

    System.out.println("=== DERBY TEST ===");
    
    System.out.println(
    "JAVA CLASS PATH = "
    + System.getProperty("java.class.path")
);

    System.out.println(
        "ClientAutoloadedDriver = "
        + getClass().getResource(
            "/org/apache/derby/client/ClientAutoloadedDriver.class"
        )
    );

    System.out.println(
        "ClientDriver = "
        + getClass().getResource(
            "/org/apache/derby/jdbc/ClientDriver.class"
        )
    );

    System.out.println(
        "DriverManager drivers:"
    );

    java.util.Enumeration<java.sql.Driver> drivers =
            DriverManager.getDrivers();

    while (drivers.hasMoreElements()) {
        System.out.println(
            drivers.nextElement().getClass().getName()
        );
    }

    System.out.println("=== END DERBY TEST ===");

    return DriverManager.getConnection(
            DB_URL,
            DB_USER,
            DB_PASSWORD
    );
}
    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(() -> {

                    DonationED app =
                            new DonationED();

                    app.setVisible(true);
                }
        );
    }
}


