package com.example.triviagame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder> {

   Context context;
   List<Score> scoreList;

    public ScoreAdapter(Context context, List<Score> scoreList) {
        this.context = context;
        this.scoreList = scoreList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ScoreAdapter.ViewHolder holder, int position) {

        if(scoreList != null && scoreList.size() > 0){
            Score model = scoreList.get(position);
            holder.email.setText(model.getPlayerName());
            holder.points.setText(Integer.toString(model.getPoints()));
            holder.date.setText(model.getDate());
        }else {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView email, points, date;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            email = itemView.findViewById(R.id.playerName);
            points = itemView.findViewById(R.id.points);
            date = itemView.findViewById(R.id.date);

        }
    }
}
