package com.kerberos.travel.pages;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kerberos.travel.R;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        WebView browse = findViewById(R.id.webview);

        browse.setWebViewClient(new WebViewClient());
        browse.getSettings().setJavaScriptEnabled(true);
        browse.loadUrl("https://www.tripadvisor.com/Restaurants-g298570-Kuala_Lumpur_Wilayah_Persekutuan.html");
    }
}