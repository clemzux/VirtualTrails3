package fr.virtualtrails.manageFriends;

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

    public ArrayList<String> frendsList = null;

    private static CManageFriendsM ourInstance = new CManageFriendsM();

    public static CManageFriendsM getInstance() {
        return ourInstance;
    }

    private CManageFriendsM() {
    }

    public void ReadFriends() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("friends");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                frendsList = new ArrayList<String>();
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject p = objects.get(i);
                        frendsList.add(p.getString("pseudo"));
                        Log.i("testbdd", "linked " + i + " " + frendsList.get(i));
                    }
                } else {
                    Log.i("testbdd", "pb?");
                }
                Log.i("testbdd", "set appelé");
                //cFriendList.setListFriends(linkedList);
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
