package com.cs160.joleary.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.common.api.PendingResult;
import android.service.carrier.CarrierMessagingService.ResultCallback;

import java.util.ArrayList;

/**
 * Created by joleary on 2/19/16.
 */
public class PhoneToWatchService extends Service {

    private GoogleApiClient mApiClient;
    ArrayList<String> ids;
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    int num;
    String count;

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Which cat do we want to feed? Grab this info from INTENT
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();
//        final String catName = extras.getString("CAT_NAME");
        if (extras != null) {
            ids = extras.getStringArrayList("IDS");
            first_names = extras.getStringArrayList("FIRST_NAMES");
            last_names = extras.getStringArrayList("LAST_NAMES");
            parties = extras.getStringArrayList("PARTIES");
            chambers = extras.getStringArrayList("CHAMBERS");
            num = ids.size();
            count = String.valueOf(num);
        }





//        String WEARABLE_DATA_PATH = "/wearable_data";
//
//        // Create a DataMap object and send it to the data layer
//        DataMap dataMap = new DataMap();
//        for (int i = 0; i < num; i++) {
//            dataMap.putString("REP_" + i + "_ID", ids.get(i));
//            dataMap.putString("REP_" + i + "_FIRST_NAME", first_names.get(i));
//            dataMap.putString("REP_" + i + "_LAST_NAME", last_names.get(i));
//            dataMap.putString("REP_" + i + "_PARTY", parties.get(i));
//            dataMap.putString("REP_" + i + "_CHAMBER", chambers.get(i));
//        }
//        //Requires a new thread to avoid blocking the UI
//        new SendToDataLayerThread(WEARABLE_DATA_PATH, dataMap).start();

        // Send the message with the cat name
        new Thread(new Runnable() {
            @Override
            public void run() {
                //first, connect to the apiclient
                mApiClient.connect();
                //now that you're connected, send a massage with the cat name
                sendMessage("/" + "LETS SEND THINGS TO WATCH", "1231213 PHONETOWATCH-OKAY");
//                sendMessage("ANOTHER ONE YES PLEASE", "DSAUDHAS");
//                for (int i = 0; i < num; i++) {
//                    sendMessage("REP " + i + "ID", ids.get(i));
//                    sendMessage("REP" + i + "FIRST_NAME", first_names.get(i));
//                }

            }
        }).start();
        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBiner
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {
        //one way to send message: start a new thread and call .await()
        //see watchtophoneservice for another way to send a message
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            mApiClient, node.getId(), path, text.getBytes() ).await();
                    //4 arguments: api client, the node ID, the path (for the listener to parse),
                    //and the message itself (you need to convert it to bytes.)
                }
            }
        }).start();
    }

    class SendToDataLayerThread extends Thread {
        String path;
        DataMap dataMap;

        // Constructor for sending data objects to the data layer
        SendToDataLayerThread(String p, DataMap data) {
            path = p;
            dataMap = data;
        }

        public void run() {
            // Construct a DataRequest and send over the data layer
            PutDataMapRequest putDMR = PutDataMapRequest.create(path);
            putDMR.getDataMap().putAll(dataMap);
            PutDataRequest request = putDMR.asPutDataRequest();
            DataApi.DataItemResult result = Wearable.DataApi.putDataItem(mApiClient, request).await();
            if (result.getStatus().isSuccess()) {
                Log.v("myTag", "DataMap: " + dataMap + " sent successfully to data layer ");
            } else {
                // Log an error
                Log.v("myTag", "ERROR: failed to send DataMap to data layer");
            }

        }
    }

}
