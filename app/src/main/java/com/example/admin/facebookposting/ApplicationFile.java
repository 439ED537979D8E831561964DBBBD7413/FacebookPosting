package com.example.admin.facebookposting;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

/**
 * Created by ashish on 6/10/17.
 */

public class ApplicationFile extends Application {

    public static SharedPreferences sharedPref;
    public static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        gson = new Gson();
    }
}
