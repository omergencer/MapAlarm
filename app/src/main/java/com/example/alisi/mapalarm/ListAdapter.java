package com.example.alisi.mapalarm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alisi on 24.05.2017.
 */

public class ListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;

    public ListAdapter(Activity context, String[] itemname, Integer[] imgid) {
        super(context, R.layout.listrow, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listrow, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.tvText);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        return rowView;

    };
}
