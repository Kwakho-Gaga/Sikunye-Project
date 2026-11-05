package za.ac.cput.sikunyecharity.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import za.ac.cput.sikunyecharity.DAO.DonorDonationDAOED;
import za.ac.cput.sikunyecharity.Domain.Project;

/**
 * Esona Dyomfana 240949072
 */
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

        // Universal Navigation Bar
        mainPanel.add(new HeaderPanel(this, "Donor"), BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(246, 240, 248));

        showDonatePage();

        mainPanel.add(contentPanel, BorderLayout.CENTER);

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

        JPanel inlineFooter = createInlineFooterPanel();
        inlineFooter.setAlignmentX(Component.CENTER_ALIGNMENT);

        pageContent.add(hero);
        pageContent.add(donationForm);
        pageContent.add(inlineFooter);

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
        outer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 520));

        JPanel form = new JPanel(new GridLayout(1, 2, 0, 0));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 1),
                new EmptyBorder(10, 20, 10, 20)
        ));

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
                "North West", "Western Cape"
        });
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

        JLabel projectTitle = new JLabel("PROJECT");
        projectTitle.setFont(new Font("Arial", Font.PLAIN, 10));
        content.add(projectTitle);
        content.add(Box.createVerticalStrut(5));

        projectBox = new JComboBox<>();
        projectBox.setFont(new Font("Arial", Font.PLAIN, 9));
        projectBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
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

        cardButton = new JRadioButton("CARD\nCREDIT/DEBIT CARD");
        eftButton = new JRadioButton("EFT\nBANK TRANSFER");

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

    private void registerAndDonate() {
        if (projectBox == null || !projectBox.isEnabled()) {
            JOptionPane.showMessageDialog(this, "The project list could not be loaded. Please check the database connection and try again.", "Projects Unavailable", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        String city = cityField.getText().trim();
        String province = (String) provinceBox.getSelectedItem();
        String postalCode = postalCodeField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || city.isEmpty() || postalCode.isEmpty() || province.equals("Select Province")) {
            JOptionPane.showMessageDialog(this, "Please complete all required donor information.", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double amount = 0;
        if (r50Button.isSelected()) {
            amount = 50;
        } else if (r100Button.isSelected()) {
            amount = 100;
        } else {
            String custom = customAmountField.getText().trim();
            if (custom.isEmpty() || custom.equals("ENTER AMOUNT")) {
                JOptionPane.showMessageDialog(this, "Please select R50, R100, or enter a custom amount.", "Donation Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                amount = Double.parseDouble(custom);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid donation amount.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Donation amount must be greater than R0.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String paymentMethod = "";
        if (cardButton.isSelected()) {
            paymentMethod = "Card";
        } else if (eftButton.isSelected()) {
            paymentMethod = "EFT";
        } else {
            JOptionPane.showMessageDialog(this, "Please select a payment method.", "Payment Method", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Project selectedProject = (Project) projectBox.getSelectedItem();
            String projectId = selectedProject == null ? null : selectedProject.getProjectId();
            DonorDonationDAOED.DonationLink savedLink = new DonorDonationDAOED().saveDonorAndDonation(
                    firstName, lastName, email, phone, address, city, province, postalCode, amount, paymentMethod, LocalDate.now(), projectId
            );

            String savedProjectId = savedLink.getProjectId() == null ? "General Fund" : savedLink.getProjectId();

            JOptionPane.showMessageDialog(this,
                    "Thank you, " + firstName + "!\n\n"
                            + "Your donation has been saved.\n\n"
                            + "Donor ID: " + savedLink.getDonorId() + "\n"
                            + "Project ID: " + savedProjectId + "\n"
                            + "Donation Amount: R" + String.format("%.2f", amount),
                    "Thank You!",
                    JOptionPane.INFORMATION_MESSAGE);

            clearForm();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Could not complete registration:\n" + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
        r50Button.setSelected(false);
        r100Button.setSelected(false);
        cardButton.setSelected(false);
        eftButton.setSelected(false);
        customAmountField.setText("ENTER AMOUNT");
        customAmountField.setForeground(Color.GRAY);
    }

    /**
     * Inline Footer Panel matching the exact layout and design from ContactGUI.
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
        tag1.setForeground(cyanAccent);
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

        String[] links = {"Home", "Projects", "Beneficiaries", "Items Donated", "Donor", "Contact", "Feedback"};
        for (String link : links) {
            JLabel linkLbl = new JLabel(link);
            linkLbl.setFont(new Font("Inter", Font.PLAIN, 11));
            linkLbl.setForeground(new Color(160, 174, 186));
            linkLbl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            linkLbl.setAlignmentX(Component.LEFT_ALIGNMENT);
            linkLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { linkLbl.setForeground(cyanAccent); }
                @Override
                public void mouseExited(MouseEvent e) { linkLbl.setForeground(new Color(160, 174, 186)); }
                @Override
                public void mouseClicked(MouseEvent e) { navigateTo(link); }
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
                public void mouseEntered(MouseEvent e) { socLbl.setForeground(cyanAccent); }
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
                g2.setColor(cyanAccent);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        reviewsBtn.setFont(new Font("Inter", Font.BOLD, 11));
        reviewsBtn.setForeground(cyanAccent);
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
        rightTagline.setForeground(cyanAccent);

        bottomBar.add(copyLabel, BorderLayout.WEST);
        bottomBar.add(rightTagline, BorderLayout.EAST);

        footerPanel.add(mainContentPanel, BorderLayout.CENTER);
        footerPanel.add(bottomBar, BorderLayout.SOUTH);

        return footerPanel;
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