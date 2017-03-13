package com.geniusnine.android.marathikavita;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.clockbyte.admobadapter.expressads.AdmobExpressRecyclerAdapterWrapper;
import com.clockbyte.admobadapter.expressads.NativeExpressAdViewHolder;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.NativeExpressAdView;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.query.Query;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOperations;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncContext;
import com.microsoft.windowsazure.mobileservices.table.sync.MobileServiceSyncTable;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.ColumnDataType;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.MobileServiceLocalStoreException;
import com.microsoft.windowsazure.mobileservices.table.sync.localstore.SQLiteLocalStore;
import com.microsoft.windowsazure.mobileservices.table.sync.synchandler.SimpleSyncHandler;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Content extends AppCompatActivity {

    private MobileServiceClient mobileServiceClient;
    private MobileServiceSyncTable<MarathiKavitaContent> mobileServiceSyncTable;//Change the parameter
    private ContentAdapter contentAdapter;
    private RecyclerView recyclerViewContent;


    private ArrayList<MarathiKavitaContent> items = new ArrayList<>(); //Change the parameter
    AdmobExpressRecyclerAdapterWrapper adapterWrapper;
    Timer updateAdsTimer;

    //Setting up progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        MobileAds.initialize(getApplicationContext(), getString(R.string.test_admob_app_id));
        setupAdsWithRecyclerView();
        fetchDataFromAzure();
        initUpdateAdsTimer();
    }

    private void fetchDataFromAzure() {
        Intent intent = getIntent();
        String cat = intent.getStringExtra("category");


        try {
            // Create the Mobile Service Client instance, using the provided

            // Mobile Service URL and key
            mobileServiceClient = new MobileServiceClient(getString(R.string.web_address), this);

            // Extend timeout from default of 10s to 20s
            mobileServiceClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });


            mobileServiceSyncTable = mobileServiceClient.getSyncTable(getString(R.string.content_table_name), MarathiKavitaContent.class);//Change the parameter
            initLocalStore().get();


            showAll(cat);


        } catch (MalformedURLException e) {

        } catch (Exception e) {

        }
    }


    private AsyncTask<Void, Void, Void> initLocalStore() throws MobileServiceLocalStoreException, ExecutionException, InterruptedException {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    MobileServiceSyncContext syncContext = mobileServiceClient.getSyncContext();

                    if (syncContext.isInitialized())
                        return null;

                    SQLiteLocalStore localStore = new SQLiteLocalStore(mobileServiceClient.getContext(), "OfflineStore", null, 1);

                    Map<String, ColumnDataType> tableDefinition = new HashMap<String, ColumnDataType>();
                    tableDefinition.put("id", ColumnDataType.String);
                    tableDefinition.put("category", ColumnDataType.String);
                    tableDefinition.put("content", ColumnDataType.String);

                    localStore.defineTable(getString(R.string.content_table_name), tableDefinition);

                    SimpleSyncHandler handler = new SimpleSyncHandler();

                    syncContext.initialize(localStore, handler).get();

                } catch (final Exception e) {
                    //createAndShowDialogFromTask(e, "Error");
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


    public void showAll(String cat) {

        progressDialog = new ProgressDialog(Content.this);
        progressDialog.setMessage("Syncing online data. You may turn off internet/click anywhere to avoid this.");
        progressDialog.show();final String categoryId = cat;
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

            @Override

            protected Void doInBackground(Void... params) {
                try {

                    sync().get();
                    Query query = QueryOperations.field("category").eq(categoryId).orderBy("id", QueryOrder.Descending);
                    final List<MarathiKavitaContent> results = mobileServiceSyncTable.read(query).get();//Change the parameter

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            items.clear();
                            for (MarathiKavitaContent item : results) {//Change the parameter
                                items.add(item);
                                progressDialog.dismiss();
                            }
                            recyclerViewContent.setAdapter(adapterWrapper);


                        }
                    });

                } catch (Exception exception) {
                    //createAndShowDialog(exception, "Error");
                }
                return null;
            }
        };
        //runAsyncTask(task);
        task.execute();
    }

    private AsyncTask<Void, Void, Void> sync() {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    MobileServiceSyncContext syncContext = mobileServiceClient.getSyncContext();
                    syncContext.push().get();
                    mobileServiceSyncTable.pull(null).get();
                } catch (final Exception e) {
                    //createAndShowDialogFromTask(e, "Error");
                }
                return null;
            }
        };
        return runAsyncTask(task);
    }

    private void setupAdsWithRecyclerView() {
        recyclerViewContent = (RecyclerView) findViewById(R.id.recyclerViewContent);
        recyclerViewContent.setLayoutManager(new LinearLayoutManager(this));
        contentAdapter = new ContentAdapter(this, items);
        adapterWrapper = new AdmobExpressRecyclerAdapterWrapper(this, getString(R.string.test_admob_express_unit_id)){

            @Override
            protected ViewGroup wrapAdView(NativeExpressAdViewHolder adViewHolder, ViewGroup parent, int viewType) {

                //get ad view
                NativeExpressAdView adView = adViewHolder.getAdView();

                RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                        RecyclerView.LayoutParams.WRAP_CONTENT);
                CardView cardView = new CardView(Content.this);
                cardView.setLayoutParams(lp);

                TextView textView = new TextView(Content.this);
                textView.setLayoutParams(lp);
                textView.setText("Ad is loading...");
                textView.setTextColor(Color.RED);

                cardView.addView(textView);
                //wrapping
                cardView.addView(adView);
                //return wrapper view
                return cardView;
            }
        };
        adapterWrapper.setAdapter(contentAdapter);
        adapterWrapper.setLimitOfAds(100);
        adapterWrapper.setNoOfDataBetweenAds(5);
        adapterWrapper.setFirstAdIndex(3);

    }

    private void initUpdateAdsTimer() {
        updateAdsTimer = new Timer();
        updateAdsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //adapterWrapper.requestUpdateAd();
                    }
                });
            }
        }, 60 * 1000, 60 * 1000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapterWrapper.release();
    }
}
