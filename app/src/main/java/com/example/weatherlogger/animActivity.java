package com.example.weatherlogger;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class animActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_test);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AnimationAdapter animationAdapter = new AnimationAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Context context = recyclerView.getContext();
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_left);
        recyclerView.setAdapter(animationAdapter);
//        recyclerView.setLayoutAnimation(animationController);
//        recyclerView.getAdapter().notifyDataSetChanged();
//        recyclerView.scheduleLayoutAnimation();
    }
}