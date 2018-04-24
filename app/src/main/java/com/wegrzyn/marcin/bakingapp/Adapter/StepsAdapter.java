package com.wegrzyn.marcin.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wegrzyn.marcin.bakingapp.Model.Step;
import com.wegrzyn.marcin.bakingapp.R;

import java.util.List;

/**
 * Created by Marcin Węgrzyn on 22.04.2018.
 * wireamg@gmail.com
 */
public class StepsAdapter extends  RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private final Context context;
    private List<Step> stepList;
    final private ListItemClickListener itemClickListener;

    public StepsAdapter(Context context, List<Step> stepList, StepsAdapter.ListItemClickListener itemClickListener ) {
        this.context = context;
        this.stepList = stepList;
        this.itemClickListener = itemClickListener;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepsAdapter.StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.step_item,parent,false);
        return new StepsAdapter.StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.StepsViewHolder holder, int position) {
        holder.stepTextView.setText(stepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepList == null) return 0;
        else return stepList.size();
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickItemIndex);
    }
    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView stepTextView;

        StepsViewHolder(View itemView) {
            super(itemView);
            stepTextView = itemView.findViewById(R.id.step_name_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onListItemClick(getAdapterPosition());
        }
    }

}
