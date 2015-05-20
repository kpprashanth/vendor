package com.example.android.navigationdrawerexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by prashanth on 26/4/15.
 */
public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename,String password) {
        prefs.edit().putString("usename", usename).commit();
        prefs.edit().putString("password", password).commit();
        
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }

    public String getpassword() {
        String password = prefs.getString("password","");
        return password;
    }
}
