package fr.virtualtrails.manageFriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseObject;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;

public class CManageFriendsCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;

    ListView mListView;
    String[] prenoms = new String[]{
            "Ami 1", "Ami 2", "Ami 3"};

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, addFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_friends_gui);

        mListView = (ListView) findViewById(R.id.listView);
        // On charge un tableau avec la liste des prenoms de la listeview
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CManageFriendsCtrl.this, android.R.layout.simple_list_item_1, prenoms);
        // On affiche la liste View avec les prénoms
        mListView.setAdapter(adapter);

        managefriends = getIntent();
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
        addFriends = new Intent(this, CAddFriendsCtrl.class);
    }

    public void initWidgets(){

        informativePart = (TextView) findViewById(R.id.manage_friends_informative_part);

        menu = (Spinner) findViewById(R.id.manage_friends_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.manage_friends_menu_list, android.R.layout.simple_spinner_item);
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

    public void testbdd(View v){

    }

    public void addFriends(View v){

        startActivity(addFriends);
    }
// Controleur de la ListeView
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
        // see http://developer.android.com/guide/topics/ui/layout/listview.html
    }

}
