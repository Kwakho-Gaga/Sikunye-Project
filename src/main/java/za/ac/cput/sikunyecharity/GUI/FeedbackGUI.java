package za.ac.cput.sikunyecharity.GUI;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;
import za.ac.cput.sikunyecharity.DAO.FeedbackDAO;
import za.ac.cput.sikunyecharity.Domain.Feedback;

public class FeedbackGUI extends JFrame implements ActionListener, ItemListener {

    private JPanel mainPanel, ratePanel, userFormPanel;
    private JLabel headerLabel, rateLabel;
    private JTextField nameTextField, emailTextField, subjectTextField;
    private JTextArea commentTextArea;
    private JButton submitButton;
    private JToggleButton[] star = new JToggleButton[5];
    private int currentRating;
    private boolean isUpdating = false;
    private String currentSelectedFeedback = "";

    Font font1 = new Font("Inter", Font.BOLD, 20);
    Font font6 = new Font("Inter", Font.BOLD, 14);
    Color color1 = new Color(0, 192, 232);
    Color color2 = Color.WHITE;
    Color color3 = new Color(245, 239, 247);
    Color color4 = new Color(245, 245, 245);

    public FeedbackGUI() {
        super("Sikunye Charity - Feedback");
        setLayout(new BorderLayout());

        add(new HeaderPanel(this, "Feedback"), BorderLayout.NORTH);

        mainPanel = new JPanel();
        mainPanel.setBackground(color3);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        userFormPanel = new JPanel();
        userFormPanel.setLayout(new BoxLayout(userFormPanel, BoxLayout.Y_AXIS));
        userFormPanel.setBackground(color4);
        userFormPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        " USER FEEDBACK ",
                        TitledBorder.LEFT,
                        TitledBorder.TOP,
                        new Font("SansSerif", Font.BOLD, 14),
                        color1
                ),
                new EmptyBorder(25, 30, 25, 30)
        ));

        headerLabel = new JLabel("Submit Your Feedback");
        headerLabel.setForeground(color1);
        headerLabel.setFont(font1);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameTextField = new JTextField(25);
        emailTextField = new JTextField(25);
        subjectTextField = new JTextField(25);
        commentTextArea = new JTextArea(4, 25);

        setupTextField(nameTextField, "Name");
        setupTextField(emailTextField, "Email");
        setupTextField(subjectTextField, "Subject");
        setupTextArea(commentTextArea, "Comment/Feedback");

        rateLabel = new JLabel("Rate Us");
        ratePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        ratePanel.setOpaque(false);

        for (int i = 0; i < 5; i++) {
            star[i] = createStar();
            ratePanel.add(star[i]);
        }

        submitButton = new JButton("SUBMIT");
        submitButton.setFont(font6);
        submitButton.setBackground(color1);
        submitButton.setForeground(color2);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(this);

        userFormPanel.add(headerLabel);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userFormPanel.add(nameTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userFormPanel.add(emailTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userFormPanel.add(subjectTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        userFormPanel.add(new JScrollPane(commentTextArea));
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        userFormPanel.add(rateLabel);
        userFormPanel.add(ratePanel);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userFormPanel.add(submitButton);

        mainPanel.add(userFormPanel);
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

        add(new FooterPanel(this), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 850);
        setLocationRelativeTo(null);
    }

    public void setGui() {
        setVisible(true);
    }

    public void populateFromFields(String id, String name, String email, String subject, String comment, int rate) {
        this.currentSelectedFeedback = id;
        nameTextField.setText(name);
        nameTextField.setForeground(Color.BLACK);
        emailTextField.setText(email);
        emailTextField.setForeground(Color.BLACK);
        subjectTextField.setText(subject);
        subjectTextField.setForeground(Color.BLACK);
        commentTextArea.setText(comment);
        commentTextArea.setForeground(Color.BLACK);
        setStar(rate);
        submitButton.setText("Update");
    }

    private void setPlaceholder(JTextComponent field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
    }

    private void setupTextField(JTextField field, String placeholder) {
        setPlaceholder(field, placeholder);
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (field.getForeground().equals(Color.GRAY)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
        });
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    setPlaceholder(field, placeholder);
                }
            }
        });
    }

    private void setupTextArea(JTextArea area, String placeholder) {
        setPlaceholder(area, placeholder);
        area.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (area.getForeground().equals(Color.GRAY)) {
                    area.setText("");
                    area.setForeground(Color.BLACK);
                }
            }
        });
        area.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (area.getText().isEmpty()) {
                    setPlaceholder(area, placeholder);
                }
            }
        });
    }

    private JToggleButton createStar() {
        JToggleButton btn = new JToggleButton("\u2606");
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 25));
        btn.addItemListener(this);
        return btn;
    }

    private void setStar(int rating) {
        isUpdating = true;
        currentRating = rating;
        for (int i = 0; i < star.length; i++) {
            if (i < rating) {
                star[i].setForeground(new Color(255, 180, 0));
                star[i].setSelected(true);
            } else {
                star[i].setForeground(Color.BLACK);
                star[i].setSelected(false);
            }
        }
        isUpdating = false;
    }

    public void refresh() {
        setupTextField(nameTextField, "Name");
        setupTextField(emailTextField, "Email");
        setupTextField(subjectTextField, "Subject");
        setupTextArea(commentTextArea, "Comment/Feedback");
        setStar(0);
        currentSelectedFeedback = "";
        submitButton.setText("SUBMIT");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText().trim();
            String email = emailTextField.getText().trim();
            String subject = subjectTextField.getText().trim();
            String comment = commentTextArea.getText().trim();

            if (!name.isEmpty() && !name.equals("Name") && !email.isEmpty() && !email.equals("Email")
                    && !subject.isEmpty() && !subject.equals("Subject") && !comment.isEmpty() && !comment.equals("Comment/Feedback")) {

                FeedbackDAO feedbackDAO = new FeedbackDAO();
                try {
                    if (currentSelectedFeedback.isEmpty()) {
                        Feedback feedback = new Feedback(0, name, email, subject, comment, currentRating);
                        feedbackDAO.submit(feedback);
                        JOptionPane.showMessageDialog(this, "Thank you " + name + "! We have received your feedback.");
                        refresh();
                    } else {
                        int existingId = Integer.parseInt(currentSelectedFeedback);
                        Feedback feedback = new Feedback(existingId, name, email, subject, comment, currentRating);
                        feedbackDAO.updateById(feedback);
                        JOptionPane.showMessageDialog(this, "Feedback updated successfully.");
                        refresh();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FeedbackGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please complete all fields before submitting.", "Validation Error", JOptionPane.WARNING_MESSAGE);
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