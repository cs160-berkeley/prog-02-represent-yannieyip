package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.AdapterView;
import android.content.Context;

public class Overview extends Activity {

    ListView lv;
    OverviewAdapter overviewAdapter;
    String[] names = {"Barabara Boxer", "Dianne Feinstein", "Barbara Lee"};
    String[] parties = {"Democratic (Senate)", "Democratic (House)", "Democratic (House)"};
    String[] emails = {"barbaraboxer@gmail.com", "diannefeinstein@gmail.com", "barbaralee@gmail.com"};
    String[] websites = {"www.barbaraboxer.com", "www.diannefeinsteine.com", "www.barbaralee.com"};
    String[] tweets = {"Barbara Boxer tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140.",
            "Diane Feinstein tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140.",
            "Barbara Lee tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140."};
    int[] pictures = {R.drawable.barbara_boxer, R.drawable.dianne_feinstein, R.drawable.barbara_lee};
    int tweetIcon = R.drawable.tweet_icon;
    Button viewMore;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        lv = (ListView) findViewById(R.id.listView);
        overviewAdapter = new OverviewAdapter(this, names, parties, emails, websites, tweets, pictures, tweetIcon, viewMore);
        lv.setAdapter(overviewAdapter);

////       Clicking on cell
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, ListView view, int position, long id) {
////                Toast.makeText(getApplicationContext(), names[position], Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(c, Detailview.class);
//                c.startActivity(intent);
//            }
//        });



    }
}