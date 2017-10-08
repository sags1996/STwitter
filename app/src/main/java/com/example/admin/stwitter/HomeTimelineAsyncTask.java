package com.example.admin.stwitter;

import android.os.AsyncTask;
import android.util.Log;

import com.twitter.sdk.android.core.OAuthSigning;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Admin on 7/24/2017.
 */

public class HomeTimelineAsyncTask extends AsyncTask<String,Void,Void> {
    /*OnDownloadCompleteListener mListener;

    void setOnDownloadCompleteListener(OnDownloadCompleteListener listener) {
        mListener = listener;
    }*/

    @Override
    protected Void doInBackground(String... params) {
        String urlString = params[0];

        try {
            TwitterAuthConfig authConfig = TwitterCore.getInstance().getAuthConfig();
            TwitterAuthToken authToken = TwitterCore.getInstance().getSessionManager().getActiveSession().getAuthToken();
            OAuthSigning oauth = new OAuthSigning(authConfig, authToken);
            Map<String, String> authHeaders = oauth.getOAuthEchoHeadersForVerifyCredentials();
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            for (Map.Entry<String, String> entry : authHeaders.entrySet()) {
                urlConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();

            Scanner s = new Scanner(inputStream);
            String str = "";
            while (s.hasNext()) {
                str += s.nextLine();
            }
            Log.i("FetchedString ", str);
            //    return parseTimeLine(str);

        } catch (MalformedURLException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
    /*private ArrayList<Home> parseTimeLine(String str) {
        try {
            JSONArray courseArray= new JSONArray(str);
            ArrayList<Home> hometimeline= new ArrayList<>();
            for (int i = 0; i < courseArray.length(); i++){
                JSONObject courseJson=courseArray.getJSONObject(i);
               long id = courseJson.getInt("id");
                Home h= new Home();
                h.id=id;
                hometimeline.add(h);
            }
            return  hometimeline;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Home> home) {
        super.onPostExecute(home);
        if (mListener != null) {
            mListener.onDownloadComplete(home);
        }



    }

}
interface OnDownloadCompleteListener {

    void onDownloadComplete(ArrayList<Home> coursesList);

}*/
