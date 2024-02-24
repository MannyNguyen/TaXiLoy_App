package com.example.prosoft.taxiloy.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;

import java.util.ArrayList;

/**
 * Created by prosoft on 12/17/15.
 */
public class GenderAdapter extends ArrayAdapter<String> {

    private ArrayList mData;
    private Resources mResources;
    private LayoutInflater mInflater;
    private int textViewResourceId;

    public GenderAdapter(Activity activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal) {
        super(activitySpinner, textViewResourceId, objects);

        mData = objects;
        mResources = resLocal;
        this.textViewResourceId = textViewResourceId;
        mInflater = (LayoutInflater) activitySpinner.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = mInflater.inflate(this.textViewResourceId, parent, false);
        TextView label = (TextView) row.findViewById(R.id.list_item);
        label.setText(mData.get(position).toString());
        if (position == 0) {
            row.setClickable(false);
        }

        return row;
    }

}
