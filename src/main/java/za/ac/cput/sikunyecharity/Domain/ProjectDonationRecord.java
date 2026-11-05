package za.ac.cput.sikunyecharity.Domain;

import java.math.BigDecimal;
import java.sql.Date;

public class ProjectDonationRecord {

    private final int donationId;
    private final int donorId;
    private final String projectId;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final Date donationDate;

    public ProjectDonationRecord(int donationId, int donorId, String projectId,
            BigDecimal amount, String paymentMethod, Date donationDate) {
        this.donationId = donationId;
        this.donorId = donorId;
        this.projectId = projectId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.donationDate = donationDate;
    }

    public int getDonationId() {
        return donationId;
    }

    public int getDonorId() {
        return donorId;
    }

    public String getProjectId() {
        return projectId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Date getDonationDate() {
        return donationDate;
    }
}
