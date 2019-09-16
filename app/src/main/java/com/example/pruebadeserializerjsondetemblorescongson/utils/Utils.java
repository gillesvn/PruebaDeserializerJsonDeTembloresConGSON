package com.example.pruebadeserializerjsondetemblorescongson.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.example.pruebadeserializerjsondetemblorescongson.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static String DAY_MONTHNAME_YEAR = "dd MMM yyyy";
    public static String TWELVE_HOUR_CLOCK = "h:mm a";
    public static String CLOCK_FOR_CONVERTION = "HHmmss";
    public static String TWENTY_FOUR_CLOCK = "HH:mm:ss";

    public static String getDateFormated(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }


    public static int getMagnitudeColor(Context context, double currentMag) {
        int magnitudeColor;
        switch ((int) currentMag) {
            case 0:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude0);
                return magnitudeColor;
            case 1:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude1);
                return magnitudeColor;
            case 2:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude2);
                return magnitudeColor;
            case 3:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude3);
                return magnitudeColor;
            case 4:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude4);
                return magnitudeColor;
            case 5:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude5);
                return magnitudeColor;
            case 6:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude6);
                return magnitudeColor;
            case 7:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude7);
                return magnitudeColor;
            case 8:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude8);
                return magnitudeColor;
            default:
                magnitudeColor = ContextCompat.getColor(context, R.color.magnitude9);
                return magnitudeColor;
        }

    }

    public static View createAlerDialog(Context context, int layout, int title, int message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", listener)
                .setNeutralButton("CANCEL", null);
        View viewInflated = LayoutInflater.from(context).inflate(layout, null);
        builder.setView(viewInflated).show();
        return viewInflated;
    }

    public static String getValueFromSharedPrefs(Context context, SharedPreferences sharedPreferences, int key) {
        return sharedPreferences.getString(context.getString(key), "");
    }

    public static String getMinMagFromSharedPrefs(Context context, SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(context.getString(R.string.key_min_mag), "");
    }

    public static String getMaxResFromSharedPrefs(Context context, SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(context.getString(R.string.key_max_res), context.getString(R.string.def_max_res));
    }

    public static String getSortByFromSharedPrefs(Context context, SharedPreferences sharedPreferences) {
        return sharedPreferences.getString(context.getString(R.string.key_order_by), "");
    }
}
