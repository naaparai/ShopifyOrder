package com.dogpo.shopifyorder.model;

public class DataProvince {
    private int count;
    private String province;
    public DataProvince(String province, int count){
        this.count=count;
        this.province=province;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
