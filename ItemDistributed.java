package za.ac.cput.mavenproject6.domain;

import java.util.Date;

public class ItemDistributed {
    private int distributionID;
    private int itemID;
    private int beneficiaryID;
    private String beneficiaryName;
    private int distributedQuantity;
    private Date distributionDate;

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
    public int getItemID() { return itemID; }
    public int getBeneficiaryID() { return beneficiaryID; }
    public String getBeneficiaryName() { return beneficiaryName; }
    public int getDistributedQuantity() { return distributedQuantity; }
    public Date getDistributionDate() { return distributionDate; }
}