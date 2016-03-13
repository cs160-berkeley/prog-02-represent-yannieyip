package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.support.wearable.view.GridViewPager;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView name;
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    ArrayList<String> term_ends = new ArrayList<String>();
    ArrayList<String> pic_urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        final Resources res = getResources();
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new GridPagerAdapter(this, getFragmentManager()));
//        addListenerOnButton();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
//            String catName = extras.getString("CAT_NAME");
            String ya = extras.getString("test");
            Log.d("LALALALLAALYESAAAAHHH", ya);
//            Log.d("In wear listener", "GOT CATNAME:" + catName);
        } else {
            Log.d("TEXTNULLLLLL?", "EXTRA NULL WHATTT");
        }

        pager.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                // Adjust page margins:
                //   A little extra horizontal spacing between pages looks a bit
                //   less crowded on a round display.
//                final boolean round = insets.isRound();
//                int rowMargin = res.getDimensionPixelOffset(R.dimen.page_row_margin);
//                int colMargin = res.getDimensionPixelOffset(round ?
//                        R.dimen.page_column_margin_round : R.dimen.page_column_margin);
//                pager.setPageMargins(rowMargin, colMargin);

                // GridViewPager relies on insets to properly handle
                // layout for round displays. They must be explicitly
                // applied since this listener has taken them over.
                pager.onApplyWindowInsets(insets);
                return insets;
            }
        });
    }
}

//    public void addListenerOnButton() {
//        final Context context = this;
//
//    }
//}
//
//        View view = new View(this);
//        name = (TextView) view.findViewById(R.id.name);
//
//        name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
////                sendIntent.putExtra("CAT_NAME", "Fred");
////                Log.d("In wear listener", "GOT CATNAME:" + catName);
//                startService(sendIntent);
//            }
//        });

//      mFeedBtn = (Button) findViewById(R.id.feed_btn);

//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();

//        if (extras != null) {
//            String catName = extras.getString("CAT_NAME");
//            mFeedBtn.setText("Feed " + catName);
//        }

//        mFeedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });
//    }