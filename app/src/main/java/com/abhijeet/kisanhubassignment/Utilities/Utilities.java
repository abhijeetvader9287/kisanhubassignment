package com.abhijeet.kisanhubassignment.Utilities;
import android.content.Context;
import android.net.ConnectivityManager;
/**
 * The type Utilities.
 */
public class Utilities {
    /**
     * Is network connected boolean.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
