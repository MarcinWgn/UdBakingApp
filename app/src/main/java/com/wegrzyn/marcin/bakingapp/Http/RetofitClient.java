package com.wegrzyn.marcin.bakingapp.Http;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marcin Węgrzyn on 17.04.2018.
 * wireamg@gmail.com
 */
class RetofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(String bUrl){
        if (retrofit==null){
            retrofit = new Retrofit.Builder().baseUrl(bUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
