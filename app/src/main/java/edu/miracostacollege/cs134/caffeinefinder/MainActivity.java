package edu.miracostacollege.cs134.caffeinefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import edu.miracostacollege.cs134.caffeinefinder.model.DBHelper;
import edu.miracostacollege.cs134.caffeinefinder.model.Location;
import edu.miracostacollege.cs134.caffeinefinder.model.LocationListAdapter;

//Done: (1) Implement the OnMapReadCallback interface for Google Maps
//Done: First, you'll need to compile GoogleMaps in build.gradle
//KindaDone: and add permissions and your Google Maps API key in the AndroidManifest.xml
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private DBHelper db;
    private List<Location> allLocationsList;
    private ListView locationsListView;
    private LocationListAdapter locationListAdapter;

    //member variable to store google map
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase(DBHelper.DATABASE_NAME);
        db = new DBHelper(this);
        db.importLocationsFromCSV("locations.csv");

        allLocationsList = db.getAllCaffeineLocations();
        locationsListView = findViewById(R.id.locationsListView);
        locationListAdapter = new LocationListAdapter(this, R.layout.location_list_item, allLocationsList);
        locationsListView.setAdapter(locationListAdapter);

        //TODO: (2) Load the support map fragment asynchronously
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng myPostion = new LatLng(33.190802, -117.301805);

        //add position to map
        map.addMarker(new MarkerOptions().position(myPostion).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.my_marker)));


        //lets move camera to our position
        CameraPosition cameraPosition = new CameraPosition.Builder().target(myPostion).zoom(15.0f).build();

        //update postition of camera
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

        //instruct map to move to this position
        map.moveCamera(cameraUpdate);

        //add all cofffee shops to map
        //loop through list and each location
        LatLng postion;
        for(Location location : allLocationsList)
        {
            postion = new LatLng(location.getLatitude(), location.getLongitude());
            map.addMarker(new MarkerOptions().position(postion).title(location.getName()));
        }
    }

    // Done: (3) Implement the onMapReady method, which will add a special "marker" for our current location,
    // Done: which is  33.190802, -117.301805  (OC4800 building)
    // Done: Then add normal markers for all the caffeine locations from the allLocationsList.
    // Done: Set the zoom level of the map to 15.0f


    // TODO: (4) Create a viewLocationsDetails(View v) method to create a new Intent to the
    // TODO: CaffeineDetailsActivity class, sending it the selectedLocation the user picked from the locationsListView
    public void viewLocationDetails(View v)
    {
        Intent detailsIntent;

        
    }
}
