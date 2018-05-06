package com.dogpo.shopifyorder.model;

public class DataYear {
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataYear(int year, int count) {

        this.year = year;
        this.count = count;
    }

    private int year, count;
}
