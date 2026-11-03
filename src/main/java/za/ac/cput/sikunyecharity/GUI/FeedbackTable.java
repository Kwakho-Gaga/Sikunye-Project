/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.GUI;

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
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import za.ac.cput.sikunyecharity.DAO.FeedbackDAO;
import za.ac.cput.sikunyecharity.Domain.Feedback;

/**
 *
 * @author Gaga Kwakho
 */
public class FeedbackTable extends JFrame implements ActionListener, ItemListener {

   
    private JPanel mainPanel, ratePanel, buttonPanel, adminPanel, charityDescrPanel;
    
    private JLabel headerLabel;
    private JLabel charityDescription;
    
    private JTextField nameTextField;
    private JTextField emailTextField;
    private JTextField subjectTextField;
    private JTextArea commentTextArea;
    private JLabel rateLabel;
    
    private JButton refreshButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private boolean isUpdating = false;
    private JToggleButton[] star = new JToggleButton[5];
    private int currentRating;
    private FeedbackGUI feedbackForm;

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

    private JPanel createLeftAlignedPanel(JLabel label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // small space between label and field
        panel.add(field);
        panel.setOpaque(false);
        return panel;
    }

    public FeedbackTable(FeedbackGUI feedbackForm) {
        this.feedbackForm=feedbackForm;
        
        charityDescrPanel = new JPanel();
        
        
        adminPanel = new JPanel();
        
        mainPanel = new JPanel();
        ratePanel = new JPanel();
        buttonPanel = new JPanel();
        charityDescription = new JLabel("Together we are one");
        charityDescription.setForeground(color1);
        charityDescription.setFont(font6);
        headerLabel = new JLabel("Sikunye Charity");
        headerLabel.setForeground(color1);
        headerLabel.setFont(font6);

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

       

        refreshButton = new JButton("REFRESH");
        refreshButton.setFont(font6);
        refreshButton.setBackground(Color.LIGHT_GRAY);
        refreshButton.setBorder(new EmptyBorder(5, 30, 5, 35));
        refreshButton.setForeground(color2);
        refreshButton.setOpaque(true);
        deleteButton = new JButton("DELETE");
        deleteButton.setFont(font6);
        deleteButton.setBackground(Color.red);
        deleteButton.setBorder(new EmptyBorder(5, 30, 5, 35));
        deleteButton.setForeground(color2);
        deleteButton.setOpaque(true);
        updateButton = new JButton("UPDATE");
        updateButton.setFont(font6);
        updateButton.setBackground(Color.ORANGE);
        updateButton.setBorder(new EmptyBorder(5, 30, 5, 35));
        updateButton.setForeground(color2);
        updateButton.setOpaque(true);
        
        refreshButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Feedback_Id");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Subject");
        tableModel.addColumn("Comment");
        tableModel.addColumn("Rating");
        table.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent arg0) {
        int i = table.getSelectedRow();

        if (i != -1) {
            String id = table.getValueAt(i, 0).toString();
            String name = table.getValueAt(i, 1).toString();
            String email = table.getValueAt(i, 2).toString();
            String subject = table.getValueAt(i, 3).toString();
            String comment = table.getValueAt(i, 4).toString();
            int rating = Integer.parseInt(
                    table.getValueAt(i, 5).toString()
            );

            // Fill the fields in FeedbackTable
            nameTextField.setText(name);
            nameTextField.setForeground(Color.BLACK);
            emailTextField.setText(email);
            emailTextField.setForeground(Color.BLACK);
            subjectTextField.setText(subject);
            subjectTextField.setForeground(Color.BLACK);
            commentTextArea.setText(comment);
            commentTextArea.setForeground(Color.BLACK);
            setStar(rating);

            // Fill the form in FeedbackGUI and change SUBMIT to Update
            if (feedbackForm != null) {
                feedbackForm.populateFromFields(
                        id, name, email, subject, comment, rating
                );
            }
        }
    }
}); 
       loadData();
    }

    public void setGui() {
        this.setTitle("Feedback Table");
         mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(refreshButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        JScrollPane scroll = new JScrollPane(table);

        mainPanel.add(buttonPanel);
        mainPanel.add(scroll);

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

    }

    public void refreshTable() {

        FeedbackDAO feedbackDAO = new FeedbackDAO();

        try {

            ArrayList<Feedback> feedbackList = feedbackDAO.getAllFeedback();

            tableModel.setRowCount(0);

            for (Feedback f : feedbackList) {
                Object[] row = {
                    f.getFeedbackId(),
                    f.getName(),
                    f.getEmail(),
                    f.getSubject(),
                    f.getFeedback(),
                    f.getRating()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error refreshing data");
        }
    } 
    public void loadData(){
    
    try {
                FeedbackDAO feedbackDAO = new FeedbackDAO();
                // Clear existing rows
                tableModel.setRowCount(0);

                ArrayList<Feedback> feedbackList = feedbackDAO.getAll();
                if (feedbackList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No records found,wait for user to submit feedback");

                } else {

                    for (Feedback f : feedbackList) {
                        String[] rowData = {
                            String.valueOf(f.getFeedbackId()),
                            f.getName(),
                            f.getEmail(),
                            f.getSubject(),
                            f.getFeedback(),
                            String.valueOf(f.getRating()),};
                        tableModel.addRow(rowData);

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(FeedbackGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

    
    
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getSource() == refreshButton) {
         refreshTable()  ; 
        }
        else if (e.getSource() == deleteButton) {
            FeedbackDAO feedbackDAO = new FeedbackDAO();
            int selectedRow = table.getSelectedRow();

            if (selectedRow != -1) {
                // Assuming ID is in column 0
                int feedbackId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

                // Call your DAO delete method
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this row?",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (response == JOptionPane.YES_OPTION) {
                    int result = feedbackDAO.deleteById(feedbackId);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Record deleted successfully!");
                        refresh();
                        refreshTable();
                    } else {

                        refresh();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to delete");
            }

        } else if (e.getSource() == updateButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int feedbackId = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());
                String name = nameTextField.getText();
                String email = emailTextField.getText();
                String subject = subjectTextField.getText();
                String comment = commentTextArea.getText();
                int rate = getRate();

                if (!name.isEmpty() && !name.equals("Name") && !email.isEmpty() && !email.equals("Email") && !subject.isEmpty()
                        && !subject.equals("Subject") && !comment.isEmpty() && !comment.equals("Comment/Feedback")) {
                    Feedback feedback = new Feedback(feedbackId, name, email, subject, comment, rate);
                    FeedbackDAO feedbackDAO = new FeedbackDAO();
                    int result = feedbackDAO.updateById(feedback);
                    if (result > 0) {
                        JOptionPane.showMessageDialog(null, "Record updated successfully!");
                        refresh();
                        refreshTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "No record found with that ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields before updating");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Please select a row to update");
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
    
   public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        FeedbackGUI feedback = new FeedbackGUI();
        feedback.setGui();
        feedback.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedback.pack();
        feedback.setLocationRelativeTo(null);
        feedback.setVisible(true);

        FeedbackTable feedbackTable = new FeedbackTable(feedback);
        feedbackTable.setGui();
        feedbackTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedbackTable.setSize(900, 700);
        feedbackTable.setLocationRelativeTo(feedback);
        feedbackTable.setVisible(true);
    });
} 
    
}
