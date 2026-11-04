package za.ac.cput.sikunyecharity;

import javax.swing.SwingUtilities;
import za.ac.cput.sikunyecharity.GUI.DonorDonationGUI_ED;

/**
 * Esona Dyomfana 240949072
 */

public class DonationED extends DonorDonationGUI_ED {

    public DonationED() {
        super();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DonationED app = new DonationED();
            app.setVisible(true);
        });
    }
}
