package com.hussein.togrther;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Options extends AppCompatActivity {
    private WebView smallVeiw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        smallVeiw = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = smallVeiw.getSettings();
        webSettings.setJavaScriptEnabled(true);
        smallVeiw.loadUrl("http://194.81.104.22/~13432608/images/Final Version/setIndexforGif.php?user=" + BackgroundWorker.user_id);
        smallVeiw.setWebViewClient(new WebViewClient2());

        Switch switch1 = (Switch)findViewById(R.id.switch2);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_LONG).show();
                    String type = "alarm";
                    String parameter = "1";
                    AlarmJob alarmJob = new AlarmJob(Options.this);
                    alarmJob.execute(type,parameter);
                } else {

                    //  int user = BackgroundWorker.user_id; // get the user_id from previous class
                    // Toast.makeText(getApplicationContext(),user+"" , Toast.LENGTH_LONG).show();

                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_LONG).show();
                    String type = "alarm";
                    String parameter = "0";
                    AlarmJob alarmJob = new AlarmJob(Options.this);
                    alarmJob.execute(type, parameter);
                }
            }
        });

        Button btn = (Button)findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Options.this, Live.class));
            }
        });

    }
    public class WebViewClient2 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view1, String url1) {
            view1.loadUrl(url1);
            return true;
        }
    }

}
