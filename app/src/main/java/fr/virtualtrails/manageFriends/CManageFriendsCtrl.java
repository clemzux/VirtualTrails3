package fr.virtualtrails.manageFriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.testBdd.CQuery;

public class CManageFriendsCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;

    public ArrayList<String> frendsList = null;

    ListView mListView;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, addFriends;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_friends_gui);
        mListView = (ListView) findViewById(R.id.listView);



        //sql request read BDD

        ArrayList arrayList = new ArrayList();
        final CFriendList cFriendList = new CFriendList(arrayList);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("friends");

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject p = objects.get(i);
                        frendsList.add(p.getString("pseudo"));
                        Log.i("testbdd", "linked " + i + " " + CFriendList.array.get(i));
                    }
                } else {
                    Log.i("testbdd", "pb?");
                }
                Log.i("testbdd", "set appelé");
                //cFriendList.setListFriends(linkedList);
            }
        });

        //String[] prenoms = new String[cFriendList.array.size()];
        //Log.i("testbdd", "taille prenomz : " + prenoms.length + " taille pseudos " + cFriendList.array.size());
        /*
        for (int j=0;j<linkedList.size();j++) {
            prenoms[j] = String.valueOf(linkedList.get(j));


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CManageFriendsCtrl.this, android.R.layout.simple_list_item_1, prenoms);

        mListView.setAdapter(adapter);*/

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

    public void Query(String key) {
        // query try hard


    }

}
