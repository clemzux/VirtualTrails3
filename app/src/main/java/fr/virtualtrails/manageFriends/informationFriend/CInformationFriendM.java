package fr.virtualtrails.manageFriends.informationFriend;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

/**
 * Created by boblinux on 15/12/15.
 */
public class CInformationFriendM {
    private static CInformationFriendM ourInstance = new CInformationFriendM();

    public static CInformationFriendM getInstance() {
        return ourInstance;
    }
    private CInformationFriendM() {
    }

    public TextView mailText;
    public TextView numText;
    public int numTel;
    public String pseudo, mail;

    public void preInitViewFriend(String pPseudo){
        pseudo = pPseudo;
        Log.i("testbdd", "pseudo passé dans la méthode : " + pPseudo);
    }

    public void initInfos(TextView mailText, TextView numText) {
        this.mailText = mailText;
        this.mailText = numText;
    }


    public void ReadFriends() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("friends");
        query.whereContains("pseudo",pseudo);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, com.parse.ParseException e) {


                if (e == null) {

                    ParseObject p;

                    p = objects.get(0);
                    mail = p.getString("mail");
                    numTel = p.getInt("num");
                    Log.i("testbdd", "mail trouvé : " + mail + " numtel " + numTel);

                    mailText.setText(p.getString("mail"));
                    numText.setText(p.get("num").toString());


                } else {
                    Log.i("testbdd", "pb?");
                }
            }
        });
    }
}
