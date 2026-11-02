package za.ac.cput.sikunyecharity.Domain;

public class BeneficiaryNeed {

    private int beneficiaryId;
    private int needCode;
    private String needDescription;
    private int quantity;

    public BeneficiaryNeed() {
    }

    public BeneficiaryNeed(int beneficiaryId, int needCode,
            String needDescription, int quantity) {
        this.beneficiaryId = beneficiaryId;
        this.needCode = needCode;
        this.needDescription = needDescription;
        this.quantity = quantity;
    }

    public int getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId; }
    public int getNeedCode() { return needCode; }
    public void setNeedCode(int needCode) { this.needCode = needCode; }
    public String getNeedDescription() { return needDescription; }
    public void setNeedDescription(String needDescription) { this.needDescription = needDescription; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return needDescription + " (x" + quantity + ")";
    }
}
