package com.example.admin.stwitter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.admin.stwitter.R;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;

public class TweetComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_compose);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setTitle("TweetComposer");

        }
        final Button TweetComposer=(Button)findViewById(R.id.tweet_composer);
        TweetComposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new TweetComposer.Builder(TweetComposeActivity.this).text("Tweet!").url(new URL("http://www.twitter.com")).show();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        final Button organicComposer=(Button)findViewById(R.id.organic_composeer);
        organicComposer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPicker();
            }
        });
    }

    private void launchPicker() {
        final Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Pick an Image"),141);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==141 && resultCode== Activity.RESULT_OK){
            launchComposer(data.getData());
        }
    }

    private void launchComposer(Uri uri) {
        final TwitterSession session= TwitterCore.getInstance().getSessionManager().getActiveSession();
        final Intent intent=new ComposerActivity.Builder(TweetComposeActivity.this)
                .session(session).image(uri).text("Tweet").hashtags("#twitter").createIntent();
        startActivity(intent);

    }
}
