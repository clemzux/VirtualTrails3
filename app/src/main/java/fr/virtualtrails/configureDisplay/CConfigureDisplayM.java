package fr.virtualtrails.configureDisplay;

/**
 * Created by clemzux on 16/12/15.
 */
public class CConfigureDisplayM {
    private static CConfigureDisplayM ourInstance = new CConfigureDisplayM();

    public static CConfigureDisplayM getInstance() {
        return ourInstance;
    }

    private CConfigureDisplayM() {
    }

    public boolean initialized = false;

    public String pseudo;
    public boolean speed = false;
    public boolean totalDistance = false;
    public boolean altitude = false;
    public boolean distanceRemaining = false;

    public void setDisplayModel(String pPseudo, boolean pSpeed, boolean pTotalDistance, boolean pAltitude, boolean pDistanceRemaining) {

        initialized = true;

        pseudo = pPseudo;
        speed = pSpeed;
        totalDistance = pTotalDistance;
        altitude = pAltitude;
        distanceRemaining = pDistanceRemaining;
    }
}
