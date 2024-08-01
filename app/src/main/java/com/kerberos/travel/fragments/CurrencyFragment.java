package com.kerberos.travel.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.kerberos.travel.R;

public class CurrencyFragment extends Fragment {

    public CurrencyFragment() {
        // Required empty public constructor
    }
    public static CurrencyFragment newInstance(String param1, String param2) {
        return new CurrencyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currency, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView back = view.findViewById(R.id.imageView14);
        WebView browse = view.findViewById(R.id.webview);

        browse.setWebViewClient(new WebViewClient());
        browse.getSettings().setJavaScriptEnabled(true);
        browse.loadUrl("https://www.visa.co.ke/support/consumer/travel-support/exchange-rate-calculator.html");
        back.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentHolder, new HomeFragment()).commit();
        });
    }
}