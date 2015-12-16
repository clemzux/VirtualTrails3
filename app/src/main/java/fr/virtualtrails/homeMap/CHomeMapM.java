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

import java.util.ArrayList;
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
        query.addAscendingOrder("createdAt");
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

    public void nowLetsRockForReal(){

        CharSequence text = "Nouvelle randonnée lancée, vous pouvez inviter des amis en cliquant sur le bouton \"inviter\" !";

        int time = Toast.LENGTH_SHORT;

        Toast info = Toast.makeText(cHomeMapCtrl, text, time);
        info.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        info.show();

        getMarkerOption();

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

                if (trailWorking) {

                    Location locationB = new Location("next");
                    locationB.setLatitude(route.get(nbWaypoint).latitude);
                    locationB.setLongitude(route.get(nbWaypoint).longitude);

                    if (location.distanceTo(locationB) < 15) {

                        markRoute.get(nbWaypoint).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                        nbWaypoint++;
                        drawMarkers();
                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    drawMarkers();

                    if (nbWaypoint == route.size()) {
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
}
