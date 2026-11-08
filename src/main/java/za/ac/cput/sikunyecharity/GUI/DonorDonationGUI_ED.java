package za.ac.cput.sikunyecharity.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import za.ac.cput.sikunyecharity.DAO.DonorDonationDAOED;
import za.ac.cput.sikunyecharity.DAO.DonorDonationDAOED.DonationLink;
import za.ac.cput.sikunyecharity.Domain.Project;
import za.ac.cput.sikunyecharity.DAO.ItemDonatedDAO;
import za.ac.cput.sikunyecharity.Domain.ItemDonatedModel;
import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.Domain.DonationItem;

public class DonorDonationGUI_ED extends JFrame {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField postalCodeField;

    private JComboBox<String> provinceBox;
    private JComboBox<Project> projectBox;
    private JComboBox<String> donationTypeCombo;
    private JComboBox<String> itemNameCombo;
    private JSpinner itemQuantitySpinner;

    private JRadioButton r50Button;
    private JRadioButton r100Button;
    private JTextField customAmountField;
    private JRadioButton cardButton;
    private JRadioButton eftButton;
    private JPanel contentPanel;

    private final Color navy = new Color(62, 72, 80);
    private final Color blue = new Color(39, 190, 216);
    private final Color lightGray = new Color(242, 237, 246);
    private final Color darkGray = new Color(111, 202, 222);
    private final Color cyanAccent = new Color(0, 192, 232);

    public DonorDonationGUI_ED() {
        setTitle("Sikunye Charity");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        createInterface();
    }

    private void createInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(new HeaderPanel(this, "Donor"), BorderLayout.NORTH);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(246, 240, 248));
        showDonatePage();
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(new FooterPanel(this), BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    private void showDonatePage() {
        JPanel pageContent = new JPanel();
        pageContent.setLayout(new BoxLayout(pageContent, BoxLayout.Y_AXIS));
        pageContent.setBackground(new Color(246, 240, 248));
        JPanel hero = createHero();
        hero.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel donationForm = createDonationForm();
        donationForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        pageContent.add(hero);
        pageContent.add(donationForm);
        JScrollPane pageScroll = new JScrollPane(pageContent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pageScroll.setBorder(null);
        pageScroll.getVerticalScrollBar().setUnitIncrement(16);
        contentPanel.removeAll();
        contentPanel.add(pageScroll, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createHero() {
        JPanel hero = new JPanel(new BorderLayout());
        hero.setPreferredSize(new Dimension(1, 155));
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        hero.setBackground(darkGray);
        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(new EmptyBorder(12, 25, 8, 10));
        JLabel title = new JLabel("<html>BECOME A <font color='#28C7DE'>DONOR</font></html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        JLabel description = new JLabel("<html>YOUR SUPPORT HELPS SIKUNYE CHARITY BRING HOPE,<br>DIGNITY AND OPPORTUNITIES TO THOSE IN NEED.</html>");
        description.setForeground(Color.WHITE);
        description.setFont(new Font("Arial", Font.PLAIN, 9));
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(description);
        textPanel.add(Box.createVerticalStrut(10));
        JPanel information = new JPanel(new GridLayout(1, 3, 15, 0));
        information.setOpaque(false);
        information.add(createInfoBox("✋", "MAKE AN IMPACT", "YOUR DONATION CHANGES LIVES."));
        information.add(createInfoBox("◎", "100% SECURE", "YOUR INFORMATION IS SAFE WITH US."));
        information.add(createInfoBox("♙", "TRANSPARENCY", "WE ARE COMMITTED TO ACCOUNTABILITY."));
        textPanel.add(information);
        hero.add(textPanel, BorderLayout.CENTER);
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        java.net.URL imageURL = getClass().getResource("/za/ac/cput/sikunyecharity/two-little-girls-hugging-in-black-and-white-photo.jpg");
        if (imageURL != null) {
            ImageIcon imageIcon = new ImageIcon(imageURL);
            Image image = imageIcon.getImage();
            Image resized = image.getScaledInstance(350, 155, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(resized));
        }
        hero.add(imageLabel, BorderLayout.EAST);
        return hero;
    }

    private JPanel createInfoBox(String icon, String title, String text) {
        JPanel panel = new JPanel(new BorderLayout(7, 0));
        panel.setOpaque(false);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setForeground(blue);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        panel.add(iconLabel, BorderLayout.WEST);
        JLabel textLabel = new JLabel("<html><b>" + title + "</b><br><font size='1'>" + text + "</font></html>");
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.PLAIN, 9));
        panel.add(textLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDonationForm() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(new Color(246, 240, 248));
        outer.setBorder(new EmptyBorder(15, 65, 20, 65));
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 560));
        JPanel form = new JPanel(new GridLayout(1, 2, 0, 0));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                new EmptyBorder(10, 20, 10, 20)));
        form.add(createDonorSection());
        form.add(createDonationSection());
        outer.add(form, BorderLayout.CENTER);
        return outer;
    }

    private JPanel createDonorSection() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.DARK_GRAY));
        JLabel heading = new JLabel("♙   DONOR INFORMATION");
        heading.setForeground(Color.BLACK);
        heading.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(heading, BorderLayout.NORTH);
        JPanel fields = new JPanel(new GridLayout(0, 1, 4, 4));
        fields.setBackground(Color.WHITE);
        JPanel names = new JPanel(new GridLayout(1, 2, 12, 0));
        names.setBackground(Color.WHITE);
        firstNameField = createField("FIRST NAME*", "ENTER YOUR FIRST NAME");
        lastNameField = createField("LAST NAME*", "ENTER YOUR LAST NAME");
        names.add(createLabeledField("FIRST NAME*", firstNameField));
        names.add(createLabeledField("LAST NAME*", lastNameField));
        fields.add(names);
        emailField = createField("EMAIL ADDRESS*", "ENTER YOUR EMAIL ADDRESS");
        fields.add(createLabeledField("EMAIL ADDRESS*", emailField));
        phoneField = createField("PHONE NUMBER*", "ENTER YOUR PHONE NUMBER E.G. 0821234567");
        fields.add(createLabeledField("PHONE NUMBER*", phoneField));
        addressField = createField("ADDRESS*", "STREET ADDRESS");
        fields.add(createLabeledField("ADDRESS*", addressField));
        JPanel location = new JPanel(new GridLayout(1, 2, 10, 0));
        location.setBackground(Color.WHITE);
        cityField = createField("", "CITY / STATE / PROVINCE");
        postalCodeField = createField("", "POSTAL CODE");
        location.add(cityField);
        location.add(postalCodeField);
        fields.add(location);
        provinceBox = new JComboBox<>(new String[]{
            "Select Province", "Eastern Cape", "Free State", "Gauteng",
            "KwaZulu-Natal", "Limpopo", "Mpumalanga", "Northern Cape",
            "North West", "Western Cape"});
        provinceBox.setFont(new Font("Arial", Font.PLAIN, 9));
        fields.add(provinceBox);
        panel.add(fields, BorderLayout.CENTER);
        JLabel required = new JLabel("* REQUIRED FIELDS");
        required.setFont(new Font("Arial", Font.PLAIN, 8));
        required.setForeground(Color.GRAY);
        panel.add(required, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDonationSection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 18, 0, 0));
        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel typeTitle = new JLabel("▣   DONATION TYPE");
        typeTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(typeTitle);
        content.add(Box.createVerticalStrut(5));
        donationTypeCombo = new JComboBox<>(new String[]{"FINANCIAL", "IN-KIND"});
        donationTypeCombo.setFont(new Font("Arial", Font.PLAIN, 9));
        content.add(donationTypeCombo);
        content.add(Box.createVerticalStrut(12));

        JLabel itemTitle = new JLabel("ITEM TO DONATE");
        itemTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(itemTitle);
        content.add(Box.createVerticalStrut(5));
        JPanel itemDonationPanel = new JPanel(new GridLayout(2, 2, 8, 3));
        itemDonationPanel.setBackground(Color.WHITE);
        JLabel itemNameLabel = new JLabel("ITEM");
        JLabel itemQuantityLabel = new JLabel("QUANTITY");
        itemNameLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        itemQuantityLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        itemNameCombo = new JComboBox<>(new String[]{
            "Select item", "School supplies", "Clothing", "Food", "Books", "Blankets"
        });
        itemNameCombo.setFont(new Font("Arial", Font.PLAIN, 9));
        itemQuantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
        itemQuantitySpinner.setFont(new Font("Arial", Font.PLAIN, 9));
        itemDonationPanel.add(itemNameLabel);
        itemDonationPanel.add(itemQuantityLabel);
        itemDonationPanel.add(itemNameCombo);
        itemDonationPanel.add(itemQuantitySpinner);
        itemTitle.setEnabled(false);
        itemNameLabel.setEnabled(false);
        itemQuantityLabel.setEnabled(false);
        itemNameCombo.setEnabled(false);
        itemQuantitySpinner.setEnabled(false);
        content.add(itemDonationPanel);
        content.add(Box.createVerticalStrut(12));

        JLabel amountTitle = new JLabel("▣   DONATION AMOUNT");
        amountTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(amountTitle);
        content.add(Box.createVerticalStrut(10));
        JPanel amountButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        amountButtons.setBackground(Color.WHITE);
        r50Button = new JRadioButton("R50");
        r100Button = new JRadioButton("R100");
        r50Button.setBackground(lightGray);
        r100Button.setBackground(lightGray);
        r50Button.setFont(new Font("Arial", Font.PLAIN, 9));
        r100Button.setFont(new Font("Arial", Font.PLAIN, 9));
        ButtonGroup amountGroup = new ButtonGroup();
        amountGroup.add(r50Button);
        amountGroup.add(r100Button);
        amountButtons.add(r50Button);
        amountButtons.add(Box.createHorizontalStrut(15));
        amountButtons.add(r100Button);
        content.add(amountButtons);
        content.add(Box.createVerticalStrut(8));
        JLabel customLabel = new JLabel("CUSTOM AMOUNT");
        customLabel.setFont(new Font("Arial", Font.PLAIN, 8));
        content.add(customLabel);
        JPanel custom = new JPanel(new BorderLayout());
        custom.setBackground(Color.WHITE);
        JLabel rand = new JLabel("  R  ");
        rand.setFont(new Font("Arial", Font.BOLD, 10));
        customAmountField = new JTextField("ENTER AMOUNT");
        customAmountField.setForeground(Color.GRAY);
        customAmountField.setFont(new Font("Arial", Font.PLAIN, 9));
        customAmountField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (customAmountField.getText().equals("ENTER AMOUNT")) {
                    customAmountField.setText("");
                    customAmountField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (customAmountField.getText().trim().isEmpty()) {
                    customAmountField.setText("ENTER AMOUNT");
                    customAmountField.setForeground(Color.GRAY);
                }
            }
        });
        custom.add(rand, BorderLayout.WEST);
        custom.add(customAmountField, BorderLayout.CENTER);
        content.add(custom);
        content.add(Box.createVerticalStrut(10));

        donationTypeCombo.addActionListener(e -> {
            boolean isInKind = "IN-KIND".equals(donationTypeCombo.getSelectedItem());
            r50Button.setEnabled(!isInKind);
            r100Button.setEnabled(!isInKind);
            customAmountField.setEnabled(!isInKind);
            itemTitle.setEnabled(isInKind);
            itemNameLabel.setEnabled(isInKind);
            itemQuantityLabel.setEnabled(isInKind);
            itemNameCombo.setEnabled(isInKind);
            itemQuantitySpinner.setEnabled(isInKind);
            if (isInKind) {
                r50Button.setSelected(false);
                r100Button.setSelected(false);
                customAmountField.setText("0.00");
                customAmountField.setForeground(Color.GRAY);
            } else {
                customAmountField.setText("ENTER AMOUNT");
                customAmountField.setForeground(Color.GRAY);
            }
        });

        JLabel projectTitle = new JLabel("PROJECT");
        projectTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(projectTitle);
        content.add(Box.createVerticalStrut(5));
        projectBox = new JComboBox<>();
        projectBox.setFont(new Font("Arial", Font.PLAIN, 9));
        projectBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "General Fund" : ((Project) value).getName());
                return this;
            }
        });
        projectBox.addItem(null);
        try {
            for (Project project : new DonorDonationDAOED().getProjects()) {
                projectBox.addItem(project);
            }
        } catch (SQLException ex) {
            projectBox.setEnabled(false);
            projectBox.setToolTipText("Could not load projects: " + ex.getMessage());
        }
        projectBox.setSelectedIndex(0);
        content.add(projectBox);
        content.add(Box.createVerticalStrut(15));
        JLabel paymentTitle = new JLabel("▣   PAYMENT METHOD");
        paymentTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(paymentTitle);
        content.add(Box.createVerticalStrut(10));
        JPanel payment = new JPanel(new GridLayout(1, 2, 10, 0));
        payment.setBackground(Color.WHITE);
        cardButton = new JRadioButton("CARD CREDIT/DEBIT CARD");
        eftButton = new JRadioButton("EFT BANK TRANSFER");
        cardButton.setHorizontalAlignment(SwingConstants.CENTER);
        eftButton.setHorizontalAlignment(SwingConstants.CENTER);
        cardButton.setBackground(new Color(248, 248, 248));
        eftButton.setBackground(new Color(248, 248, 248));
        cardButton.setFont(new Font("Arial", Font.PLAIN, 9));
        eftButton.setFont(new Font("Arial", Font.PLAIN, 9));
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cardButton);
        paymentGroup.add(eftButton);
        payment.add(cardButton);
        payment.add(eftButton);
        content.add(payment);
        content.add(Box.createVerticalStrut(15));
        JButton donateBtn = new JButton("<html>♡  REGISTER & DONATE<br><font size='1'>JOIN US IN MAKING A DIFFERENCE</font></html>");
        donateBtn.setForeground(Color.WHITE);
        donateBtn.setBackground(blue);
        donateBtn.setOpaque(true);
        donateBtn.setContentAreaFilled(true);
        donateBtn.setBorderPainted(false);
        donateBtn.setFocusPainted(false);
        donateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension btnDim = new Dimension(300, 55);
        donateBtn.setPreferredSize(btnDim);
        donateBtn.setMinimumSize(btnDim);
        donateBtn.setMaximumSize(btnDim);
        donateBtn.addActionListener(e -> registerAndDonate());
        JScrollPane contentScroll = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        contentScroll.setBorder(BorderFactory.createEmptyBorder());
        panel.add(contentScroll, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(donateBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JTextField createField(String title, String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 9));
        field.setBackground(lightGray);
        field.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        field.setToolTipText(placeholder);
        return field;
    }

    private JPanel createLabeledField(String label, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(0, 2));
        panel.setBackground(Color.WHITE);
        if (!label.isEmpty()) {
            JLabel title = new JLabel(label);
            title.setFont(new Font("Arial", Font.PLAIN, 8));
            panel.add(title, BorderLayout.NORTH);
        }
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    // FIXED: IN-KIND now behaves like every other page - no second window
    // FIXED: No redirect, no login, saves ItemID to BOTH tables
    private void registerAndDonate() {
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String street = addressField.getText().trim();
            String city = cityField.getText().trim();
            String province = (String) provinceBox.getSelectedItem();
            if ("Select Province".equals(province)) {
                province = "";
            }
            String postal = postalCodeField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || street.isEmpty() || city.isEmpty() || postal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required donor fields.");
                return;
            }

            String donationType = (String) donationTypeCombo.getSelectedItem();
            boolean isInKind = "IN-KIND".equals(donationType);
            String itemName = isInKind ? (String) itemNameCombo.getSelectedItem() : null;
            int itemQuantity = isInKind ? ((Number) itemQuantitySpinner.getValue()).intValue() : 0;
            if (isInKind && (itemName == null || "Select item".equals(itemName))) {
                JOptionPane.showMessageDialog(this, "Please choose the item being donated.");
                return;
            }

            // Map Donor combo -> ItemID for DONATIONITEM table only
            // School supplies=1, Clothing=2, Food=3, Books=4, Blankets=5
            int itemId = 0;
            if (isInKind) {
                switch (itemName) {
                    case "School supplies":
                        itemId = 1;
                        break;
                    case "Clothing":
                        itemId = 2;
                        break;
                    case "Food":
                        itemId = 3;
                        break;
                    case "Books":
                        itemId = 4;
                        break;
                    case "Blankets":
                        itemId = 5;
                        break;
                }
            }

            double amount = 0;
            if (!isInKind) {
                if (r50Button.isSelected()) {
                    amount = 50;
                } else if (r100Button.isSelected()) {
                    amount = 100;
                } else {
                    String t = customAmountField.getText().trim();
                    if (t.isEmpty() || t.equals("ENTER AMOUNT")) {
                        JOptionPane.showMessageDialog(this, "Please select R50, R100 or enter a custom amount.");
                        return;
                    }
                    amount = Double.parseDouble(t);
                }
            }

            String paymentMethod = "Card";
            if (eftButton.isSelected()) {
                paymentMethod = "EFT";
            } else if (cardButton.isSelected()) {
                paymentMethod = "Card";
            }

            Project selectedProject = (Project) projectBox.getSelectedItem();
            String projectId = (selectedProject == null) ? null : selectedProject.getProjectId();

            DonorDonationDAOED dao = new DonorDonationDAOED();
            DonationLink link = dao.saveDonorAndDonation(
                    firstName, lastName, email, phone, street, city, province, postal,
                    amount, paymentMethod, LocalDate.now(), projectId, donationType,
                    itemName, itemQuantity
            );

            if (isInKind) {
                // ---- POPULATE BOTH TABLES WITH SAME DONATION ----

                // Table 1: DONATIONITEM = Donated Items Inventory (bottom table on Donations Received)
                try {
                    DonationItemDAO donationItemDao = new DonationItemDAO();
                    if (!donationItemDao.itemExistsForDonation(link.getDonationId(), itemId)) {
                        // DonationItem has no empty constructor - must use 5-arg constructor
// 0 = DonationItemID, DAO will auto-generate it in addDonationItem()
                        DonationItem di = new DonationItem(
                                0, // DonationItemID - auto
                                link.getDonationId(), // DonationID - e.g. 6
                                itemId, // ItemID - e.g. 4 for Books
                                itemQuantity, // Quantity - e.g. 6
                                "Good" // Condition
                        );
                        donationItemDao.addDonationItem(di);
                    }
                } catch (Exception e1) {
                    System.out.println("DONATIONITEM not saved: " + e1.getMessage());
                }

                // Table 2: ITEMS_DONATED = Items Donated / Item Received page
                // ItemDonatedModel constructor from your ItemDonated.java:
                // new ItemDonatedModel(receiptID, itemName, quantity, condition)
                // itemID is Auto in this table, receiptID = DonationID links it to Table 1
                try {
    ItemDonatedDAO inventoryDao = new ItemDonatedDAO();
    ItemDonatedModel inv = new ItemDonatedModel(
            itemId,                 // ItemID - SAME as DONATIONITEM, e.g. 2 for Clothing
            link.getDonationId(),   // receiptID = DonationID, e.g. 2
            itemName,               // Clothing
            itemQuantity,           // 500
            "Good"                  // not "Not specified"
    );
    inventoryDao.addInventoryItem(inv);
} catch (Exception e2) {
    System.out.println("ITEMS_DONATED not saved: " + e2.getMessage());
    e2.printStackTrace();
}

                // ---- JUST SHOW MESSAGE, STAY HERE - NO REDIRECT, NO LOGIN ----
                JOptionPane.showMessageDialog(this,
                        "Thank you, " + firstName + "!\n\n"
                        + "Your IN-KIND donation has been saved.\n"
                        + "Donor ID: " + link.getDonorId()
                        + "\nProject ID: " + (link.getProjectId() == null ? "General Fund" : link.getProjectId())
                        + "\nDonation ID: " + link.getDonationId()
                        + "\nItem: " + itemName
                        + "\nItem ID: " + itemId
                        + "\nQuantity: " + itemQuantity,
                        "Thank You", JOptionPane.INFORMATION_MESSAGE);

                clearForm();
                // DELETED: DashboardGUI.setPendingInKindDonation(...) + AppNavigation.open(...)
            } else {
                JOptionPane.showMessageDialog(this,
                        "Thank you, " + firstName + "!\n\n"
                        + "Your donation has been saved.\nDonor ID: " + link.getDonorId()
                        + "\nProject ID: " + (link.getProjectId() == null ? "General Fund" : link.getProjectId())
                        + "\nAmount: R" + String.format("%.2f", amount));
                clearForm();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount (e.g. 100 or 100.00).");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
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
        projectBox.setSelectedIndex(0);
        donationTypeCombo.setSelectedIndex(0);
        itemNameCombo.setSelectedIndex(0);
        itemQuantitySpinner.setValue(1);
        r50Button.setSelected(false);
        r100Button.setSelected(false);
        r50Button.setEnabled(true);
        r100Button.setEnabled(true);
        cardButton.setSelected(false);
        eftButton.setSelected(false);
        customAmountField.setText("ENTER AMOUNT");
        customAmountField.setForeground(Color.GRAY);
        customAmountField.setEnabled(true);
    }

    private void navigateTo(String destination) {
        if ("Donor".equals(destination)) {
            showDonatePage();
            return;
        }
        if ("DonationsRecieved".equals(destination)) {
            JFrame frame = new JFrame("Donation Management");
            DonationGUI donationsPage = new DonationGUI();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(donationsPage);
            frame.setVisible(true);
            dispose();
            return;
        }
        AppNavigation.open(this, destination);
    }
}
