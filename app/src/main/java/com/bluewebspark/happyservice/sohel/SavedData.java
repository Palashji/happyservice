package com.bluewebspark.happyservice.sohel;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bluewebspark.happyservice.AppController;

public class SavedData {
    private static final String CITY_STATUS = "cityStatus";

    static SharedPreferences prefs;

    public static SharedPreferences getInstance() {
        if (prefs == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(AppController.getInstance());
        }
        return prefs;
    }

    public static boolean getCityStatus() {
        return getInstance().getBoolean(CITY_STATUS, false);
    }

    public static void saveCityStatus(boolean cityStatus) {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.putBoolean(CITY_STATUS, cityStatus);
        editor.apply();
    }

    public static void clear() {
        SharedPreferences.Editor editor = getInstance().edit();
        editor.clear();
        editor.apply();
    }
}