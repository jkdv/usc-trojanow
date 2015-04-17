package com.trojanow.gui.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trojanow.R;

import java.util.List;

/**
 * Created by heetae on 4/8/15.
 */
public class ChatArrayAdapter extends ArrayAdapter<Chat> {
    private Context mContext;

    public ChatArrayAdapter(Context context, int resource, List<Chat> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Chat object = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_chat, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewNameRight = (TextView) convertView.findViewById(R.id.textViewNameRight);
            viewHolder.textViewNameLeft = (TextView) convertView.findViewById(R.id.textViewNameLeft);
            viewHolder.textViewContentRight = (TextView) convertView.findViewById(R.id.textViewContentRight);
            viewHolder.textViewContentLeft = (TextView) convertView.findViewById(R.id.textViewContentLeft);
            viewHolder.textViewTimeRight = (TextView) convertView.findViewById(R.id.textViewTimeRight);
            viewHolder.textViewTimeLeft = (TextView) convertView.findViewById(R.id.textViewTimeLeft);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (object.getSentByMe()) {
            viewHolder.textViewNameRight.setText(
                    object.getUserFromFirstName() + " " + object.getUserFromLastName());
            viewHolder.textViewNameRight.setVisibility(View.VISIBLE);

            viewHolder.textViewContentRight.setText(object.getContent());
            viewHolder.textViewContentRight.setVisibility(View.VISIBLE);
            viewHolder.textViewTimeRight.setText(
                    object.getTimeElapsed().equals("null") ? "Now" : object.getTimeElapsed());
            viewHolder.textViewTimeRight.setVisibility(View.VISIBLE);

            viewHolder.textViewContentLeft.setVisibility(View.INVISIBLE);
            viewHolder.textViewNameLeft.setVisibility(View.INVISIBLE);
            viewHolder.textViewTimeLeft.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.textViewNameLeft.setText(
                    object.getUserFromFirstName() + " " + object.getUserFromLastName());
            viewHolder.textViewNameLeft.setVisibility(View.VISIBLE);

            viewHolder.textViewContentLeft.setText(object.getContent());
            viewHolder.textViewContentLeft.setVisibility(View.VISIBLE);

            viewHolder.textViewTimeLeft.setText(
                    object.getTimeElapsed().equals("null") ? "Now" : object.getTimeElapsed());
            viewHolder.textViewTimeLeft.setVisibility(View.VISIBLE);

            viewHolder.textViewNameRight.setVisibility(View.INVISIBLE);
            viewHolder.textViewContentRight.setVisibility(View.INVISIBLE);
            viewHolder.textViewTimeRight.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNameRight;
        TextView textViewNameLeft;
        TextView textViewContentRight;
        TextView textViewContentLeft;
        TextView textViewTimeRight;
        TextView textViewTimeLeft;

    }
}
