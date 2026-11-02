package za.ac.cput.sikunyecharity.GUI;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** Navigation for the pages currently present in this repository. */
public final class AppNavigation {

    private AppNavigation() {
    }

    public static void link(JLabel label, JFrame current, String destination) {
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                open(current, destination);
            }
        });
    }

    public static void open(JFrame current, String destination) {
        JFrame next;
        switch (destination) {
            case "Home":
                HomeGUI home = new HomeGUI();
                home.setGui();
                next = home;
                break;
            case "Beneficiaries":
                next = new BeneficiariesMenu();
                break;
            case "Contact":
                ContactGUI contact = new ContactGUI();
                contact.setGUI();
                next = contact;
                break;
            case "Feedback":
                FeedbackGUI feedback = new FeedbackGUI();
                feedback.setGui();
                next = feedback;
                break;
            default:
                return;
        }
        next.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        next.setSize(1180, 900);
        next.setLocationRelativeTo(null);
        next.setVisible(true);
        if (current != null) {
            current.dispose();
        }
    }
}
