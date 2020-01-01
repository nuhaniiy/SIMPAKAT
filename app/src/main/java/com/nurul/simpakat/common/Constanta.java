package com.nurul.simpakat.common;

import java.text.SimpleDateFormat;

public class Constanta {
    public static final SimpleDateFormat OPEN_TICKET_DATE_FORMAT = new SimpleDateFormat("dd/mm/yy");
    public static final SimpleDateFormat MAIN_LAYOUT_DATE_TIME_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    // API Server
    public static final String APPLICATION_URL = "http://simpakat.yippy.web.id/";
    public static final String APPLICATION_PATH = "api/";

    // DB
    public static final String APPLICATION_SCHEMA = "simpakat_db";

    // App
    public static final String APP_NAME = "Simpakat";
    public static final String PACKAGE_NAME = "com.nurul.simpakat";
    public static final String PLATFORM = "Android";

    // Permission
    public static final int REQUEST_APP_PERMISSIONS = 10000;
    public static final int REQUEST_REGISTRATION = 10001;
    public static final int REQUEST_FORGOT_PASSWORD = 10002;

    // API Result
    public static final String OK = "success";
    public static final String FAIL = "failed";

    // Preference
    public static final String APPLICATION_PREFERENCE = "simpakat_preference";
    public static final String PREF_ID = "id";
    public static final String PREF_EMAIL = "email";
    public static final String PREF_NAME = "name";
    public static final String PREF_FCM_TOKEN = "fcm_token";
    public static final int NOTIFY_SUBMISSION = 1010;

    public static boolean isValidEmailID(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
