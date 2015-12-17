package fr.virtualtrails.manageFriends.addFriend;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import fr.virtualtrails.R;
import fr.virtualtrails.configureDisplay.CConfigureDisplayCtrl;
import fr.virtualtrails.configureRoute.configureRoute.CConfigureRouteCtrl;
import fr.virtualtrails.statistics.consultStatistics.CConsultStatisticsCtrl;
import fr.virtualtrails.homeMap.CHomeMapCtrl;
import fr.virtualtrails.launchRoute.CLaunchRouteCtrl;
import fr.virtualtrails.manageFriends.informationFriend.CInformationFriendCtrl;
import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

public class CAddFriendsCtrl extends AppCompatActivity {
    private Spinner menu;
    private TextView informativePart;

    Intent homeMap, configureRoute, configureDisplay, launchRoute, consultStatistics, managefriends, addFriends, infoFriend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends_gui);

        final EditText pseudo = (EditText) findViewById(R.id.edit_pseudo);
        final EditText num = (EditText) findViewById(R.id.edit_num);
        final EditText mail = (EditText) findViewById(R.id.edit_mail);
        Button valider = (Button) findViewById(R.id.bouton_valider);

        // listeners
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject friend = new ParseObject("friends");
                friend.put("pseudo", pseudo.getText().toString());
                friend.put("num", Integer.parseInt(num.getText().toString()));
                friend.put("mail", mail.getText().toString());
                friend.saveInBackground();
                Snackbar.make(view, "Ami ajouté !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                // Si on a envie d'envoyer un mail via un mini-client
                // sendEmail("azedine.game@gmail.com", mail.getText().toString());

            }
        });

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
    public void sendEmail(String TO, String CC) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CAddFriendsCtrl.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}