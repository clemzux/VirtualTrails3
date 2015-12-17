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
    public ArrayList<String> routeList = null;
    ListView lst;
    CSharedRouteCtrl cSharedRouteCtrl;

    private static CSharedRouteM ourInstance = new CSharedRouteM();

    public static CSharedRouteM getInstance() {
        return ourInstance;
    }

    private CSharedRouteM() {
    }

    public void initConfigureRouteM(ListView list, CSharedRouteCtrl pSharedRouteCtrl) {

        lst = list;
        cSharedRouteCtrl = pSharedRouteCtrl;
    }

    public void readRouteNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("friendsRoutes");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {

                routeList = new ArrayList<String>();

                if (e == null) {

                    ParseObject p;

                    for (int i = 0; i < objects.size(); i++) {
                        p = objects.get(i);
                        routeList.add(p.getString("routeName"));
                    }
                } else {
                    Log.i("testbdd", "pb?");
                }

                String[] friends = new String[routeList.size()];
                int i = 0;
                for (String s : routeList)
                    friends[i++] = s;

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(cSharedRouteCtrl, android.R.layout.simple_list_item_1,
                        friends);

                lst.setAdapter(adapter);
            }
        });
    }
}