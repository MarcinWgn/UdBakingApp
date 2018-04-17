package com.wegrzyn.marcin.bakingapp.Http;

import com.wegrzyn.marcin.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Marcin Węgrzyn on 17.04.2018.
 * wireamg@gmail.com
 */
public interface HttpOperation {
    @GET(HttpUtils.RECIPES_QUERY)
    Call<List<Recipe>> getRecipes();
}
