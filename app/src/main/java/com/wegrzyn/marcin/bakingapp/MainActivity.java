package com.wegrzyn.marcin.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.wegrzyn.marcin.bakingapp.Http.HttpOperation;
import com.wegrzyn.marcin.bakingapp.Http.HttpUtils;
import com.wegrzyn.marcin.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRecipes();
    }



    private void getRecipes(){
        HttpOperation httpOperation = HttpUtils.getHttpService();
        Call<List<Recipe>> call = httpOperation.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG,"\n"+"onResponse"+"\n");
                if(response.isSuccessful()){
                    for (Recipe recipe : response.body()) {

                        Log.d(TAG, "\n"+"id: "+ recipe.getId());
                        Log.d(TAG, "element: "+ recipe.getName());
                        Log.d(TAG, "image: "+ recipe.getImage());
                        Log.d(TAG, "servings: "+ recipe.getServings());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG," onFailure"+ t.getMessage());
            }
        });
    }
}
