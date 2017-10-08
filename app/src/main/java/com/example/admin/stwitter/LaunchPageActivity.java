package com.example.admin.stwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

public class LaunchPageActivity extends AppCompatActivity {
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_page);

       /* TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;*/
      handler.postDelayed(new Runnable() {
          @Override
          public void run() {

              doStuff();

          }
      },1500);
        /*if(token!=null && secret!=null){
            Intent intent=new Intent(LaunchPageActivity.this,MainActivity.class);
            startActivity(intent);
        }*/





    }

    private void doStuff() {
/*SharedPreferences sharedPreferences=getSharedPreferences("MyPref",MODE_PRIVATE);
        String token=sharedPreferences.getString(LoginStartActivity.TOKEN,null);
        if(token!=null){
            String TokenSecret=sharedPreferences.getString(LoginStartActivity.TOKENSECRET,null);
            String UserName=sharedPreferences.getString(LoginStartActivity.USERNAME,null);
            long userId=sharedPreferences.getLong(LoginStartActivity.USERID,-1);
            TwitterAuthToken authToken=new TwitterAuthToken(token,TokenSecret);
            TwitterSession session=new TwitterSession(authToken,userId,UserName);
            TwitterCore.getInstance().getSessionManager().setActiveSession(session);
            startActivity(new Intent(LaunchPageActivity.this,MainActivity.class));

        }*/
      //  else{
        if(TwitterUtils.getSession()!=null){
            startActivity(new Intent(LaunchPageActivity.this,MainActivity.class));
        }
        else{
            startActivity(new Intent(LaunchPageActivity.this,LoginStartActivity.class));}
       // }
    }
}
