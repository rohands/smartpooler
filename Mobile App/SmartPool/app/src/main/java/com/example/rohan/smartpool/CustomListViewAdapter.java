package com.example.rohan.smartpool;

/**
 * Created by rohan on 21/4/16.
 */

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView name;
        TextView source;
        TextView destination;
        TextView distance;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.distance = (TextView) convertView.findViewById(R.id.distance);
            holder.source = (TextView) convertView.findViewById(R.id.source);
            holder.destination = (TextView) convertView.findViewById(R.id.destination);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.name.setText(rowItem.getName());
        holder.source.setText(rowItem.getSrc());
        holder.distance.setText(rowItem.getDist());
        holder.destination.setText(rowItem.getDest());

        return convertView;
    }
}
