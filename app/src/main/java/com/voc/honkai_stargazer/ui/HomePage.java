/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HomeAdapter;

import java.util.ArrayList;

public class HomePage {

    Context context;
    Activity activity;
    View rootView;

    //https://www.jianshu.com/p/fecd87ec655f

    RecyclerView homeRecycleView;
    HomeAdapter homeAdapter;
    SharedPreferences sharedPreferences;

    ArrayList<Integer> homeList = new ArrayList<>();

    public HomePage(Context context, Activity activity, View rootView) {
        this.context = context;
        this.activity = activity;
        this.rootView = rootView;
        sharedPreferences = context.getSharedPreferences("user_info",Context.MODE_PRIVATE);

        homeRecycleView = rootView.findViewById(R.id.homeListView);

        homeList.add(HomeAdapter.TYPE_DAILYMEMO_STAMINA);
        homeList.add(HomeAdapter.TYPE_DAILYMEMO_ASSIGNMENT);
        homeList.add(HomeAdapter.TYPE_DAILYMEMO_ECHO_OF_WAR);
        //homeList.add(HomeAdapter.TYPE_DAILYMEMO_AUTO_CHECK_IN);
        homeList.add(HomeAdapter.TYPE_FAVOURITE);
        //homeList.add(HomeAdapter.TYPE_CALCULATOR);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        homeRecycleView.setLayoutManager(staggeredGridLayoutManager);
        homeAdapter = new HomeAdapter(context,activity, sharedPreferences);
        homeAdapter.filterList(homeList);
        homeRecycleView.setAdapter(homeAdapter);



    }


}
