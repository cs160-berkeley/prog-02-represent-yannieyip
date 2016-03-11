package com.cs160.joleary.represent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;


/**
 * Created by yannie on 3/2/16.
 */
public class OverviewAdapter extends ArrayAdapter<String> {

    int[] pictures = {};
    int tweetIcon;
    String[] names = {};
    String[] parties = {};
    String[] emails = {};
    String[] websites = {};
    String[] tweets = {};
    Context c;
    LayoutInflater inflater;

    public OverviewAdapter(Context context, String[] names, String[] parties, String[] emails,
                           String[] websites, String[] tweets, int[] pictures, int tweetIcon) {
        super(context, R.layout.list_row, names);

        this.names = names;
        this.parties = parties;
        this.emails = emails;
        this.websites = websites;
        this.tweets = tweets;
        this.pictures = pictures;
        this.tweetIcon = tweetIcon;
        this.c = context;
    }

    public class RowView {
        TextView name;
        TextView party;
        TextView email;
        TextView website;
        TextView tweet;
        ImageView picture;
        ImageView tweetIcon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_row, null);
        }

//        if (convertView == null) {
//            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.list_row, null);
//
//        } else {

//            wrapper = (rowWrapper) convertView.getTag();
//            wrapper.getButton().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(c, Detailview.class);
//                    c.startActivity(intent);
//                }
//            });
//        }
        final RowView rowView = new RowView();
        rowView.name = (TextView) convertView.findViewById(R.id.name);
        rowView.party = (TextView) convertView.findViewById(R.id.party);
        rowView.email = (TextView) convertView.findViewById(R.id.email);
        rowView.website = (TextView) convertView.findViewById(R.id.website);
        rowView.tweet = (TextView) convertView.findViewById(R.id.tweet);
        rowView.picture = (ImageView) convertView.findViewById(R.id.picture);
        rowView.tweetIcon = (ImageView) convertView.findViewById(R.id.tweetIcon);

        rowView.name.setText(names[position]);
        rowView.party.setText(parties[position]);
        rowView.email.setText(emails[position]);
        rowView.website.setText(websites[position]);
        rowView.tweet.setText(tweets[position]);
        rowView.picture.setImageResource(pictures[position]);

        return convertView;
    }


    @Override
    public int getCount() {
        return names.length;
    }
}