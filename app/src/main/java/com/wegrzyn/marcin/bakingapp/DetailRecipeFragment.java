package com.wegrzyn.marcin.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.wegrzyn.marcin.bakingapp.Model.Step;

/**
 * Created by Marcin Węgrzyn on 22.04.2018.
 * wireamg@gmail.com
 */
public class DetailRecipeFragment extends Fragment {

    private static final String TAG = DetailRecipeFragment.class.getSimpleName() ;
    private Step step;

    OnButtonClickListener mCallback;

    Button nextButton;
    Button prevButton;



    public DetailRecipeFragment() {
    }

    public interface OnButtonClickListener {
        public void onButtonClick(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnButtonClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
           step = getArguments().getParcelable(RecipeStepsActivity.BUNDLE_STEP);
           if(!step.getVideoURL().isEmpty()){
               Toast.makeText(getActivity(),step.getVideoURL(),Toast.LENGTH_SHORT).show();
           }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_steps_fragment,container,false);
        TextView textView = rootView.findViewById(R.id.detail_tv);
        setButton(rootView);
        if(step != null) textView.setText(step.getDescription());
        return rootView;
    }

    private void setButton(View rootView) {
        if(!getResources().getBoolean(R.bool.tablet)){
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onButtonClick(v);
                }
            };
            nextButton = rootView.findViewById(R.id.next_btn);
            nextButton.setOnClickListener(onClickListener);
            prevButton = rootView.findViewById(R.id.prev_btn);
            prevButton.setOnClickListener(onClickListener);



        }
    }
}
