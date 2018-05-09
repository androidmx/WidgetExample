package com.widgetexample.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.widgetexample.widget.collection.CollectionApiWidgetProvider;

import java.lang.reflect.Type;

/**
 * Created by ajea on 10/10/16.
 */
public class GeneralUtils {

    /**
     * Obtain JsonString from object
     *
     * @param data Object to serialize
     * @return json string from Object
     * **/
    public static <T> String serialize(T data) {
        Gson mGson = new Gson();
        String json;

        try {
            json = mGson.toJson(data);
        } catch (JsonSyntaxException e) {
            json = null;
        }

        return json;
    }

    /**
     * Obtain Object from jsonString
     *
     * @param json String to convert in Object
     * @param typeClass Object class to convert
     *
     * @return Object from json string
     * **/
    public static <T> T deserialize(String json, Class<T> typeClass) {
        Gson mGson = new Gson();
        T data;

        try {
            data = mGson.fromJson(json, typeClass);
        } catch (JsonSyntaxException e) {
            data = null;
        }

        return data;
    }

    /**
     * Obtain Object from jsonString
     *
     * @param json String to convert in Object
     * @param typeOfT Object Type to convert
     *
     * @return Object from json string
     * **/
    public static <T> T deserialize(String json, Type typeOfT) {
        Gson mGson = new Gson();
        T data;

        try {
            data = mGson.fromJson(json, typeOfT);
        } catch (JsonSyntaxException e) {
            data = null;
        }

        return data;
    }


    /**
     * Obtain string error from array string errors
     *
     * @param errors Array string errors
     * @return String from errors
     * **/
    public static String getStringError(String[] errors){
        String errorString = null;
        for (String currentError : errors){
            if (errorString == null){
                errorString = currentError;
            } else {
                errorString += "\n"+ currentError;
            }
        }
        return errorString;
    }

    /**
     * Validate if is support lollipop level
     *
     * @return true if support lollipop level
     * **/
    public static boolean isSupportLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? true : false;
    }

    /**
     * Convert dps to pxs
     * @param dp dps to cpnvert
     *
     * @return number of pxs
     * **/
    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Convert pxs to dps
     * @param px pxs to cpnvert
     *
     * @return number of dps
     * **/
    public static int pxToDp(int px){
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * Gets screen sizes
     */
    public static int getScreenWidthDP(Activity activity){
        try {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            return Math.round(metrics.widthPixels / metrics.density);
        }catch (Exception e){
            return -1;
        }
    }

    /**
     * Gets screen sizes
     */
    public static int getScreenHeightDP(Activity activity){
        try {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            return Math.round(metrics.heightPixels / metrics.density);
        }catch(Exception e){
            return -1;
        }

    }

    /**
     * Obtain number factorial from number
     *
     * @param N number to obtain factorial
     * @return factorial from number
     * **/
    public static long factorialNumber(int N) {
        long mFactorial = 1;
        for (int i = 1; i <= N; i++) {
            mFactorial *= i;
        }
        return mFactorial;
    }

    /**
     * Validate if device has internet connection
     *
     * @param mContext aplication context
     * @return true if device has conection
     * **/
    public static boolean isConnected(Context mContext) {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Validate if device has internet connection
     *
     * @return true if device has conection
     * **/
    public static boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) CollectionApiWidgetProvider.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
