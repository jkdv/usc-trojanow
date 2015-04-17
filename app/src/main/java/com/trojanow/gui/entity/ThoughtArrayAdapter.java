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
public class ThoughtArrayAdapter extends ArrayAdapter<Thought> {
    private Context mContext;

    public ThoughtArrayAdapter(Context context, int resource, List<Thought> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    public void populateData(List<Thought> objects) {
        clear();
        addAll(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Thought object = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_thought, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);
            viewHolder.textViewContent = (TextView) convertView.findViewById(R.id.textViewContent);
            viewHolder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (object.getAnonymous(false)) {
            viewHolder.textViewName.setText("Anonymous");
        } else {
            viewHolder.textViewName.setText(object.getFullName());
        }
        viewHolder.textViewContent.setText(object.getContent());
        viewHolder.textViewDate.setText(
                object.getTimeElapsed().equals("null") ? "Now" : object.getTimeElapsed());

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewName;
        TextView textViewContent;
        TextView textViewDate;
    }
}
