package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;

public class Overview extends Activity {

    ListView lv;
    OverviewAdapter overviewAdapter;
    private String host = "http://congress.api.sunlightfoundation.com";
    private String API_KEY = "40762425261540849256549d0ea423ba";
    int curr;
    String apikey;
    String comsUrl;
    String billsUrl;
    ArrayList<String> comms = new ArrayList<String>();
    ArrayList<String> bills = new ArrayList<String>();

    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> websites = new ArrayList<String>();
    ArrayList<String> term_ends = new ArrayList<String>();
    ArrayList<String> pic_urls = new ArrayList<String>();
    int tweetIcon = R.drawable.tweet_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        lv = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            ids = extras.getStringArrayList("IDS");
            first_names = extras.getStringArrayList("FIRST_NAMES");
            last_names = extras.getStringArrayList("LAST_NAMES");
            parties = extras.getStringArrayList("PARTIES");
            chambers = extras.getStringArrayList("CHAMBERS");
            emails = extras.getStringArrayList("EMAILS");
            websites = extras.getStringArrayList("WEBSITES");
            term_ends = extras.getStringArrayList("TERM_ENDS");
            pic_urls = extras.getStringArrayList("PIC_URLS");
        }

        overviewAdapter = new OverviewAdapter(this, ids, first_names, last_names, parties, chambers, emails,
                websites, term_ends, pic_urls, tweetIcon);
        lv.setAdapter(overviewAdapter);

        lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), first_names.get(position), Toast.LENGTH_SHORT).show();
                getCommittees(position);
            }
        });
    }

    public void getCommittees(int pos) {
        Log.d("detail info", "GET COMMITTEES");
        curr = pos;
        String method = "/committees?";
        String raw_id = ids.get(pos);
        String parsed_id = raw_id.replaceAll("^\"|\"$", "");
        String id = "member_ids=" + parsed_id;
        apikey = "&apikey=" + API_KEY;
        comsUrl = host + method + id + apikey;
        Log.d("comsURL", comsUrl);
        Ion.with(getBaseContext())
                .load(comsUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        JsonArray results = result.getAsJsonArray("results");
                        int len = results.size();
                        for (int i = 0; i < len; i++) {
                            comms.add(results.get(i).getAsJsonObject().get("name").toString());
                        }
                        getBills(curr);
                    }
                });
    }

    public void getBills(int pos) {
        Log.d("detail info", "GET BILLS");
        curr = pos;
        String method = "/bills?";
        String raw_id = ids.get(pos);
        String parsed_id = raw_id.replaceAll("^\"|\"$", "");
        String id = "sponsor_id=" + parsed_id;
        apikey = "&apikey=" + API_KEY;
        billsUrl = host + method + id + apikey;
        Ion.with(getBaseContext())
                .load(billsUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        JsonArray results = result.getAsJsonArray("results");
                        for (int i = 0; i < 3; i++) {
                            bills.add(results.get(i).getAsJsonObject().get("official_title").toString());
                        }
                        Intent intent = new Intent(Overview.this, Detailview.class);
                        intent.putExtra("ID", ids.get(curr));
                        intent.putExtra("FIRST_NAME", first_names.get(curr));
                        intent.putExtra("LAST_NAME", last_names.get(curr));
                        intent.putExtra("PARTY", parties.get(curr));
                        intent.putExtra("CHAMBER", chambers.get(curr));
                        intent.putExtra("TERM_END", term_ends.get(curr));
                        intent.putExtra("PIC_URL", pic_urls.get(curr));
                        intent.putExtra("COMMS", comms);
                        intent.putExtra("BILLS", bills);
                        startActivity(intent);
                    }
                });
    }
}