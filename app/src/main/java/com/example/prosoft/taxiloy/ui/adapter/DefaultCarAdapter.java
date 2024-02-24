package com.example.prosoft.taxiloy.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.objects.Driver;
import com.example.prosoft.taxiloy.ui.objects.MessageInbox;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.CircleImageView;

import java.util.List;

/**
 * Created by prosoft on 1/26/16.
 */
public class DefaultCarAdapter  extends ArrayAdapter<Driver> {
    private final Activity context;
    private List<Driver> drivers;

    public DefaultCarAdapter(Activity context, List<Driver> drivers) {
        super(context, R.layout.cell_listview_car, drivers);
        this.context = context;
        this.drivers = drivers;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.cell_listview_car, null, true);
        Driver driver = getItem(position);

        TextView name = (TextView) rowView.findViewById(R.id.tv_name_driver);
        TextView type = (TextView) rowView.findViewById(R.id.type_car);
        TextView brand = (TextView) rowView.findViewById(R.id.brand_car);
        TextView seats = (TextView) rowView.findViewById(R.id.seats_car);
        CircleImageView avatar = (CircleImageView) rowView.findViewById(R.id.iv_avatar);
        name.setText(driver.name);
        type.setText("Type : " + driver.cartypename);
        brand.setText("Brand : " + driver.carbrand);
        seats.setText("Seats : " + driver.carseats);

        if (SystemUtils.StringToBitMap(driver.avatar, 50, 50) != null) {
            avatar.setImageBitmap(SystemUtils
                    .StringToBitMap(driver.avatar, 50, 50));
        }

        return rowView;
    }

    @Override
    public Driver getItem(int position) {
        return super.getItem(position);
    }
}
