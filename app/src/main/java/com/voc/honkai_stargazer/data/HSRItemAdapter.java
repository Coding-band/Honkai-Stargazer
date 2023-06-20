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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.dev.CharAdviceSuggester;
import com.voc.honkai_stargazer.ui.DevPage;
import com.voc.honkai_stargazer.ui.InfoCharacterPage;
import com.voc.honkai_stargazer.ui.InfoLightconePage;
import com.voc.honkai_stargazer.util.ItemRSS;
import com.voc.honkai_stargazer.util.LangUtil;
import com.voc.honkai_stargazer.util.LogExport;
import com.voc.honkai_stargazer.util.RoundedCornersTransformation;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class HSRItemAdapter extends RecyclerView.Adapter<HSRItemAdapter.ViewHolder>{


    public static final String DEFAULT = "ONE_IN_ROW";
    public static final String ONE_IN_ROW = "ONE_IN_ROW";
    public static final String THREE_IN_ROW = "THREE_IN_ROW";
    private AdapterView.OnItemClickListener mListener;
    private ArrayList<HSRItem> hsritemList;
    private ArrayList<HSRItem> hsritemSelectedList;
    private int maxSizeOfList = -1;
    private boolean isForSelect = false;

    private ItemRSS item_rss ;
    private Context context;
    private Activity activity;
    private SharedPreferences sharedPreferences;

    private String TYPE = ItemRSS.TYPE_CHARACTER;
    private int lastPosition = -1;

    public static final String TAG = "HSRItemAdapter";

    public HSRItemAdapter(Context context, Activity activity, SharedPreferences sharedPreferences, String TYPE, boolean isForSelect) {
        this.context = context;
        this.activity = activity;
        this.sharedPreferences = sharedPreferences;
        this.TYPE = TYPE;
        this.isForSelect = isForSelect;
    }

    @NonNull
    @Override
    public HSRItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT)){
            case HSRItemAdapter.THREE_IN_ROW:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list_item_row3, parent, false);
                return new ViewHolder(v, (AdapterView.OnItemClickListener) mListener);
            }
            case HSRItemAdapter.ONE_IN_ROW:{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list_item_row1, parent, false);
                return new ViewHolder(v, (AdapterView.OnItemClickListener) mListener);
            }
            default: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_list_item_row1, parent, false);
                return new ViewHolder(v, (AdapterView.OnItemClickListener) mListener);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull HSRItemAdapter.ViewHolder holder, int position) {
        HSRItem hsrItem = hsritemList.get(position);
        setAnimation(holder.itemView);
        item_rss = new ItemRSS();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int radius = (int) (15*displayMetrics.density);
        int margin = 0;
        Transformation transformation = new RoundedCornersTransformation(radius, margin);

        holder.item_card.setCardElevation((sharedPreferences.getBoolean("isShadowInListItem",true) ? 4*displayMetrics.density : 0));

        holder.item_element.setVisibility(View.GONE);
        holder.item_relic_ll.setVisibility(View.GONE);
        holder.item_sub_ll.setVisibility(View.GONE);
        holder.item_normal_ll.setVisibility(View.VISIBLE);

        int ico_rss = R.drawable.ico_lost_img;
        if (TYPE.equals(ItemRSS.TYPE_CHARACTER) || TYPE.equals(ItemRSS.TYPE_CHARACTER_TEAM1) || TYPE.equals(ItemRSS.TYPE_CHARACTER_TEAM2)){
            ico_rss = item_rss.getCharByName(hsrItem.getName())[(sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT).equals(THREE_IN_ROW) ? 2 : 0)];
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

        }else if (TYPE.equals(ItemRSS.TYPE_RELIC) || TYPE.equals(ItemRSS.TYPE_ORNAMENT)){
            holder.item_relic_ll.setVisibility(View.VISIBLE);
            holder.item_normal_ll.setVisibility(View.GONE);
            holder.item_rare.setVisibility(View.GONE);
            holder.item_sub_ll.setVisibility(View.VISIBLE);
            holder.item_sub_item2.setVisibility(View.GONE);
            holder.item_sub_item3.setVisibility(View.GONE);

            holder.item_2pc_status.setText(item_rss.getRelicStatusByName(hsrItem.getName(), context)[0], TextView.BufferType.SPANNABLE);
            holder.item_4pc_status.setText(item_rss.getRelicStatusByName(hsrItem.getName(), context)[1], TextView.BufferType.SPANNABLE);

            if (holder.item_4pc_status.getText().toString().equals("DEFAULT N/A")){
                holder.item_4pc_status.setVisibility(View.GONE);
                holder.item_4pc.setVisibility(View.GONE);
            }

            ico_rss = item_rss.getRelicByName(hsrItem.getName())[0];
        }

        double img_width = 96 * displayMetrics.density;
        double img_height = 96 * displayMetrics.density;
        int grid = 1;

        if (sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT).equals(THREE_IN_ROW)){
            grid = 3;
            img_width = (displayMetrics.widthPixels )/3;
            img_height = img_width * 1.2;
        }

        holder.item_ico.getLayoutParams().width = (int) img_width;
        holder.item_ico.getLayoutParams().height = (int) img_height;


        Picasso.get()
                .load (ico_rss)
                .resize((int) img_width, (int) img_height)
                .centerCrop()
                .transform(transformation)
                .rotate((sharedPreferences.getBoolean("isRotateItemIcon", false) ? 180 :0 ))
                .into (holder.item_ico);
        holder.item_ico.setAdjustViewBounds(true);


        holder.itemView.getLayoutParams().width = (int) ((displayMetrics.widthPixels ) / grid);

        holder.item_kwang_alpha.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        holder.item_kwang_alpha.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

        if (TYPE.equals(ItemRSS.TYPE_RELIC) || TYPE.equals(ItemRSS.TYPE_ORNAMENT)) {
            Picasso.get()
                    .load(item_rss.getRelicByName(hsrItem.getName())[1])
                    .rotate((sharedPreferences.getBoolean("isRotateItemIcon", false) ? 180 :0 ))
                    .into(holder.item_sub_item1);

            if (item_rss.getRelicByName(hsrItem.getName()).length > 2){
                holder.item_sub_item2.setVisibility(View.VISIBLE);
                holder.item_sub_item3.setVisibility(View.VISIBLE);
                Picasso.get()
                        .load(item_rss.getRelicByName(hsrItem.getName())[2])
                        .rotate((sharedPreferences.getBoolean("isRotateItemIcon", false) ? 180 :0 ))
                        .into(holder.item_sub_item2);
                Picasso.get()
                        .load(item_rss.getRelicByName(hsrItem.getName())[3])
                        .rotate((sharedPreferences.getBoolean("isRotateItemIcon", false) ? 180 :0 ))
                        .into(holder.item_sub_item3);
            }
        }

        holder.item_name.setText(item_rss.getLocalNameByName(hsrItem.getName(),context));
        holder.item_name.getPaint().setFakeBoldText(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isForSelect){
                    if (context instanceof DevPage){
                        if (!hsritemSelectedList.contains(hsrItem) && hsritemSelectedList.size() + 1 <= maxSizeOfList){
                            ((DevPage) context).casAddItem(hsrItem,TYPE);
                        }else if(hsritemSelectedList.size() + 1 > maxSizeOfList){
                            Toast.makeText(context, context.getString(R.string.cas_max_size)+String.valueOf(maxSizeOfList), Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, context.getString(R.string.cas_item_selected), Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    switch (TYPE){
                        case ItemRSS.TYPE_CHARACTER: new InfoCharacterPage().setup(context, activity,hsrItem); return;
                        case ItemRSS.TYPE_LIGHTCONE: new InfoLightconePage().setup(context,activity, hsrItem);return;
                        case ItemRSS.TYPE_RELIC: return;
                    }
                }
            }
        });

        if(hsrItem.getStatus().equals(ItemRSS.STATUS_BETA)){
            holder.item_beta.setVisibility(View.VISIBLE);
        }else{
            holder.item_beta.setVisibility(View.GONE);
        }

        String LANGUAGE = ItemRSS.initLang(context).getCode();
        //Read JSON from Assests
        String heading = "character_data";
        if (TYPE.equals(TYPE_LIGHTCONE)){
            heading = "lightcone_data";

            holder.item_material_3.setVisibility(View.GONE);
        }


        holder.item_material_1.setImageResource(R.drawable.ico_lost_img);
        holder.item_material_2.setImageResource(R.drawable.ico_lost_img);
        holder.item_material_3.setImageResource(R.drawable.ico_lost_img);

        //Read JSON Assest Data
        String json_base = LoadAssestData(context,heading+"/"+LANGUAGE+"/"+hsrItem.getFileName()+".json");
        String json_base2 = LoadAssestData(context,heading+"/"+ LangUtil.LangType.EN.getCode()+"/"+hsrItem.getFileName()+".json");
        if (json_base == "" && json_base2 != ""){json_base = json_base2;}
        if (json_base != "" && sharedPreferences.getString("grid_"+TYPE,HSRItemAdapter.DEFAULT).equals(ONE_IN_ROW)){
            try {
                JSONObject jsonObject = new JSONObject(json_base);
                JSONObject itemReferences = jsonObject.getJSONObject("itemReferences");
                Iterator<String> item_iter = itemReferences.keys();
                while (item_iter.hasNext()){
                    String materialKey = item_iter.next();
                    int id = itemReferences.getJSONObject(materialKey).getInt("id");
                    int purposeId = itemReferences.getJSONObject(materialKey).getInt("purposeId");
                    int rare = itemReferences.getJSONObject(materialKey).getInt("rarity");

                    //獵獸之矢
                    if (purposeId == 3 && rare == 2){
                        Picasso.get().load(item_rss.getMaterialByID(id)).into(holder.item_material_1);
                    }
                    //熄滅原核
                    if (purposeId == 7 && rare == 2){
                        Picasso.get().load(item_rss.getMaterialByID(id)).into(holder.item_material_2);
                    }
                    //暴風之眼
                    if (purposeId == 2 && rare == 4){
                        Picasso.get().load(item_rss.getMaterialByID(id)).into(holder.item_material_3);
                    }

                }
            } catch (JSONException e) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                LogExport.bugLog(TAG, "Read JSON Assest Data", sw.toString(), context);
            }
        }
    }

    private void setAnimation(View viewToAnimate){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return hsritemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_ico, item_element, item_path_ico, item_kwang_alpha;
        public ImageView item_sub_item1, item_sub_item2, item_sub_item3, item_beta;
        public ImageView item_material_1, item_material_2, item_material_3;
        public TextView item_name, item_hp_tv, item_def_tv, item_atk_tv, item_speed_tv, item_path_tv;
        public TextView item_2pc_status, item_4pc_status, item_4pc;
        public LinearLayout item_hp_ll, item_def_ll, item_atk_ll, item_speed_ll, item_sub_ll, item_relic_ll, item_normal_ll;
        public ScaleRatingBar item_rare;

        public CardView item_card;

        public ViewHolder(View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);

            item_ico = itemView.findViewById(R.id.item_ico);
            item_element = itemView.findViewById(R.id.item_element);
            item_name = itemView.findViewById(R.id.item_name);
            item_rare = itemView.findViewById(R.id.item_rare);
            item_path_tv = itemView.findViewById(R.id.item_path_tv);
            item_path_ico = itemView.findViewById(R.id.item_path_ico);
            item_kwang_alpha = itemView.findViewById(R.id.item_kwang_alpha);
            item_card = itemView.findViewById(R.id.item_card);

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
            item_material_1 = itemView.findViewById(R.id.item_material_1);
            item_material_2 = itemView.findViewById(R.id.item_material_2);
            item_material_3 = itemView.findViewById(R.id.item_material_3);

            item_sub_item1 = itemView.findViewById(R.id.item_sub_item1);
            item_sub_item2 = itemView.findViewById(R.id.item_sub_item2);
            item_sub_item3 = itemView.findViewById(R.id.item_sub_item3);

            item_2pc_status = itemView.findViewById(R.id.item_2pc_status);
            item_4pc_status = itemView.findViewById(R.id.item_4pc_status);
            item_4pc = itemView.findViewById(R.id.item_4pc);

            item_beta = itemView.findViewById(R.id.item_beta);

        }
    }


    public ArrayList<HSRItem> getFilterList(){
        return hsritemList;
    }
    public ArrayList<HSRItem> getFilterSelectedList(){
        return hsritemSelectedList;
    }

    public void filterList(ArrayList<HSRItem> filteredList) {
        hsritemList = filteredList;
        notifyDataSetChanged();
    }
    public void selectedList(ArrayList<HSRItem> hsritemSelectedList) {
        this.hsritemSelectedList = hsritemSelectedList;
    }
    public void maxSizeOfList(int maxSizeOfList) {
        this.maxSizeOfList = maxSizeOfList;
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
