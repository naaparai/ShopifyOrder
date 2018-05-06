package com.dogpo.shopifyorder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dogpo.shopifyorder.R;

public class MainActivity extends AppCompatActivity {

    Button bProvince,bYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bProvince=findViewById(R.id.b_province);
        bYear=findViewById(R.id.b_year);
        bProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OrderByProvinceActivity.class);
                startActivity(intent);
            }
        });
        bYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,OrderByYearActivity.class);
                startActivity(intent);
            }
        });
    }
}
