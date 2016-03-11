package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import android.widget.AdapterView;
import android.content.Context;
import android.widget.Toast;

public class Overview extends Activity {

    ListView lv;
    OverviewAdapter overviewAdapter;
    String name;
    String party;
    String email;
    String website;
//    String tweet;
//    String picture;


    String[] names = {"Barabara Boxer", "Dianne Feinstein", "Barbara Lee"};
    String[] parties = {"Democratic (Senate)", "Democratic (House)", "Democratic (House)"};
    String[] emails = {"barbaraboxer@gmail.com", "diannefeinstein@gmail.com", "barbaralee@gmail.com"};
    String[] websites = {"www.barbaraboxer.com", "www.diannefeinstein.com", "www.barbaralee.com"};
    String[] tweets = {"Barbara Boxer tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140.",
            "Diane Feinstein tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140.",
            "Barbara Lee tweeted this tweet most recently and this is to test if tweet shows correctly at the approximate maximum character count of 140."};
    int[] pictures = {R.drawable.barbara_boxer, R.drawable.dianne_feinstein, R.drawable.barbara_lee};
    int tweetIcon = R.drawable.tweet_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        lv = (ListView) findViewById(R.id.listView);
        overviewAdapter = new OverviewAdapter(this, names, parties, emails, websites, tweets, pictures, tweetIcon);
        lv.setAdapter(overviewAdapter);

        lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), names[position], Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Overview.this, Detailview.class);
                startActivity(intent);
            }
        });

    }
}