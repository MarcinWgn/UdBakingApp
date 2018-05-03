package com.wegrzyn.marcin.bakingapp.Http;

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
}
