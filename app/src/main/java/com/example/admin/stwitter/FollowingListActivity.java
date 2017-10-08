package com.example.admin.stwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingListActivity extends AppCompatActivity {
CustomAdapter customAdapter;
    ArrayList<FollowClass>followClassArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);
        setTitle("Following");
        final ListView listView=(ListView)findViewById(R.id.ListFollowing);
        followClassArrayList=new ArrayList<>();
        customAdapter= new CustomAdapter(this,followClassArrayList);
        listView.setAdapter(customAdapter);
        final ArrayList<String>ids=new ArrayList<>();
        final ApiInterface apiInterface= ApiClient.getApiInterface();
        Call<Followers> call=apiInterface.getFollowerList();
        call.enqueue(new Callback<Followers>() {
            @Override
            public void onResponse(Call<Followers> call, Response<Followers> response) {
                Followers f= response.body();
                  ArrayList<Followers.U> arrayList=f.users;

               // Log.d("users",""+f.users.size());
                for(int i=0;i<arrayList.size();i++){
                    FollowClass fc=new FollowClass();
                  fc.Name=arrayList.get(i).name;
                    fc.Picture=arrayList.get(i).profile_image_url_https.toString();
                    followClassArrayList.add(fc);
                    ids.add(arrayList.get(i).id_str);
                }
                customAdapter.notifyDataSetChanged();
                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                        ApiInterface apiInterface1=ApiClient.getApiInterface();
                        Call<UserUnfollow>call1=apiInterface1.Unfollow(Long.valueOf(ids.get(position)));
                        call1.enqueue(new Callback<UserUnfollow>() {
                            @Override
                            public void onResponse(Call<UserUnfollow> call, Response<UserUnfollow> response) {
                                UserUnfollow u=response.body();
                                if(response.code()==200){
                                    followClassArrayList.remove(position);
                                    customAdapter.notifyDataSetChanged();
                                    Toast.makeText(FollowingListActivity.this,"Unfollowed: "+u.name,Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserUnfollow> call, Throwable t) {

                            }
                        });

                        return true;
                    }
                });


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

