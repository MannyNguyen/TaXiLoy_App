package com.example.prosoft.taxiloy.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prosoft.taxiloy.api.object_response_api.GetBannerSliderResponse;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.enums.SliderDriverEnum;
import com.example.prosoft.taxiloy.ui.view.SliderDriver;

import java.util.ArrayList;

/**
 * Created by prosoft on 12/25/15.
 */
public class SliderPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<GetBannerSliderResponse.Banner> banners;

    public SliderPagerAdapter(Context context, ArrayList<GetBannerSliderResponse.Banner> banners) {
        mContext = context;
        this.banners = banners;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        GetBannerSliderResponse.Banner banner = banners.get(position);
        SliderDriver sliderDriver = new SliderDriver(mContext);
        sliderDriver.setUI(banner.subtitle, banner.imagesslide);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) sliderDriver;
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return banners.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return banners.get(position).titleslide;
    }

}
