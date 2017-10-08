package com.example.admin.stwitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class TweetFailureReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle IntentExtras=intent.getExtras();
        if(IntentExtras!=null){
            final Intent retryIntent=IntentExtras.getParcelable(TweetUploadService.EXTRA_RETRY_INTENT);
            Log.e("TweetFailureReciver",retryIntent.toString());
        }
    }
}
