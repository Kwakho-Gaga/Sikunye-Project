package za.ac.cput.sikunyecharity.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/** Universal Navigation Controller */
public final class AppNavigation {

    private static final Color INACTIVE_COLOR = new Color(24, 24, 24);
    private static final Color HOVER_COLOR = new Color(65, 186, 207);
    private static final Color ACTIVE_COLOR = new Color(31, 190, 221);

    private AppNavigation() {
    }

    public static void link(JLabel label, JFrame current, String destination) {
        if (label == null) return;

        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                open(current, destination);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (!label.getForeground().equals(ACTIVE_COLOR)) {
                    label.setForeground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (current != null && isMatchingPage(current.getClass().getSimpleName(), destination)) {
                    label.setForeground(ACTIVE_COLOR);
                } else {
                    label.setForeground(INACTIVE_COLOR);
                }
            }
        });
    }

    public static void open(JFrame current, String destination) {
        if (destination == null) return;

        JFrame next;
        switch (destination) {
            case "Home":
                HomeGUI home = new HomeGUI();
                home.setGui();
                next = home;
                break;
            case "Projects":
                next = new ProjectsPage();
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
                FeedbackGUI feedback = new FeedbackGUI(current);
                feedback.setVisible(true);
                return;
                //break;
            case "Items Donated":
            case "ItemsDonated":
            case "Inventory":
                JFrame itemFrame = new JFrame("Sikunye Charity - Item Inventory & Distribution");
                itemFrame.setLayout(new java.awt.BorderLayout());
                itemFrame.add(new HeaderPanel(itemFrame, "Items Donated"), java.awt.BorderLayout.NORTH);
                itemFrame.add(new ItemDonated(), java.awt.BorderLayout.CENTER);
                itemFrame.add(new FooterPanel(itemFrame), java.awt.BorderLayout.SOUTH);
                next = itemFrame;
                break;
            default:
                return;
        }

        next.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Preserve size, position, and maximized state from current frame
        if (current != null) {
            Rectangle bounds = current.getBounds();
            int extendedState = current.getExtendedState();
            next.setBounds(bounds);
            next.setExtendedState(extendedState);
        } else {
            next.setSize(1180, 900);
            next.setLocationRelativeTo(null);
        }

        next.setVisible(true);

        if (current != null) {
            current.dispose();
        }
    }

    private static boolean isMatchingPage(String className, String destination) {
        if (className == null || destination == null) return false;
        if (className.equalsIgnoreCase("HomeGUI") && destination.equalsIgnoreCase("Home")) return true;
        if (className.equalsIgnoreCase("ProjectsPage") && destination.equalsIgnoreCase("Projects")) return true;
        if (className.equalsIgnoreCase("BeneficiariesMenu") && destination.equalsIgnoreCase("Beneficiaries")) return true;
        if (className.equalsIgnoreCase("ContactGUI") && destination.equalsIgnoreCase("Contact")) return true;
        if (className.equalsIgnoreCase("FeedbackGUI") && destination.equalsIgnoreCase("Feedback")) return true;
        if (destination.toLowerCase().contains("item") && className.toLowerCase().contains("item")) return true;
        return false;
    }
}