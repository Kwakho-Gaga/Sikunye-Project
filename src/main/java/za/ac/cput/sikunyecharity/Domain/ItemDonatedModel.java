package za.ac.cput.mavenproject6.domain;

public class ItemDonatedModel {
    private int itemID;
    private int receiptID;
    private String itemName;
    private int quantity;
    private String itemCondition;

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
    public int getReceiptID() { return receiptID; }
    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public String getItemCondition() { return itemCondition; }
}