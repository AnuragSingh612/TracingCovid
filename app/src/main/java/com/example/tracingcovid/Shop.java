package com.example.tracingcovid;

public class Shop {
    private String ShopName,Shopphone,Shopaddress;

    public Shop() {
    }

    public Shop(String shopName, String shopphone, String shopaddress) {
        ShopName = shopName;
        Shopphone = shopphone;
        Shopaddress = shopaddress;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName = shopName;
    }

    public String getShopphone() {
        return Shopphone;
    }

    public void setShopphone(String shopphone) {
        Shopphone = shopphone;
    }

    public String getShopaddress() {
        return Shopaddress;
    }

    public void setShopaddress(String shopaddress) {
        Shopaddress = shopaddress;
    }
}
