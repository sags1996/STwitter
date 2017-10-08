package com.example.admin.stwitter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Admin on 8/3/2017.
 */

public class Helper {
    public static void getListViewSize(ListView listView){
        ListAdapter listAdapter=listView.getAdapter();
        if(listAdapter==null){
            return;
        }
        int totalHeight=0;
        for(int size=0;size<listAdapter.getCount();size++){
            View listItem=listAdapter.getView(size,null,listView);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params= listView.getLayoutParams();
        params.height=totalHeight+(listView.getDividerHeight()*(listAdapter.getCount()-1));
        listView.setLayoutParams(params);
        Log.i("Height", String.valueOf(totalHeight));
    }
}
