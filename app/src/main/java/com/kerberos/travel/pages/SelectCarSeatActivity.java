package com.kerberos.travel.pages;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.kerberos.travel.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class SelectCarSeatActivity extends AppCompatActivity {
    TextInputLayout date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flight_seat);
        Button next = findViewById(R.id.button3);
        date = findViewById(R.id.textInputLayout9);
        TextInputLayout children = findViewById(R.id.textInputLayout8);
        TextInputLayout adults = findViewById(R.id.adults);
        ImageView back = findViewById(R.id.imageView18);
        ArrayList<String> seats = new ArrayList<>();

        ImageView s1 = findViewById(R.id.s1);
        ImageView s2 = findViewById(R.id.s2);
        ImageView s3 = findViewById(R.id.s3);
        ImageView s5 = findViewById(R.id.s5);
        ImageView s6 = findViewById(R.id.s6);
        ImageView s7 = findViewById(R.id.s7);
        ImageView s9 = findViewById(R.id.s9);
        ImageView s10 = findViewById(R.id.s10);
        ImageView s11 = findViewById(R.id.s11);
        ImageView s13 = findViewById(R.id.s13);
        ImageView s14 = findViewById(R.id.s14);
        ImageView s15 = findViewById(R.id.s15);
        ImageView s17 = findViewById(R.id.s17);
        ImageView s18 = findViewById(R.id.s18);
        ImageView s19 = findViewById(R.id.s19);
        ImageView s21 = findViewById(R.id.s21);
        ImageView s22 = findViewById(R.id.s22);
        ImageView s23 = findViewById(R.id.s23);
        ImageView s25 = findViewById(R.id.s25);
        ImageView s26 = findViewById(R.id.s26);
        ImageView s27 = findViewById(R.id.s27);
        ImageView s29 = findViewById(R.id.s29);
        ImageView s30 = findViewById(R.id.s30);
        ImageView s31 = findViewById(R.id.s31);
        ImageView s33 = findViewById(R.id.s33);
        ImageView s34 = findViewById(R.id.s34);
        ImageView s35 = findViewById(R.id.s35);
        ImageView s36 = findViewById(R.id.s36);

        s1.setOnClickListener(v -> {
            s1.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s1");
        });
        s2.setOnClickListener(v -> {
            s2.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s2");
        });
        s3.setOnClickListener(v -> {
            s3.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s3");
        });
        s5.setOnClickListener(v -> {
            s5.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s4");
        });
        s6.setOnClickListener(v -> {
            s6.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s5");
        });
        s7.setOnClickListener(v -> {
            s7.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s6");
        });
        s9.setOnClickListener(v -> {
            s9.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s9");
        });
        s10.setOnClickListener(v -> {
            s10.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s11");
        });
        s11.setOnClickListener(v -> {
            s11.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s11");
        });
        s13.setOnClickListener(v -> {
            s13.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s13");
        });
        s14.setOnClickListener(v -> {
            s14.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s14");
        });
        s15.setOnClickListener(v -> {
            s15.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s15");
        });
        s17.setOnClickListener(v -> {
            s17.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s17");
        });
        s18.setOnClickListener(v -> {
            s18.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s18");
        });
        s19.setOnClickListener(v -> {
            s19.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s19");
        });
        s21.setOnClickListener(v -> {
            s21.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s21");
        });
        s22.setOnClickListener(v -> {
            s22.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s23");
        });
        s23.setOnClickListener(v -> {
            s23.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s23");
        });
        s25.setOnClickListener(v -> {
            s25.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s25");
        });
        s26.setOnClickListener(v -> {
            s26.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s26");
        });
        s27.setOnClickListener(v -> {
            s27.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s27");
        });
        s29.setOnClickListener(v -> {
            s29.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s29");
        });
        s30.setOnClickListener(v -> {
            s30.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s30");
        });
        s31.setOnClickListener(v -> {
            s31.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s31");
        });
        s33.setOnClickListener(v -> {
            s33.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s33");
        });
        s34.setOnClickListener(v -> {
            s34.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s34");
        });
        s35.setOnClickListener(v -> {
            s35.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s35");
        });
        s36.setOnClickListener(v -> {
            s36.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
            seats.add("s36");
        });


        String dateValue = getIntent().getStringExtra("date");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price",0);
        String userId = getIntent().getStringExtra("userId");
        String type = getIntent().getStringExtra("type");
        String time = getIntent().getStringExtra("time");

        if(dateValue != null){
            date.getEditText().setText(dateValue);
        }
        date.setEndIconOnClickListener(view->{
            showDatePicker();
        });
        back.setOnClickListener(view->{
            super.onBackPressed();
        });

        next.setOnClickListener(view->{
            Intent intent = new Intent(this, PaymentActivity.class);
            if(TextUtils.isEmpty(date.getEditText().getText().toString())){
                date.setError("Enter date");
            }else if(TextUtils.isEmpty(adults.getEditText().getText().toString())){
                adults.setError("Enter number of adults");
            }else if(seats.isEmpty()){
                Toast.makeText(SelectCarSeatActivity.this,"Choose seats",Toast.LENGTH_LONG).show();
            }
            else if((Integer.parseInt(adults.getEditText().getText().toString()) + Integer.parseInt(children.getEditText().getText().toString())) != seats.size()){
                Toast.makeText(SelectCarSeatActivity.this,"The seats selected does not match with the travellers",Toast.LENGTH_LONG).show();
            }
            else{
                StringBuilder allSelectedSeats = new StringBuilder();
                intent.putExtra("date",date.getEditText().getText().toString());
                intent.putExtra("from",from);
                intent.putExtra("to",to);
                intent.putExtra("name",name);
                intent.putExtra("price",price);
                intent.putExtra("userId",userId);
                intent.putExtra("type",type);
                intent.putExtra("time",time);
                intent.putExtra("children",children.getEditText().getText().toString());
                for(String seat : seats){
                    allSelectedSeats.append(","+ seat);
                }
                intent.putExtra("seats",allSelectedSeats.toString());
                startActivity(intent);
            }

        });
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle date selection here
                        String selectedDate = dayOfMonth + " " + getMonthName(monthOfYear) + " " + year;
                        Objects.requireNonNull(date.getEditText()).setText(selectedDate);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month];
    }
}