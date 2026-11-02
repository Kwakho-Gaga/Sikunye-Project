package za.ac.cput.sikunyecharity.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class BeneficiariesMenu extends JFrame implements ActionListener {

    private JButton btnBeneficiaries;
    private JButton btnAssignments;
    private JButton btnBack;

    public BeneficiariesMenu() {
        super("Sikunye Charity - Beneficiaries");

        JPanel content = new JPanel(new BorderLayout(0, 24));
        content.setBorder(new EmptyBorder(38, 90, 40, 90));
        JPanel introduction = new JPanel(new GridLayout(2, 1, 0, 5));
        introduction.setOpaque(false);
        JLabel heading = new JLabel("Beneficiary management");
        heading.setFont(new Font("SansSerif", Font.BOLD, 20));
        JLabel description = new JLabel("Choose a section to manage people and the support they need.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 14));
        introduction.add(heading);
        introduction.add(description);
        content.add(introduction, BorderLayout.NORTH);

        JPanel choices = new JPanel(new GridLayout(2, 1, 0, 17));
        choices.setOpaque(false);
        btnBeneficiaries = choice("Manage Beneficiaries",
                "Add, view, update and remove beneficiary records.");
        btnAssignments = choice("Manage Beneficiary Needs",
                "Assign, update and remove needs for each beneficiary.");
        choices.add(btnBeneficiaries);
        choices.add(btnAssignments);
        content.add(choices, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        btnBack = new JButton("Back to Home");
        BeneficiaryPageStyle.styleButton(btnBack, BeneficiaryPageStyle.DARK);
        bottom.add(btnBack, BorderLayout.WEST);
        content.add(bottom, BorderLayout.SOUTH);

        btnBeneficiaries.addActionListener(this);
        btnAssignments.addActionListener(this);
        btnBack.addActionListener(this);
        BeneficiaryPageStyle.setup(this, "Beneficiaries", "Sikunye Charity System", content);
    }

    private JButton choice(String title, String description) {
        JButton button = new JButton("<html><div style='text-align:center'><b>"
                + title + "</b><br><span style='font-size:10px'>"
                + description + "</span></div></html>");
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(35, 53, 65));
        button.setFont(new Font("SansSerif", Font.PLAIN, 15));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(195, 222, 229)));
        button.setPreferredSize(new Dimension(500, 95));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnBack) {
            AppNavigation.open(this, "Home");
            return;
        }
        JFrame next;
        if (source == btnBeneficiaries) {
            next = new BeneficiaryGui(this);
        } else if (source == btnAssignments) {
            next = new BeneficiaryNeedsGui(this);
        } else {
            return;
        }
        setVisible(false);
        next.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BeneficiariesMenu().setVisible(true);
            }
        });
    }
}
