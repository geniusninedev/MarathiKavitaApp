package com.geniusnine.android.marathikavita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by AndriodDev8 on 10-03-2017.
 */

public class NewsFeedsAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;



    public NewsFeedsAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        this.context = context;
        this.layoutResourceId = layoutResourceId;


    }
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final String currentItem = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_news_feeds, parent, false);

        }
        row.setTag(currentItem);

        final TextView textViewCategory = (TextView)row.findViewById(R.id.textViewNews);

        textViewCategory.setText(currentItem);



        return row;

    }
}
