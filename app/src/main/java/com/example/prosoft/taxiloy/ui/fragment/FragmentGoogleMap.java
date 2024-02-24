package com.example.prosoft.taxiloy.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prosoft.taxiloy.R;
import com.example.prosoft.taxiloy.ui.Model;
import com.example.prosoft.taxiloy.ui.activity.BaseActivity;
import com.example.prosoft.taxiloy.ui.activity.MainScreenActivity;
import com.example.prosoft.taxiloy.ui.objects.Driver;
import com.example.prosoft.taxiloy.ui.utils.DirectionsJSONParser;
import com.example.prosoft.taxiloy.ui.utils.SystemUtils;
import com.example.prosoft.taxiloy.ui.view.MapWrapperLayout;
import com.example.prosoft.taxiloy.ui.view.PopupInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.simonvt.menudrawer.Position;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/**
 * Created by prosoft on 12/10/15.
 */
public class FragmentGoogleMap extends BaseFragment implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private static FragmentGoogleMap instance;
    private Marker mPositionMarker;
    private Location myLocation;
    private int LENGTH_OF_ICON_LOCATION = 60;
    private int WIDTH_OF_ICON_LOCATION = 45;
    private ArrayList<Driver> driverArrayList;
    private Map<Marker, Driver> driverMarkerMap;
    public static boolean isReady;
    private BaseActivity context;
    private MapWrapperLayout mapWrapperLayout;
    private boolean isDisableClickMarker = false;
    private ArrayList<LatLng> markerPoints = new ArrayList<>();
    private String API_KEY = "AIzaSyARJEkT2c5am8paxVyYMq10bTLmzESj-8I";

    //    public static FragmentGoogleMap getInstance() {
//        if (instance == null) {
//            instance = new FragmentGoogleMap();
//        }
//        return instance;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);
        setUpMap();

        getMap().setInfoWindowAdapter(this);
        getMap().setOnMarkerClickListener(this);
        getMap().setOnInfoWindowClickListener(this);
        mapWrapperLayout = (MapWrapperLayout) v.findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(getMap(), getPixelsFromDp(getActivity(), 20));
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isReady = true;
        context = (BaseActivity) getActivity();
    }

    private void setUpMap() {
        getMap().setMyLocationEnabled(false);
        getMap().getUiSettings().setMapToolbarEnabled(false);

    }

    public void updateLocation(Location location) {
        if (location == null)
            return;
        getMap().clear();
        myLocation = location;
        Bitmap resizeMapIcons = SystemUtils.resizeMapIcons(R.drawable.icon_location,
                WIDTH_OF_ICON_LOCATION, LENGTH_OF_ICON_LOCATION, getActivity());
        mPositionMarker = getMap().addMarker(new MarkerOptions()
                .flat(true)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons)).anchor(0.5f, 0.5f)
                .position(new LatLng(location.getLatitude(), location.getLongitude())));

        animateMarker(mPositionMarker, location);
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location
                .getLatitude(), location.getLongitude()), 18));
    }

    public void updatePassengerLocation(Location location) {
        if (location == null)
            return;
        if (markerPoints.size() > 0) {
            markerPoints.clear();
            googleMap.clear();
        }
        markerPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        getMap().clear();
        Bitmap resizeMapIcons = SystemUtils.resizeMapIcons(R.drawable.icon_location,
                WIDTH_OF_ICON_LOCATION, LENGTH_OF_ICON_LOCATION, getActivity());
        Marker marker = getMap().addMarker(new MarkerOptions()
                .flat(true)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons)).anchor(0.5f, 0.5f)
                .position(new LatLng(location.getLatitude(), location.getLongitude())));

        animateMarker(marker, location);
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location
                .getLatitude(), location.getLongitude()), 15));
    }

    public void updateDriver(Location location) {
        if (location == null)
            return;
        markerPoints.add(new LatLng(location.getLatitude(), location.getLongitude()));
        if (markerPoints.size() >= 2) {
            LatLng origin = markerPoints.get(0);
            LatLng dest = markerPoints.get(1);

            String url = getDirectionsUrl(origin, dest);

            DownloadTask downloadTask = new DownloadTask();

            downloadTask.execute(url);
        }

        Bitmap resizeMapIcons = SystemUtils.resizeMapIcons(R.drawable.icon_map_logo,
                WIDTH_OF_ICON_LOCATION, LENGTH_OF_ICON_LOCATION, getActivity());
        Marker marker = getMap().addMarker(new MarkerOptions()
                .flat(true)
                .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons)).anchor(0.5f, 0.5f)
                .position(new LatLng(location.getLatitude(), location.getLongitude())));

        animateMarker(marker, location);
        getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location
                .getLatitude(), location.getLongitude()), 15));
    }

    public void setCarsLocation() {
        for (Driver driver : getDriverArrayList()) {
            LatLng someWhere = new LatLng(Double.valueOf(driver.geox), Double.valueOf(driver.geoy));
            Bitmap resizeMapIcons = SystemUtils.resizeMapIcons(R.drawable.icon_map_logo,
                    WIDTH_OF_ICON_LOCATION, LENGTH_OF_ICON_LOCATION, getActivity());
            Marker marker = getMap().addMarker(new MarkerOptions().flat(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons))
                    .anchor(0.5f, 0.5f).position(someWhere));
            getDriverMarkerMap().put(marker, driver);
        }
    }

    private void animateMarker(final Marker marker, final Location location) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final LatLng startLatLng = marker.getPosition();
        final double startRotation = marker.getRotation();
        final long duration = 500;

        final Interpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);

                double lng = t * location.getLongitude() + (1 - t)
                        * startLatLng.longitude;
                double lat = t * location.getLatitude() + (1 - t)
                        * startLatLng.latitude;

                float rotation = (float) (t * location.getBearing() + (1 - t)
                        * startRotation);

                marker.setPosition(new LatLng(lat, lng));
                marker.setRotation(rotation);

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        if(marker.getSnippet() == null){
//            getMap().moveCamera(CameraUpdateFactory.zoomIn());
//        }
//        Driver driver = getDriverMarkerMap().get(marker);
//        Model.getInstance().bookTheRide(SystemUtils.getAccessToken(getActivity()),
//                myLocation, driver.idcar, SystemUtils.getIDPassenger(getActivity()));
//        Intent in=new Intent(Intent.ACTION_CALL, Uri.parse("tel:0862966805"));
//        try{
//            startActivity(in);
//        }
//
//        catch (android.content.ActivityNotFoundException ex){
//            Toast.makeText(getActivity(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public View getInfoWindow(Marker marker) {

        final Driver driver = getDriverMarkerMap().get(marker);
        PopupInfo popupInfo = new PopupInfo(getActivity(), driver, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }, myLocation, context.getBusyIndicator("Book car ... "));
        mapWrapperLayout.setMarkerWithInfoWindow(marker, popupInfo);
        return popupInfo;

    }

    @Override
    public View getInfoContents(Marker arg0) {
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("isDisableClickMarker", isDisableClickMarker + "");
        if (!isDisableClickMarker) {
            if (!marker.getId().equalsIgnoreCase(mPositionMarker.getId())) {
                marker.showInfoWindow();
            }
            LatLng markerLocation = marker.getPosition();
            getMap().animateCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 20));

        }
        return true;
    }

    public ArrayList<Driver> getDriverArrayList() {
        return driverArrayList;
    }

    public void setDriverArrayList(ArrayList<Driver> driverArrayList) {
        this.driverArrayList = driverArrayList;
    }

    public Map<Marker, Driver> getDriverMarkerMap() {
        if (driverMarkerMap == null) {
            driverMarkerMap = new HashMap<>();
        }
        return driverMarkerMap;
    }

    private GoogleMap getMap() {
        if (googleMap == null) {
            googleMap = ((SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

        }
        return googleMap;
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public boolean isDisableClickMarker() {
        return isDisableClickMarker;
    }

    public void setIsDisableClickMarker(boolean isDisableClickMarker) {
        this.isDisableClickMarker = isDisableClickMarker;
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        String key = "key=" + API_KEY;

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor ;

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.i("Data","" + data);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }


}
