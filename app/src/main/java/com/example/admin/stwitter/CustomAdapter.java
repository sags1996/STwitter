package com.example.admin.stwitter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.ArrayList;

/**
 * Created by Admin on 7/21/2017.
 */

public class CustomAdapter extends ArrayAdapter<FollowClass> {
ArrayList<FollowClass> followlist;
    Context context;
    public CustomAdapter(@NonNull Context context, ArrayList<FollowClass> followlist) {
        super(context,0, followlist);
        this.context=context;
        this.followlist=followlist;
    }
    static class FollowViewHolder{
        TextView followview;
        ImageView followpic;
        FollowViewHolder(TextView followview,ImageView followpic){
            this.followview=followview;
            this.followpic=followpic;
        }
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.follower_list_item,null);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.FollowerName);
            ImageView PicImageView = (ImageView) convertView.findViewById(R.id.FollowerImage);

            FollowViewHolder followViewHolder = new FollowViewHolder(nameTextView,PicImageView);
            convertView.setTag(followViewHolder);

        }

        final int pos = position;
        FollowClass e = followlist.get(position);
        FollowViewHolder followViewHolder = (FollowViewHolder)convertView.getTag();
        followViewHolder.followview.setText("@"+e.Name);
        Picasso.with(context).load(e.Picture).into(followViewHolder.followpic);

        return  convertView;
    }
}