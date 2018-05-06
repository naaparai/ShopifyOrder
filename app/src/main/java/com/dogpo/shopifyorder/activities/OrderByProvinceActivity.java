package com.dogpo.shopifyorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dogpo.shopifyorder.adapter.AdapterProvince;
import com.dogpo.shopifyorder.model.DataProvince;
import com.dogpo.shopifyorder.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class OrderByProvinceActivity extends AppCompatActivity {

    RecyclerView rvProvince;
    ProgressBar pb;
    AdapterProvince adapterProvince;
    ArrayList<DataProvince> dataProvinces = new ArrayList<>();
    Map<String, Integer> provinceCount = new TreeMap<>();
    Button bProvince;
    JsonObject result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_by_province);
        rvProvince = findViewById(R.id.rv_province);
        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        bProvince = findViewById(R.id.b_province);
        bProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (result == null) {
                    Toast.makeText(OrderByProvinceActivity.this, "Loading data... please wait", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(OrderByProvinceActivity.this, DetailOrderActivity.class);
                intent.putExtra("result", result.toString());
                startActivity(intent);
            }
        });
        adapterProvince = new AdapterProvince(this, dataProvinces);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvProvince.setLayoutManager(linearLayoutManager);
        rvProvince.setAdapter(adapterProvince);
        fetchListFromAPI();
    }

    private void fetchListFromAPI() {
        pb.setVisibility(View.VISIBLE);

        dataProvinces.clear();
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
                            OrderByProvinceActivity.this.result = result;
                        } else {
                            Toast.makeText(OrderByProvinceActivity.this, "Could not load data... Please try again", Toast.LENGTH_SHORT).show();
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
                if (order.has("billing_address")) {
                    JsonObject billingAddress = order.get("billing_address").getAsJsonObject();
                    String province = billingAddress.get("province").getAsString();
                    if (provinceCount.containsKey(province)) {
                        provinceCount.put(province, provinceCount.get(province) + 1);
                    } else {
                        provinceCount.put(province, 1);
                    }

                } else {
                    if (provinceCount.containsKey("Unknown")) {
                        provinceCount.put("Unknown", provinceCount.get("Unknown") + 1);
                    } else {
                        provinceCount.put("Unknown", 1);
                    }
                }
            }
            for (Map.Entry<String, Integer> entry : provinceCount.entrySet()) {
                String key = entry.getKey();
                int count = entry.getValue();
                DataProvince dataProvince = new DataProvince(key, count);
                dataProvinces.add(dataProvince);
                // ...
            }
            adapterProvince.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Error in reading Json data", Toast.LENGTH_SHORT).show();
        }
    }
}
