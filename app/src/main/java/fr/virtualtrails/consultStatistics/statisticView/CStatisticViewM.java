package fr.virtualtrails.consultStatistics.statisticView;

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

    public void initStatViewM(String pObject){

        objectId = pObject;
    }
}
