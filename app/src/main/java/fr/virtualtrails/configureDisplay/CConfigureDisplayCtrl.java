package fr.virtualtrails.configureDisplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import fr.virtualtrails.R;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CConfigureDisplayCtrl extends AppCompatActivity {

    private Spinner menu;
    private TextView informativePart;
    private Button validate;
    private EditText pseudo;
    private CheckBox speed, distanceRemaining, altitude, totalDistance;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_display_gui);

        ///////////////////////////////////////

        configureDisplay = getIntent();

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

        informativePart = (TextView) findViewById(R.id.disp_informative_part);

        menu = (Spinner) findViewById(R.id.disp_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.disp_menu_list, android.R.layout.simple_spinner_item);
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

        pseudo = (EditText) findViewById(R.id.disp_pseudo);
        speed = (CheckBox) findViewById(R.id.disp_speed);
        distanceRemaining = (CheckBox) findViewById(R.id.disp_ditance_remaining);
        totalDistance = (CheckBox) findViewById(R.id.disp_total_distance);
        altitude = (CheckBox) findViewById(R.id.disp_altitude);

        validate = (Button) findViewById(R.id.bouton_valider);
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Affichage configuré !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                CConfigureDisplayM.getInstance().setDisplayModel(String.valueOf(pseudo.getText()), speed.isChecked(),
                        totalDistance.isChecked(), altitude.isChecked(), distanceRemaining.isChecked());
            }
        });
    }

    public void launchActivity(int position){

        switch (position){

            case 1 :
                startActivity(homeMap);
                break;

            case 2 :
                startActivity(managefriends);
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
