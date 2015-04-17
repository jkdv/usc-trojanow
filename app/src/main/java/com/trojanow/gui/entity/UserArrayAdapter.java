package com.trojanow.gui.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.trojanow.R;

import java.util.List;

/**
 * Created by heetae on 4/8/15.
 */
public class UserArrayAdapter extends ArrayAdapter<User> {
    private Context mContext;

    public UserArrayAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User object = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_user, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.radioName = (RadioButton) convertView.findViewById(R.id.radioName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.radioName.setText(object.getFirstName() + " " + object.getLastName());
        viewHolder.radioName.setChecked(object.getOnline());

        return convertView;
    }

    private static class ViewHolder {
        RadioButton radioName;
    }
}
