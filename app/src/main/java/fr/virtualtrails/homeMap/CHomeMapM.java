package fr.virtualtrails.homeMap;

import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by clemzux on 10/12/15.
 */
public class CHomeMapM {

    private static CHomeMapM ourInstance = new CHomeMapM();
    public static CHomeMapM getInstance() { return ourInstance; }

    private GoogleMap mMap;
    private Spinner menu;
    private TextView informativePart;
    public boolean initializeBDD = true;

    boolean routeMode = false;
    String routeName;
    int nbWaypoint = 0;
    ArrayList<LatLng> route;
    ArrayList<MarkerOptions> markRoute;
    public PolylineOptions plo = new PolylineOptions();
    public Polyline pl;
    private CHomeMapCtrl cHomeMapCtrl;
    private boolean trailWorking = true;
    private long startTrail;
    private long endtrail;
    public String Hdebut;
    public String Hfin;
    private long timeTravel;
    float[] speed;
    public float averageSpeed;
    public double altitude = 0;

    public int totalDistance = 0; // en m


    private CHomeMapM() {}

    public void setRouteMode(String pRouteName){
        routeMode = true;
        routeName = pRouteName;
    }

    public void setHomeMode(){
        routeMode = false;
        trailWorking = false;
    }

    public void setMap(GoogleMap pMap, CHomeMapCtrl pcHomeMapCtrl){
        mMap = pMap;
        cHomeMapCtrl = pcHomeMapCtrl;
    }

    public void initHomeMapM(GoogleMap pMap, Spinner pMenu, TextView pInfo){

        mMap = pMap;
        menu = pMenu;
        informativePart = pInfo;
    }

    public boolean isBDDinitialized(){ return initializeBDD; }

    public void setBDDinitialized(){ initializeBDD = false; }

    public void readRoute(){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("coordonees");
        query.whereContains("nomItineraire", routeName);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                route = new ArrayList<LatLng>();

                if (e == null) {
                    for (ParseObject po : objects)
                        route.add(new LatLng(po.getDouble("latitude"), po.getDouble("longitude")));

                    nowLetsRockForReal();
                }
            }
        });
    }

    public String getTime(long time){

        int h,m;

        h = (int) (((time / 1000) / 60) / 60);
        h = h % 24;
        h++; // d'hivers lol

        m = (int) ((time / 1000) / 60) % 60;

        return h + "h " + m + "min";
    }

    public void nowLetsRockForReal(){

        startTrail = System.currentTimeMillis();
        Hdebut = getTime(startTrail);
        speed = new float[route.size()];

        setTotalDistance();

        getMarkerOption();

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (trailWorking) {

                    if (altitude < location.getAltitude())
                        altitude = location.getAltitude();

                    Location locationB = new Location("next");
                    locationB.setLatitude(route.get(nbWaypoint).latitude);
                    locationB.setLongitude(route.get(nbWaypoint).longitude);

                    if (location.distanceTo(locationB) < 15) {

                        markRoute.get(nbWaypoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        speed[nbWaypoint] = location.getSpeed();
                        nbWaypoint++;
                        drawMarkers();
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    drawMarkers();

                    if (nbWaypoint == route.size()) {
                        endtrail = System.currentTimeMillis();
                        Hfin = getTime(endtrail);

                        for (int i = 0; i < route.size(); i++)
                            averageSpeed += speed[i];

                        averageSpeed /= route.size();

                        cHomeMapCtrl.stopItNow();
                    }
                }

            }

        });
    }

    void getMarkerOption(){

        int i = 0;
        markRoute = new ArrayList<>();

        for (LatLng ll : route){

            if (i == 0) {
                markRoute.add(new MarkerOptions().position(ll).title("Départ").
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else {
                if (i == route.size()-1){
                    markRoute.add(new MarkerOptions().position(ll).title("Arrivée").
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
                else{
                    markRoute.add(new MarkerOptions().position(ll).title("Etape n°"+i).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                }
            }

            i++;
        }
    }

    void drawMarkers(){

        mMap.clear();

        plo = new PolylineOptions();
        pl = mMap.addPolyline(plo);
        pl.setColor(Color.BLUE);

        for (int i = 0; i < markRoute.size(); i++) {

            mMap.addMarker(markRoute.get(i));

            plo.add(markRoute.get(i).getPosition());
            pl.setPoints(plo.getPoints());
        }
    }

    public void setTotalDistance(){

        Location locA = new Location("actual");
        locA.setLatitude((route.get(0)).latitude);
        locA.setLongitude((route.get(0)).longitude);
        Location locB = new Location("next");

        for (int i = 1; i < route.size(); i++){
            locB.setLatitude((route.get(i)).latitude);
            locB.setLongitude((route.get(i)).longitude);

            totalDistance += locA.distanceTo(locB);

            locA.setLatitude(locB.getLatitude());
            locA.setLongitude(locB.getLongitude());
        }
    }

    public void saveStatistics(){

        Thread save = new Thread(new SaveStatisticsThread(routeName));
        save.run();
    }

    public class SaveStatisticsThread implements Runnable{

        String nameRoute;

        public SaveStatisticsThread(String pRouteName){
            nameRoute = pRouteName;
        }

        @Override
        public void run() {

            ParseObject wp;

            wp = new ParseObject("statistic");
            wp.put("distTot", totalDistance);
            wp.put("Hdepart", Hdebut);
            wp.put("Harrivee", Hfin);
            wp.put("Vmoy", averageSpeed);
            wp.put("altMax", altitude);
            wp.saveInBackground();
        }
    }
}
