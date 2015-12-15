package fr.virtualtrails.homeMap;

import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

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

    private CHomeMapM() {}

    public void initHomeMapM(GoogleMap pMap, Spinner pMenu, TextView pInfo){

        mMap = pMap;
        menu = pMenu;
        informativePart = pInfo;
    }

    public boolean isBDDinitialized(){ return initializeBDD; }

    public void setBDDinitialized(){ initializeBDD = false; }
}
