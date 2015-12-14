package metier;

import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by clemzux on 13/12/15.
 */
public class CRoute implements Serializable {

    String routeName = null;
    ArrayList<MarkerOptions> route = null;

    public CRoute (String pName, ArrayList<MarkerOptions> pRoute){

        routeName = pName;
        route = pRoute;
    }
}
