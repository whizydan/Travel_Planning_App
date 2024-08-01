package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.ItemAdapter;
import com.kerberos.travel.models.FoodModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PreOrderActivity extends AppCompatActivity {
    TextView total;
    int totalValue = 0;
    StringBuilder orders;
    CheckBox wine, water, crips, coke, pepsi, cookies, popcorns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_order);
        Button proceedButton = findViewById(R.id.button4);
        total = findViewById(R.id.textView51);
        wine = findViewById(R.id.checkBox);
        water = findViewById(R.id.checkBox2);
        crips = findViewById(R.id.checkBox3);
        coke = findViewById(R.id.checkBox4);
        pepsi = findViewById(R.id.checkBox5);
        cookies = findViewById(R.id.checkBox6);
        popcorns = findViewById(R.id.checkbox7);
        ImageView back = findViewById(R.id.back);
        orders = new StringBuilder();

        String date = getIntent().getStringExtra("date");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price",0);
        String userId = getIntent().getStringExtra("userId");
        String type = getIntent().getStringExtra("type");
        String time = getIntent().getStringExtra("time");


        wine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        crips.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        coke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        pepsi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        cookies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        popcorns.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                updateTotal();
            }
        });
        back.setOnClickListener(view->{
            super.onBackPressed();
        });
        proceedButton.setOnClickListener(v -> {
            // Pass along the items to the payment class
            Intent intent = new Intent(PreOrderActivity.this, PaymentActivity.class);
            intent.putExtra("date",date);
            intent.putExtra("from",from);
            intent.putExtra("to",to);
            intent.putExtra("name",name);
            intent.putExtra("price",price + totalValue);
            intent.putExtra("userId",userId);
            intent.putExtra("type",type);
            intent.putExtra("time",time);
            intent.putExtra("extra",orders.toString());

            startActivity(intent);
        });

    }
    private void updateTotal(){
        totalValue = 0;
        orders = new StringBuilder();
        if(wine.isChecked()){
            totalValue = totalValue + 50;
            orders.append("\nRed wine small glass - RM 50");
        }
        if(water.isChecked()){
            totalValue = totalValue + 20;
            orders.append("\nCold water - RM 20");
        }
        if(crips.isChecked()){
            totalValue = totalValue + 35;
            orders.append("\nPotato Crips - RM 35");
        }
        if(coke.isChecked()){
            totalValue = totalValue + 50;
            orders.append("\nCoke 300 ml - RM 50");
        }
        if(pepsi.isChecked()){
            totalValue = totalValue + 50;
            orders.append("\nPepsi 300 ml - RM 50");
        }
        if(cookies.isChecked()){
            totalValue = totalValue + 50;
            orders.append("\nCookies 400 g - RM 50");
        }
        if(popcorns.isChecked()){
            totalValue = totalValue + 40;
            orders.append("\nPopcorns - RM 40");
        }

        total.setText("Total: RM " + totalValue);
    }

}
