package com.groupsale.Lootlo.models;

public class deal {
    int pinCode,teamSize,dealPrice;
    String dealID,productID,textMessage,description,name,creatorID,dateTime;
    String[] currentSubscribers,imageUrl;

    public deal() {
    }

    public deal(int pinCode, int teamSize, int dealPrice, String dealID, String productID, String textMessage, String description, String name, String creatorID, String dateTime, String[] currentSubscribers, String[] imageUrl) {
        this.pinCode = pinCode;
        this.teamSize = teamSize;
        this.dealPrice = dealPrice;
        this.dealID = dealID;
        this.productID = productID;
        this.textMessage = textMessage;
        this.description = description;
        this.name = name;
        this.creatorID = creatorID;
        this.dateTime = dateTime;
        this.currentSubscribers = currentSubscribers;
        this.imageUrl = imageUrl;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String[] getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String[] imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(int dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealID() {
        return dealID;
    }

    public void setDealID(String dealID) {
        this.dealID = dealID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String[] getCurrentSubscribers() {
        return currentSubscribers;
    }

    public void setCurrentSubscribers(String[] currentSubscribers) {
        this.currentSubscribers = currentSubscribers;
    }

    //left dateTime



}
