package com.geniusnine.android.marathikavita;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by Dev on 03-03-2017.
 * this is to hold recycler view content
 */

public class ContentHolder extends FrameLayout {
    TextView textViewContent;
    public ContentHolder (Context context){
        super(context);
        inflate(context, R.layout.row_list_content, this);
        textViewContent = (TextView)findViewById(R.id.textViewContent);
    }
    public void bind(String str){
        textViewContent.setText(str);
    }

}
