package com.kerberos.travel.pages;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.kerberos.travel.R;
import com.kerberos.travel.fragments.HomeFragment;

public class TranslateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        WebView browse = findViewById(R.id.webview);

        browse.setWebViewClient(new WebViewClient());
        browse.getSettings().setJavaScriptEnabled(true);
        browse.loadUrl("https://translate.google.com/?sl=auto&tl=en&op=translate&hl=en");

        ImageView back = findViewById(R.id.imageView36);

        back.setOnClickListener(v ->{
            super.onBackPressed();
        });
    }
}