package za.ac.cput.sikunyecharity.Domain;

public class Beneficiary {

    private int beneficiaryId;
    private String firstName;
    private String lastName;
    private int familySize;
    private String address;
    private String livingSituation;

    public Beneficiary() {
    }

    public Beneficiary(int beneficiaryId, String firstName, String lastName,
            int familySize, String address, String livingSituation) {
        this.beneficiaryId = beneficiaryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.familySize = familySize;
        this.address = address;
        this.livingSituation = livingSituation;
    }

    public int getBeneficiaryId() { return beneficiaryId; }
    public void setBeneficiaryId(int beneficiaryId) { this.beneficiaryId = beneficiaryId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public int getFamilySize() { return familySize; }
    public void setFamilySize(int familySize) { this.familySize = familySize; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getLivingSituation() { return livingSituation; }
    public void setLivingSituation(String livingSituation) { this.livingSituation = livingSituation; }

    @Override
    public String toString() {
        return beneficiaryId + " - " + firstName + " " + lastName;
    }
}
