package com.wegrzyn.marcin.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.wegrzyn.marcin.bakingapp.Model.Ingredient;
import com.wegrzyn.marcin.bakingapp.Model.Step;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 22.04.2018.
 * wireamg@gmail.com
 */
public class DetailRecipeFragment extends Fragment {

    private static final String TAG = DetailRecipeFragment.class.getSimpleName();

    public static final String EXO_POSITION = "exo_position";
    public static final String EXO_STATE = "exo_state";

    private Step step;
    private List<Ingredient> ingredients;

    OnButtonClickListener mCallback;

    private SimpleExoPlayerView exoPlayerView;
    private SimpleExoPlayer exoPlayer;

    private Long exoPosition;
    private Boolean state;

    public DetailRecipeFragment() {
    }

    public interface OnButtonClickListener {
        void onButtonClick(View view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnButtonClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            step = getArguments().getParcelable(RecipeStepsActivity.BUNDLE_STEP);
            ingredients = getArguments().getParcelableArrayList(RecipeStepsActivity.BUNDLE_INGREDIENTS);
        }

        if (savedInstanceState != null) {
            exoPosition = savedInstanceState.getLong(EXO_POSITION);
            state = savedInstanceState.getBoolean(EXO_STATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_steps_fragment, container, false);

        TextView descriptionTextView = rootView.findViewById(R.id.detail_tv);
        TextView titleTextView = rootView.findViewById(R.id.detail_tv_label);

        LinearLayout linearLayout = rootView.findViewById(R.id.landscape_ll);

        ImageView thumbnailView = rootView.findViewById(R.id.thumbnailURL_iw);

        exoPlayerView = rootView.findViewById(R.id.simple_exo_player_view);
        setButton(rootView);

        if (step != null) {
            if (!step.getShortDescription().contains(getString(R.string.intro))) {
                descriptionTextView.setText(step.getDescription());
            }
            titleTextView.setText(step.getShortDescription());
        } else if (!ingredients.isEmpty()) {
            showIngredients(descriptionTextView);
            titleTextView.setText(R.string.recipe_ingredients);
        }

        if ( step != null && !TextUtils.isEmpty(step.getVideoURL())) {
            exoPlayerInit(exoPosition);
            if (getResources().getBoolean(R.bool.landscape_mode)
                    && linearLayout != null)
                linearLayout.setVisibility(View.GONE);
        } else exoPlayerView.setVisibility(View.GONE);

        if ( step != null && ! TextUtils.isEmpty(step.getThumbnailURL())){

            Picasso.with(getContext())
//                    .load("http://i.imgur.com/DvpvklR.png")
                    .load(step.getThumbnailURL())
                    .resize(50, 50)
                    .centerCrop()
                    .into(thumbnailView);
        } else {
            thumbnailView.setVisibility(View.GONE);
        }
        return rootView;
    }

    private void showIngredients(TextView ingredientsView) {
        for (Ingredient ing : ingredients) {
            ingredientsView.append(ing.getIngredient()
                    + " " + String.valueOf(ing.getQuantity())
                    + " " + ing.getMeasure() + "\n");
        }
    }

    private void exoPlayerInit(Long position) {
        if (exoPlayer == null) {
            exoPlayerView.setVisibility(View.VISIBLE);
            TrackSelector trackSelector = new DefaultTrackSelector();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            exoPlayerView.setPlayer(exoPlayer);

            String userAgent = Util.getUserAgent(getContext(), getString(R.string.recipe));
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(requireContext(), userAgent))
                    .createMediaSource(Uri.parse(step.getVideoURL()));
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
            if (position != null) exoPlayer.seekTo(position);
            if (state != null) exoPlayer.setPlayWhenReady(state);
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong(EXO_POSITION, exoPlayer.getCurrentPosition());
            outState.putBoolean(EXO_STATE, exoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void setButton(View rootView) {
        if (!getResources().getBoolean(R.bool.tablet)
                && !getResources().getBoolean(R.bool.landscape_mode)) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onButtonClick(v);
                }
            };
            Button nextButton = rootView.findViewById(R.id.next_btn);
            nextButton.setOnClickListener(onClickListener);
            Button prevButton = rootView.findViewById(R.id.prev_btn);
            prevButton.setOnClickListener(onClickListener);
        }
    }
}
