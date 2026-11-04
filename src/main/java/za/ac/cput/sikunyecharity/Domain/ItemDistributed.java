package za.ac.cput.sikunyecharity.Domain;

import java.util.Date;

public class ItemDistributed {
    private int distributionID;
    private int itemID;
    private int beneficiaryID;
    private String beneficiaryName;
    private int distributedQuantity;
    private Date distributionDate;

    public ItemDistributed() {}

    public ItemDistributed(int distributionID, int itemID, int beneficiaryID, String beneficiaryName, int distributedQuantity, Date distributionDate) {
        this.distributionID = distributionID;
        this.itemID = itemID;
        this.beneficiaryID = beneficiaryID;
        this.beneficiaryName = beneficiaryName;
        this.distributedQuantity = distributedQuantity;
        this.distributionDate = distributionDate;
    }

    public ItemDistributed(int itemID, int beneficiaryID, String beneficiaryName, int distributedQuantity, Date distributionDate) {
        this.itemID = itemID;
        this.beneficiaryID = beneficiaryID;
        this.beneficiaryName = beneficiaryName;
        this.distributedQuantity = distributedQuantity;
        this.distributionDate = distributionDate;
    }

    public int getDistributionID() { return distributionID; }
    public void setDistributionID(int distributionID) { this.distributionID = distributionID; }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public int getBeneficiaryID() { return beneficiaryID; }
    public void setBeneficiaryID(int beneficiaryID) { this.beneficiaryID = beneficiaryID; }

    public String getBeneficiaryName() { return beneficiaryName; }
    public void setBeneficiaryName(String beneficiaryName) { this.beneficiaryName = beneficiaryName; }

    public int getDistributedQuantity() { return distributedQuantity; }
    public void setDistributedQuantity(int distributedQuantity) { this.distributedQuantity = distributedQuantity; }

    public Date getDistributionDate() { return distributionDate; }
    public void setDistributionDate(Date distributionDate) { this.distributionDate = distributionDate; }
}