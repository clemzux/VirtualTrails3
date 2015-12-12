package fr.virtualtrails.manageFriends;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import fr.virtualtrails.R;

public class CAddFriendsCtrl extends AppCompatActivity {

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