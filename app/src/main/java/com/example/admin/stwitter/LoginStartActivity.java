package com.example.admin.stwitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Logger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

public class LoginStartActivity extends AppCompatActivity {
      TwitterLoginButton loginButton;
    ImageView profilepic;
    TextView  UserName;
    Button VerifyButton;
    String Name;
    public final static String USERNAME="UserName";
    public final static String USERID="UserId";
    public final static String TOKEN="Token";
    public final static String TOKENSECRET="TokenSecret";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_start);
        this.setTitle("MyTwitter Login");

        profilepic=(ImageView)findViewById(R.id.ImageProfile);
        UserName=(TextView)findViewById(R.id.USerNAmeDetail);
        VerifyButton=(Button)findViewById(R.id.VerifyButton);
        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session=result.data;
                TwitterAuthToken authToken=session.getAuthToken();
                Name=result.data.getUserName();
                SharedPreferences sharedPreferences= getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putLong(USERID,session.getUserId());
                editor.putString(USERNAME,Name);
                editor.putString(TOKEN,authToken.token);
                editor.putString(TOKENSECRET,authToken.secret);
                editor.commit();
                Intent intent=new Intent(LoginStartActivity.this,MainActivity.class);
                startActivity(intent);
              /*  TwitterSession session=result.data;
                Name=session.getUserName();
                TwitterApiClient twitterApiClient=TwitterCore.getInstance().getApiClient();
                final retrofit2.Call<user> user=twitterApiClient.getAccountService().verifyCredentials(true,false,false);
                user.enqueue(new Callback<user>() {
                    @Override
                    public void success(Result<user> result) {
                        user userinfo=result.data;
                        String profileImageUrl=userinfo.profileImageUrl.replace("normal","");
                        Picasso.with(getApplicationContext()).load(profileImageUrl).into(profilepic);
                        UserName.setText(Name);
                        loginButton.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });*/





            }

            @Override
            public void failure(TwitterException exception) {
             exception.printStackTrace();
            }
        });




    }
  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

}
