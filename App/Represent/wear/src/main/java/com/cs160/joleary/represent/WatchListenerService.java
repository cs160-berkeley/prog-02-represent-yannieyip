package com.cs160.joleary.represent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import android.support.v4.content.LocalBroadcastManager;



/**
 * Created by joleary and noon on 2/19/16 at very late in the night. (early in the morning?)
 */
public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path, either "/FRED" or "/LEXY"
    // These paths serve to differentiate different phone-to-watch messages
    private static final String FRED_FEED = "/Fred";
    private static final String LEXY_FEED = "/Lexy";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in sendmessage to differentiate use cases
        //(here, fred vs lexy)

        super.onMessageReceived(messageEvent);

        String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
        Log.d("ON WATCH FROM PHONE", value);

//        if( messageEvent.getPath().equalsIgnoreCase( FRED_FEED ) ) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class );
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Fred");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Fred");
//            startActivity(intent);
//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );

//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
//        } else {
//            super.onMessageReceived( messageEvent );
//        }
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Log.d("OOOONNN DATA CHANGED", "EHEHEEREREREREE UGH");

        DataMap dataMap;
        for (DataEvent event : dataEvents) {
            Log.v("myTag", "DataMap received on watch: " + DataMapItem.fromDataItem(event.getDataItem()).getDataMap());
            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals("/wearble_data")) {
                }
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Log.d("HIHHIII", "IN THIS LOOOOP??");


                // Broadcast DataMap contents to wearable activity for display
                // The content has the golf hole number and distances to the front,
                // middle and back pin placements.

                Intent messageIntent = new Intent(this, MainActivity.class);
                messageIntent.setAction(Intent.ACTION_SEND);
                messageIntent.putExtra("datamap", dataMap.toBundle());
                LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
//
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("TEST", "OKAYGETTTOOOOITT!");
                startActivity(messageIntent);
//            }
            }
        }
    }
}
