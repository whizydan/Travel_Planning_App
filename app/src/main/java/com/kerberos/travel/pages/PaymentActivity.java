package com.kerberos.travel.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kerberos.travel.R;
import com.kerberos.travel.adapters.DealsAdapter;
import com.kerberos.travel.database.Database;
import com.kerberos.travel.models.CouponsModel;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    TextView tax;
    TextView subTotal;
    TextView total;
    TextView discountApplied;
    double price;
    LinearLayout discountArea;
    ArrayList<CouponsModel> coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        MaterialButton proceed = findViewById(R.id.materialButton4);
        CheckBox checkBox = findViewById(R.id.checkbox);
        tax = findViewById(R.id.tax);
        subTotal = findViewById(R.id.subTotal);
        total = findViewById(R.id.total);
        MaterialButton promoCode = findViewById(R.id.promo);
        price = 0.0;
        discountApplied = findViewById(R.id.discount);
        discountArea = findViewById(R.id.discountArea);
        ImageView back = findViewById(R.id.imageView23);

        back.setOnClickListener(v->{
            super.onBackPressed();
        });

        coupons = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("coupons");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    CouponsModel coupon = childSnapshot.getValue(CouponsModel.class);
                    coupons.add(coupon);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PaymentActivity.this,error.getDetails(),Toast.LENGTH_LONG).show();
            }
        });

        View dialogView = LayoutInflater.from(this).inflate(R.layout.input_box, null);
        TextInputLayout editText = dialogView.findViewById(R.id.inputText);

        AlertDialog alertDialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Enter Discount Code")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enteredText = editText.getEditText().getText().toString();
                        for(CouponsModel model : coupons){
                            if(enteredText.equalsIgnoreCase(model.getCode())){
                                Toast.makeText(PaymentActivity.this,"You have activated " + model.getDiscount() + "% discount",Toast.LENGTH_LONG).show();
                                int perc = 100 - model.getDiscount();
                                price = price * ((double) perc /100);
                                refreshUi();
                                promoCode.setEnabled(false);
                                discountArea.setVisibility(View.VISIBLE);
                                discountApplied.setText(model.getDiscount() + "% OFF");
                                break;
                            }else{
                                Toast.makeText(PaymentActivity.this,"No valid code has been provided",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click (Cancel)
                        dialog.dismiss();
                    }
                })
                .create();

        promoCode.setOnClickListener(v->{
            // Show the dialog
            alertDialog.show();
        });


        String date = getIntent().getStringExtra("date");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String name = getIntent().getStringExtra("name");
        price = getIntent().getIntExtra("price",0);
        int pax = getIntent().getIntExtra("pax",1);
        String userId = getIntent().getStringExtra("userId");
        String type = getIntent().getStringExtra("type");
        String time = getIntent().getStringExtra("time");
        String extra = getIntent().getStringExtra("extra");
        Toast.makeText(this,pax + "",Toast.LENGTH_LONG).show();

        checkBox.setOnClickListener(v->{
            if(checkBox.isChecked()){
                proceed.setEnabled(true);
            }else{
                proceed.setEnabled(false);
            }
        });

        double taxValue = price*16/100;
        tax.setText("RM " + taxValue);
        subTotal.setText("RM " + price);
        try{
            double val = price + taxValue;
            total.setText("RM " + val);
        }catch (Exception ignored){}


        proceed.setOnClickListener(view->{
            Intent intent = new Intent(this, PayoutActivity.class);
            intent.putExtra("date",date);
            intent.putExtra("from",from);
            intent.putExtra("to",to);
            intent.putExtra("name",name);
            intent.putExtra("price",price);
            intent.putExtra("userId",userId);
            intent.putExtra("type",type);
            intent.putExtra("time",time);
            intent.putExtra("extra",extra);
            intent.putExtra("pax",pax);
            startActivity(intent);
        });
    }
    void refreshUi(){
        double taxValue = price*16/100;
        tax.setText("RM " + taxValue);
        subTotal.setText("RM " + price);
        try{
            double val = price + taxValue;
            total.setText("RM " + val);
        }catch (Exception ignored){}
    }
}