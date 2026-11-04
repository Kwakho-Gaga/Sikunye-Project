package za.ac.cput.sikunyecharity.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import za.ac.cput.sikunyecharity.GUI.ContactGUI;
import za.ac.cput.sikunyecharity.GUI.ContactTable;
import za.ac.cput.sikunyecharity.GUI.FeedbackGUI;
import za.ac.cput.sikunyecharity.GUI.FeedbackTable;
import za.ac.cput.sikunyecharity.GUI.HomeGUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Gaga Kwakho
 */
public class Tables extends JFrame implements ActionListener {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final JPanel loginPanel = new JPanel();
    private final JButton loginButton = new JButton("Login");
    private final JTextField usernameTextField = new JTextField();
    private final JPasswordField passwordTextField = new JPasswordField();

    private final Font font6 = new Font("Inter", Font.BOLD, 14);
    private final Color color1 = new Color(0, 192, 232);
;
ContactGUI contactForm;
    public Tables() {
        super("Admin Login");

        buildLoginPanel();
        buildTabs();

        mainPanel.add(loginPanel, "login");
        cardLayout.show(mainPanel, "login");

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void buildLoginPanel() {
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        JLabel title = new JLabel("Admin Login");
        title.setFont(new Font("Inter", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameTextField.setMaximumSize(new Dimension(300, 35));
        passwordTextField.setMaximumSize(new Dimension(300, 35));

        loginButton.setFont(font6);
        loginButton.setBackground(color1);
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.addActionListener(this);

        loginPanel.add(title);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameTextField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordTextField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
    }

    private void buildTabs() {
        JTabbedPane tabbedPane = new JTabbedPane();

        ContactGUI contact = new ContactGUI();
        ContactTable contactTable = new ContactTable(contact);
        contactTable.setGui();
        tabbedPane.addTab("Contact", contactTable.getContentPane());

        FeedbackGUI feedback = new FeedbackGUI();
        FeedbackTable feedbackTable = new FeedbackTable(feedback);
        feedbackTable.setGui();
        tabbedPane.addTab("Feedback", feedbackTable.getContentPane());

        mainPanel.add(tabbedPane, "admin");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameTextField.getText().trim();
            String password = new String(passwordTextField.getPassword());

            if (username.equals("Staff") && password.equals("password")) {
                cardLayout.show(mainPanel, "admin");
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Incorrect username or password.",
                        "Login failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {

        ContactGUI contact = new ContactGUI();
        contact.setGUI();
        contact.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contact.setSize(1000, 900);
        contact.setVisible(true);

        FeedbackGUI feedback = new FeedbackGUI();
        feedback.setGui();
        feedback.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedback.pack();
        feedback.setLocationRelativeTo(null);
        feedback.setVisible(true);

        Tables table = new Tables();

        table.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        table.setSize(1000, 900);
        table.setVisible(true);

    }
}
