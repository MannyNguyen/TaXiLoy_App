package com.example.prosoft.taxiloy.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.objects.HistoryList;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;

import java.util.List;

/**
 * Created by Manh on 1/19/2016.
 */
public class HistoryAdapter extends ArrayAdapter<HistoryList> {

    private final Activity context;
    private List<HistoryList> listHistory;

    public HistoryAdapter(Activity context, List<HistoryList> listHistory) {
        super(context, R.layout.item_transaction_history_screen, listHistory);
        this.context = context;
        this.listHistory = listHistory;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_transaction_history_screen, null, true);
        HistoryList historyList = getItem(position);

        TextView txtHistoryId = (TextView) rowView.findViewById(R.id.txt_history_id);
        TextView txtHistoryName = (TextView) rowView.findViewById(R.id.txt_history_name);
        TextView txtHistoryDate = (TextView) rowView.findViewById(R.id.txt_history_date);

        txtHistoryId.setText(historyList.getHistoryId());
        txtHistoryName.setText(historyList.getHistoryName());
        if (historyList.getHistoryDate().length() > 10) {
            txtHistoryDate.setText(historyList.getHistoryDate().substring(0, 10));
        } else {
            txtHistoryDate.setText(historyList.getHistoryDate());
        }

        return rowView;
    }

    @Override
    public HistoryList getItem(int position) {
        return super.getItem(position);
    }
}
