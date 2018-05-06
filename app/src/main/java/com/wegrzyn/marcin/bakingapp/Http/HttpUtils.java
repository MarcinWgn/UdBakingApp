package com.wegrzyn.marcin.bakingapp.Http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.wegrzyn.marcin.bakingapp.R;

/**
 * Created by Marcin Węgrzyn on 17.04.2018.
 * wireamg@gmail.com
 */
public class HttpUtils {
    private static final String BASE_URL = "http://go.udacity.com/";
    public static final String RECIPES_QUERY = "android-baking-app-json";

    public static HttpOperation getHttpService() {
        return RetofitClient.getRetrofit(BASE_URL).create(HttpOperation.class);
    }

    public static boolean  isInternetConnections(Context context){
        boolean connection;
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        connection  = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!connection) Toast.makeText(context, R.string.no_internet,Toast.LENGTH_LONG).show();
        return connection;
    }
}
