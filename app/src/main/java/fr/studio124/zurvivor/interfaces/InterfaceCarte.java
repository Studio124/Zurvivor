package fr.studio124.zurvivor.interfaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import fr.studio124.zurvivor.Fouille;
import fr.studio124.zurvivor.R;

/**
 * Created by Quentin on 08/02/2015.
 */
public class InterfaceCarte extends ActionBarActivity implements LocationListener {

    private Intent intent = null;

    private int userIcon, vetoIcon, medikitIcon, biereIcon, steackIcon, grenadeIcon, cercueilIcon, caddieIcon;

    private GoogleMap theMap;

    private LocationManager locMan;

    private Marker userMarker;

    private Marker[] placeMarkers;

    private final int MAX_PLACES = 20;

    private MarkerOptions[] places;

    private String search = "police",
                    choix = "armes";

    private Fouille fouille = null;

    private Circle circle = null;

    private boolean etatRadius = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interface_carte);

        userIcon = R.drawable.user_icon;
        vetoIcon = R.drawable.veto_icon;
        medikitIcon = R.drawable.medikit_icon;
        biereIcon = R.drawable.biere_icon;
        steackIcon = R.drawable.steack_icon;
        grenadeIcon = R.drawable.grenade_icon;
        cercueilIcon = R.drawable.cercueil_icon;
        caddieIcon = R.drawable.caddie_icon;

        fouille = new Fouille(this);

        if(theMap==null){
            //map not instantiated yet
            theMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.the_map)).getMap();
        }

        if(theMap != null){
            //ok - proceed
            theMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            placeMarkers = new Marker[MAX_PLACES];

            updatePlaces();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v("MyMapActivity", "location changed");
        //updatePlaces();
    }
    @Override
    public void onProviderDisabled(String provider){
        Log.v("MyMapActivity", "provider disabled");
    }
    @Override
    public void onProviderEnabled(String provider) {
        Log.v("MyMapActivity", "provider enabled");
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.v("MyMapActivity", "status changed");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(theMap!=null){
            locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(theMap!=null){
            locMan.removeUpdates(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemCarte = menu.findItem(R.id.action_carte),
                itemSearch = menu.findItem(R.id.action_search);

        itemCarte.setEnabled(false);
        itemSearch.setVisible(true);

        this.invalidateOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        boolean etat = false;


        if (id != 0) {
            switch (id) {
                case R.id.search_armes : {
                    search = "police";
                    choix = "armes";
                    etat = true;
                } break;
                case R.id.search_sante : {
                    search = "health|hospital|pharmacy";
                    choix = "sante";
                    etat = true;
                } break;
                case R.id.search_nourriture : {
                    search = "bakery|food|restaurant";
                    choix = "nourriture";
                    etat = true;
                } break;
                case R.id.search_supermarket : {
                    search = "grocery_or_supermarket|shopping_mall|store";
                    choix = "supermarket";
                    etat = true;
                } break;
                case R.id.search_boisson : {
                    search = "bar|cafe|liquor_store";
                    choix = "boisson";
                    etat = true;
                } break;
                case R.id.search_animaux : {
                    search = "pet_store|veterinary_care";
                    choix = "animaux";
                    etat = true;
                } break;
                case R.id.search_baston : {
                    search = "cemetery|funeral_home";
                    choix = "baston";
                    etat = true;
                } break;
                case R.id.action_chat : {
                    intent = new Intent(getApplicationContext(), InterfaceChat.class);
                    startActivity(intent);
                } break;
                case R.id.action_profil : {
                    intent = new Intent(getApplicationContext(), InterfaceProfil.class);
                    startActivity(intent);
                } break;
                case R.id.action_inventaire : {
                    intent = new Intent(getApplicationContext(), InterfaceInventaire.class);
                    startActivity(intent);
                } break;
            }

            if (etat) {

                try {
                    search = URLEncoder.encode(search, "UTF-8");
                } catch (UnsupportedEncodingException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                updatePlaces();
                etat = false;
            }

            if(item.isChecked())
                item.setChecked(false);
            else
                item.setChecked(true);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updatePlaces(){
        //update location
        locMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        Location lastLoc = locMan.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        double lat = lastLoc.getLatitude();
        double lng = lastLoc.getLongitude();

        LatLng lastLatLng = new LatLng(lat, lng);

        if(userMarker!=null) userMarker.remove();

        userMarker = theMap.addMarker(new MarkerOptions()
                .position(lastLatLng)
                .title("Vous")
                .icon(BitmapDescriptorFactory.fromResource(userIcon))
                .snippet("Dernier emplacement connu"));

        fouille.setUserPosition(userMarker);

        theMap.animateCamera(CameraUpdateFactory.newLatLng(lastLatLng), 3000, null);

        String placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location="+lat+","+lng+
                "&radius=1000&sensor=true" +
                "&types=" + search +
                "&key=AIzaSyBcQPtkBZTey9KVMJd6uP58IPtDIMX27sg";// + R.string.api_key_browser;

        new GetPlaces().execute(placesSearchStr);

        locMan.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 0, this);
    }

    private class GetPlaces extends AsyncTask<String, Void, String> {
        //fetch and parse place data

        @Override
        protected String doInBackground(String... placesURL) {
            //fetch places
            StringBuilder placesBuilder = new StringBuilder();

            //process search parameter string(s)
            for (String placeSearchURL : placesURL) {
                //execute search
                HttpClient placesClient = new DefaultHttpClient();

                try {
                    //try to fetch the data
                    HttpGet placesGet = new HttpGet(placeSearchURL);

                    HttpResponse placesResponse = placesClient.execute(placesGet);

                    StatusLine placeSearchStatus = placesResponse.getStatusLine();

                    if (placeSearchStatus.getStatusCode() == 200) {
                        //we have an OK response
                        HttpEntity placesEntity = placesResponse.getEntity();

                        InputStream placesContent = placesEntity.getContent();

                        InputStreamReader placesInput = new InputStreamReader(placesContent);

                        BufferedReader placesReader = new BufferedReader(placesInput);

                        String lineIn;
                        while ((lineIn = placesReader.readLine()) != null) {
                            placesBuilder.append(lineIn);
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }

            return placesBuilder.toString();
        }

        protected void onPostExecute(String result) {
            //parse place data returned from Google Places
            if(placeMarkers!=null){
                for(int pm=0; pm<placeMarkers.length; pm++){
                    if(placeMarkers[pm]!=null)
                        placeMarkers[pm].remove();
                }
            }

            theMap.setOnMarkerClickListener(fouille);
            fouille.setChoix(choix);

            try {
                //parse JSON
                JSONObject resultObject = new JSONObject(result);

                JSONArray placesArray = resultObject.getJSONArray("results");

                places = new MarkerOptions[placesArray.length()];

                //loop through places
                for (int p=0; p<placesArray.length(); p++) {
                    //parse each place
                    boolean missingValue=false;

                    LatLng placeLL=null;
                    String placeName="";
                    String vicinity="";
                    int currIcon = userIcon;

                    try{
                        //attempt to retrieve place data values
                        missingValue=false;

                        JSONObject placeObject = placesArray.getJSONObject(p);

                        JSONObject loc = placeObject.getJSONObject("geometry").getJSONObject("location");

                        placeLL = new LatLng(
                                Double.valueOf(loc.getString("lat")),
                                Double.valueOf(loc.getString("lng")));

                        JSONArray types = placeObject.getJSONArray("types");

                        for(int t=0; t<types.length(); t++){
                            //what type is it
                            String thisType=types.get(t).toString();

                            if(search.equals("bar%7Ccafe%7Cliquor_store")){
                                currIcon = biereIcon;
                                break;
                            }
                            else if(search.equals("bakery%7Cfood%7Crestaurant")){
                                currIcon = steackIcon;
                            }
                            else if(search.equals("cemetery%7Cfuneral_home")){
                                currIcon = cercueilIcon;
                            }
                            else if(search.equals("grocery_or_supermarket%7Cshopping_mall%7Cstore")){
                                currIcon = caddieIcon;
                            }
                            else if(search.equals("health%7Chospital%7Cpharmacy")){
                                currIcon = medikitIcon;
                            }
                            else if(search.equals("pet_store%7Cveterinary_care")){
                                currIcon = vetoIcon;
                            }
                            else if(search.equals("police")){
                                currIcon = grenadeIcon;
                            }
                        }

                        vicinity = placeObject.getString("vicinity");

                        placeName = placeObject.getString("name");
                    }
                    catch(JSONException jse){
                        missingValue=true;
                        jse.printStackTrace();
                    }


                    if(missingValue)    places[p]=null;
                    else {

                        places[p] = new MarkerOptions()
                                .position(placeLL)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.fromResource(currIcon))
                                .snippet(vicinity);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            if(places!=null && placeMarkers!=null){
                for(int p=0; p<places.length && p<placeMarkers.length; p++){
                    //will be null if a value was missing
                    if(places[p]!=null) {
                        placeMarkers[p] = theMap.addMarker(places[p]);
                    }
                }
            }
        }
    }

    public void onRadius(View view) {

        if (!etatRadius) {
            // Instantiates a new CircleOptions object and defines the center and radius
            CircleOptions circleOptions = new CircleOptions()
                    .center(userMarker.getPosition())
                    .fillColor(0x40336600)
                    .strokeColor(0x40000000)
                    .radius(800); // In meters

            // Get back the mutable Circle
            circle = theMap.addCircle(circleOptions);

            etatRadius = true;
        } else {
            circle.remove();
            etatRadius = false;
        }
    }
}