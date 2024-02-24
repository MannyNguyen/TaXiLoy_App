package com.example.prosoft.taxiloy.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.utils.ImageLoader;

/**
 * Created by prosoft on 12/25/15.
 */
public class SliderDriver extends FrameLayout {

    ImageView iv_slider;
    TextView tv_slider;
    Context context;
    public SliderDriver(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.slider_driver, this);

        loadViews();
    }

    public SliderDriver(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.slider_driver, this);

        loadViews();
    }

    private void loadViews() {
        iv_slider = (ImageView) findViewById(R.id.iv_slider);
        tv_slider = (TextView) findViewById(R.id.tv_slider);
    }

    public void setUI(String title, String url){
        int loader = R.drawable.bg_loading;
//        iv_slider.setBackgroundResource(resImageId);
        ImageLoader imgLoader = new ImageLoader(context);
        imgLoader.displayImage(url, loader, iv_slider);
        tv_slider.setText(title);
    }

}
