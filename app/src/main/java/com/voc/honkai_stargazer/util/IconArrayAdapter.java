/*
 * Project Honkai Stargazer (崩壞•星穹觀星者) was
 * Created & Develop by Voc-夜芷冰 , Programmer of Xectorda
 * Copyright © 2023 Xectorda 版權所有
 */

package com.voc.honkai_stargazer.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.voc.honkai_stargazer.R;
import com.voc.honkai_stargazer.data.HSRItem;

import java.util.ArrayList;

public class IconArrayAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<HSRItem> hsrItems;

    public IconArrayAdapter(Context context, ArrayList<HSRItem> hsrItems) {
        super(context, android.R.layout.simple_spinner_item, hsrItems);
        this.context = context;
        this.hsrItems = hsrItems;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getImageForPosition(position, convertView, parent);
    }

    private View getImageForPosition(int position, View convertView, ViewGroup parent) {
        Transformation transformation = new RoundedCornersTransformation(90, 0);
        ItemRSS itemRSS = new ItemRSS();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.icon_spinner_dropdown_item, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.spinnerTextView);
        textView.setText(itemRSS.getLocalNameByName(hsrItems.get(position).getName(),context));
        ImageView imageView = (ImageView) row.findViewById(R.id.spinnerImages);
        Picasso.get()
                .load(itemRSS.getCharByName(hsrItems.get(position).getName(),hsrItems.get(position).getSex())[0])
                .fit()
                .transform(transformation)
                .into(imageView);
        return row;
    }
}