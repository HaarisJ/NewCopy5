package com.example.haaris.newcopy5;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PublicGridAdapter extends BaseAdapter {

    private ArrayList<Integer> icons;

    private ArrayList<String> seshNames;

    private Context context;

    private LayoutInflater inflater;


    public PublicGridAdapter(Context context, ArrayList<Integer> icons, ArrayList<String> seshNames){

        this.context = context;
        this.icons = icons;
        this.seshNames = seshNames;

    }

    @Override
    public int getCount() {
        return seshNames.size();
    }

    @Override
    public Object getItem(int i) {
        return seshNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        View gridView = convertView;

        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.public_rooms_grid, null);

        }

        ImageView cubeIcon = (ImageView) gridView.findViewById(R.id.cube_icon);
        TextView  seshName = (TextView) gridView.findViewById(R.id.sesh_name);

        cubeIcon.setImageResource(icons.get(i));
        seshName.setText(seshNames.get(i));

        return gridView;
    }
}
