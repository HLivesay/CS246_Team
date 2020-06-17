package com.example.cs246;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class PreferencesManager {
    private static SharedPreferences prefs;

    public static void init(Activity act) {
        prefs = act.getSharedPreferences("com.example.cs246", Context.MODE_PRIVATE);
    }

    public static void storeString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return prefs.getString(key, "Null");
    }

}
