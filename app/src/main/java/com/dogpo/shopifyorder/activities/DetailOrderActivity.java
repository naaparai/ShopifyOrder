package com.dogpo.shopifyorder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.dogpo.shopifyorder.R;
import com.dogpo.shopifyorder.adapter.ExpandableAdapterProvince;
import com.dogpo.shopifyorder.model.DataChildProvince;
import com.dogpo.shopifyorder.model.DataChildYear;
import com.dogpo.shopifyorder.model.DataProvince;
import com.dogpo.shopifyorder.model.DataYear;
import com.dogpo.shopifyorder.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DetailOrderActivity extends AppCompatActivity {

    JsonObject result;
    ExpandableListView elDetail;
    ExpandableAdapterProvince expandableAdapterProvince;
    List<String> listDataHeader = new ArrayList<>();
    Map<String, Integer> mapProvince = new TreeMap<>();
    Map<String, List<DataChildProvince>> listDataChild = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        elDetail = findViewById(R.id.el_detail);
        JsonParser jsonParser = new JsonParser();
        result = jsonParser.parse(getIntent().getStringExtra("result")).getAsJsonObject();
        elDetail = findViewById(R.id.el_detail);
        // preparing list data
        expandableAdapterProvince = new ExpandableAdapterProvince(this, listDataHeader, listDataChild);
        // setting list adapter
        elDetail.setAdapter(expandableAdapterProvince);
        setupList(result);
    }

    private void setupList(JsonObject result) {
        if (result.has("orders")) {
            JsonArray orders = result.getAsJsonArray("orders");
            int size = orders.size();
            for (int i = 0; i < size; i++) {
                JsonObject order = orders.get(i).getAsJsonObject();
                if (order.has("billing_address")) {
                    JsonObject billingAddress = order.get("billing_address").getAsJsonObject();
                    String province = billingAddress.get("province").getAsString();
                    String orderNo = order.get("id").getAsString();
                    String customerName = null;
                    if (order.has("customer")) {
                        JsonObject customer = order.get("customer").getAsJsonObject();
                        customerName = customer.get("first_name").getAsString();

                    } else {
                        customerName = "Unknown";
                    }
                    String price = order.get("total_price").getAsString();
                    String date = order.get("created_at").getAsString();
                    DataChildProvince dataChildProvince = new DataChildProvince(orderNo, customerName, price, date);
                    if (mapProvince.containsKey(province)) {
                        mapProvince.put(province, mapProvince.get(province) + 1);
                        List<DataChildProvince> list = listDataChild.get(province);
                        list.add(dataChildProvince);
                        listDataChild.put(province, list);
                    } else {
                        mapProvince.put(province, 1);
                        List<DataChildProvince> list = new ArrayList<>();
                        list.add(dataChildProvince);
                        listDataChild.put(province, list);
                    }

                } else {
                    String orderNo = order.get("id").getAsString();
                    String customerName = null;
                    if (order.has("customer")) {
                        JsonObject customer = order.get("customer").getAsJsonObject();
                        customerName = customer.get("first_name").getAsString();

                    } else {
                        customerName = "Unknown";
                    }
                    String price = order.get("total_price").getAsString();
                    String date = order.get("created_at").getAsString();
                    DataChildProvince dataChildProvince = new DataChildProvince(orderNo, customerName, price, date);
                    if (mapProvince.containsKey("Unknown")) {
                        mapProvince.put("Unknown", mapProvince.get("Unknown") + 1);
                        List<DataChildProvince> list = listDataChild.get("Unknown");
                        list.add(dataChildProvince);
                        listDataChild.put("Unknown", list);
                    } else {
                        mapProvince.put("Unknown", 1);
                        List<DataChildProvince> list = new ArrayList<>();
                        list.add(dataChildProvince);
                        listDataChild.put("Unknown", list);
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : mapProvince.entrySet()) {
                String key = entry.getKey();
                int count = entry.getValue();
                DataProvince dataProvince = new DataProvince(key, count);
                listDataHeader.add(key);
                // ...
            }

        } else {
            Toast.makeText(this, "Error in reading Json data", Toast.LENGTH_SHORT).show();
        }
        expandableAdapterProvince.notifyDataSetChanged();

    }
}
