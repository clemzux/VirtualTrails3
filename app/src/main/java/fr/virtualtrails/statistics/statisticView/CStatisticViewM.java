package fr.virtualtrails.statistics.statisticView;

import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

/**
 * Created by clemzux on 17/12/15.
 */
public class CStatisticViewM {
    private static CStatisticViewM ourInstance = new CStatisticViewM();

    public static CStatisticViewM getInstance() {
        return ourInstance;
    }

    private CStatisticViewM() {
    }

    String objectId;
    GoogleMap mMap;
    String nameRoute;
    public TextView routeName, hStart, hFinish, totDistance, averageSpeed, altMax;
    ArrayList<LatLng> route = null;
    ArrayList<MarkerOptions> routeList;
    public PolylineOptions plo = new PolylineOptions();
    public Polyline pl;

    public void initStatViewM(String pObject){

        objectId = pObject;
    }

    public void setMap(GoogleMap pMap){
        mMap = pMap;
    }

    public void setRouteName(String pName){
        nameRoute = pName;
    }

    public void setViewers(TextView pRouteName, TextView pHstart, TextView pHfinish, TextView pTotDist,
                           TextView pAverSpeed, TextView pAltMax){

        routeName = pRouteName;
        hStart = pHstart;
        hFinish = pHfinish;
        totDistance = pTotDist;
        averageSpeed = pAverSpeed;
        altMax = pAltMax;
    }

    public void readRouteStats() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("statistic");
        query.whereContains("objectId", objectId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                routeName.setText(objects.get(0).getString("nomItineraire"));
                hStart.setText(objects.get(0).getString("Hdepart"));
                hFinish.setText(objects.get(0).getString("Harrivee"));
                totDistance.setText(String.valueOf(objects.get(0).getNumber("distTot")) + " m");
                averageSpeed.setText(String.valueOf(objects.get(0).getNumber("Vmoy")) + " km/h");
                altMax.setText(String.valueOf(objects.get(0).getNumber("altMax")) + " m");
            }
        });
    }

    public void readRoute(){

        System.out.println("FUUUUUUUUUUUUUUUUUUUCK = " + nameRoute);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("coordonees");
        query.whereContains("nomItineraire", nameRoute);
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
