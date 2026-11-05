package za.ac.cput.sikunyecharity.Domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class Donation {
    
    private int donationID;
    private int donorID;
    private int projectID;
    private String donationType;
    private BigDecimal amount;
    private String paymentStatus;
    private Timestamp paymentDate;
    private boolean receiptGenerated;
    private Date dateReceived;

    // Constructor
    public Donation(int donationID, int donorID, int projectID, String donationType,
                    BigDecimal amount, String paymentStatus, Timestamp paymentDate,
                    boolean receiptGenerated, Date dateReceived) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.projectID = projectID;
        this.donationType = donationType;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.receiptGenerated = receiptGenerated;
        this.dateReceived = dateReceived;
    }

    public int getDonationID() { 
        return donationID; 
    }
    public int getDonorID() { 
        return donorID; 
    }
    public int getProjectID() { 
        return projectID; 
    }
    public String getDonationType() { 
        return donationType; 
    }
    public BigDecimal getAmount() { 
        return amount; 
    }
    public String getPaymentStatus() { 
        return paymentStatus; 
    }
    public Timestamp getPaymentDate() { 
        return paymentDate; 
    }
    public boolean isReceiptGenerated() { 
        return receiptGenerated; 
    }
    public Date getDateReceived() { 
        return dateReceived; 
    }

    public void setDonationID(int donationID) { 
        this.donationID = donationID; 
    }
    public void setDonorID(int donorID) { 
        this.donorID = donorID; 
    }
    public void setProjectID(int projectID) { 
        this.projectID = projectID; 
    }
    public void setDonationType(String donationType) { 
        this.donationType = donationType; 
    }
    public void setAmount(BigDecimal amount) { 
        this.amount = amount; 
    }
    public void setPaymentStatus(String paymentStatus) { 
        this.paymentStatus = paymentStatus; 
    }
    public void setPaymentDate(Timestamp paymentDate) { 
        this.paymentDate = paymentDate; 
    }
    public void setReceiptGenerated(boolean receiptGenerated) { 
        this.receiptGenerated = receiptGenerated; 
    }
    public void setDateReceived(Date dateReceived) { 
        this.dateReceived = dateReceived; 
    }
}