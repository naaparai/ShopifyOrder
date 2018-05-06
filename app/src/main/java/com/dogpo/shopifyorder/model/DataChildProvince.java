package com.dogpo.shopifyorder.model;

public class DataChildProvince {
    private String orderNumber,customerName,price,orderDate;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public DataChildProvince(String orderNumber, String customerName, String price, String orderDate) {

        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.price = price;
        this.orderDate = orderDate;
    }
}
