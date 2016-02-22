package com.task.satu.rabbitsfarmer.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.task.satu.rabbitsfarmer.R;

import java.text.SimpleDateFormat;


/**
 * Created by Satu on 2014-09-26.
 */
public class RabbitsAdapter extends ArrayAdapter<Rabbit> {
    private final LayoutInflater mInflater;

    public RabbitsAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        mInflater = LayoutInflater.from(getContext());
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_rabbit, null);
            holder = new ViewHolder();
            holder.date = (TextView) convertView.findViewById(R.id.textView_rabbit_BirthdayDate);
            holder.name = (TextView) convertView.findViewById(R.id.textView_rabbit_name);
            holder.color = (TextView) convertView.findViewById(R.id.textView_rabbit_color);


            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        final Rabbit rabbit = getItem(position);

       SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
       holder.date.setText(sdf.format(rabbit.getBirthday()));
       holder.name.setText(rabbit.getName());
       holder.color.setText(rabbit.getColor());

        return convertView;
    }



    private static class ViewHolder{
        public TextView name;
        public TextView date;
        public TextView color;

    }


}
