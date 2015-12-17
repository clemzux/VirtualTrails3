package fr.virtualtrails.statistics.statisticView;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.homeMap.CHomeMapM;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CStatisticViewCtrl extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner menu;
    private TextView informativePart, routeName, hStart, hFinish, totDistance, averageSpeed, altMax;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_view_gui);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        /////////////////////////////////

        initWidgets();
        initActivity();
    }

    public void initActivity(){

        homeMap = new Intent(this, CHomeMapCtrl.class);
        configureRoute = new Intent(this, CConfigureRouteCtrl.class);
        configureDisplay = new Intent(this, CConfigureDisplayCtrl.class);
        consultStatistics = new Intent(this, CConsultStatisticsCtrl.class);
        managefriends = new Intent(this, CManageFriendsCtrl.class);
        launchRoute = new Intent(this, CLaunchRouteCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.view_stats_informative_part);
        routeName = (TextView) findViewById(R.id.view_stats_route_name);
        hStart = (TextView) findViewById(R.id.view_stats_h_start);
        hFinish = (TextView) findViewById(R.id.view_stats_h_finish);
        totDistance = (TextView) findViewById(R.id.view_stats_total_distance);
        averageSpeed = (TextView) findViewById(R.id.view_stats_average_speed);
        altMax = (TextView) findViewById(R.id.view_stats_alt_max);

        menu = (Spinner) findViewById(R.id.view_stat_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.consult_stat_menu_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapter);

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                launchActivity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CStatisticViewM.getInstance().setViewers(routeName, hStart, hFinish, totDistance, averageSpeed, altMax);
        CStatisticViewM.getInstance().setMap(mMap);
        CStatisticViewM.getInstance().readRouteStats();
        CStatisticViewM.getInstance().readRoute();
    }

    public void launchActivity(int position){

        switch (position){

            case 1 :
                startActivity(homeMap);
                break;

            case 2 :
                startActivity(configureDisplay);
                break;

            case 3 :
                startActivity(managefriends);
                break;

            case 4 :
                startActivity(configureRoute);
                break;

            case 5 :
                startActivity(launchRoute);
                break;

            case 6 :
                // realité augmentée
                break;
        }
    }
}
