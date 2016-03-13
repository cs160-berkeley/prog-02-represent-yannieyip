package com.cs160.joleary.represent;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Detailview extends Activity {

    TextView rep_name;
    TextView rep_party;
    TextView rep_term_end;
    ImageView rep_pic;
    TextView rep_com_label;
    TextView rep_bill_label;
    TextView rep_comms;
    TextView rep_bills;
    String rename_party;

    String id;
    String first_name;
    String last_name;
    String party;
    String chamber;
    String term_end;
    String pic_url;
    String comLabel = "Committees Currently Serving:";
    String billLabel = "Bills Recently Sponsored:";
    ArrayList<String> comms = new ArrayList<String>();
    ArrayList<String> bills = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            id = extras.getString("ID");
            first_name = extras.getString("FIRST_NAME");
            last_name = extras.getString("LAST_NAME");
            party = extras.getString("PARTY");
            chamber = extras.getString("CHAMBER");
            term_end = extras.getString("TERM_END");
            pic_url = extras.getString("PIC_URL");
            comms = extras.getStringArrayList("COMMS");
            bills = extras.getStringArrayList("BILLS");
        }

        rep_name = (TextView) findViewById(R.id.name);
        rep_party = (TextView) findViewById(R.id.party);
        rep_term_end = (TextView) findViewById(R.id.termEnds);
        rep_com_label = (TextView) findViewById(R.id.comLabel);
        rep_bill_label = (TextView) findViewById(R.id.billLabel);
        rep_comms = (TextView) findViewById(R.id.committees);
        rep_bills = (TextView) findViewById(R.id.bills);
        rep_pic = (ImageView) findViewById(R.id.picture);

        String parsed_first_name = first_name.replaceAll("^\"|\"$", "");
        String parsed_last_name = last_name.replaceAll("^\"|\"$", "");
        String name = parsed_first_name + " " + parsed_last_name;
        String parsed_party = party.replaceAll("^\"|\"$", "");
        if (parsed_party.equals("D")) {
            rename_party = "Democrat";
        } else if (parsed_party.equals("R")) {
            rename_party = "Republican";
        } else {
            rename_party = "Independent";
        }
        String parsed_chamber = capitalize(chamber.replaceAll("^\"|\"$", ""));
        String party_chamber = rename_party + " - " + parsed_chamber;
        String parsed_term_end = "Term ends:  " + term_end.replaceAll("^\"|\"", "");
        ;
        String parsed_pic_url = pic_url.replaceAll("^\"|\"$", "");

        String parsed_comms = "";
        int limit = 5;
        if (comms.size() >= limit) {
            for (int i = 0; i < limit; i++) {
                parsed_comms += comms.get(i);
                parsed_comms += '\n';
            }
        } else {
            for (int i = 0; i < comms.size(); i++) {
                parsed_comms += comms.get(i);
                parsed_comms += '\n';
            }
        }

        String parsed_bills = "";
        int lim = 3;
        if (bills.size() >= lim) {
            for (int i = 0; i < lim; i++) {
                parsed_bills += bills.get(i);
                parsed_bills += '\n';
            }
        } else {
            for (int i = 0; i < bills.size(); i++) {
                parsed_bills += bills.get(i);
                parsed_bills += '\n';
            }
        }
        rep_name.setText(name);
        rep_party.setText(party_chamber);
        rep_term_end.setText(parsed_term_end);
        rep_com_label.setText(comLabel);
        rep_bill_label.setText(billLabel);
        rep_comms.setText(parsed_comms);
        rep_bills.setText(parsed_bills);
        Picasso.with(getBaseContext()).load(parsed_pic_url).into(rep_pic);

    }
    private String capitalize(String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

}
