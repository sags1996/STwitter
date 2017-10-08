package com.example.admin.stwitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrendListActivity extends AppCompatActivity {
ArrayList<String> trendlist;
    ArrayList<String> trendQueryList;
    ArrayAdapter<String>arrayAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_list);
        setTitle("Trends");
        listView=(ListView)findViewById(R.id.TrendList);
        trendlist=new ArrayList<>();
        trendQueryList=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,trendlist);
        listView.setAdapter(arrayAdapter);
        final ApiInterface apiInterface=ApiClient.getApiInterface();
        Call call = apiInterface.getTrends(23424848l);
        call.enqueue(new retrofit2.Callback<ArrayList<Trends>>() {
            @Override
            public void onResponse(Call<ArrayList<Trends>> call, Response<ArrayList<Trends>> response) {

                ArrayList<Trends> t=response.body();
                Trends tr=t.get(0);
                ArrayList<Trends.trendsi> arrayList=tr.trends;
                for(int i=0;i<arrayList.size();i++){
                   // Trends.trendsi e=new Trends.trendsi();
                    trendlist.add(arrayList.get(i).name);
                    trendQueryList.add(arrayList.get(i).query);
                }
                arrayAdapter.notifyDataSetChanged();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent=new Intent(TrendListActivity.this,TrendViewActivity.class);
                        intent.putExtra("Query",trendQueryList.get(position));
                        startActivity(intent);

                    }

                });

            }

            @Override
            public void onFailure(Call<ArrayList<Trends>> call, Throwable t) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
