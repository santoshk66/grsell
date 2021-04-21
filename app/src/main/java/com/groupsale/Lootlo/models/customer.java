package com.groupsale.Lootlo.models;

public class customer {
    String  customerID, name,  phoneNumber,createdAt;
    String[] leadDeal;
    String[] currentDeal;
    // left refer schema


    public customer() {
    }

    public customer(String customerID, String name, String phoneNumber, String createdAt, String[] leadDeal, String[] currentDeal) {
        this.customerID = customerID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.leadDeal = leadDeal;
        this.currentDeal = currentDeal;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getLeadDeal() {
        return leadDeal;
    }

    public void setLeadDeal(String[] leadDeal) {
        this.leadDeal = leadDeal;
    }

    public String[] getCurrentDeal() {
        return currentDeal;
    }

    public void setCurrentDeal(String[] currentDeal) {
        this.currentDeal = currentDeal;
    }
}
