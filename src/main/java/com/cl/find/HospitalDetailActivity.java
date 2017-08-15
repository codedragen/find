package com.cl.find;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.StringJoiner;

/**
 * Created by sks on 2017/4/12.
 */

public class HospitalDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private WebView webView;
    private WebSettings webSetting;
    private String mIntentUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospitaldetail);
        mIntentUrl=getIntent().getStringExtra("url");
        toolbar= (Toolbar) findViewById(R.id.hospitaldetail_toolbar);
        progressBar= (ProgressBar) findViewById(R.id.hospitaldetail_progress);
        webView= (WebView) findViewById(R.id.hospitaldetail_web);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
        webSetting=webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
         webView.setWebChromeClient(new WebChromeClient(){
             @Override
             public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);

             }

         });
        webView.setWebViewClient(new WebViewClient(){


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //webView.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });
        if (mIntentUrl != null) {
            webView.loadUrl(mIntentUrl);
        }


    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
