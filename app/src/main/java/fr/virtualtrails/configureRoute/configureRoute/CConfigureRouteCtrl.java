package fr.virtualtrails.configureRoute.configureRoute;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureDisplay.CConfigureDisplayM;
import fr.virtualtrails.configureRoute.sharedRoute.CSharedRouteCtrl;
import fr.virtualtrails.configureRoute.viewRoute.CViewRouteCtrl;
import fr.virtualtrails.configureRoute.viewRoute.CViewRouteM;
import fr.virtualtrails.configureRoute.addRoute.CAddRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.configureRoute.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CConfigureRouteCtrl extends AppCompatActivity {

    ListView mListView;
    //String[] routeName = new String[]{
    //        "itinéraire 1", "itinéraire 2", "itinéraire 3"};
    private GoogleMap mMap;
    private Spinner menu;
    private TextView informativePart;

    Intent homeMap, configureRoute, configureDisplay,
            launchRoute, consultStatistics, managefriends, addItineraire, viewRoute, sharedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_route_gui);

        ////////////////////////////

        configureRoute = getIntent();

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
        sharedRoute = new Intent(this, CSharedRouteCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.conf_route_informative_part);
        if (CConfigureDisplayM.getInstance().pseudoSetted)
            informativePart.setText(CConfigureDisplayM.getInstance().pseudo);

        // remplissage menu

        menu = (Spinner) findViewById(R.id.conf_route_menu);
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

        // remplissage listview

        mListView = (ListView) findViewById(R.id.listView);
        CConfigureRouteM.getInstance().initConfigureRouteM(mListView, this);
        CConfigureRouteM.getInstance().readRouteNames();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchViewRoute((String)parent.getItemAtPosition(position));
            }
        });
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

    public void addRoute(View v){

        startActivity(addItineraire);
    }

    public void launchViewRoute(String pRouteName){
        System.out.println(pRouteName);
        CViewRouteM.getInstance().preInitViewRoute(pRouteName);

        startActivity(viewRoute);
    }

    public void OpenSharedRoutes(View v){

        startActivity(sharedRoute);
    }
}
