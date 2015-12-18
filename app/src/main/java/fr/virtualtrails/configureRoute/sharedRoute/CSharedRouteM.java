package fr.virtualtrails.configureRoute.sharedRoute;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import fr.virtualtrails.manageFriends.manageFriend.CManageFriendsCtrl;

/**
 * Created by boblinux on 17/12/15.
 */
public class CSharedRouteM {

    private static CSharedRouteM ourInstance = new CSharedRouteM();

    public static CSharedRouteM getInstance() {
        return ourInstance;
    }

    private CSharedRouteM() {
    }

    String[] routeList;
    String[] friends;
    ListView lst;
    CSharedRouteCtrl cSharedRouteCtrl;

    public void initShareRouteM(ListView pList, CSharedRouteCtrl pCSharedRouteCtrl){
       lst = pList;
        cSharedRouteCtrl = pCSharedRouteCtrl;
    }

    public void readSharedRoutes() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("friendsRoutes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {

                if (e == null) {

                    ParseObject p;
                    friends = new String[objects.size()];
                    routeList = new String[objects.size()];
                    String[] onScreen = new String[objects.size()];

                    for (int i = 0; i < objects.size(); i++) {
                        p = objects.get(i);
                        routeList[i] = p.getString("routeName");
                        friends[i] = p.getString("pseudo");
                        onScreen[i] = "Nom itinéraire : " + p.getString("routeName") + "     Créé par : " + p.getString("pseudo");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(cSharedRouteCtrl, android.R.layout.simple_list_item_1,
                            onScreen);

                    lst.setAdapter(adapter);
                }
            }
        });
    }
}