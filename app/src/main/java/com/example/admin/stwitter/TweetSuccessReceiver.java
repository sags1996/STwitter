package com.example.admin.stwitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetUploadService;

public class TweetSuccessReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras=intent.getExtras();
        if(intentExtras!=null){
            Long TweetId=intentExtras.getLong(TweetUploadService.EXTRA_TWEET_ID);
            Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
        }
    }
}
