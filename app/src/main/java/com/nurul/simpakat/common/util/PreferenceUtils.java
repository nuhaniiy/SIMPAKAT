package com.nurul.simpakat.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {


    private SharedPreferences sharedPreferences;

    public PreferenceUtils(Context context, String name) {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    public SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public void putString(String key, String value) {
        getEditor().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        getEditor().putInt(key, value).apply();
    }

    public void putLong(String key, long value) {
        getEditor().putLong(key, value).apply();
    }

    public void putFloat(String key, float value) {
        getEditor().putFloat(key, value).apply();
    }

    public void putBoolean(String key, boolean value) {
        getEditor().putBoolean(key, value).apply();
    }

    public void remove(String key) {
        getEditor().remove(key).apply();
    }

    public void clear() {
        getEditor().clear().apply();
    }

}
