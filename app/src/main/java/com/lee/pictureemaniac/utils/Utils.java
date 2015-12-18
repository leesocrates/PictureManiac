package com.lee.pictureemaniac.utils;

import android.util.Log;

/**
 * Created by lee on 2015/12/16.
 */
public class Utils {
    public static void logE(String message) {
        if (Constants.DEBUG) {
            Log.e(Constants.TAG, message);
        }
    }

    public static void logE(String tag, String message) {
        if (Constants.DEBUG) {
            Log.e(tag, message);
        }
    }

    public static void logI(String message) {
        if (Constants.DEBUG) {
            Log.i(Constants.TAG, message);
        }
    }

    public static void logI(String tag, String message) {
        if (Constants.DEBUG) {
            Log.i(tag, message);
        }
    }

}
