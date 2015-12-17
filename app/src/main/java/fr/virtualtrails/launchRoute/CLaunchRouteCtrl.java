package fr.virtualtrails.launchRoute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.homeMap.CHomeMapM;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CLaunchRouteCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;
    private ListView launchRouteListView;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.launch_route_gui);

        launchRoute = getIntent();
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

        // partie informative
        informativePart = (TextView) findViewById(R.id.launch_route_informative_part);

        // initiation de la listview qui contiendra touts les itinéraires
        launchRouteListView = (ListView) findViewById(R.id.launch_route_list_view);
        CLaunchRouteM.getInstance().initClaunchRouteM(launchRouteListView, this);
        CLaunchRouteM.getInstance().readRouteNames();
        launchRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchRouteAtHomeActivity((String)parent.getItemAtPosition(position));
            }
        });

        // initiation du menu permettant de circuler d'une activité a l'autre dans l'application
        menu = (Spinner) findViewById(R.id.launch_route_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.launch_route_list, android.R.layout.simple_spinner_item);
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
                startActivity(configureRoute);
                break;

            case 6 :
                // realité augmentée
                break;
        }
    }

    public void launchRouteAtHomeActivity(String pRouteName){
        CHomeMapM.getInstance().setRouteMode(pRouteName);
        startActivity(homeMap);
    }
}
