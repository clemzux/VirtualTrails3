package fr.virtualtrails.configureRoute.viewRoute;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import fr.virtualtrails.configureDisplay.CConfigureDisplayM;
import fr.virtualtrails.configureRoute.sharedRoute.CViewSharedRoutesCtrl;

/**
 * Created by clemzux on 15/12/15.
 */

public class CViewSharedRoutesM {
    private static CViewSharedRoutesM ourInstance = new CViewSharedRoutesM();

    public static CViewSharedRoutesM getInstance() {
        return ourInstance;
    }

    GoogleMap mMap;
    public String routeName;
    ArrayList<LatLng> route = null;
    ArrayList<MarkerOptions> routeList;
    public PolylineOptions plo = new PolylineOptions();
    public Polyline pl;
    public CViewSharedRoutesCtrl cViewSharedRoutesCtrl;

    private CViewSharedRoutesM() {
    }

    public void preInitSharedViewRoute(String pName){
        routeName = pName;
    }

    public void initViewRoute(GoogleMap pMap, CViewSharedRoutesCtrl pCViewSharedRoutesCtrl){

        mMap = pMap;
        cViewSharedRoutesCtrl = pCViewSharedRoutesCtrl;
    }

    public void readRoute(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("coordonees");
        query.whereContains("nomItineraire", routeName);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                route = new ArrayList<LatLng>();

                if (e == null) {

                    for (ParseObject po : objects) {
                        route.add(new LatLng(po.getDouble("latitude"), po.getDouble("longitude")));
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(route.get(0).latitude, route.get(0).longitude)));
                    drawMarkerOptions();
                }
            }
        });
    }

    void drawMarkerOptions(){

        routeList = new ArrayList();
        plo = new PolylineOptions();
        pl = mMap.addPolyline(plo);
        pl.setColor(Color.BLUE);

        for (int i = 0; i<route.size(); i++){

            if (i == 0){
                routeList.add(new MarkerOptions().position(route.get(i)).title("Départ")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else {
                if (i < route.size()-1 && i > 0){
                    routeList.add(new MarkerOptions().position(route.get(i)).title("Etape n°" + i)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
                else {
                    if (i == route.size() - 1){
                        routeList.add(new MarkerOptions().position(route.get(i)).title("Arrivée")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    }
                }
            }

            mMap.addMarker(routeList.get(i));
            plo.add(routeList.get(i).getPosition());
        }

        pl.setPoints(plo.getPoints());
    }
}
