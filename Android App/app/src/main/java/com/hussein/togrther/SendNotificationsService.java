package com.hussein.togrther;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

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
import java.util.Timer;
import java.util.TimerTask;

public class SendNotificationsService extends Service
{
    private static Timer timer = new Timer();
    private Context myCtx;

     public IBinder onBind(Intent arg0)
    {
        return null;
    }


    public void onCreate()
    {
        super.onCreate();
        myCtx = this;
        startService();
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 6000);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {

             String userName = getDefaults("user", myCtx);
             String passWord = getDefaults("pass", myCtx);

             String login_url = "http://194.81.104.22/~13432608/images/Final%20Version/notify.php";
            // login_url += loginPref.getString("username", "")+"&userpass="+loginPref.getString("password", "");

            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"
                        +URLEncoder.encode("userpass","UTF-8")+"="+URLEncoder.encode(passWord,"UTF-8");
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


                if (result.equals("Yes")) {
                    Intent intent = new Intent(SendNotificationsService.this, Login.class);
                    PendingIntent pIntent = PendingIntent.getActivity(SendNotificationsService.this, 0, intent, 0);

                    Notification noti = new Notification.Builder(SendNotificationsService.this)
                            .setTicker("Ticker Title")
                            .setContentTitle("Important!!!")
                            .setContentText("Noticed some movements in your place")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pIntent).getNotification();
                    noti.flags = Notification.FLAG_AUTO_CANCEL;
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(0, noti);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        public  String getDefaults(String key, Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, null);
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
    }
}