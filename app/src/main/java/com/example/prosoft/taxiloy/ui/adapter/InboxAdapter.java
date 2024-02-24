package com.example.prosoft.taxiloy.ui.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Manh on 1/18/2016.
 */
public class InboxAdapter extends ArrayAdapter<MessageInbox> {

    private final Activity context;
    private List<MessageInbox> messageInboxList;

    public InboxAdapter(Activity context, List<MessageInbox> messageInboxList) {
        super(context, R.layout.item_inbox_screen, messageInboxList);
        this.context = context;
        this.messageInboxList = messageInboxList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_inbox_screen, null, true);
        MessageInbox messageInbox = getItem(position);

        TextView txtTime = (TextView) rowView.findViewById(R.id.txt_time);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt_inbox_title);
        TextView txtMessage = (TextView) rowView.findViewById(R.id.txt_message);
        TextView txtDay = (TextView) rowView.findViewById(R.id.txt_day);

        //substring to split
        txtTime.setText(messageInbox.getTime().substring(0,10));
        txtTitle.setText(messageInbox.getTitle());
        txtMessage.setText(messageInbox.getMessage());
        txtDay.setText(messageInbox.getTime().substring(12,16));

        return rowView;
    }

    @Override
    public MessageInbox getItem(int position) {
        return super.getItem(position);
    }
}
