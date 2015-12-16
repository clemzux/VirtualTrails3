package fr.virtualtrails.consultStatistics;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by clemzux on 16/12/15.
 */
public class CConsultStatisticsM {
    private static CConsultStatisticsM ourInstance = new CConsultStatisticsM();

    public static CConsultStatisticsM getInstance() {
        return ourInstance;
    }

    private ListView statList;
    private CConsultStatisticsCtrl cConsultStatisticsCtrl;
    public ArrayList<String> objectId;

    private CConsultStatisticsM() {
    }

    public void initConsultM(ListView pLst, CConsultStatisticsCtrl pConsult){
        statList = pLst;
        cConsultStatisticsCtrl = pConsult;
    }

    public void readStats() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("statistic");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, com.parse.ParseException e) {

                String[] statsNames = new String[objects.size()];
                objectId = new ArrayList<String>();
                int i = 0;

                if (e == null) {
                    for (ParseObject po : objects) {
                        statsNames[i] = po.getString("nomItineraire") + "      Créé le : " + po.getCreatedAt();
                        objectId.add(po.getObjectId());
                        i++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(cConsultStatisticsCtrl, android.R.layout.simple_list_item_1,
                            statsNames);

                    statList.setAdapter(adapter);
                }
            }
        });
    }
}
