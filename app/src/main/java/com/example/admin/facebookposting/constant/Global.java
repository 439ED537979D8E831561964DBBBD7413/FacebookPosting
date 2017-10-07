package com.example.admin.facebookposting.constant;

import android.content.SharedPreferences;

import com.example.admin.facebookposting.ApplicationFile;


/**
 * Created by ashish on 6/10/17.
 */

public class Global {

    public static void storePreference(String key, String value) {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void storePreference(String key, int value) {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void removePreference(String key) {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removePreferences(String keys[]) {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        for (String key : keys) {
            editor.remove(key);
        }
        editor.commit();
    }

    public static void clearPreferences() {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        editor.clear();
        editor.commit();
    }

    public static void storePreference(String key, Boolean value) {
        SharedPreferences.Editor editor = ApplicationFile.sharedPref
                .edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getPreference(String key, Boolean defValue) {
        return ApplicationFile.sharedPref.getBoolean(key, defValue);
    }


    public static String getPreference(String key, String defValue) {
        return ApplicationFile.sharedPref.getString(key, defValue);
    }

    public static int getPreference(String key, Integer defValue) {
        return ApplicationFile.sharedPref.getInt(key, defValue);
    }
}
