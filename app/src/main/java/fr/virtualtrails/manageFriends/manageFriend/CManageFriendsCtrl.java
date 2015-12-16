package fr.virtualtrails.manageFriends.manageFriend;

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

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.addFriend.CAddFriendsCtrl;
import fr.virtualtrails.manageFriends.informationFriend.CInformationFriendCtrl;
import fr.virtualtrails.manageFriends.informationFriend.CInformationFriendM;

public class CManageFriendsCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;

    ListView mListView;
    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, addFriends, infoFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_friends_gui);
        mListView = (ListView) findViewById(R.id.listView);

        CManageFriendsM.getInstance().ReadFriends(mListView, this);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchInfoFriend((String) parent.getItemAtPosition(position));
            }
        });

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
        infoFriend = new Intent(this, CInformationFriendCtrl.class);
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

    public void launchInfoFriend(String pPseudo){
        CInformationFriendM.getInstance().preInitViewFriend(pPseudo);
        startActivity(infoFriend);
    }

}
