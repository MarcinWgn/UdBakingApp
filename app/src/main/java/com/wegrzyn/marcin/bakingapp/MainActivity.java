package com.wegrzyn.marcin.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wegrzyn.marcin.bakingapp.Adapter.RecipesAdapter;
import com.wegrzyn.marcin.bakingapp.Http.HttpOperation;
import com.wegrzyn.marcin.bakingapp.Http.HttpUtils;
import com.wegrzyn.marcin.bakingapp.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipesAdapter.ListItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName() ;
    public static final String RECIPE_LIST = "recipe_list";
    public static final String RECIPE_EXTRA = "recipe_extra";

    private ProgressBar progressBar;
    private RecipesAdapter adapter;
    private List<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progress);

        if (savedInstanceState != null ){
            recipeList = savedInstanceState.getParcelableArrayList(RECIPE_LIST);
        }else getRecipes();


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(this, getResources().getInteger(R.integer.column_size));
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new RecipesAdapter(this,recipeList,this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(RECIPE_LIST, (ArrayList<? extends Parcelable>) recipeList);
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
                Toast.makeText(getBaseContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.refresh_item_id){
            getRecipes();
            return true;
        }else return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickItemIndex) {

        Log.d(TAG, String.valueOf(clickItemIndex)+" item cliced");

        Recipe recipe = recipeList.get(clickItemIndex);
        Intent intent = new Intent(this,RecipeStepsActivity.class);
        intent.putExtra(RECIPE_EXTRA,recipe);
        sendWidgetBroadcast(recipe);

        startActivity(intent);
    }

    private void sendWidgetBroadcast (Recipe recipe){
        Intent intentWidget = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        Bundle bundle = new Bundle();
        bundle.putParcelable("bundle_extra",recipe);
        intentWidget.putExtra("recipe_extra",bundle);
        sendBroadcast(intentWidget);
    }

}
