package za.ac.cput.sikunyecharity.GUI;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/** Login for project management only; independent of other admin pages. */
final class ProjectAdminLogin {

    private ProjectAdminLogin() {
    }

    static boolean requestAccess(Component parent) {
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JPanel fields = new JPanel(new GridLayout(2, 2, 8, 8));
        fields.add(new JLabel("Username:"));
        fields.add(username);
        fields.add(new JLabel("Password:"));
        fields.add(password);

        int choice = JOptionPane.showConfirmDialog(parent, fields,
                "Project Admin Login", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
        if (choice != JOptionPane.OK_OPTION) {
            return false;
        }
        boolean valid = "admin".equals(username.getText().trim())
                && "1234".equals(new String(password.getPassword()));
        if (!valid) {
            JOptionPane.showMessageDialog(parent, "Incorrect username or password.");
        }
        return valid;
    }
}
