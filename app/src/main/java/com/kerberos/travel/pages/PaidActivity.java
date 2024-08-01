package com.kerberos.travel.pages;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kerberos.travel.MainActivity;
import com.kerberos.travel.R;

public class PaidActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to start the new activity
                Intent intent = new Intent(PaidActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}