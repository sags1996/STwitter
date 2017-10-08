package com.example.admin.stwitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Admin on 7/30/2017.
 */

public class ProfilePicAsyncTask extends AsyncTask<Void,Void,String> {
    String url;
   ImageView imageView;
    Context context;
    public ProfilePicAsyncTask(String url, ImageView imageView){
        this.url=url;
        this.imageView=imageView;
    }
    @Override
    protected String doInBackground(Void... params) {
        try {
            String header="";
            URL urlconnection=new URL(url);
            HttpURLConnection connection= (HttpURLConnection)urlconnection.openConnection();
           connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization",header);
            connection.connect();
            InputStream inputStream=connection.getInputStream();
            Scanner s=new Scanner(inputStream);
            String str="";
            while (s.hasNext()){
                str+=s.nextLine();
            }
            Log.i("FetchedString",str);
            JSONObject jsonObject=new JSONObject(str);
            String image=jsonObject.getString("profile_image_url");
            return image;
          //  Picasso.with(context).load(image).into(imageView);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Picasso.with(context).load(s).into(imageView);
    }
}
