package com.example.admin.stwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DMViewActivity extends AppCompatActivity {
    ArrayList<String> messages;
    ListView listView;
    String idMessage;
    ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmview);
        Intent intent=getIntent();
        idMessage=intent.getStringExtra("idM");
        messages= new ArrayList<>();
        listView=(ListView)findViewById(R.id.DmViewList);
        Button SendButton=(Button)findViewById(R.id.SendMessageButton);
        listAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messages);
       listView.setAdapter(listAdapter);
        final ApiInterface apiInterface=ApiClient.getApiInterface();
        Call<DirectMessagesID> call=apiInterface.getDMessages();
        call.enqueue(new Callback<DirectMessagesID>() {
            @Override
            public void onResponse(Call<DirectMessagesID> call, Response<DirectMessagesID> response) {
                Log.d("ErrorMe", String.valueOf(response.body()));
                DirectMessagesID d= response.body();
                ArrayList<DirectMessagesID.EEvents> arrayList=d.events;

                for(int i=arrayList.size()-1; i>=0;i--){
                    Log.d("Asixe",arrayList.get(i).message_create.message_data.text);
                    if(arrayList.get(i).id.equals(idMessage))
                    messages.add(arrayList.get(i).message_create.message_data.text);
                    else{
                        messages.add("You: "+arrayList.get(i).message_create.message_data.text);
                    }
                }


                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<DirectMessagesID> call, Throwable t) {

            }
        });
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiInterface apiInterface1=ApiClient.getApiInterface();
                Call<DMPost> call= apiInterface.postMessage("892070359910019074","Hi ");
                call.enqueue(new Callback<DMPost>() {
                    @Override
                    public void onResponse(Call<DMPost> call, Response<DMPost> response) {
                        Log.d("errorMe", String.valueOf(response.body()));
                        DMPost p= response.body();
                        messages.add(p.e.m.d.text);
                        listAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DMPost> call, Throwable t) {

                    }
                });

            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

