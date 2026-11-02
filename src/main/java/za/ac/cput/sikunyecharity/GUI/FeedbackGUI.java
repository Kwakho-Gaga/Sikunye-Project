/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

/**
 *
 * @author Gaga Kwakho
 */
import static java.awt.AWTEventMulticaster.add;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;
import za.ac.cput.sikunyecharity.DAO.ContactDAO;
import za.ac.cput.sikunyecharity.DAO.FeedbackDAO;
import za.ac.cput.sikunyecharity.Domain.Contact;
import za.ac.cput.sikunyecharity.Domain.Feedback;

/**
 *
 * @author Gaga Kwakho
 */
public class FeedbackGUI extends JFrame implements ActionListener, ItemListener {

    private JPanel mainPanel, ratePanel, buttonPanel;
    private JPanel userFormPanel, adminPanel;

    private JLabel headerLabel;
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField subjectTextField;
    private JTextArea commentTextArea;
    private JLabel rateLabel;
    private JButton submitButton;
    private JButton viewButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private boolean isUpdating = false;
    private JToggleButton[] star = new JToggleButton[5];
    private int currentRating;
    private String currentSelectedFeedback="";
    

    Font font1 = new Font("Inter", Font.BOLD, 20);
    Font font2 = new Font("Inter", Font.PLAIN, 20);
    Font font3 = new Font("Inter", Font.PLAIN, 15);
    Font font4 = new Font("Inter", Font.BOLD, 15);
    Font font5 = new Font("Inter", Font.BOLD, 16);
    Font font6 = new Font("Inter", Font.BOLD, 14);

    Color color1 = new Color(0, 192, 232);
    Color color2 = Color.WHITE;
    Color color3 = new Color(245, 239, 247);
    Color color4 = new Color(245, 245, 245);
    // footer
    Color color5 = new Color(43, 55, 63, 230);
    //strokein footer
    Color color6 = new Color(142, 142, 147);
    Color color7 = new Color(179, 179, 179);

    public FeedbackGUI() {

        userFormPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };

        mainPanel = new JPanel();
        ratePanel = new JPanel();
        headerLabel = new JLabel("Feedback");
        headerLabel.setForeground(color1);
        headerLabel.setFont(font1);

        nameTextField = new JTextField();
        emailTextField = new JTextField();
        subjectTextField = new JTextField();
        commentTextArea = new JTextArea();

        setupTextField(nameTextField, "Name");
        setupTextField(emailTextField, "Email");
        setupTextField(subjectTextField, "Subject");
        setupTextArea(commentTextArea, "Comment/Feedback");

        rateLabel = new JLabel("Rate Us");
        for (int i = 0; i < 5; i++) {
            star[i] = createStar();
        }

        submitButton = new JButton("SUBMIT");
        submitButton.setFont(font6);
        submitButton.setBackground(color1);
        submitButton.setBorder(new EmptyBorder(6, 30, 10, 35));
        submitButton.setForeground(color2);
        submitButton.setOpaque(true);
        submitButton.addActionListener(this);

    }

    public void setGui() {
        this.setTitle("Feedback");
        Dimension panelSize = new Dimension(500, 600);

        mainPanel.setOpaque(false);
        mainPanel.setBackground(color3);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        Dimension mainPanelSize = new Dimension(600, 400);

        mainPanel.setPreferredSize(mainPanelSize);
        mainPanel.setMinimumSize(mainPanelSize);
        mainPanel.setMaximumSize(mainPanelSize);
        ratePanel.setOpaque(false);

        userFormPanel.setLayout(new BoxLayout(userFormPanel, BoxLayout.Y_AXIS));
        userFormPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                " USER PANEL ",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 14),
                Color.CYAN
        ));
        userFormPanel.setOpaque(false);
        userFormPanel.setPreferredSize(panelSize);
        userFormPanel.setMinimumSize(panelSize);
        userFormPanel.setMaximumSize(panelSize);
        userFormPanel.setBackground(color4);
        userFormPanel.setBorder(new EmptyBorder(25, 30, 85, 35));
        userFormPanel.add(headerLabel);
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        userFormPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        Dimension myCustomSize = new Dimension(20, 40);

        nameTextField.setPreferredSize(myCustomSize);
        userFormPanel.add(nameTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        nameTextField.setMinimumSize(panelSize);
        nameTextField.setMaximumSize(panelSize);
        nameTextField.setOpaque(false);

        emailTextField.setPreferredSize(myCustomSize);
        emailTextField.setMinimumSize(panelSize);
        emailTextField.setMaximumSize(panelSize);
        emailTextField.setOpaque(false);
        userFormPanel.add(emailTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        subjectTextField.setPreferredSize(myCustomSize);
        subjectTextField.setMinimumSize(panelSize);
        subjectTextField.setMaximumSize(panelSize);
        subjectTextField.setOpaque(false);
        userFormPanel.add(subjectTextField);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JScrollPane comment = new JScrollPane(commentTextArea);
        Dimension scrollPaneSize = new Dimension(500, 75);
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);
        commentTextArea.setOpaque(true);
        commentTextArea.setBackground(color4);
        comment.setPreferredSize(scrollPaneSize);
        comment.setMinimumSize(scrollPaneSize);
        comment.setMaximumSize(scrollPaneSize);
        comment.getVerticalScrollBar().setUnitIncrement(16);
        comment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        comment.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        userFormPanel.add(comment);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        userFormPanel.add(rateLabel);
        rateLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        ratePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        Dimension ratePanelSize = new Dimension(350, 50);

        ratePanel.setPreferredSize(ratePanelSize);
        ratePanel.setMinimumSize(ratePanelSize);
        ratePanel.setMaximumSize(ratePanelSize);

        for (JToggleButton stars : star) {
            ratePanel.add(stars);
        }
        userFormPanel.add(ratePanel);
        userFormPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension submit = new Dimension(288, 65);
        submitButton.setPreferredSize(submit);
        submitButton.setMinimumSize(submit);
        submitButton.setMaximumSize(submit);

        userFormPanel.add(submitButton);

        mainPanel.add(userFormPanel);

        this.add(mainPanel);
    }

    private void setPlaceholder(JTextComponent field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
    }

// For JTextField
    private void setupTextField(JTextField field, String placeholder) {
        setPlaceholder(field, placeholder);
        field.setForeground(Color.GRAY);
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

// For JTextArea
    private void setupTextArea(JTextArea area, String placeholder) {
        setPlaceholder(area, placeholder);
        area.setForeground(Color.GRAY);
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
        JToggleButton star = new JToggleButton("\u2606");
        star.setBorderPainted(false);
        star.setContentAreaFilled(false);
        star.setFocusPainted(false);
        star.setOpaque(false);
        star.setFont(new Font("SansSerif", Font.PLAIN, 25));
        star.addItemListener(this);
        return star;
    }

    private void setStar(int rating) {
        isUpdating = true;
        currentRating = rating;

        for (int i = 0; i < star.length; i++) {
            if (i < rating) {
                star[i].setForeground(Color.YELLOW);
                star[i].setSelected(true);
            } else {
                star[i].setForeground(Color.BLACK);
                star[i].setSelected(true);
            }
        }
        isUpdating = false; // allow user interaction
    }

    private void clearStar() {
        isUpdating = true;
        for (JToggleButton star : star) {
            star.setSelected(false);
            star.setForeground(Color.BLACK);
        }
        currentRating = 0;
        isUpdating = false;
    }

    public int getRate() {
        return currentRating;
    }

    public void refresh() {
        setupTextField(nameTextField, "Name");
        setupTextField(emailTextField, "Email");
        setupTextField(subjectTextField, "Subject");
        setupTextArea(commentTextArea, "Comment/Feedback");

        clearStar();
        nameTextField.requestFocus();
        currentSelectedFeedback="";
        submitButton.setText("Submit");

    }

    public void populateFromFields(String id, String name, String email, String subject, String comment, int rate) {
this.currentSelectedFeedback=id;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String subject = subjectTextField.getText();
            String comment = commentTextArea.getText();
            int rate = getRate();

            if (!name.isEmpty() && !name.equals("Name") && !email.isEmpty() && !email.equals("Email") && !subject.isEmpty()
                    && !subject.equals("Subject") && !comment.isEmpty() && !comment.equals("Comment/Feedback")) {

               
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                try {
                    if(currentSelectedFeedback.isEmpty()){
                         Feedback feedback = new Feedback(0, name, email, subject, comment, rate);
                    
                    feedbackDAO.submit(feedback);
                     JOptionPane.showMessageDialog(null, "Thank you " + name + " for helping us improve !!we have recieved your feedback.");
                refresh();}
                    else{
                    
                    int existingId=Integer.parseInt(currentSelectedFeedback);
                    Feedback feedback = new Feedback(existingId, name, email, subject, comment, rate);
                    
                    feedbackDAO.updateById(feedback);
                    JOptionPane.showMessageDialog(null, "Feedback updated successfully.");
                refresh();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FeedbackGUI.class.getName()).log(Level.SEVERE, null, ex);
                }

               
            }
            
            
            
            
                 else {
                JOptionPane.showMessageDialog(null, "Message not sent please enter all required fields");
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (isUpdating) {
            return;
        }
        for (int i = 0; i < star.length; i++) {
            if (e.getSource() == star[i]) {
                setStar(i + 1); // always set rating to this star's position, no toggle-to-0
                if (getRate() > 0) {
                    JOptionPane.showMessageDialog(null, "You have rated us " + getRate());
                }

                break;

            }
        }

    }
}
