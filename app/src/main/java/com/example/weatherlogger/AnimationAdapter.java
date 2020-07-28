package com.example.weatherlogger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AnimationAdapter extends RecyclerView.Adapter<AnimationAdapter.AnimationViewHolder> {
List<Integer> dataSource;
Context context;
public AnimationAdapter(Context context){
    dataSource=new ArrayList<>();
    for(int i=1;i<=5;i++)
        dataSource.add(i);
    this.context=context;
}
    @NonNull
    @Override
    public AnimationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item,parent,false);
        return new AnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimationViewHolder holder, int position) {
        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context,R.anim.fade_scale_animation));
        holder.textVie.setText(String.valueOf(dataSource.get(position)));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public static class AnimationViewHolder extends RecyclerView.ViewHolder{
        TextView textVie;
        CardView cardView;
        public AnimationViewHolder(@NonNull View itemView) {
            super(itemView);
            textVie=itemView.findViewById(R.id.textview);
            cardView=itemView.findViewById(R.id.card_item);
        }
    }
}
