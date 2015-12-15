package fr.virtualtrails.configureRoute;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by clemzux on 13/12/15.
 */
public class CConfigureRouteM {
    private static CConfigureRouteM ourInstance = new CConfigureRouteM();

    public static CConfigureRouteM getInstance() {
        return ourInstance;
    }

    //////// attibuts ////////

    public ArrayList<String> routeList = null;
    ListView lst;
    CConfigureRouteCtrl cConfigureRouteCtrl;

    private CConfigureRouteM() {}

    public void initConfigureRouteM(ListView list, CConfigureRouteCtrl pConfigureRouteCtrl){

        lst = list;
        cConfigureRouteCtrl = pConfigureRouteCtrl;
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
                            Log.d("ya pas bon banania :", "salut");
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(cConfigureRouteCtrl, android.R.layout.simple_list_item_1,
                            CConfigureRouteM.getInstance().getRouteNames());

                    lst.setAdapter(adapter);
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
