package com.cs160.joleary.represent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yannie on 3/2/16.
 */
public class DetailviewAdapter extends ArrayAdapter<String> {

    int[] pictures={};
    String[] names={};
    String[] parties={};
    String[] termEnds={};
    String comLabel;
    String billLabel;
    String[] committees={};
    String[] bills={};
    Context c;
    LayoutInflater inflater;

    public DetailviewAdapter(Context context, String[] names, String[] parties, String[] termEnds, String comLabel, String billLabel,
                           String[] committees, String[] bills, int[] pictures) {
        super(context, R.layout.detail_row,names);

        this.names = names;
        this.parties = parties;
        this.termEnds = termEnds;
        this.comLabel = comLabel;
        this.billLabel = billLabel;
        this.committees = committees;
        this.bills = bills;
        this.pictures = pictures;
        this.c = context;
    }

    public class RowView {
        TextView name;
        TextView party;
        TextView termEnds;
        TextView comLabel;
        TextView billLabel;
        TextView committees;
        TextView bills;
        ImageView picture;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.detail_row, null);
        }
        final RowView rowView = new RowView();
        rowView.name = (TextView) convertView.findViewById(R.id.name);
        rowView.party = (TextView) convertView.findViewById(R.id.party);
        rowView.termEnds = (TextView) convertView.findViewById(R.id.termEnds);
        rowView.comLabel = (TextView) convertView.findViewById(R.id.comLabel);
        rowView.billLabel = (TextView) convertView.findViewById(R.id.billLabel);
        rowView.committees = (TextView) convertView.findViewById(R.id.committees);
        rowView.bills = (TextView) convertView.findViewById(R.id.bills);
        rowView.picture = (ImageView) convertView.findViewById(R.id.picture);

        rowView.name.setText(names[position]);
        rowView.party.setText(parties[position]);
        rowView.termEnds.setText(termEnds[position]);
        rowView.committees.setText(committees[position]);
        rowView.bills.setText(bills[position]);
        rowView.picture.setImageResource(pictures[position]);

        return convertView;
    }

    @Override
    public int getCount(){
        return names.length;
    }

}
