package com.wegrzyn.marcin.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wegrzyn.marcin.bakingapp.Model.Recipe;
import com.wegrzyn.marcin.bakingapp.R;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 19.04.2018.
 * wireamg@gmail.com
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder> {

    private final Context context;
    private List<Recipe> recipeList;
    final private ListItemClickListener itemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipeList, ListItemClickListener itemClickListener ) {
        this.context = context;
        this.recipeList = recipeList;
        this.itemClickListener = itemClickListener;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recipe_card_item,parent,false);

        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        holder.recipeNameTextView.setText(recipeList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (recipeList == null) return 0;
        else return recipeList.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickItemIndex);
    }
    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView recipeNameTextView;

        RecipesViewHolder(View itemView) {
            super(itemView);

            recipeNameTextView = itemView.findViewById(R.id.recipe_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onListItemClick(getAdapterPosition());
        }
    }
}
