package com.example.makepayment.helpers;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makepayment.R;
import com.example.makepayment.classes.CustomListItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CustomListAdapter extends ArrayAdapter<CustomListItem> {

    private final Activity context;
    ArrayList<CustomListItem> customList;

    public CustomListAdapter(Activity context, ArrayList<CustomListItem> customList) {
        super(context, R.layout.activity_custom_list_item, customList);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.customList = customList;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_custom_list_item, null,true);

        TextView amount = (TextView) rowView.findViewById(R.id.tvHistoryAmt);
        ImageView icon = (ImageView) rowView.findViewById(R.id.tvHistoryIcon);
        TextView dateText = (TextView) rowView.findViewById(R.id.tvHistoryDate);

        CustomListItem item = customList.get(position);

        SimpleDateFormat spf = new SimpleDateFormat("MMM yy");

        amount.setText(item.amount);
        icon.setImageResource(item.imgId);
        dateText.setText(spf.format(item.date));

        return rowView;

    };
}