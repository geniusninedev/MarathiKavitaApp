package com.geniusnine.android.marathikavita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
//This is to connect app category data to Main activity list view

public class CategoryAdapter extends ArrayAdapter<MarathiKavitaCategory> {//Change the parameter
    Context context;

    int layoutResourceId;
    public List<MarathiKavitaCategory> mCategory; //Change the parameter
    public CategoryAdapter(Context context, int resource){
        super(context, resource);
        this.context = context;
        layoutResourceId = resource;

    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View row = convertView;
        final MarathiKavitaCategory currentItem = getItem(position); //Change the parameter
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_list_category, parent, false);

        }
        row.setTag(currentItem);

        final TextView textViewCategory = (TextView)row.findViewById(R.id.textViewCategory);
        textViewCategory.setText(currentItem.getCategory());

        final ImageView imageViewCategory = (ImageView) row.findViewById(R.id.imageViewCategory);
        Picasso.with(context).load(currentItem.getImagelink()).transform(new CircleTransform()).into(imageViewCategory);


        return row;

    }
}
