package com.geniusnine.android.marathikavita;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.clockbyte.admobadapter.ViewWrapper;

import java.util.ArrayList;

/**
 * Created by AndriodDev8 on 20-02-2017.
 * this is adapter to connect data to content activity
 */

public class ContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MarathiKavitaContent> items = new ArrayList<>(); //Change the parameter


    private Context context;

    public ContentAdapter(Context context, ArrayList<MarathiKavitaContent> items) {//Change the parameter
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewWrapper<ContentHolder> onCreateViewHolder(ViewGroup parent, int viewType){
        return new ViewWrapper<ContentHolder>(new ContentHolder(context));
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
        ContentHolder contentHolder = (ContentHolder)viewHolder.itemView;
        MarathiKavitaContent item = items.get(position);//Change the parameter
        contentHolder.bind(item.getContent());
    }


    @Override
    public int getItemCount(){
        return  items.size();
    }


}
