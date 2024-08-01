package com.kerberos.travel.pages;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.kerberos.travel.R;
import com.kerberos.travel.fragments.HomeFragment;

public class MapsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        WebView browse = findViewById(R.id.mapView);

        browse.setWebViewClient(new WebViewClient());
        browse.getSettings().setJavaScriptEnabled(true);
        browse.loadUrl("https://maps.google.com");

        ImageView back = findViewById(R.id.imageView35);

        back.setOnClickListener(v ->{
            super.onBackPressed();
        });
    }
}