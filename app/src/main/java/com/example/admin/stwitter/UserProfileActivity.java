package com.example.admin.stwitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.mopub.nativeads.MoPubAdAdapter;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.mopub.TwitterMoPubAdAdapter;
import com.twitter.sdk.android.mopub.TwitterStaticNativeAdRenderer;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.net.URL;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        setTitle("");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         final ImageView profilepicImage=(ImageView)findViewById(R.id.ProfilePictureImageView);
         final ImageView bannerImage=(ImageView)findViewById(R.id.BannerImageView);
        final TextView followerView=(TextView)findViewById(R.id.FollowersTextView);
        final TextView followingView=(TextView)findViewById(R.id.FollowingTextView);
        final TextView DescText=(TextView)findViewById(R.id.DescText);

        /*TwitterAuthConfig authConfig= TwitterCore.getInstance().getAuthConfig();
        TwitterAuthToken authToken=TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();
        OAuthSigning authSigning= new OAuthSigning(authConfig,authToken);
        HashMap<String,String> params=new HashMap<>();
        params.put("screen_name","twitterdev");
        String header=authSigning.getAuthorizationHeader("GET","https://api.twitter.com/1.1/users/show.json",params);
        Log.i("FetchedHeader",header);
       String profile_img_url;

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://api.twitter.com/1.1/").addConverterFactory(GsonConverterFactory.create()).build();*/
        ApiInterface apiInterface= ApiClient.getApiInterface();
        Call<user> call= apiInterface.getProfilePic(TwitterUtils.getUserId());
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                Log.d("userPic", String.valueOf(response.body()));
              user u=response.body();
                URL url= u.profile_image_url_https;
                String name= u.name;
                int followers=u.followers_count;
                int following=u.friends_count;
                String disc=u.description;

             URL urlBanner=u.profile_banner_url;
                Picasso.with(UserProfileActivity.this).load(String.valueOf(url)).into(profilepicImage);
                setTitle(name);
                followerView.setText("Followers:"+followers);
                followingView.setText("Following:"+following);
                DescText.setText("BIO:"+disc);
                Picasso.with(UserProfileActivity.this).load(String.valueOf(urlBanner)).into(bannerImage);

            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {

            }
        });
       Call<ProfileBanner> c= apiInterface.getProfileBanner();
        c.enqueue(new Callback<ProfileBanner>() {
            @Override
            public void onResponse(Call<ProfileBanner> call, Response<ProfileBanner> response) {
                Log.d("errorb", String.valueOf(response.code()));
                Log.d("ProfileBanner", String.valueOf(response.body()));
                //ProfileBanner p=response.body();
              //  URL purl=p.sizes.mobile.url;
             //  Picasso.with(UserProfileActivity.this).load(String.valueOf(purl)).into(bannerImage);
            }

            @Override
            public void onFailure(Call<ProfileBanner> call, Throwable t) {

            }
        });
        ListView listView=(ListView)findViewById(R.id.listUser);
        final UserTimeline userTimeline= new UserTimeline.Builder().screenName("Sagarika_1996").build();
        final TweetTimelineListAdapter adapter= new TweetTimelineListAdapter.Builder(this).setTimeline(userTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle).build();
        final MoPubAdAdapter moPubAdAdapter=new TwitterMoPubAdAdapter(UserProfileActivity.this,adapter);
        final TwitterStaticNativeAdRenderer adRenderer= new TwitterStaticNativeAdRenderer();
        moPubAdAdapter.registerAdRenderer(adRenderer);
        moPubAdAdapter.loadAds(BuildConfig.APPLICATION_ID);
        listView.setAdapter(moPubAdAdapter);
     //   Helper.getListViewSize(listView);
        followerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UserProfileActivity.this,FollowerListActivity.class);
                startActivity(intent);

            }
        });
        followingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(UserProfileActivity.this,FollowingListActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
