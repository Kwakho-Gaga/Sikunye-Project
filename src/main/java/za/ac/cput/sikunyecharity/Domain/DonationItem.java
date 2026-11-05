/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.sikunyecharity.Domain;

/**
 *
 * @author User
 */
public class DonationItem {
    


    private int donationItemID;
    private int donationID;
    private int itemID;
    private int quantity;
    private String condition;

 
    public DonationItem(int donationItemID, int donationID, int itemID, int quantity, String condition) {
        this.donationItemID = donationItemID;
        this.donationID = donationID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.condition = condition;
    }

   
    public int getDonationItemID() { 
        return donationItemID; 
    }
    public int getDonationID() { 
        return donationID; 
    }
    public int getItemID() { 
        return itemID; 
    }
    public int getQuantity() { 
        return quantity; 
    }
    public String getCondition() { 
        return condition; 
    }


    public void setDonationItemID(int donationItemID) { 
        this.donationItemID = donationItemID; 
    }
    public void setDonationID(int donationID) { 
        this.donationID = donationID; 
    }
    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    public void setCondition(String condition) { 
        this.condition = condition; 
    }
}


