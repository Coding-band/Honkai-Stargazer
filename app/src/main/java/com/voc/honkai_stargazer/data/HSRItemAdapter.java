/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.data;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.ui.InfoCharacterPage;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.RoundedCornersTransformation;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

public class HSRItemAdapter extends RecyclerView.Adapter<HSRItemAdapter.ViewHolder>{

    private AdapterView.OnItemClickListener mListener;
    private ArrayList<HSRItem> hsritemList;

    private ItemRSS item_rss ;
    private Context context;
    private Activity activity;
    private SharedPreferences sharedPreferences;

    private String TYPE = ItemRSS.TYPE_CHARACTER;

    public HSRItemAdapter(Context context, Activity activity, SharedPreferences sharedPreferences, String TYPE) {
        this.context = context;
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public HSRItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list_item, parent, false);
        return new ViewHolder(v, (AdapterView.OnItemClickListener) mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HSRItemAdapter.ViewHolder holder, int position) {
        HSRItem hsrItem = hsritemList.get(position);
        item_rss = new ItemRSS();

        int radius = 80;
        int margin = 0;
        Transformation transformation = new RoundedCornersTransformation(radius, margin);

        holder.item_element.setVisibility(View.GONE);
        holder.item_relic_ll.setVisibility(View.GONE);
        holder.item_sub_ll.setVisibility(View.GONE);
        holder.item_normal_ll.setVisibility(View.VISIBLE);

        int ico_rss = R.drawable.ico_lost_img;
        if (TYPE.equals(ItemRSS.TYPE_CHARACTER)){
            ico_rss = item_rss.getCharByName(hsrItem.getName())[0];
            holder.item_element.setVisibility(View.VISIBLE);
            holder.item_element.setImageResource(item_rss.getIconByElement(hsrItem.getElement()));

            holder.item_rare.setNumStars(hsrItem.getRare());
            holder.item_rare.setRating(hsrItem.getRare());

            holder.item_path_ico.setImageResource(item_rss.getIconByPath(hsrItem.getPath()));
            holder.item_path_tv.setText(hsrItem.getPath());

        }else if (TYPE.equals(ItemRSS.TYPE_LIGHTCONE)){
            ico_rss = item_rss.getLightconeByName(hsrItem.getName())[0];

            holder.item_rare.setNumStars(hsrItem.getRare());
            holder.item_rare.setRating(hsrItem.getRare());

            holder.item_path_ico.setImageResource(item_rss.getIconByPath(hsrItem.getPath()));
            holder.item_path_tv.setText(hsrItem.getPath());

        }else if (TYPE.equals(ItemRSS.TYPE_RELIC)){
            holder.item_relic_ll.setVisibility(View.VISIBLE);
            holder.item_normal_ll.setVisibility(View.GONE);
            holder.item_rare.setVisibility(View.GONE);
            holder.item_sub_ll.setVisibility(View.VISIBLE);
            holder.item_sub_item2.setVisibility(View.GONE);
            holder.item_sub_item3.setVisibility(View.GONE);
            holder.item_2pc_status.setText(item_rss.getRelicStatusByName(hsrItem.getName(), context, ItemRSS.LANG_EN)[0], TextView.BufferType.SPANNABLE);
            holder.item_4pc_status.setText(item_rss.getRelicStatusByName(hsrItem.getName(), context, ItemRSS.LANG_EN)[1], TextView.BufferType.SPANNABLE);
            ico_rss = item_rss.getRelicByName(hsrItem.getName())[0];
        }

        //https://honkai-star-rail.fandom.com/wiki/Relic/Sets

        Picasso.get()
                .load (ico_rss)
                .transform(transformation)
                .into (holder.item_ico);

        if (TYPE.equals(ItemRSS.TYPE_RELIC)) {
            Picasso.get()
                    .load(item_rss.getRelicByName(hsrItem.getName())[1])
                    .into(holder.item_sub_item1);

            if (item_rss.getRelicByName(hsrItem.getName()).length > 2){
                holder.item_sub_item2.setVisibility(View.VISIBLE);
                holder.item_sub_item3.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(item_rss.getRelicByName(hsrItem.getName())[2])
                        .into(holder.item_sub_item2);
                Picasso.get()
                        .load(item_rss.getRelicByName(hsrItem.getName())[3])
                        .into(holder.item_sub_item3);
            }
        }

        holder.item_name.setText(hsrItem.getName());
        holder.item_name.getPaint().setFakeBoldText(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (TYPE){
                    case ItemRSS.TYPE_CHARACTER: new InfoCharacterPage().setup(context, activity,hsrItem); return;
                    case ItemRSS.TYPE_LIGHTCONE: return;
                    case ItemRSS.TYPE_RELIC: return;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hsritemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_ico, item_element, item_path_ico;
        public ImageView item_sub_item1, item_sub_item2, item_sub_item3;
        public TextView item_name, item_hp_tv, item_def_tv, item_atk_tv, item_speed_tv, item_path_tv;
        public TextView item_2pc_status, item_4pc_status;
        public LinearLayout item_hp_ll, item_def_ll, item_atk_ll, item_speed_ll, item_sub_ll, item_relic_ll, item_normal_ll;
        public ScaleRatingBar item_rare;

        public ViewHolder(View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);

            item_ico = itemView.findViewById(R.id.item_ico);
            item_element = itemView.findViewById(R.id.item_element);
            item_name = itemView.findViewById(R.id.item_name);
            item_rare = itemView.findViewById(R.id.item_rare);
            item_path_tv = itemView.findViewById(R.id.item_path_tv);
            item_path_ico = itemView.findViewById(R.id.item_path_ico);

            item_hp_tv = itemView.findViewById(R.id.item_hp_tv);
            item_def_tv = itemView.findViewById(R.id.item_def_tv);
            item_atk_tv = itemView.findViewById(R.id.item_atk_tv);
            item_speed_tv = itemView.findViewById(R.id.item_speed_tv);
            item_hp_ll = itemView.findViewById(R.id.item_hp_ll);
            item_def_ll = itemView.findViewById(R.id.item_def_ll);
            item_atk_ll = itemView.findViewById(R.id.item_atk_ll);
            item_speed_ll = itemView.findViewById(R.id.item_speed_ll);
            item_sub_ll = itemView.findViewById(R.id.item_sub_ll);
            item_relic_ll = itemView.findViewById(R.id.item_relic_ll);
            item_normal_ll = itemView.findViewById(R.id.item_normal_ll);

            item_sub_item1 = itemView.findViewById(R.id.item_sub_item1);
            item_sub_item2 = itemView.findViewById(R.id.item_sub_item2);
            item_sub_item3 = itemView.findViewById(R.id.item_sub_item3);

            item_2pc_status = itemView.findViewById(R.id.item_2pc_status);
            item_4pc_status = itemView.findViewById(R.id.item_4pc_status);

        }
    }


    public void filterList(ArrayList<HSRItem> filteredList) {
        hsritemList = filteredList;
        notifyDataSetChanged();
    }

    public void filterRequestList(ArrayList<HSRItem> preList, FilterPreference preference) {
        ArrayList<HSRItem> filteredList = new ArrayList<>();
        if (!preference.isAll()){
            for (HSRItem hsrItem : preList){
                boolean isTrueA = false;
                boolean isTrueB = false;
                boolean isTrueC = false;
                boolean isTrueD = false;


                if (!preference.isElementNotRequest()){
                    if (preference.isFire() && hsrItem.getElement().equals(ItemRSS.ELEMENT_FIRE)){isTrueA = true;}
                    else if (preference.isImaginary() && hsrItem.getElement().equals(ItemRSS.ELEMENT_IMAGINARY)){isTrueA = true;}
                    else if (preference.isIce() && hsrItem.getElement().equals(ItemRSS.ELEMENT_ICE)){isTrueA = true;}
                    else if (preference.isLightning() && hsrItem.getElement().equals(ItemRSS.ELEMENT_LIGHTNING)){isTrueA = true;}
                    else if (preference.isPhysical() && hsrItem.getElement().equals(ItemRSS.ELEMENT_PHYSICAL)){isTrueA = true;}
                    else if (preference.isQuantum() && hsrItem.getElement().equals(ItemRSS.ELEMENT_QUANTUM)){isTrueA = true;}
                    else if (preference.isWind() && hsrItem.getElement().equals(ItemRSS.ELEMENT_WIND)){isTrueA = true;}
                }

                if (!preference.isPathNotRequest()){
                    if (preference.isAbundance() && hsrItem.getPath().equals(ItemRSS.PATH_ABSTRUCTION)){isTrueB = true;}
                    else if (preference.isDestruction() && hsrItem.getPath().equals(ItemRSS.PATH_DESTRUCTION)){isTrueB = true;}
                    else if (preference.isErudition() && hsrItem.getPath().equals(ItemRSS.PATH_ERUDITION)){isTrueB = true;}
                    else if (preference.isHarmony() && hsrItem.getPath().equals(ItemRSS.PATH_HARMONY)){isTrueB = true;}
                    else if (preference.isHunt() && hsrItem.getPath().equals(ItemRSS.PATH_HUNT)){isTrueB = true;}
                    else if (preference.isNihility() && hsrItem.getPath().equals(ItemRSS.PATH_NIHILITY)){isTrueB = true;}
                    else if (preference.isPreservation() && hsrItem.getPath().equals(ItemRSS.PATH_PRESERVATION)){isTrueB = true;}
                }

                if (!preference.isRareNotRequest()){
                    if (preference.isRare3() && hsrItem.getRare() == 3 && (preference.getType().equals(ItemRSS.TYPE_LIGHTCONE))){isTrueC = true;}
                    else if (preference.isRare4() && hsrItem.getRare() == 4){isTrueC = true;}
                    else if (preference.isRare5() && hsrItem.getRare() == 5){isTrueC = true;}
                }
                if (!preference.isStatusNotRequest() ){
                    if (preference.isRelease() && hsrItem.getStatus().equals(ItemRSS.STATUS_RELEASED)){isTrueD = true;}
                    else if (preference.isSoon() && hsrItem.getStatus().equals(ItemRSS.STATUS_SOON)){isTrueD = true;}
                    else if (preference.isBeta() && hsrItem.getStatus().equals(ItemRSS.STATUS_BETA)){isTrueD = true;}
                }

                if ((!preference.isElementNotRequest() ? isTrueA : true) && (!preference.isPathNotRequest() ? isTrueB : true) && (!preference.isRareNotRequest() ? isTrueC : true) && (!preference.isStatusNotRequest() ? isTrueD : true)){
                    filteredList.add(hsrItem);
                }
            }
            hsritemList = filteredList;
            notifyDataSetChanged();
        }
    }
}
