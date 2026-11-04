package za.ac.cput.sikunyecharity.Domain;

public class ItemDonatedModel {
    private int itemID;
    private int receiptID;
    private String itemName;
    private int quantity;
    private String itemCondition;

    public ItemDonatedModel() {}

    public ItemDonatedModel(int itemID, int receiptID, String itemName, int quantity, String itemCondition) {
        this.itemID = itemID;
        this.receiptID = receiptID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemCondition = itemCondition;
    }

    public ItemDonatedModel(int receiptID, String itemName, int quantity, String itemCondition) {
        this.receiptID = receiptID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemCondition = itemCondition;
    }

    public int getItemID() { return itemID; }
    public void setItemID(int itemID) { this.itemID = itemID; }

    public int getReceiptID() { return receiptID; }
    public void setReceiptID(int receiptID) { this.receiptID = receiptID; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getItemCondition() { return itemCondition; }
    public void setItemCondition(String itemCondition) { this.itemCondition = itemCondition; }
}