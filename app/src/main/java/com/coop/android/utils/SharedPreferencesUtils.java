package com.coop.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MR-Z on 2018/12/11.
 */
public class SharedPreferencesUtils {

    public static final String FIRSTRUN = "FIRSTRUN";

    public static boolean getFirstRun(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FIRSTRUN, Context.MODE_PRIVATE);
        return sp.getBoolean(FIRSTRUN, true);
    }

    public static void setFirstRun(Context context, boolean values) {
        SharedPreferences sp = context.getSharedPreferences(FIRSTRUN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(FIRSTRUN, values);
        editor.apply();
    }
}
