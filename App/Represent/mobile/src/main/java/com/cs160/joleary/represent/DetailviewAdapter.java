package com.cs160.joleary.represent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yannie on 3/2/16.
 */
public class DetailviewAdapter extends ArrayAdapter<String> {

    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> websites = new ArrayList<String>();
    ArrayList<String> term_ends = new ArrayList<String>();
    ArrayList<String> pic_urls = new ArrayList<String>();
    Context c;
    LayoutInflater inflater;

    public DetailviewAdapter(Context context, String[] names, String[] parties, String[] termEnds, String comLabel, String billLabel,
                           String[] committees, String[] bills, int[] pictures) {
        super(context, R.layout.detail_row,names);


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


        return convertView;
    }

    @Override
    public int getCount(){
        return 1;
    }

}
