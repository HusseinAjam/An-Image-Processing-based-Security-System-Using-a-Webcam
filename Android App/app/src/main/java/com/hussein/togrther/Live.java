package com.hussein.togrther;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Live extends AppCompatActivity {
    private WebView largVeiw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        largVeiw = (WebView) findViewById(R.id.activity_main_webview);
        largVeiw.setVerticalScrollBarEnabled(false);
        largVeiw.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = largVeiw.getSettings();
        webSettings.setJavaScriptEnabled(true);
        largVeiw.loadUrl("http://194.81.104.22/~13432608/images/Final%20Version/setIndexForLive.php?user=" + BackgroundWorker.user_id);

        largVeiw.setWebViewClient(new WebViewClient1());
    }

    public class WebViewClient1 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view1, String url1) {
            view1.loadUrl(url1);
            return true;
        }
    }


}
