package com.example.admin.stwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

public class ShowListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_list_activity);
        Intent intent=getIntent();
        String Query= intent.getStringExtra("listId");
        ListView listView=(ListView)findViewById(R.id.ListList);
        final SearchTimeline searchTimeline=new SearchTimeline.Builder().query(Query).maxItemsPerRequest(50).build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(this,searchTimeline);
        listView.setAdapter(adapter);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
