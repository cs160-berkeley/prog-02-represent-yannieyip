package com.cs160.joleary.represent;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.DataMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.common.api.PendingResult;
import android.service.carrier.CarrierMessagingService.ResultCallback;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import java.lang.String;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import java.util.ArrayList;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //there's not much interesting happening. when the buttons are pressed, they start
    //the PhoneToWatchService with the cat name passed in.

    private Button currLocButton;
    private ImageButton zipCodeButton;
    private GoogleApiClient mGoogleApiClient;
    private String API_KEY = "40762425261540849256549d0ea423ba";
    private String host = "http://congress.api.sunlightfoundation.com";
    String lat;
    String lon;
    String apikey;
    String basicUrl;
    ArrayList<String> ids = new ArrayList<String>();
    ArrayList<String> first_names = new ArrayList<String>();
    ArrayList<String> last_names = new ArrayList<String>();
    ArrayList<String> parties = new ArrayList<String>();
    ArrayList<String> chambers = new ArrayList<String>();
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> websites = new ArrayList<String>();
    ArrayList<String> term_ends = new ArrayList<String>();
    ArrayList<String> pic_urls = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        currLocButton = (Button) findViewById(R.id.currrentLocation);
        zipCodeButton = (ImageButton) findViewById(R.id.check);

        currLocButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getLegislators();
            }
        });

        zipCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getLegislators();
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
        getBasicInfo();
    }

    private void getBasicInfo() {
        String method = "/legislators/locate?";
        String str_lat = "latitude=" + lat;
        String str_lon = "&longitude=" + lon;
        apikey = "&apikey=" + API_KEY;
        basicUrl = host + method + str_lat + str_lon + apikey;

        Ion.with(getBaseContext())
                .load(basicUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                        Log.d("msg", "BASIC INFO WORKING");
                        JsonArray results = result.getAsJsonArray("results");
                        int len = results.size();

                        for (int i = 0; i < len; i++) {
                            String id = results.get(i).getAsJsonObject().get("bioguide_id").toString();
                            id = id.replaceAll("^\"|\"$", "");
                            ids.add(id);
                            String first_name = results.get(i).getAsJsonObject().get("first_name").toString();
                            first_names.add(first_name);
                            String last_name = results.get(i).getAsJsonObject().get("last_name").toString();
                            last_names.add(last_name);
                            String party = results.get(i).getAsJsonObject().get("party").toString();
                            parties.add(party);
                            String chamber = results.get(i).getAsJsonObject().get("chamber").toString();
                            chambers.add(chamber);
                            String email = results.get(i).getAsJsonObject().get("oc_email").toString();
                            emails.add(email);
                            String website = results.get(i).getAsJsonObject().get("website").toString();
                            websites.add(website);
                            String term_end = results.get(i).getAsJsonObject().get("term_end").toString();
                            term_ends.add(term_end);
                            String pic_url = "https://theunitedstates.io/images/congress/225x275/" + id + ".jpg";
                            pic_urls.add(pic_url);

                        }
                        Intent intent = new Intent(getBaseContext(), Overview.class);
                        intent.putExtra("IDS", ids);
                        intent.putExtra("FIRST_NAMES", first_names);
                        intent.putExtra("LAST_NAMES", last_names);
                        intent.putExtra("PARTIES", parties);
                        intent.putExtra("CHAMBERS", chambers);
                        intent.putExtra("EMAILS", emails);
                        intent.putExtra("WEBSITES", websites);
                        intent.putExtra("TERM_ENDS", term_ends);
                        intent.putExtra("PIC_URLS", pic_urls);
                        startActivity(intent);

                        Intent sendIntent = new Intent(getBaseContext(), PhoneToWatchService.class);
                        sendIntent.putExtra("IDS", ids);
                        sendIntent.putExtra("FIRST_NAMES", first_names);
                        sendIntent.putExtra("LAST_NAMES", last_names);
                        sendIntent.putExtra("PARTIES", parties);
                        sendIntent.putExtra("CHAMBERS", chambers);
                        startService(sendIntent);
                    }
                });
    }

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
