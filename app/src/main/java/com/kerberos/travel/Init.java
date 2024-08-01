package com.kerberos.travel;

import android.app.Application;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.kerberos.travel.pages.MemoryActivity;

public class Init extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Intent intent = new Intent(getApplicationContext(), MemoryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }
}
