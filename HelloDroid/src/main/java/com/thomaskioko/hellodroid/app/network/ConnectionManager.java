package com.thomaskioko.hellodroid.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author: kioko
 **/


public class ConnectionManager {

    private Context mContext;


    public ConnectionManager(Context context) {
        this.mContext = context;
    }

    /**
     * Checking for all possible internet providers
     * *
     */
    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * @param context Application context
     * @return network state
     */
    public static boolean isConnectedMobile(Context context) {
        NetworkInfo info = ConnectionManager.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}
