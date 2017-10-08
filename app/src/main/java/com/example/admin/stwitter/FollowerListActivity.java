 package com.example.admin.stwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class FollowerListActivity extends AppCompatActivity {
ArrayList<FollowClass> followlist;
     CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follower_list);
        setTitle("Followers");
        final ListView FollowListView=(ListView)findViewById(R.id.followersList);
        followlist=new ArrayList<>();
        customAdapter=new CustomAdapter(this,followlist);
        FollowListView.setAdapter(customAdapter);
        ApiInterface apiInterface= ApiClient.getApiInterface();
        Call<Followers> call=apiInterface.getFollowList();
        call.enqueue(new Callback<Followers>() {
            @Override
            public void onResponse(Call<Followers> call, Response<Followers> response) {
                Log.d("Followers", String.valueOf(response.body()));
                Log.d("FollowerError", String.valueOf(response.code()));
               Followers f= response.body();
                ArrayList<Followers.U> arrayList=f.users;
                for(int i=0;i<arrayList.size();i++){
                    FollowClass fc=new FollowClass();
                    fc.Name=arrayList.get(i).name;
                    fc.Picture=arrayList.get(i).profile_image_url_https.toString();
                    followlist.add(fc);
                }
                customAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Followers> call, Throwable t) {

            }
        });

    }

     @Override
     public void onBackPressed() {
         super.onBackPressed();
         finish();
     }
 }
