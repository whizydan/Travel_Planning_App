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

public class SelectFlightSeatActivity extends AppCompatActivity {
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
            if(seats.contains("s1")){
                seats.remove("s1");
                s1.setColorFilter(null);
            }else{
                s1.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s1");
            }
        });
        s2.setOnClickListener(v -> {
            if(seats.contains("s2")){
                seats.remove("s2");
                s2.setColorFilter(null);
            }else{
                s2.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s2");
            }
        });
        s3.setOnClickListener(v -> {
            if(seats.contains("s3")){
                seats.remove("s3");
                s3.setColorFilter(null);
            }else{
                s3.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s3");
            }
        });
        s5.setOnClickListener(v -> {
            if(seats.contains("s5")){
                seats.remove("s5");
                s5.setColorFilter(null);
            }else{
                s5.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s5");
            }
        });
        s6.setOnClickListener(v -> {
            if(seats.contains("s6")){
                seats.remove("s6");
                s6.setColorFilter(null);
            }else{
                s6.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s6");
            }
        });
        s7.setOnClickListener(v -> {
            if(seats.contains("s7")){
                seats.remove("s7");
                s7.setColorFilter(null);
            }else{
                s7.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s7");
            }
        });
        s9.setOnClickListener(v -> {
            if(seats.contains("s9")){
                seats.remove("s9");
                s9.setColorFilter(null);
            }else{
                s9.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s9");
            }
        });
        s10.setOnClickListener(v -> {
            if(seats.contains("s10")){
                seats.remove("s10");
                s10.setColorFilter(null);
            }else{
                s10.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s10");
            }
        });
        s11.setOnClickListener(v -> {
            if(seats.contains("s11")){
                seats.remove("s11");
                s11.setColorFilter(null);
            }else{
                s11.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s11");
            }
        });
        s13.setOnClickListener(v -> {
            if(seats.contains("s13")){
                seats.remove("s13");
                s13.setColorFilter(null);
            }else{
                s13.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s13");
            }
        });
        s14.setOnClickListener(v -> {
            if(seats.contains("s14")){
                seats.remove("s14");
                s14.setColorFilter(null);
            }else{
                s14.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s14");
            }
        });
        s15.setOnClickListener(v -> {
            if(seats.contains("s15")){
                seats.remove("s15");
                s15.setColorFilter(null);
            }else{
                s15.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s15");
            }
        });
        s17.setOnClickListener(v -> {
            if(seats.contains("s17")){
                seats.remove("s17");
                s17.setColorFilter(null);
            }else{
                s17.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s17");
            }
        });
        s18.setOnClickListener(v -> {
            if(seats.contains("s18")){
                seats.remove("s18");
                s18.setColorFilter(null);
            }else{
                s18.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s18");
            }
        });
        s19.setOnClickListener(v -> {
            if(seats.contains("s19")){
                seats.remove("s19");
                s19.setColorFilter(null);
            }else{
                s19.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s19");
            }
        });
        s21.setOnClickListener(v -> {
            if(seats.contains("s21")){
                seats.remove("s21");
                s21.setColorFilter(null);
            }else{
                s21.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s21");
            }
        });
        s22.setOnClickListener(v -> {
            if(seats.contains("s22")){
                seats.remove("s22");
                s22.setColorFilter(null);
            }else{
                s22.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s22");
            }
        });
        s23.setOnClickListener(v -> {
            if(seats.contains("s23")){
                seats.remove("s23");
                s23.setColorFilter(null);
            }else{
                s23.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s23");
            }
        });
        s25.setOnClickListener(v -> {
            if(seats.contains("s25")){
                seats.remove("s25");
                s25.setColorFilter(null);
            }else{
                s25.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s25");
            }
        });
        s26.setOnClickListener(v -> {
            if(seats.contains("s26")){
                seats.remove("s26");
                s26.setColorFilter(null);
            }else{
                s26.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s26");
            }
        });
        s27.setOnClickListener(v -> {
            if(seats.contains("s27")){
                seats.remove("s27");
                s27.setColorFilter(null);
            }else{
                s27.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s27");
            }
        });
        s29.setOnClickListener(v -> {
            if(seats.contains("s29")){
                seats.remove("s29");
                s29.setColorFilter(null);
            }else{
                s29.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s29");
            }
        });
        s30.setOnClickListener(v -> {
            if(seats.contains("s30")){
                seats.remove("s30");
                s30.setColorFilter(null);
            }else{
                s30.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s30");
            }
        });
        s31.setOnClickListener(v -> {
            if(seats.contains("s31")){
                seats.remove("s31");
                s31.setColorFilter(null);
            }else{
                s31.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s31");
            }
        });
        s33.setOnClickListener(v -> {
            if(seats.contains("s33")){
                seats.remove("s33");
                s33.setColorFilter(null);
            }else{
                s33.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s33");
            }
        });
        s34.setOnClickListener(v -> {
            if(seats.contains("s34")){
                seats.remove("s34");
                s34.setColorFilter(null);
            }else{
                s34.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s34");
            }
        });
        s35.setOnClickListener(v -> {
            if(seats.contains("s35")){
                seats.remove("s35");
                s35.setColorFilter(null);
            }else{
                s35.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s35");
            }
        });
        s36.setOnClickListener(v -> {
            if(seats.contains("s36")){
                seats.remove("s36");
                s36.setColorFilter(null);
            }else{
                s36.setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_IN);
                seats.add("s36");
            }
        });


        String dateValue = getIntent().getStringExtra("date");
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String name = getIntent().getStringExtra("name");
        int price = getIntent().getIntExtra("price",0);
        String userId = getIntent().getStringExtra("userId");
        String type = getIntent().getStringExtra("type");
        String time = getIntent().getStringExtra("time");
        String pax = getIntent().getStringExtra("pax");

        Objects.requireNonNull(adults.getEditText()).setText(pax);
        Objects.requireNonNull(children.getEditText()).setText("0");

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
            Intent intent = new Intent(this, PreOrderActivity.class);
            if(TextUtils.isEmpty(date.getEditText().getText().toString())){
                date.setError("Enter date");
            }else if(TextUtils.isEmpty(adults.getEditText().getText().toString())){
                adults.setError("Enter number of adults");
            }else if(seats.isEmpty()){
                Toast.makeText(SelectFlightSeatActivity.this,"Choose seats",Toast.LENGTH_LONG).show();
            }
            else if((Integer.parseInt(adults.getEditText().getText().toString()) + Integer.parseInt(children.getEditText().getText().toString())) != seats.size()){
                Toast.makeText(SelectFlightSeatActivity.this,"The seats selected does not match with the travellers",Toast.LENGTH_LONG).show();
            }
            else{
                StringBuilder allSelectedSeats = new StringBuilder();
                intent.putExtra("date",date.getEditText().getText().toString());
                intent.putExtra("from",from);
                intent.putExtra("to",to);
                intent.putExtra("name",name);
                try{
                    intent.putExtra("price",price * seats.size());
                }catch (Exception e){
                    intent.putExtra("price",price);
                }
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