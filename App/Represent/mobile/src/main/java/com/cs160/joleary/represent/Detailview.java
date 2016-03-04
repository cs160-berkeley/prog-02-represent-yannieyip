package com.cs160.joleary.represent;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;

public class Detailview extends Activity {

    ListView lv;
    DetailviewAdapter detailviewAdapter;
    String[] names = {"Barabara Boxer", "Dianne Feinstein", "Barbara Lee"};
    String[] parties = {"Democratic (Senate)", "Democratic (House)", "Democratic (House)"};
    String[] termEnds = {"Term Ends MONTH YEAR", "Term Ends MONTH YEAR", "Term Ends MONTH YEAR"};
    String comLabel;
    String billLabel;
    String[] committees = {"Barbara Boxer Committees1\nBarbara Boxer Committees2\nBarbara Boxer Committees3", "Dianne Feinstein Committees1\nDianne Feinstein Committees2\nDianne Feinstein Committees3", "Barbara Lee Committees1\nBarbara Lee Committees2\nBarbara Lee Committees1"};
    String[] bills = {"Barbara Boxer Bills1\nBarbara Boxer Bills2\nBarbara Boxer Bills3", "Dianne Feinstein Bills1\nDianne Feinstein Bills2\nDianne Feinstein Bills3", "Barbara Lee Bills1\nBarbara Lee Bills2\nBarbara Lee Bills3"};
    int[] pictures = {R.drawable.barbara_boxer, R.drawable.dianne_feinstein, R.drawable.barbara_lee};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);
        lv = (ListView) findViewById(R.id.listView2);
        detailviewAdapter = new DetailviewAdapter(this, names, parties, termEnds, comLabel, billLabel, committees, bills, pictures);
        lv.setAdapter(detailviewAdapter);
    }

}
