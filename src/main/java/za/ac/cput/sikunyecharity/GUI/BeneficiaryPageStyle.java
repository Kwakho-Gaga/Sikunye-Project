package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

final class BeneficiaryPageStyle {

    static final Color BACKGROUND = new Color(248, 244, 250);
    static final Color CYAN = new Color(31, 190, 221);
    static final Color DARK = new Color(58, 72, 82);

    private BeneficiaryPageStyle() {
    }

    static void setup(JFrame frame, String title, String subtitle, JPanel content) {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(111, 204, 229));
        header.setBorder(new EmptyBorder(14, 30, 14, 30));
        header.setPreferredSize(new Dimension(1180, 82));
        JLabel heading = new JLabel(title, SwingConstants.CENTER);
        heading.setFont(new Font("SansSerif", Font.BOLD, 21));
        heading.setForeground(Color.WHITE);
        JLabel description = new JLabel(subtitle, SwingConstants.CENTER);
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setForeground(Color.WHITE);
        header.add(heading, BorderLayout.CENTER);
        header.add(description, BorderLayout.SOUTH);
        page.add(header, BorderLayout.NORTH);

        content.setBackground(BACKGROUND);
        page.add(content, BorderLayout.CENTER);

        page.add(HomeFooter.create(frame instanceof BeneficiariesMenu ? frame : null),
                BorderLayout.SOUTH);

        frame.setContentPane(page);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setMinimumSize(new Dimension(900, 650));
        frame.setSize(1180, 850);
        frame.setLocationRelativeTo(null);
    }

    static void styleButton(JButton button, Color color) {
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
    }

    static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        return label;
    }
}
