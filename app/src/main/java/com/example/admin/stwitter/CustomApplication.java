package com.example.admin.stwitter;

import android.app.Application;
import android.os.Bundle;

import com.twitter.sdk.android.core.Twitter;

/**
 * Created by Admin on 7/21/2017.
 */

public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Twitter.initialize(this);
    }
}