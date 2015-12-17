package fr.virtualtrails.configureRoute.viewRoute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureDisplay.CConfigureDisplayM;
import fr.virtualtrails.configureRoute.addRoute.CAddRouteCtrl;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.configureRoute.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CViewRouteCtrl extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Spinner menu;
    private TextView informativePart, routeName;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, addItineraire, viewRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_route_gui);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ///////////////////////////////

        viewRoute = getIntent();

        initActivity();
        initWidgets();
    }

    public void initActivity(){

        homeMap = new Intent(this, CHomeMapCtrl.class);
        configureRoute = new Intent(this, CConfigureRouteCtrl.class);
        configureDisplay = new Intent(this, CConfigureDisplayCtrl.class);
        consultStatistics = new Intent(this, CConsultStatisticsCtrl.class);
        managefriends = new Intent(this, CManageFriendsCtrl.class);
        launchRoute = new Intent(this, CLaunchRouteCtrl.class);
        addItineraire = new Intent(this, CAddRouteCtrl.class);
        viewRoute = new Intent(this, CViewRouteCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.view_route_informative_part);
        if (CConfigureDisplayM.getInstance().pseudoSetted)
            informativePart.setText(CConfigureDisplayM.getInstance().pseudo);

        routeName = (TextView) findViewById(R.id.view_route_name);
        routeName.setText(CViewRouteM.getInstance().routeName);

        // remplissage menu

        menu = (Spinner) findViewById(R.id.view_route_menu);
        ArrayAdapter<CharSequence> adapterMenu = ArrayAdapter.createFromResource(this,
                R.array.conf_route_menu_list, android.R.layout.simple_spinner_item);
        adapterMenu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        menu.setAdapter(adapterMenu);

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                launchActivity(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //a completer
        //CConfigureDisplay.getIns....
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
                startActivity(consultStatistics);
                break;

            case 5 :
                startActivity(launchRoute);
                break;

            case 6 :
                // realité augmentée
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        CViewRouteM.getInstance().initViewRoute(mMap);
        CViewRouteM.getInstance().readRoute();
    }
}
