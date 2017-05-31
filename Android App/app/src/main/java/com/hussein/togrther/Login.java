package com.hussein.togrther;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Login extends Activity implements View.OnClickListener {

    private String username;
    private String password;
    private Button log;
    private EditText editUsername;
    private EditText editPassword;
    private CheckBox saveCheckBox;
    private SharedPreferences loginPref;
    private SharedPreferences.Editor loginPrefEditor;
    private Boolean saveLog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log = (Button) findViewById(R.id.butLogin);
        log.setOnClickListener(this);
        loginPref = getSharedPreferences("loginPref", MODE_PRIVATE);
        loginPrefEditor = loginPref.edit();
        editUsername = (EditText) findViewById(R.id.userEB);
        editPassword = (EditText) findViewById(R.id.passEB);
        saveCheckBox = (CheckBox) findViewById(R.id.saveCB);


        saveLog = loginPref.getBoolean("saveLog", false);

        if (saveLog == true) {
            editUsername.setText(loginPref.getString("username", ""));
            editPassword.setText(loginPref.getString("password", ""));
            saveCheckBox.setChecked(true);
        }
    }

    public void onClick(View view) {
        if (view == log) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editUsername.getWindowToken(), 0);

            username = editUsername.getText().toString();
            password = editPassword.getText().toString();

            if (saveCheckBox.isChecked()) {
                loginPrefEditor.putBoolean("saveLog", true);
                loginPrefEditor.putString("username", username);
                loginPrefEditor.putString("password", password);
                loginPrefEditor.commit();
                setpref("user", username, this); // this is specific for the services
                setpref("pass", password , this);// this is specific for the services
            } else {
                loginPrefEditor.clear();
                loginPrefEditor.commit();
            }
            loginwork();
        }
    }
    public static void setpref(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void loginwork() {

        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }
}