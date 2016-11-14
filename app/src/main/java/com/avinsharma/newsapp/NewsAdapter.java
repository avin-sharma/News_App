package com.avinsharma.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Avin on 14-11-2016.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> objects) {
        super(context, 0, objects);
    }

    static class ViewHolder {
        TextView title;
        TextView date;
        TextView type;
        TextView section;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.date = (TextView) convertView.findViewById(R.id.date);
            holder.section = (TextView) convertView.findViewById(R.id.section);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        News currentNews = getItem(position);

        holder.title.setText(currentNews.getmTitle());
        holder.date.setText(formatDate(currentNews.getmDate()));
        holder.type.setText(currentNews.getmType());
        holder.section.setText(currentNews.getmSection());
        return convertView;
    }
    String formatDate(String stringDate){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat output = new SimpleDateFormat("dd MMM");
        try {
            Date date = format.parse(stringDate);
            return output.format(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return  null;
    }
}
