package com.dogpo.shopifyorder.model;

public class DataChildYear {
    private String itemPrice,customerName,orderDate;

    public DataChildYear(String itemPrice, String customerName, String orderDate) {
        this.itemPrice = itemPrice;
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }



    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
