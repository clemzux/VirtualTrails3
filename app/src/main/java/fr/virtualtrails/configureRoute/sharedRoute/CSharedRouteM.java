package fr.virtualtrails.configureRoute.sharedRoute;

import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    public void ReadFriends(final ListView pListView, final CSharedRouteCtrl cSharedRouteCtrl){
        String[] friends = new String[5];
        friends[0]= "Route Shared 0";
        friends[1]= "Route Shared 1";
        friends[2]= "Route Shared 2";
        friends[3]= "Route Shared 3";
        friends[4]= "Route Shared 4";

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(cSharedRouteCtrl, android.R.layout.simple_list_item_1,
                friends);
        pListView.setAdapter(adapter);

    }
}
