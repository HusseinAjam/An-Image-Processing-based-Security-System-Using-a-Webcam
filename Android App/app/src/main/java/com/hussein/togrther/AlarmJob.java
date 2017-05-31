package com.hussein.togrther;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AlarmJob extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    AlertDialog progress;

    AlarmJob(Context ctx) {
        context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://194.81.104.22/~13432608/images/Final%20Version/Alarm.php";
        if(type.equals("alarm")) {
            try {
                String user_choice = params[1];

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_choice","UTF-8")+"="+URLEncoder.encode(user_choice,"UTF-8")+"&"
                        +URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(BackgroundWorker.user_id+"","UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Connect Database");
        progress = new AlertDialog.Builder(context).create();
        progress.setTitle("Connect Database");
        //show dialog
        progress.setMessage("Conecting...");
        progress.show();
    }

    @Override
    protected void onPostExecute(String result) {
        if(progress != null && progress.isShowing()){
            progress.dismiss();
        }
        if (result.equals("OK"))
        {
            alertDialog.dismiss();
        }
        else {
            alertDialog.setMessage(result);
            alertDialog.show();
        }

    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}