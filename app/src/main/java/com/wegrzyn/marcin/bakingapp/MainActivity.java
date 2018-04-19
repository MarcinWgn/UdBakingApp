package com.wegrzyn.marcin.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.wegrzyn.marcin.bakingapp.Adapter.RecipesAdapter;
import com.wegrzyn.marcin.bakingapp.Http.HttpOperation;
import com.wegrzyn.marcin.bakingapp.Http.HttpUtils;
import com.wegrzyn.marcin.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() ;

    private ProgressBar progressBar;
    private RecipesAdapter adapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress);


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecipesAdapter(this,recipeList,this);
        recyclerView.setAdapter(adapter);

        getRecipes();
    }


    private void getRecipes(){

        progressBar.setVisibility(View.VISIBLE);

        HttpOperation httpOperation = HttpUtils.getHttpService();
        Call<List<Recipe>> call = httpOperation.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d(TAG,"\n"+"onResponse"+"\n");
                if(response.isSuccessful()){
                    recipeList = response.body();
                    adapter.setRecipeList(recipeList);
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d(TAG," onFailure: "+ t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onListItemClick(int clickItemIndex) {

        Log.d(TAG, String.valueOf(clickItemIndex)+" item cliced");
    }
}
