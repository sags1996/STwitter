package com.example.admin.stwitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountDetailActivity extends AppCompatActivity {
ImageView profilePic;
    ImageView BanPic;
    TextView Name;
    TextView Desc;
    TextView create;
    TextView sname;
    TextView lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);
        setTitle("Account Details");
        profilePic = (ImageView) findViewById(R.id.PictureImageView);
        BanPic = (ImageView) findViewById(R.id.BanImageView);
        Name = (TextView) findViewById(R.id.Text1);
        Desc = (TextView) findViewById(R.id.Text2);
        create = (TextView) findViewById(R.id.Text3);
        sname = (TextView) findViewById(R.id.Text4);
        lang = (TextView) findViewById(R.id.Text5);

        ApiInterface apiInterface=ApiClient.getApiInterface();
        Call<user> call= apiInterface.getProfilePic(TwitterUtils.getUserId());
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                user u=response.body();
                String nme=u.name;
                String desc=u.location;
                String created=u.created_at;
                String Language=u.lang;
                String Screen=u.screen_name;
                URL ppic= u.profile_image_url_https;
                URL bpic=u.profile_banner_url;
                Picasso.with(AccountDetailActivity.this).load(String.valueOf(ppic)).into(profilePic);
                Picasso.with(AccountDetailActivity.this).load(String.valueOf(bpic)).into(BanPic);
                Name.setText(nme);
                sname.setText("USerName: "+Screen);
                lang.setText("Language: "+Language);
                Desc.setText("Location: "+desc);
                create.setText("Created:"+created);

            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {

            }
        });

    } @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
