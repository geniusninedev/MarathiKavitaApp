package com.geniusnine.android.marathikavita;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class NewsFeeds extends AppCompatActivity {

    public NewsFeedsAdapter newsFeedsAdapter;
    private ListView listViewNews;
    private ArrayList<String> headlines;
    private ArrayList<String> links;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feeds);
        doSomething();
        newsFeedsAdapter = new NewsFeedsAdapter(this, R.layout.row_news_feeds);
        listViewNews = (ListView)findViewById(R.id.listViewNewsFeeds);
        listViewNews.setAdapter(newsFeedsAdapter);
        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = links.get(position);
                Intent news = new Intent(NewsFeeds.this, WebViewNews.class);
                news.putExtra("url", item );
                startActivity(news);
                Toast.makeText(NewsFeeds.this, item, Toast.LENGTH_SHORT).show();
            }
        });



    }


    private AsyncTask<Void, Void, Void> doSomething(){
        headlines = new ArrayList<>();
        links = new ArrayList<>();

        Log.e("doSomething", "Parsing");

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try{
                    /////////////////////////////////////////////////
                    try {
                        URL url = new URL(getString(R.string.news_url));
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        factory.setNamespaceAware(false);
                        final XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(getInputStream(url), "UTF_8");
                        boolean insideItem = false;
                        int eventType = xpp.getEventType();
                        while(eventType!= XmlPullParser.END_DOCUMENT){

                            if (eventType == XmlPullParser.START_TAG) {
                                if (xpp.getName().equalsIgnoreCase("item")) {
                                    insideItem = true;
                                } else if (xpp.getName().equalsIgnoreCase("title")) {
                                    if (insideItem){
                                        try {
                                            final String headline=(xpp.nextText()); ////////////
                                            Log.e("Headlines ", headline);
                                            headlines.add(headline);
                                            try{
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        newsFeedsAdapter.add(headline);

                                                    }
                                                });
                                            }catch (Exception e){
                                                Log.e("TAG2 ", e.toString());
                                            }


                                        }catch(Exception e){
                                            Log.e("ERROR_PARSER", e.toString());
                                        }



                                    }
                                    //extract the headline
                                } else if (xpp.getName().equalsIgnoreCase("link")) {
                                    if (insideItem){
                                        try {
                                            final String link=(xpp.nextText()); ////////////
                                            Log.e("Link ", link);
                                            links.add(link);
                                        }catch(Exception e){
                                            Log.e("ERROR_PARSER", e.toString());
                                        }
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                /////////////

                                            }
                                        });
                                        //extract the link of article
                                    }

                                }
                            }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                                insideItem=false;
                            }
                            eventType = xpp.next();
                        }



                    }catch (Exception e){
                        Log.e("ERROR_PARSER", e.toString());
                    }
                    ////////////////////////////////////////////////////


                }catch (Exception e){
                    Log.e("getFeedsOnline ", e.toString());
                }
                return null;
            }
        };
        return runAsyncTask(task);
    }
    private AsyncTask<Void, Void, Void> runAsyncTask(AsyncTask<Void, Void, Void> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.e("doSomething.. ", e.toString());
            return null;
        }
    }

}
