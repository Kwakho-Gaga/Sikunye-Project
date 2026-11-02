package za.ac.cput.sikunyecharity.Domain;

import java.math.BigDecimal;

public class Donation {

    private String projectId;
    private String donorName;
    private String donorEmail;
    private BigDecimal amount;

    public Donation() {
    }

    public Donation(String projectId, String donorName, String donorEmail, BigDecimal amount) {
        this.projectId = projectId;
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.amount = amount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public String getDonorEmail() {
        return donorEmail;
    }

    public void setDonorEmail(String donorEmail) {
        this.donorEmail = donorEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
