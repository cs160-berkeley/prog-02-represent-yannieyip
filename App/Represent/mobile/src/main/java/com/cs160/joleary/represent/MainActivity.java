package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import java.lang.String;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import org.json.JSONArray;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
//    private static final String TWITTER_KEY = "j4VhUVhRTbuLAmz42rvQbtuBg";
//    private static final String TWITTER_SECRET = "rX8rHJuEwbK2ikSx9Uhaaj9XYb0zdlCVz7mHSwGCO0B4sbRkda";

    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button currLocButton;
    private ImageButton zipCodeButton;
    private GoogleApiClient mGoogleApiClient;
    private String API_KEY = "40762425261540849256549d0ea423ba";
    private String host = "http://congress.api.sunlightfoundation.com";
    String method;
    String lat;
    String lon;
    String apikey;
    String url;
    ArrayList<Representative> reps = new ArrayList<Representative>();
//    ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
//        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        addListenerOnButton();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(AppIndex.API).build();
        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();
        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);
        // Getting Current Location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null){
                //Sets global vars lat and lon
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 200000, 0, this);

        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        // Getting latitude of the current location
        double latitude = loc.getLatitude();
        lat = String.valueOf(latitude);
        Log.d("latitude", String.valueOf(latitude));

        // Getting longitude of the current location
        double longitude = loc.getLongitude();
        lon = String.valueOf(longitude);
        Log.d("longitude", String.valueOf(longitude));
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void addListenerOnButton() {
        final Context context = this;
        currLocButton = (Button) findViewById(R.id.currrentLocation);
        zipCodeButton = (ImageButton) findViewById(R.id.check);

        currLocButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getLegislators();
                Intent intent = new Intent(context, Overview.class);
                startActivity(intent);

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Fred");
                startService(sendIntent);
            }
        });

        zipCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getLegislators();
                Intent intent = new Intent(context, Overview.class);
                startActivity(intent);

                Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                sendIntent.putExtra("CAT_NAME", "Lexy");
                startService(sendIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connResult) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getLegislators() {
        method = "/legislators/locate?";
        String str_lat = "latitude=" + lat;
        String str_lon = "&longitude=" + lon;
        apikey = "&apikey=" + API_KEY;
        url = host + method + str_lat + str_lon + apikey;
        Log.d("url", url);
        getBasicInfo();
    }

    private void getBasicInfo() {
        Ion.with(getBaseContext())
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error

                        Log.d("msg", "WORKING");
                        JsonArray results = result.getAsJsonArray("results");
                        int len = results.size();

                        for (int i = 0; i < len; i++) {
                            String id = results.get(i).getAsJsonObject().get("bioguide_id").toString();
                            id = id.replaceAll("^\"|\"$", "");
                            Log.d("IIIDDDD", id);
                            String first_name = results.get(i).getAsJsonObject().get("first_name").toString();
                            String last_name = results.get(i).getAsJsonObject().get("last_name").toString();
                            String party = results.get(i).getAsJsonObject().get("party").toString();
                            String chamber = results.get(i).getAsJsonObject().get("chamber").toString();
                            String email = results.get(i).getAsJsonObject().get("oc_email").toString();
                            String website = results.get(i).getAsJsonObject().get("website").toString();
                            String pic_url = "https://theunitedstates.io/images/congress/225x275/" + id + ".jpg";
                            Log.d("PIC_URL", pic_url);

                            Representative rep = new Representative(first_name, last_name, party, chamber, email, website, pic_url);
                            reps.add(i, rep);
                        }
                    }
                });
        getDetailInfo();
    }

    public void getDetailInfo() {
        Log.d("detail info", "GET COMMITTEES AND BILLS");
//        getTweet();
    }

//    public void getTweet() {
//        TwitterSession session = Twitter.getSessionManager().getActiveSession();
//        TwitterCore.getInstance().getApiClient(session).getStatusesService().userTimeline(
//                null,"RepBarbaraLee", 1, null, null, null, null, null, null,
//                new Callback<List<Tweet>>() {
//
//                    @Override
//                    public void success(Result<List<Tweet>> result) {
//                        Log.d("success", "SUCCESS");
////                        User user = userResult.data;
////                        String imageUrl = user.profileImageUrl;
//                        for (Tweet t : result.data) {
//                            tweets.add(t);
//                            Log.d("tweeeeet", "TWEET IS" + t.text);
//                        }
//                    }
//                    @Override
//                    public void failure(TwitterException exception) {
//                        Log.d("failure", "TWEET FAILURE");
//                    }
//                });
//
//    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cs160.joleary.represent/http/host/path")
        );
        AppIndex.AppIndexApi.start(mGoogleApiClient, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.cs160.joleary.represent/http/host/path")
        );
        AppIndex.AppIndexApi.end(mGoogleApiClient, viewAction);
        mGoogleApiClient.disconnect();
    }
}
