package fr.virtualtrails.configureRoute;

import android.graphics.Color;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.parse.ParseObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Time;
import java.util.ArrayList;

import metier.CRoute;

/**
 * Created by clemzux on 13/12/15.
 */
public class CAddRouteM {
    private static CAddRouteM ourInstance = new CAddRouteM();

    public static CAddRouteM getInstance() {
        return ourInstance;
    }

    //////// attibutes ////////

    EditText routeName;
    GoogleMap mMap;
    public int nbWaypoint = 0;
    public PolylineOptions plo = new PolylineOptions();
    public Polyline pl;
    public ArrayList<MarkerOptions> itinéraire = new ArrayList<MarkerOptions>();

    //////// builder ////////

    private CAddRouteM() {
    }

    //////// methods ////////

    public void initModel(EditText pName, GoogleMap pMap){
        routeName = pName;
        mMap = pMap;
    }

    public void clickMap(LatLng pLatlng){

        if (nbWaypoint == 0) {

            itinéraire.add(new MarkerOptions().position(pLatlng).title("Départ").draggable(true).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            drawMarkers();

            nbWaypoint++;
        } else {

            if (nbWaypoint == 1) {

                itinéraire.add(new MarkerOptions().position(pLatlng).title("Arrivée").
                        draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                drawMarkers();

                nbWaypoint++;
            } else {

                itinéraire.add(new MarkerOptions().position(pLatlng).title("Arrivée").
                        draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                itinéraire.get(nbWaypoint - 1).title("Etape n°" + String.valueOf(nbWaypoint)).
                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                drawMarkers();

                plo.add(pLatlng);
                pl.setPoints(plo.getPoints());

                nbWaypoint++;
            }
        }
    }

    public void dragPoints(Marker pMarker){

        if (pMarker.getTitle().compareTo("Départ") == 0) {

            itinéraire.get(0).position(pMarker.getPosition());
        } else {

            if (pMarker.getTitle().compareTo("Arrivée") == 0)
                itinéraire.get(itinéraire.size() - 1).position(pMarker.getPosition());
            else
                itinéraire.get(Integer.valueOf(pMarker.getTitle().split("°")[1]) - 1).position(pMarker.getPosition());
        }

        drawMarkers();
    }

    void drawMarkers(){

        mMap.clear();

        plo = new PolylineOptions();
        pl = mMap.addPolyline(plo);
        pl.setColor(Color.BLUE);

        for (int i = 0; i < itinéraire.size(); i++) {

            mMap.addMarker(itinéraire.get(i));

            plo.add(itinéraire.get(i).getPosition());
            pl.setPoints(plo.getPoints());
        }
    }

    public void saveRoute(String pRouteName){

        // save en ligne sur parse

        ParseObject wp;

        for (MarkerOptions m : itinéraire) {

            wp = new ParseObject("coordonees");
            wp.put("nomItineraire", pRouteName);
            wp.put("latitude", m.getPosition().latitude);
            wp.put("longitude", m.getPosition().longitude);
            wp.saveInBackground();
        }

        //////// save en local

        /*CRoute iti = new CRoute(String.valueOf(routeName.getText()), itinéraire);

        ObjectOutputStream oos;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    new File("clemzux.txt"))));
            oos.writeObject(iti);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
