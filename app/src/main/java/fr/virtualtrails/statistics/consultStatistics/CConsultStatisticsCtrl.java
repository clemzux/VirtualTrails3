package fr.virtualtrails.statistics.consultStatistics;

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
import fr.virtualtrails.configureDisplay.CConfigureDisplayM;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.statisticView.CStatisticViewCtrl;
import fr.virtualtrails.statistics.statisticView.CStatisticViewM;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.configureRoute.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CConsultStatisticsCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;
    private ListView statList;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, statView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consult_statistics_gui);

        consultStatistics = getIntent();
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
        statView = new Intent(this, CStatisticViewCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.consult_stat_informative_part);
        if (CConfigureDisplayM.getInstance().pseudoSetted)
            informativePart.setText(CConfigureDisplayM.getInstance().pseudo);

        menu = (Spinner) findViewById(R.id.consult_stat_menu);
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // initialisation de la liste de stats

        statList = (ListView) findViewById(R.id.consult_stat_listview);
        CConsultStatisticsM.getInstance().initConsultM(statList, this);
        CConsultStatisticsM.getInstance().readStats();

        statList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchViewStats(CConsultStatisticsM.getInstance().objectId.get(position), position);
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

    public void launchViewStats(String pObjectId, int pPosition){
        CStatisticViewM.getInstance().initStatViewM(pObjectId);
        CStatisticViewM.getInstance().setRouteName(CConsultStatisticsM.getInstance().routeNames[pPosition]);
        startActivity(statView);
    }
}
