package com.geniusnine.android.marathikavita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewNews extends AppCompatActivity {

    private WebView webViewNewsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_news);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webViewNewsContent = (WebView)findViewById(R.id.webViewNewsContent);
        WebSettings webSettings = webViewNewsContent.getSettings();
        webSettings.setJavaScriptEnabled(false);
        /*webViewNewsContent.getSettings().setJavaScriptEnabled(true);
        webViewNewsContent.getSettings().setGeolocationEnabled(true);
        webViewNewsContent.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewNewsContent.getSettings().setBuiltInZoomControls(true);
        webViewNewsContent.getSettings().setDomStorageEnabled(true);
        webViewNewsContent.setWebChromeClient(new WebChromeClient());*/
        webViewNewsContent.loadUrl(url);
    }
}
