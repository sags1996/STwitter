package com.example.admin.stwitter;

import android.content.Intent;
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

public class DirectMessageListActivity extends AppCompatActivity {
ListView messageList ;
    ArrayList<FollowClass> messages;
    CustomAdapter customAdapter;
ArrayList<Long> idMessageList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_message_list);
        setTitle("Messages");
        idMessageList=new ArrayList<>();
        messages=new ArrayList<>();
        messageList=(ListView)findViewById(R.id.MessageListUser);
        customAdapter=new CustomAdapter(this,messages);
        messageList.setAdapter(customAdapter);
        ApiInterface apiInterface= ApiClient.getApiInterface();
        Call<Followers> call=apiInterface.getFollowList();
        call.enqueue(new Callback<Followers>() {
            @Override
            public void onResponse(Call<Followers> call, Response<Followers> response) {
                Followers f = response.body();
                FollowClass fc = new FollowClass();
                for (int i = 0; i < f.users.size(); i++) {
                    fc.Name = f.users.get(i).name;
                    fc.Picture = f.users.get(i).profile_image_url_https.toString();
                  idMessageList.add(Long.valueOf(f.users.get(i).id_str));
                    messages.add(fc);
                }
                customAdapter.notifyDataSetChanged();
                messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent= new Intent(DirectMessageListActivity.this,DMViewActivity.class);
                        intent.putExtra("idM", "892071641907417091");
                     //   intent.putExtra("idM",idMessageList.get(position));
                        startActivity(intent);
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
