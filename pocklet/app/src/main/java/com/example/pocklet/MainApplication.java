package com.example.pocklet;
import android.app.Application;

import com.plaid.link.Plaid;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Plaid.create(this);
    }
}


