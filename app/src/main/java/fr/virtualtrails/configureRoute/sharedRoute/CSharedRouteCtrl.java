package fr.virtualtrails.configureRoute.sharedRoute;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureDisplay.CConfigureDisplayM;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.configureRoute.viewRoute.CViewSharedRoutesM;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.configureRoute.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.homeMap.CHomeMapM;
import fr.virtualtrails.manageFriends.addFriend.CAddFriendsCtrl;
import fr.virtualtrails.manageFriends.informationFriend.CInformationFriendCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;

public class CSharedRouteCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;

    ListView shareRouteListView;
    Intent homeMap, configureRoute, configureDisplay,
            launchRoute, consultStatistics, managefriends,
            addFriends, infoFriend, sharedFriend, viewSharedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shared_route_gui);

        sharedFriend = getIntent();

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
        addFriends = new Intent(this, CAddFriendsCtrl.class);
        infoFriend = new Intent(this, CInformationFriendCtrl.class);
        sharedFriend = new Intent(this, CSharedRouteCtrl.class );
        viewSharedRoute = new Intent(this, CViewSharedRoutesCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.share_route_informative_part);
        if (CConfigureDisplayM.getInstance().pseudoSetted)
            informativePart.setText(CConfigureDisplayM.getInstance().pseudo);

        menu = (Spinner) findViewById(R.id.share_route_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.conf_route_menu_list, android.R.layout.simple_spinner_item);
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

        shareRouteListView = (ListView) findViewById(R.id.share_route_listView);
        CSharedRouteM.getInstance().initShareRouteM(shareRouteListView, this);
        CSharedRouteM.getInstance().readSharedRoutes();

        shareRouteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchViewShareRoute(CSharedRouteM.getInstance().routeList[position]);
            }
        });
    }

    public void launchViewShareRoute(String routeName){

        CViewSharedRoutesM.getInstance().preInitSharedViewRoute(routeName);
        startActivity(viewSharedRoute);
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
                startActivity(consultStatistics);
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
