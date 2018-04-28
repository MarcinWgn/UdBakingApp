package com.wegrzyn.marcin.bakingapp;

import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.wegrzyn.marcin.bakingapp.Model.Recipe;
import com.wegrzyn.marcin.bakingapp.Model.Step;

import java.util.ArrayList;

public class RecipeStepsActivity extends AppCompatActivity
        implements MasterStepsFragment.OnStepSelectedListener, DetailRecipeFragment.OnButtonClickListener{

    public static final String POSITION = "position";
    private static final String TAG =RecipeStepsActivity.class.getSimpleName() ;

    public static final String RECIPE = "recipe";
    public static final String BUNDLE_STEP = "bundle step";
    public static final String BUNDLE_INGREDIENTS = "bundle ingredients";
    private boolean mTabletMode;
    private Recipe recipe;

    private int curPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps);

        if(savedInstanceState!= null){
            curPosition = savedInstanceState.getInt(POSITION);
        }
        getData();
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(recipe.getName());
            if(curPosition>-1
                    && !getResources().getBoolean(R.bool.tablet)
                    &&getResources().getBoolean(R.bool.landscape_mode)
                    &&!recipe.getSteps().get(curPosition).getVideoURL().isEmpty())
            getSupportActionBar().hide();
        }

        Bundle recipeBundle = new Bundle();
        recipeBundle.putParcelable(RECIPE,recipe);
        Bundle stepStart = new Bundle();
        stepStart.putParcelable(BUNDLE_STEP,recipe.getSteps().get(0));

        if(findViewById(R.id.tablet_layout)!= null){
            mTabletMode = true;

            if(savedInstanceState==null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                DetailRecipeFragment detailFragment = new DetailRecipeFragment();
                detailFragment.setArguments(stepStart);
                MasterStepsFragment  masterStepsFragment = new MasterStepsFragment();
                masterStepsFragment.setArguments(recipeBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.detail_container,detailFragment)
                        .add(R.id.master_container,masterStepsFragment)
                        .commit();
            }

        }else {
            mTabletMode = false;

            if(savedInstanceState==null){
                FragmentManager fragmentManager = getSupportFragmentManager();
                MasterStepsFragment masterStepsFragment = new MasterStepsFragment();
                masterStepsFragment.setArguments(recipeBundle);
                fragmentManager.beginTransaction()
                        .add(R.id.master_container,masterStepsFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onStepSelected(int position) {
        curPosition = position;
        replaceFragment(position);
    }

    private void replaceFragment(int position) {

        Bundle bundle = new Bundle();
        if(position==-1){
            bundle.putParcelableArrayList(BUNDLE_INGREDIENTS, (ArrayList<? extends Parcelable>) recipe.getIngredients());
        }else{
            bundle.putParcelable(BUNDLE_STEP,recipe.getSteps().get(position));
        }


        if(!mTabletMode){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            transaction.replace(R.id.master_container,detailRecipeFragment)
                    .commit();
        }else {

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            DetailRecipeFragment detailRecipeFragment = new DetailRecipeFragment();
            detailRecipeFragment.setArguments(bundle);
            transaction.replace(R.id.detail_container,detailRecipeFragment)
                    .commit();
        }
    }

    private void getData() {
        if(getIntent().hasExtra(MainActivity.RECIPE_EXTRA)){
            recipe = getIntent().getParcelableExtra(MainActivity.RECIPE_EXTRA);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, curPosition);
    }

    /**
     *  Callback from DetailRecipeFragment
     * @param view
     */
    @Override
    public void onButtonClick(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.next_btn:
                if(curPosition+1<recipe.getSteps().size()){
                   curPosition ++;
                }
                replaceFragment(curPosition);
                Log.d(TAG, "next button");
                break;
            case R.id.prev_btn:
                if(curPosition > -1 ){
                    curPosition--;
                }
                replaceFragment(curPosition);
                Log.d(TAG, "prev button");
                break;
        }
    }
}
