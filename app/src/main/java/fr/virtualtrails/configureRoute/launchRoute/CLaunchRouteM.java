package fr.virtualtrails.configureRoute.launchRoute;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clemzux on 16/12/15.
 */
public class CLaunchRouteM {
    private static CLaunchRouteM ourInstance = new CLaunchRouteM();

    public static CLaunchRouteM getInstance() {
        return ourInstance;
    }

    ListView listRoute;
    CLaunchRouteCtrl cLaunchRouteCtrl;
    public ArrayList<String> routeList = null;

    private CLaunchRouteM() {
    }

    public void initClaunchRouteM(ListView lv, CLaunchRouteCtrl pCLaunchRouteCtrl){
        listRoute = lv;
        cLaunchRouteCtrl = pCLaunchRouteCtrl;
    }

    public void readRouteNames() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("coordonees");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {
                routeList = new ArrayList<String>();
                String lastName = "string";

                if (e == null) {
                    for (ParseObject po : objects) {
                        if (po.getString("nomItineraire").compareTo(lastName) != 0) {
                            routeList.add(po.getString("nomItineraire"));
                            lastName = po.getString("nomItineraire");
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(cLaunchRouteCtrl, android.R.layout.simple_list_item_1,
                            CLaunchRouteM.getInstance().getRouteNames());

                    listRoute.setAdapter(adapter);
                }
            }
        });
    }

    public String[] getRouteNames(){

        String[] routes = new String[routeList.size()];

        int i =0;

        for (String s : routeList)
            routes[i++] = s;

        return routes;
    }
}
