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
        String route = destination.trim().toLowerCase();

        switch (route) {
            case "home":
                HomeGUI home = new HomeGUI();
                home.setGui();
                next = home;
                break;

            case "projects":
                next = new ProjectsPage();
                break;

            case "beneficiaries":
                next = new BeneficiariesMenu();
                break;

            case "contact":
                ContactGUI contact = new ContactGUI();
                contact.setGUI();
                next = contact;
                break;

            case "feedback":
                FeedbackGUI feedback = new FeedbackGUI(current);
                feedback.setVisible(true);
                return; // Modal popup, retain active frame

            case "donor":
            case "donate":
            case "donation":
            case "donations":
                DonationED donation = new DonationED();
//                try {
//                    donation.setGui();
//                } catch (NoSuchMethodError | Exception e) {
//                    try {
//                        donation.setGUI();
//                    } catch (Exception ignored) {
//                    }
//                }
                next = donation;
                break;

            case "items donated":
            case "itemsdonated":
            case "item donated":
            case "inventory":
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

        String c = className.toLowerCase();
        String d = destination.toLowerCase();

        if (c.contains("home") && d.contains("home")) return true;
        if (c.contains("project") && d.contains("project")) return true;
        if (c.contains("beneficiar") && d.contains("beneficiar")) return true;
        if (c.contains("contact") && d.contains("contact")) return true;
        if (c.contains("feedback") && d.contains("feedback")) return true;
        if ((c.contains("donation") || c.contains("donor")) && (d.contains("donat") || d.contains("donor"))) return true;
        if (c.contains("item") && d.contains("item")) return true;

        return false;
    }
}