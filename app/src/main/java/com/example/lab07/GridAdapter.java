package com.example.lab07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridAdapter extends BaseAdapter {
    private Context context;
    private List<Publisher> publishers;

    public GridAdapter(Context context, List<Publisher> publishers) {
        this.context = context;
        this.publishers = publishers;
    }

    @Override
    public int getCount() {
        return publishers.size();
    }

    @Override
    public Object getItem(int position) {
        return publishers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView textView = convertView.findViewById(R.id.textView);

        Publisher publisher = publishers.get(position);
        imageView.setImageResource(publisher.getImageResId());
        textView.setText(publisher.getName());

        return convertView;
    }
}
