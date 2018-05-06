package com.dogpo.shopifyorder.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dogpo.shopifyorder.model.DataChildYear;
import com.dogpo.shopifyorder.model.DataYear;
import com.dogpo.shopifyorder.adapter.ExpandableAdapterYear;
import com.dogpo.shopifyorder.R;
import com.dogpo.shopifyorder.util.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class OrderByYearActivity extends AppCompatActivity {
    ExpandableListView elYears;
    ExpandableAdapterYear expandableAdapterYear;
    List<DataYear> listDataHeader = new ArrayList<>();
    Map<Integer, Integer> mapYear = new TreeMap<>();
    HashMap<Integer, List<DataChildYear>> listDataChild = new HashMap<>();
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_year);
        pb = findViewById(R.id.pb);
        elYears = findViewById(R.id.el_year);
        // preparing list data
        expandableAdapterYear = new ExpandableAdapterYear(this, listDataHeader, listDataChild);
        // setting list adapter
        elYears.setAdapter(expandableAdapterYear);
        fetchListFromAPI();
    }

    private void fetchListFromAPI() {
        pb.setVisibility(View.VISIBLE);

        listDataChild.clear();
        listDataHeader.clear();
        String url = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        Ion.with(this)
                .load(url)
                .progressBar(pb)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        pb.setVisibility(View.GONE);

                        if (e == null) {
                            getResult(result);
                        } else {
                            Toast.makeText(OrderByYearActivity.this, "Could not load data... Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void getResult(JsonObject result) {
        if (result.has("orders")) {
            JsonArray orders = result.getAsJsonArray("orders");
            int size = orders.size();
            for (int i = 0; i < size; i++) {
                JsonObject order = orders.get(i).getAsJsonObject();
                if (order.has("created_at")) {
                    String createdAt = order.get("created_at").getAsString();
//2017-07-11T14:05:00-04:00
                    int year = Util.getYear(createdAt);

                    if (mapYear.containsKey(year)) {
                        int count = mapYear.get(year);
                        mapYear.put(year, count + 1);
                        if (count < 10) {
                            String totalPrice = order.get("total_price").getAsString();
                            String customerName = null;
                            if (order.has("customer")) {
                                JsonObject billingAddress = order.getAsJsonObject("customer");
                                if (billingAddress.has("first_name"))
                                    customerName = billingAddress.get("first_name").getAsString();
                                else customerName = "Unknown";
                            } else {
                                customerName = "Unknown";
                            }
                            DataChildYear dataChildYear = new DataChildYear(totalPrice, customerName, createdAt);
                            List<DataChildYear> list = listDataChild.get(year);
                            list.add(dataChildYear);
                            listDataChild.put(year, list);
                        }
                    } else {
                        mapYear.put(year, 1);
                        // this.itemName = itemName;
                        //        this.customerName = customerName;
                        //        this.orderDate = orderDate;
                        String totalPrice = order.get("total_price").getAsString();
                        String customerName = null;
                        if (order.has("customer")) {
                            JsonObject billingAddress = order.getAsJsonObject("customer");
                            if (billingAddress.has("first_name"))
                                customerName = billingAddress.get("first_name").getAsString();
                            else customerName = "Unknown";
                        } else {
                            customerName = "Unknown";
                        }
                        List<DataChildYear> list = new ArrayList<>();
                        DataChildYear dataChildYear = new DataChildYear(totalPrice, customerName, createdAt);
                        list.add(dataChildYear);
                        listDataChild.put(year, list);
                    }
                }
            }
            for (Map.Entry<Integer, Integer> entry : mapYear.entrySet()) {
                int key = entry.getKey();
                int count = entry.getValue();
                DataYear dataYear = new DataYear(key, count);
                listDataHeader.add(dataYear);
                // ...
            }
        } else {
            Toast.makeText(this, "Error in reading Json data", Toast.LENGTH_SHORT).show();
        }
        expandableAdapterYear.notifyDataSetChanged();

    }
}
