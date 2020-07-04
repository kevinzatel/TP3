package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context context) {

        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setSessionUsername(String sessionUsername) {
        prefs.edit().putString("sessionUsername", sessionUsername).commit();
    }

    public String getSessionUsername() {
        String sessionUsername = prefs.getString("sessionUsername","");
        return sessionUsername;
    }

}
