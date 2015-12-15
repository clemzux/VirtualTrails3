package fr.virtualtrails.manageFriends.manageFriend;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boblinux on 14/12/15.
 */
public class CManageFriendsM {

    private static CManageFriendsM ourInstance = new CManageFriendsM();

    public static CManageFriendsM getInstance() {
        return ourInstance;
    }

    public ArrayList<String> frendsList = null;

    private CManageFriendsM() {
    }

    public void ReadFriends() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("friends");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                frendsList = new ArrayList<String>();

                if (e == null) {

                    ParseObject p;

                    for (int i = 0; i < objects.size(); i++) {
                        p = objects.get(i);
                        frendsList.add(p.getString("pseudo"));

                    }
                } else {
                    Log.i("testbdd", "pb?");
                }
            }
        });
    }

    public String[] getFriends(){

        String[] friends = new String[frendsList.size()];
        int i =0;
        for (String s : frendsList)
            friends[i++] = s;

        return friends;
    }
}