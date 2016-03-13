package com.cs160.joleary.represent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by yannie on 3/2/16.
 */
public class OverviewAdapter extends ArrayAdapter<String> {

    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> websites = new ArrayList<String>();
    ArrayList<String> term_ends = new ArrayList<String>();
    ArrayList<String> pic_urls = new ArrayList<String>();
    String rename_party;
    int tweetIcon = R.drawable.tweet_icon;
    Context c;
    LayoutInflater inflater;

    public OverviewAdapter(Context context, ArrayList<String> ids, ArrayList<String> first_names,
                           ArrayList<String> last_names, ArrayList<String> parties, ArrayList<String> chambers,
                           ArrayList<String> emails, ArrayList<String> websites, ArrayList<String> term_ends,
                           ArrayList<String> pic_urls, int tweetIcon) {
        super(context, R.layout.list_row, first_names);

        this.ids = ids;
        this.first_names = first_names;
        this.last_names = last_names;
        this.parties = parties;
        this.chambers = chambers;
        this.emails = emails;
        this.websites = websites;
        this.term_ends = term_ends;
        this.pic_urls = pic_urls;
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

        final RowView rowView = new RowView();
        rowView.name = (TextView) convertView.findViewById(R.id.name);
        rowView.party = (TextView) convertView.findViewById(R.id.party);
        rowView.email = (TextView) convertView.findViewById(R.id.email);
        rowView.website = (TextView) convertView.findViewById(R.id.website);
        rowView.tweet = (TextView) convertView.findViewById(R.id.tweet);
        rowView.picture = (ImageView) convertView.findViewById(R.id.picture);
        rowView.tweetIcon = (ImageView) convertView.findViewById(R.id.tweetIcon);

        String parsed_first_name = first_names.get(position).replaceAll("^\"|\"$", "");
        String parsed_last_name = last_names.get(position).replaceAll("^\"|\"$", "");
        String name = parsed_first_name + " " + parsed_last_name;
        String parsed_party = parties.get(position).replaceAll("^\"|\"$", "");
        if (parsed_party.equals("D")) {
            rename_party = "Democrat";
        } else if (parsed_party.equals("R")) {
            rename_party = "Republican";
        } else {
            rename_party = "Independent";
        }
        String parsed_chamber = capitalize(chambers.get(position).replaceAll("^\"|\"$", ""));
        String party_chamber = rename_party + " - " + parsed_chamber;
        String parsed_email = emails.get(position).replaceAll("^\"|\"$", "");
        String parsed_website = websites.get(position).replaceAll("^\"|\"$", "");
        String pic_url = pic_urls.get(position).replaceAll("^\"|\"$", "");

        rowView.name.setText(name);
        rowView.party.setText(party_chamber);
        rowView.email.setText(parsed_email);
        rowView.website.setText(parsed_website);
        rowView.tweet.setText("I wish I had time to include a tweet. Sadly that was not the case. This tweet is here for your viewing pleasure.");
        Picasso.with(c).load(pic_url).into(rowView.picture);
        return convertView;
    }

    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public int getCount() {
        return first_names.size();
    }
}
