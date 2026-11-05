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
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import za.ac.cput.sikunyecharity.DAO.FeedbackDAO;
import za.ac.cput.sikunyecharity.Domain.Feedback;

public class FeedbackGUI extends JDialog implements ActionListener, ItemListener {

    private static final Color CYAN_PRIMARY = new Color(31, 190, 221);
    private static final Color BG_LIGHT = new Color(246, 248, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(218, 224, 233);
    private static final Color TEXT_DARK = new Color(35, 53, 65);
    private static final Color TEXT_MUTED = new Color(110, 125, 140);
    private static final Color STAR_GOLD = new Color(255, 180, 0);

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font SUBTITLE_FONT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font LABEL_FONT = new Font("SansSerif", Font.BOLD, 13);
    private static final Font INPUT_FONT = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 13);

    private JTextField nameTextField, emailTextField, subjectTextField;
    private JTextArea commentTextArea;
    private JButton submitButton, cancelButton;
    private JToggleButton[] star = new JToggleButton[5];
    private JLabel ratingValueLabel;
    private int currentRating = 0;
    private boolean isUpdating = false;
    private String currentSelectedFeedback = "";

    public FeedbackGUI() {
        this(null);
    }

    public FeedbackGUI(Window owner) {
        super(owner, "Sikunye Charity - Feedback", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());

        // 1. Top Header Banner
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CYAN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(16, 24, 16, 24));

        JLabel lblTitle = new JLabel("Your Feedback Matters", SwingConstants.CENTER);
        lblTitle.setFont(TITLE_FONT);
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSubtitle = new JLabel("Help us improve our programs and support for local beneficiaries", SwingConstants.CENTER);
        lblSubtitle.setFont(SUBTITLE_FONT);
        lblSubtitle.setForeground(new Color(240, 250, 255));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.add(lblTitle);
        titleBox.add(Box.createRigidArea(new Dimension(0, 4)));
        titleBox.add(lblSubtitle);

        headerPanel.add(titleBox, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Main Form Container
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(BG_LIGHT);
        mainContent.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(CARD_BG);
        formCard.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(20, 24, 20, 24)
        ));

        nameTextField = createStyledTextField("Full Name");
        emailTextField = createStyledTextField("Email Address");
        subjectTextField = createStyledTextField("Subject");
        commentTextArea = createStyledTextArea("Share your comments, suggestions, or feedback...");

        formCard.add(createFieldBlock("Name", nameTextField));
        formCard.add(Box.createRigidArea(new Dimension(0, 12)));
        formCard.add(createFieldBlock("Email", emailTextField));
        formCard.add(Box.createRigidArea(new Dimension(0, 12)));
        formCard.add(createFieldBlock("Subject", subjectTextField));
        formCard.add(Box.createRigidArea(new Dimension(0, 12)));

        JScrollPane commentScroll = new JScrollPane(commentTextArea);
        commentScroll.setBorder(new LineBorder(BORDER_COLOR, 1, true));
        commentScroll.getVerticalScrollBar().setUnitIncrement(12);
        formCard.add(createFieldBlock("Message", commentScroll));
        formCard.add(Box.createRigidArea(new Dimension(0, 14)));

        // Star Rating Bar
        JLabel lblRateHeader = new JLabel("Rate Your Experience");
        lblRateHeader.setFont(LABEL_FONT);
        lblRateHeader.setForeground(TEXT_DARK);
        lblRateHeader.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel ratePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        ratePanel.setOpaque(false);
        ratePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < 5; i++) {
            star[i] = createStar();
            ratePanel.add(star[i]);
        }

        ratingValueLabel = new JLabel("Select rating");
        ratingValueLabel.setFont(SUBTITLE_FONT);
        ratingValueLabel.setForeground(TEXT_MUTED);
        ratePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        ratePanel.add(ratingValueLabel);

        formCard.add(lblRateHeader);
        formCard.add(Box.createRigidArea(new Dimension(0, 6)));
        formCard.add(ratePanel);

        mainContent.add(formCard);
        add(mainContent, BorderLayout.CENTER);

        // 3. Bottom Action Buttons Bar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 14));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(new MatteBorder(1, 0, 0, 0, BORDER_COLOR));

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(BUTTON_FONT);
        cancelButton.setForeground(TEXT_MUTED);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorder(new EmptyBorder(8, 18, 8, 18));
        cancelButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> dispose());

        submitButton = new JButton("Submit Feedback") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        submitButton.setFont(BUTTON_FONT);
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(CYAN_PRIMARY);
        submitButton.setContentAreaFilled(false);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new EmptyBorder(8, 22, 8, 22));
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        submitButton.addActionListener(this);

        buttonPanel.add(cancelButton);
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(540, 680);
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private JPanel createFieldBlock(String labelText, JComponent inputComponent) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setOpaque(false);
        block.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputComponent.setAlignmentX(Component.LEFT_ALIGNMENT);

        block.add(label);
        block.add(Box.createRigidArea(new Dimension(0, 5)));
        block.add(inputComponent);
        return block;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(INPUT_FONT);
        field.setPreferredSize(new Dimension(440, 36));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER_COLOR, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        ));
        setupPlaceholder(field, placeholder);
        return field;
    }

    private JTextArea createStyledTextArea(String placeholder) {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(INPUT_FONT);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(new EmptyBorder(8, 10, 8, 10));
        setupPlaceholder(area, placeholder);
        return area;
    }

    private void setupPlaceholder(JTextComponent field, String placeholder) {
        setPlaceholder(field, placeholder);
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (field.getForeground().equals(TEXT_MUTED)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                }
            }
        });
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().trim().isEmpty()) {
                    setPlaceholder(field, placeholder);
                }
            }
        });
    }

    private void setPlaceholder(JTextComponent field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(TEXT_MUTED);
    }

    private JToggleButton createStar() {
        JToggleButton btn = new JToggleButton("\u2605");
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 26));
        btn.setForeground(BORDER_COLOR);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addItemListener(this);
        return btn;
    }

    private void setStar(int rating) {
        isUpdating = true;
        currentRating = rating;
        String[] labels = {"Select rating", "Poor", "Fair", "Good", "Very Good", "Excellent"};

        for (int i = 0; i < star.length; i++) {
            if (i < rating) {
                star[i].setForeground(STAR_GOLD);
                star[i].setSelected(true);
            } else {
                star[i].setForeground(BORDER_COLOR);
                star[i].setSelected(false);
            }
        }
        ratingValueLabel.setText(labels[rating]);
        ratingValueLabel.setForeground(rating > 0 ? TEXT_DARK : TEXT_MUTED);
        isUpdating = false;
    }

    public void setGui() {
        setVisible(true);
    }

    public void populateFromFields(String id, String name, String email, String subject, String comment, int rate) {
        this.currentSelectedFeedback = id;
        nameTextField.setText(name);
        nameTextField.setForeground(TEXT_DARK);

        emailTextField.setText(email);
        emailTextField.setForeground(TEXT_DARK);

        subjectTextField.setText(subject);
        subjectTextField.setForeground(TEXT_DARK);

        commentTextArea.setText(comment);
        commentTextArea.setForeground(TEXT_DARK);

        setStar(rate);
        submitButton.setText("Update Feedback");
    }

    public void refresh() {
        setupPlaceholder(nameTextField, "Full Name");
        setupPlaceholder(emailTextField, "Email Address");
        setupPlaceholder(subjectTextField, "Subject");
        setupPlaceholder(commentTextArea, "Share your comments, suggestions, or feedback...");
        setStar(0);
        currentSelectedFeedback = "";
        submitButton.setText("Submit Feedback");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText().trim();
            String email = emailTextField.getText().trim();
            String subject = subjectTextField.getText().trim();
            String comment = commentTextArea.getText().trim();

            boolean isNameValid = !name.isEmpty() && !name.equals("Full Name");
            boolean isEmailValid = !email.isEmpty() && !email.equals("Email Address");
            boolean isSubjectValid = !subject.isEmpty() && !subject.equals("Subject");
            boolean isCommentValid = !comment.isEmpty() && !comment.equals("Share your comments, suggestions, or feedback...");

            if (isNameValid && isEmailValid && isSubjectValid && isCommentValid) {
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                try {
                    if (currentSelectedFeedback.isEmpty()) {
                        Feedback feedback = new Feedback(0, name, email, subject, comment, currentRating);
                        feedbackDAO.submit(feedback);

                        JOptionPane.showMessageDialog(
                                this,
                                "Thank you, " + name + "!\nYour feedback has been successfully sent.",
                                "Feedback Sent",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    } else {
                        int existingId = Integer.parseInt(currentSelectedFeedback);
                        Feedback feedback = new Feedback(existingId, name, email, subject, comment, currentRating);
                        feedbackDAO.updateById(feedback);

                        JOptionPane.showMessageDialog(
                                this,
                                "Feedback updated successfully.",
                                "Feedback Updated",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        dispose();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FeedbackGUI.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(
                            this,
                            "An error occurred while saving feedback: " + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please fill in all required fields before submitting.",
                        "Validation Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (isUpdating) return;
        for (int i = 0; i < star.length; i++) {
            if (e.getSource() == star[i]) {
                setStar(i + 1);
                break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeedbackGUI().setVisible(true));
    }
}