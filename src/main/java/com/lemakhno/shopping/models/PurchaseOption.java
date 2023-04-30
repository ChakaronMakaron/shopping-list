package com.lemakhno.shopping.models;

public class PurchaseOption {
    
    private String link;
    private String shopName;
    
    public PurchaseOption() {}

    public PurchaseOption(String link, String shopName) {
        this.link = link;
        this.shopName = shopName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "PurchaseOption [link=" + link + ", shopName=" + shopName + "]";
    }
}
