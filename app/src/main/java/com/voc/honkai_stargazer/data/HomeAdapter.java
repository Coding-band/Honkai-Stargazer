/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.data;

import static com.voc.honkai_stargazer.util.ItemRSS.LoadAssestData;
import static com.voc.honkai_stargazer.util.ItemRSS.TYPE_LIGHTCONE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.ui.DevPage;
import com.voc.honkai_stargazer.ui.InfoCharacterPage;
import com.voc.honkai_stargazer.ui.InfoLightconePage;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.LangUtil;
import com.voc.honkai_stargazer.util.LogExport;
import com.voc.honkai_stargazer.util.RoundedCornersTransformation;
import com.voc.honkai_stargazer.util.ThemeUtil;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{


    private AdapterView.OnItemClickListener mListener;
    private ArrayList<Integer> itemList;
    private int maxSizeOfList = -1;
    private boolean isForSelect = false;

    private ItemRSS item_rss ;
    private Context context;
    private Activity activity;
    private SharedPreferences sharedPreferences;

    public static final int TYPE_DAILYMEMO_STAMINA = 1000;
    public static final int TYPE_DAILYMEMO_ASSIGNMENT = 1001;
    public static final int TYPE_DAILYMEMO_ECHO_OF_WAR = 1002;
    public static final int TYPE_DAILYMEMO_AUTO_CHECK_IN = 1003;
    public static final int TYPE_FAVOURITE = 2000;
    public static final int TYPE_CALCULATOR = 3000;


    public static final String TAG = "HomeAdapter";
    ThemeUtil themeUtil;

    public HomeAdapter(Context context, Activity activity, SharedPreferences sharedPreferences) {
        this.context = context;
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;

        themeUtil = new ThemeUtil(context, activity);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_stamina, parent, false);

        switch (viewType){
            case TYPE_DAILYMEMO_STAMINA: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_stamina, parent, false); break;
            case TYPE_DAILYMEMO_ASSIGNMENT: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_assignments, parent, false); break;
            case TYPE_DAILYMEMO_ECHO_OF_WAR: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_echo_of_war, parent, false); break;
            //case TYPE_DAILYMEMO_AUTO_CHECK_IN: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_echo_of_war, parent, false); break;
            case TYPE_FAVOURITE: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_favourite, parent, false); break;
            //case TYPE_CALCULATOR: v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_dailymemo_echo_of_war, parent, false); break;
        }

        return new ViewHolder(v, (AdapterView.OnItemClickListener) mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case TYPE_DAILYMEMO_STAMINA:{
                // ...
                System.out.println("HI");
                break;
            }
        }
        themeUtil.themeTint(holder.itemView.findViewById(R.id.rootView));
    }

    private void setAnimation(View viewToAnimate){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    public void filterList(ArrayList<Integer> filteredList) {
        itemList = filteredList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int pos){
        return itemList.get(pos);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);


        }
    }
}
