package com.example.prosoft.taxiloy.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;

import java.util.ArrayList;

/**
 * Created by prosoft on 1/14/16.
 */
public class NationAdapter extends ArrayAdapter<String> {

    private ArrayList mData;
    private Resources mResources;
    private LayoutInflater mInflater;

    public NationAdapter(Activity activitySpinner, int textViewResourceId, ArrayList objects, Resources resLocal) {
        super(activitySpinner, textViewResourceId, objects);

        mData = objects;
        mResources = resLocal;
        mInflater = (LayoutInflater) activitySpinner.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.cell_spinner_gender, parent, false);
        TextView label = (TextView) convertView.findViewById(R.id.list_item);
        label.setText(mData.get(position).toString());
        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        String[] nationName = mResources.getStringArray(R.array.nation_code);
        convertView = mInflater.inflate(R.layout.cell_spinner_gender, parent, false);
        TextView label = (TextView) convertView.findViewById(R.id.list_item);
        label.setText(nationName[position].toString());
        return convertView;
    }
}
