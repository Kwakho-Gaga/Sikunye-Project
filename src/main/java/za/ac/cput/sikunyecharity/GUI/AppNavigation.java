package za.ac.cput.sikunyecharity.GUI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public final class AppNavigation {
// Overload for JPanel pages like DonationGUI / DashboardGUI
// Finds the frame from the label itself
public static void link(JLabel label, String destination) {
    link(label, null, destination);
}
    private static final Color INACTIVE_COLOR = new Color(24, 24, 24);
    private static final Color HOVER_COLOR = new Color(65, 186, 207);
    private static final Color ACTIVE_COLOR = new Color(31, 190, 221);

    private AppNavigation() {
    }

    // Connects a navigation label to its destination
    public static void link(JLabel label, JFrame current, String destination) {

        if (label == null) {
            return;
        }

        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // FIX: If current is null or disposed (happens when inside DashboardGUI JPanel),
                // find the real window that contains this label
                JFrame effectiveCurrent = current;

                if (effectiveCurrent == null || !effectiveCurrent.isDisplayable()) {
                    Window w = SwingUtilities.getWindowAncestor(label);
                    if (w instanceof JFrame) {
                        effectiveCurrent = (JFrame) w;
                    }
                }

                open(effectiveCurrent, destination);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                if (!label.getForeground().equals(ACTIVE_COLOR)) {
                    label.setForeground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                if (current != null
                        && isMatchingPage(current.getClass().getSimpleName(), destination)) {

                    label.setForeground(ACTIVE_COLOR);

                } else {
                    label.setForeground(INACTIVE_COLOR);
                }
            }
        });
    }

    // Opens the selected page
    public static void open(JFrame current, String destination) {

        if (destination == null) {
            return;
        }

        String route = destination.trim().toLowerCase();

        JFrame next;

        switch (route) {

            case "home":

                // HomeGUI already calls setGui() in its constructor
                next = new HomeGUI();
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
                return;

            case "donor":
            case "donate":
            case "donor donation":

                next = new DonorDonationGUI_ED();
                break;

            // Donations Received opens the existing DashboardGUI
            case "donations received":
            case "donationsreceived":
            case "donations recieved":
            case "donationsrecieved":

                DashboardGUI dashboard = DashboardGUI.getDashboardInstance();

                // Create the dashboard only if it does not exist
                if (dashboard == null || !dashboard.isDisplayable()) {
                    dashboard = new DashboardGUI();
                }

                dashboard.setVisible(true);
                dashboard.showDonationsReceivedPage();

                // If we are already on Dashboard, do nothing else - don't hide it
                if (current instanceof DashboardGUI) {
                    return;
                }

                // Hide the previous dashboard instead of destroying it
                if (current != null && current != dashboard) {

                    if (current instanceof DashboardGUI) {
                        current.setVisible(false);
                    } else {
                        current.dispose();
                    }
                }

                return;

            case "items donated":
            case "itemsdonated":
            case "item donated":
            case "inventory":

                JFrame itemFrame = new JFrame(
                        "Sikunye Charity - Item Inventory & Distribution"
                );

                itemFrame.setLayout(new java.awt.BorderLayout());

                itemFrame.add(
                        new HeaderPanel(itemFrame, "Items Donated"),
                        java.awt.BorderLayout.NORTH
                );

                itemFrame.add(
                        new ItemDonated(),
                        java.awt.BorderLayout.CENTER
                );

                itemFrame.add(
                        new FooterPanel(itemFrame),
                        java.awt.BorderLayout.SOUTH
                );

                next = itemFrame;
                break;

            default:
                return;
        }

        // Set the new frame's close operation
        next.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Keep the same size and position as the previous frame
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

        // Hide DashboardGUI so it can be reused later
        if (current != null && current != next) {

            if (current instanceof DashboardGUI) {
                current.setVisible(false);
            } else {
                current.dispose();
            }
        }
    }

    // Checks which navigation page is currently active
    private static boolean isMatchingPage(String className, String destination) {

        if (className == null || destination == null) {
            return false;
        }

        String c = className.toLowerCase();
        String d = destination.toLowerCase();

        if (c.contains("home") && d.contains("home")) {
            return true;
        }

        if (c.contains("project") && d.contains("project")) {
            return true;
        }

        if (c.contains("beneficiar") && d.contains("beneficiar")) {
            return true;
        }

        if (c.contains("contact") && d.contains("contact")) {
            return true;
        }

        if (c.contains("feedback") && d.contains("feedback")) {
            return true;
        }

        if ((c.contains("donation") || c.contains("donor"))
                && (d.contains("donat") || d.contains("donor"))) {
            return true;
        }

        if (c.contains("item") && d.contains("item")) {
            return true;
        }

        return false;
    }
}
