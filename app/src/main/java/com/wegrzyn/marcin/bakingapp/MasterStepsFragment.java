package com.wegrzyn.marcin.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wegrzyn.marcin.bakingapp.Adapter.StepsAdapter;
import com.wegrzyn.marcin.bakingapp.Model.Recipe;
import com.wegrzyn.marcin.bakingapp.Model.Step;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 21.04.2018.
 * wireamg@gmail.com
 */
public class MasterStepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private OnStepSelectedListener  mCallback;

    private static final String TAG = MasterStepsFragment.class.getSimpleName() ;

    private List<Step>steps;

    public MasterStepsFragment(){
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnStepSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.master_steps_fragment,container,false);

        TextView ingredientsView = rootView.findViewById(R.id.ingredients_label);
        ingredientsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepSelected(-1);
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.steps_recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(), steps, this);
        recyclerView.setAdapter(stepsAdapter);

        return rootView;
    }
    private void getData() {
        if(getArguments()!=null){
            Recipe recipe = getArguments().getParcelable(RecipeStepsActivity.RECIPE);
            if (recipe != null) steps = recipe.getSteps();
        }
    }

    @Override
    public void onListItemClick(int clickItemIndex) {

        mCallback.onStepSelected(clickItemIndex);
        Log.d(TAG, String.valueOf(clickItemIndex));
    }

    public interface OnStepSelectedListener{
        void onStepSelected(int position);
    }
}
