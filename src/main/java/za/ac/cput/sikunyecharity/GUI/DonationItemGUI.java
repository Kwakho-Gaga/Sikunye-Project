package za.ac.cput.sikunyecharity.GUI;

import za.ac.cput.sikunyecharity.DAO.DonationItemDAO;
import za.ac.cput.sikunyecharity.DAO.DonationDAO;
import za.ac.cput.sikunyecharity.Domain.DonationItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

/**
 * DonationItemGUI - Styled to match the Sikunye Charity design system,
 * featuring a full-width horizontal header banner, styled work card area, custom tables, and inline footer.
 */
public class DonationItemGUI extends JPanel {

    private JTextField donationItemIDField;
    private JTextField donationIDField;
    private JTextField itemIDField;
    private JTextField quantityField;
    private JTextField conditionField;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JButton backButton;

    private JTable itemTable;
    private DefaultTableModel model;

    // Used by the main application to return to DonationGUI.
    private Runnable backAction;

    // Design Tokens & System Colors
    private final Color color1 = new Color(0, 192, 232);       // Signature Cyan Accent
    private final Color color2 = Color.WHITE;                  // Pure White
    private final Color color3 = new Color(245, 239, 247);     // Soft Body Background
    private final Color color4 = Color.WHITE;                  // Card Background
    private final Color color5 = new Color(43, 55, 63);        // Dark Banner Background
    private final Color color7 = new Color(215, 220, 225);     // Light Gray Border

    private final Font titleFont = new Font("Inter", Font.BOLD, 22);
    private final Font subtitleFont = new Font("Inter", Font.PLAIN, 13);
    private final Font headerFont = new Font("Inter", Font.BOLD, 15);
    private final Font labelFont = new Font("Inter", Font.BOLD, 12);
    private final Font textFont = new Font("Inter", Font.PLAIN, 12);

    // -------------------- CONSTRUCTORS --------------------
    public DonationItemGUI() {
        this(0, null);
    }

    public DonationItemGUI(int donationID) {
        this(donationID, null);
    }

    public DonationItemGUI(int donationID, Runnable backAction) {
        this.backAction = backAction;

        setLayout(new BorderLayout());
        setBackground(color3);

        createGUI();

        if (donationID > 0) {
            setDonationID(donationID);
        }

        loadDonationItems();
        loadNextDonationItemID();
    }

    // -------------------- CREATE GUI --------------------
    private void createGUI() {
        JPanel mainScrollContent = new JPanel();
        mainScrollContent.setLayout(new BoxLayout(mainScrollContent, BoxLayout.Y_AXIS));
        mainScrollContent.setBackground(color3);

        // 1. Full-Width Dark Top Banner
        JPanel bannerPanel = new JPanel();
        bannerPanel.setLayout(new BoxLayout(bannerPanel, BoxLayout.Y_AXIS));
        bannerPanel.setBackground(color5);
        bannerPanel.setBorder(new EmptyBorder(18, 20, 18, 20));
        bannerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bannerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        JLabel titleLabel = new JLabel("DONATION ITEM MANAGEMENT", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(color1);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subLabel = new JLabel("Record, update, and manage in-kind donated items assigned to charity contributions.", SwingConstants.CENTER);
        subLabel.setFont(subtitleFont);
        subLabel.setForeground(color2);
        subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bannerPanel.add(titleLabel);
        bannerPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        bannerPanel.add(subLabel);

        // 2. Main Work Area (Form Card + Inventory Table Card)
        JPanel workArea = new JPanel(new BorderLayout(25, 25));
        workArea.setBackground(color3);
        workArea.setBorder(new EmptyBorder(25, 40, 25, 40));
        workArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formCard = createFormCard();
        JPanel tableCard = createTableCard();

        workArea.add(formCard, BorderLayout.WEST);
        workArea.add(tableCard, BorderLayout.CENTER);

        // 3. Inline Footer
        JPanel inlineFooter = createInlineFooterPanel();

        // Assemble Vertical Stack
        mainScrollContent.add(bannerPanel);
        mainScrollContent.add(workArea);
        mainScrollContent.add(inlineFooter);

        // Scroll Pane Wrap
        JScrollPane scrollPane = new JScrollPane(
                mainScrollContent,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // -------------------- BUTTON ACTIONS --------------------
        addButton.addActionListener(e -> addDonationItem());
        updateButton.addActionListener(e -> updateDonationItem());
        deleteButton.addActionListener(e -> deleteDonationItem());
        clearButton.addActionListener(e -> clearForm());
        backButton.addActionListener(e -> goBack());
    }

    // -------------------- FORM CARD CREATION --------------------
    private JPanel createFormCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(color4);
        card.setPreferredSize(new Dimension(380, 520));
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(20, 22, 20, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form Title
        JLabel cardTitle = new JLabel("Record Donated Item");
        cardTitle.setFont(headerFont);
        cardTitle.setForeground(color5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 4, 15, 4);
        card.add(cardTitle, gbc);

        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.gridwidth = 1;

        // Donation Item ID
        addFormField(card, gbc, 1, "Donation Item ID:");
        donationItemIDField = createStyledTextField(false);
        addFormComponent(card, gbc, 1, donationItemIDField);

        // Donation ID
        addFormField(card, gbc, 2, "Donation ID:");
        donationIDField = createStyledTextField(false);
        addFormComponent(card, gbc, 2, donationIDField);

        // Item ID
        addFormField(card, gbc, 3, "Item ID:");
        itemIDField = createStyledTextField(true);
        addFormComponent(card, gbc, 3, itemIDField);

        // Quantity
        addFormField(card, gbc, 4, "Quantity:");
        quantityField = createStyledTextField(true);
        addFormComponent(card, gbc, 4, quantityField);

        // Condition
        addFormField(card, gbc, 5, "Condition:");
        conditionField = createStyledTextField(true);
        addFormComponent(card, gbc, 5, conditionField);

        // Action Buttons Grid
        JPanel formButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        formButtonPanel.setBackground(color4);

        addButton = createStyledButton("Add Item", color1, color2);
        updateButton = createStyledButton("Update Item", color5, color2);
        deleteButton = createStyledButton("Delete Item", new Color(210, 45, 60), color2);
        clearButton = createStyledButton("Clear", new Color(110, 120, 130), color2);

        formButtonPanel.add(addButton);
        formButtonPanel.add(updateButton);
        formButtonPanel.add(deleteButton);
        formButtonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 4, 10, 4);
        card.add(formButtonPanel, gbc);

        // Back Button Row
        backButton = createStyledButton("Back to Donor", new Color(240, 243, 246), new Color(45, 55, 65));
        backButton.setBorder(new LineBorder(color7, 1, true));

        gbc.gridy = 7;
        gbc.insets = new Insets(5, 4, 0, 4);
        card.add(backButton, gbc);

        return card;
    }

    // -------------------- TABLE CARD CREATION --------------------
    private JPanel createTableCard() {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(color4);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 225, 230), 1, true),
                new EmptyBorder(20, 22, 20, 22)
        ));

        JLabel tableTitle = new JLabel("Donated Items Inventory");
        tableTitle.setFont(headerFont);
        tableTitle.setForeground(color5);

        String[] columns = {
            "DONATION ITEM ID",
            "DONATION ID",
            "ITEM ID",
            "QUANTITY",
            "CONDITION"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemTable = new JTable(model);
        itemTable.setRowHeight(34);
        itemTable.setFont(textFont);
        itemTable.setGridColor(new Color(230, 235, 240));
        itemTable.setSelectionBackground(new Color(220, 245, 252));
        itemTable.setSelectionForeground(Color.BLACK);

        JTableHeader header = itemTable.getTableHeader();
        header.setFont(new Font("Inter", Font.BOLD, 12));
        header.setBackground(color5);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 36));

        // Center align table contents
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < itemTable.getColumnCount(); i++) {
            itemTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane tableScrollPane = new JScrollPane(itemTable);
        tableScrollPane.setBorder(new LineBorder(new Color(220, 225, 230), 1));
        tableScrollPane.getViewport().setBackground(Color.WHITE);

        card.add(tableTitle, BorderLayout.NORTH);
        card.add(tableScrollPane, BorderLayout.CENTER);

        return card;
    }

    // -------------------- INLINE FOOTER PANEL --------------------
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

        String[] links = {"Home", "Projects", "Beneficiaries", "Items Donated", "Donor", "Contact", "Feedback"};
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
                public void mouseClicked(MouseEvent e) { navigateFooter(link); }
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

    private void navigateFooter(String destination) {
        Window currentWindow = SwingUtilities.getWindowAncestor(this);
        JFrame currentFrame = (currentWindow instanceof JFrame) ? (JFrame) currentWindow : null;
        AppNavigation.open(currentFrame, destination);
    }

    // -------------------- FORM HELPERS & STYLED COMPONENTS --------------------
    private JTextField createStyledTextField(boolean editable) {
        JTextField field = new JTextField();
        field.setFont(textFont);
        field.setPreferredSize(new Dimension(160, 30));
        field.setEditable(editable);

        if (editable) {
            field.setBackground(new Color(248, 250, 252));
            field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(color7, 1, true),
                    new EmptyBorder(4, 8, 4, 8)
            ));
        } else {
            field.setBackground(new Color(235, 238, 242));
            field.setForeground(new Color(100, 110, 120));
            field.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(210, 215, 220), 1, true),
                    new EmptyBorder(4, 8, 4, 8)
            ));
        }
        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        button.setFont(new Font("Inter", Font.BOLD, 12));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 34));
        return button;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String labelText) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(new Color(45, 55, 65));
        panel.add(label, gbc);
    }

    private void addFormComponent(JPanel panel, GridBagConstraints gbc, int row, JComponent component) {
        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(component, gbc);
    }

    // -------------------- SET DONATION ID --------------------
    public void setDonationID(int donationID) {
        donationIDField.setText(String.valueOf(donationID));
        donationIDField.setEditable(false);
    }

    // -------------------- BACK NAVIGATION --------------------
    private void goBack() {
        if (backAction != null) {
            backAction.run();
        } else {
            Window currentWindow = SwingUtilities.getWindowAncestor(this);
            JFrame currentFrame = (currentWindow instanceof JFrame) ? (JFrame) currentWindow : null;
            AppNavigation.open(currentFrame, "Donor");
        }
    }

    // -------------------- LOAD NEXT DONATION ITEM ID --------------------
    private void loadNextDonationItemID() {
        try {
            DonationItemDAO dao = new DonationItemDAO();
            int nextID = dao.getNextDonationItemID();
            donationItemIDField.setText(String.valueOf(nextID));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Unable to generate Donation Item ID:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- LOAD DONATION ITEMS --------------------
    private void loadDonationItems() {
        model.setRowCount(0);
        try {
            DonationItemDAO dao = new DonationItemDAO();
            List<DonationItem> items = dao.getAllDonationItems();

            for (DonationItem item : items) {
                model.addRow(new Object[]{
                    item.getDonationItemID(),
                    item.getDonationID(),
                    item.getItemID(),
                    item.getQuantity(),
                    item.getCondition()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading donation items:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- ADD DONATION ITEM --------------------
    private void addDonationItem() {
        try {
            String donationText = donationIDField.getText().trim();
            String itemText = itemIDField.getText().trim();
            String quantityText = quantityField.getText().trim();
            String condition = conditionField.getText().trim().toUpperCase();

            // Validate empty fields.
            if (donationText.isEmpty() || itemText.isEmpty() || quantityText.isEmpty() || condition.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please complete all fields.",
                        "Missing Information",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            int donationID = Integer.parseInt(donationText);
            int donationItemID = Integer.parseInt(donationItemIDField.getText().trim());
            int itemID = Integer.parseInt(itemText);
            int quantity = Integer.parseInt(quantityText);

            // Validate quantity.
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "Quantity must be greater than zero.",
                        "Invalid Quantity",
                        JOptionPane.WARNING_MESSAGE
                );
                quantityField.requestFocus();
                return;
            }

            DonationDAO donationDAO = new DonationDAO();

            // Confirm that the donation exists and is IN-KIND.
            if (!donationDAO.isInKindDonation(donationID)) {
                JOptionPane.showMessageDialog(
                        this,
                        "This Donation ID does not exist or is not an IN-KIND donation.",
                        "Invalid Donation ID",
                        JOptionPane.WARNING_MESSAGE
                );
                donationIDField.requestFocus();
                return;
            }

            DonationItemDAO dao = new DonationItemDAO();

            // Check whether this Donation ID already has an item.
            if (dao.donationIDAlreadyUsed(donationID)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Donation ID " + donationID + " has already been used to capture a donated item.\n"
                        + "You cannot add another item to this donation.",
                        "Duplicate Donation ID",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            DonationItem newItem = new DonationItem(
                    donationItemID,
                    donationID,
                    itemID,
                    quantity,
                    condition
            );

            dao.insertDonationItem(newItem);

            JOptionPane.showMessageDialog(
                    this,
                    "Donation item added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();
            loadDonationItems();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter valid numeric values for the IDs and quantity.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "The donated item could not be captured.\nPlease verify the information and try again.",
                    "Donation Item Not Saved",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- UPDATE DONATION ITEM --------------------
    private void updateDonationItem() {
        int selectedRow = itemTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to update.");
            return;
        }

        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        int donationItemID = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

        String newCondition = JOptionPane.showInputDialog(
                this,
                "Enter the new condition:"
        );

        if (newCondition == null || newCondition.trim().isEmpty()) {
            return;
        }

        try {
            newCondition = newCondition.trim().toUpperCase();
            DonationItemDAO dao = new DonationItemDAO();
            dao.updateDonationItemCondition(donationItemID, newCondition);

            loadDonationItems();
            JOptionPane.showMessageDialog(this, "Item updated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error updating item:\n" + e.getMessage(),
                    "Update Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- DELETE DONATION ITEM --------------------
    private void deleteDonationItem() {
        int selectedRow = itemTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an item to delete.");
            return;
        }

        int modelRow = itemTable.convertRowIndexToModel(selectedRow);
        int donationItemID = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

        int confirmation = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this donation item?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            DonationItemDAO dao = new DonationItemDAO();
            dao.deleteDonationItem(donationItemID);

            loadDonationItems();
            loadNextDonationItemID();

            JOptionPane.showMessageDialog(this, "Item deleted successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error deleting item:\n" + e.getMessage(),
                    "Delete Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // -------------------- CLEAR FORM --------------------
    private void clearForm() {
        itemIDField.setText("");
        quantityField.setText("");
        conditionField.setText("");

        loadNextDonationItemID();
        itemIDField.requestFocus();
    }

    // -------------------- STANDALONE TESTING --------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Donation Item Management");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 750);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new DonationItemGUI());
            frame.setVisible(true);
        });
    }
}