package com.example.prosoft.taxiloy.ui.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.prosoft.taxiloy.ui.objects.MessageInbox;

import java.util.List;

/**
 * Created by prosoft on 1/25/16.
 */
public class CustomListView extends ListView implements AbsListView.OnScrollListener {

    private View footer;
    private boolean isLoading;
    private EndlessListener listener;
    private ArrayAdapter adapter;

    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);
    }

    public CustomListView(Context context) {
        super(context);
        this.setOnScrollListener(this);
    }

    public void setListener(EndlessListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (getAdapter() == null)
            return ;

        if (getAdapter().getCount() == 0)
            return ;

        if(visibleItemCount + firstVisibleItem > 9) {
            int l = visibleItemCount + firstVisibleItem;
            if (l >= totalItemCount && !isLoading) {
                this.addFooterView(footer);
                isLoading = true;
                listener.loadData();
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {}

    public void setLoadingView(int resId) {
        LayoutInflater inflater = (LayoutInflater) super.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = (View) inflater.inflate(resId, null);
    }



    public void addNewData() {
        this.removeFooterView(footer);
        isLoading = false;
    }


    public EndlessListener setListener() {
        return listener;
    }


    public interface EndlessListener {
         void loadData() ;
    }

}
