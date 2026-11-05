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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import za.ac.cput.sikunyecharity.DAO.FeedbackDAO;
import za.ac.cput.sikunyecharity.Domain.Feedback;

/**
 * Public/Admin view for reading and tracking user reviews and feedback submissions.
 * 
 * @author Gaga Kwakho
 */
public class FeedbackTable extends JFrame implements ActionListener {

    private static final Color CYAN_PRIMARY = new Color(31, 190, 221);
    private static final Color BG_LIGHT = new Color(246, 248, 251);
    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(218, 224, 233);
    private static final Color TEXT_DARK = new Color(35, 53, 65);

    private static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 18);
    private static final Font SUBTITLE_FONT = new Font("SansSerif", Font.PLAIN, 12);
    private static final Font FONT_BOLD = new Font("SansSerif", Font.BOLD, 13);
    private static final Font FONT_PLAIN = new Font("SansSerif", Font.PLAIN, 13);

    private JPanel mainPanel, buttonPanel, headerPanel;
    private JLabel headerLabel, subHeaderLabel;
    
    private JButton refreshButton;
    private JTable table;
    private DefaultTableModel tableModel;
    private FeedbackGUI feedbackForm;

    public FeedbackTable(FeedbackGUI feedbackForm) {
        super("Sikunye Charity - Community Reviews & Feedback");
        this.feedbackForm = feedbackForm;

        setLayout(new BorderLayout());
        setBackground(BG_LIGHT);

        // 1. Top Header Banner
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(CYAN_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(16, 24, 16, 24));

        headerLabel = new JLabel("Community Reviews & Feedback", SwingConstants.CENTER);
        headerLabel.setFont(TITLE_FONT);
        headerLabel.setForeground(Color.WHITE);

        subHeaderLabel = new JLabel("Browse feedback, testimonials, and ratings submitted by donors and community members", SwingConstants.CENTER);
        subHeaderLabel.setFont(SUBTITLE_FONT);
        subHeaderLabel.setForeground(new Color(240, 250, 255));

        JPanel titleBox = new JPanel();
        titleBox.setOpaque(false);
        titleBox.setLayout(new BoxLayout(titleBox, BoxLayout.Y_AXIS));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleBox.add(headerLabel);
        titleBox.add(Box.createRigidArea(new Dimension(0, 4)));
        titleBox.add(subHeaderLabel);

        headerPanel.add(titleBox, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // 2. Action Toolbar (Centered Single Refresh Button)
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        refreshButton = createRoundedButton("Refresh Records", CYAN_PRIMARY);
        refreshButton.addActionListener(this);

        buttonPanel.add(refreshButton);

        // 3. Table Configuration
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Read-only view
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(32);
        table.setFont(FONT_PLAIN);
        table.setSelectionBackground(new Color(225, 245, 250));
        table.setSelectionForeground(TEXT_DARK);
        table.setGridColor(new Color(235, 240, 245));
        table.setShowVerticalLines(false);

        // Custom Table Header Styling
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(new Color(238, 242, 246));
        table.getTableHeader().setForeground(TEXT_DARK);
        table.getTableHeader().setPreferredSize(new Dimension(0, 36));
        table.getTableHeader().setBorder(new MatteBorder(0, 0, 1, 0, BORDER_COLOR));

        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Subject");
        tableModel.addColumn("Feedback Comment");
        tableModel.addColumn("Rating (★)");

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(160);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(320);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);

        // Center alignment for ID & Rating columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

        // 4. Main Table Card Container
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BG_LIGHT);
        mainPanel.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(CARD_BG);
        tableCard.setBorder(new LineBorder(BORDER_COLOR, 1, true));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(CARD_BG);

        tableCard.add(buttonPanel, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tableCard, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        loadData();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);
    }

    public void setGui() {
        setVisible(true);
    }

    private JButton createRoundedButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        btn.setFont(FONT_BOLD);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 22, 8, 22));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void loadData() {
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ArrayList<Feedback> feedbackList = feedbackDAO.getAllFeedback();
        tableModel.setRowCount(0);
        if (feedbackList == null || feedbackList.isEmpty()) {
            try {
                feedbackList = feedbackDAO.getAll();
            } catch (Exception ignored) {
            }
        }
        if (feedbackList != null && !feedbackList.isEmpty()) {
            for (Feedback f : feedbackList) {
                Object[] rowData = {
                    f.getFeedbackId(),
                    f.getName(),
                    f.getEmail(),
                    f.getSubject(),
                    f.getFeedback(),
                    f.getRating()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshButton) {
            loadData();
            JOptionPane.showMessageDialog(this, "Feedback records refreshed.", "Table Refreshed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FeedbackGUI feedback = new FeedbackGUI();
            FeedbackTable feedbackTable = new FeedbackTable(feedback);
            feedbackTable.setVisible(true);
        });
    }
}