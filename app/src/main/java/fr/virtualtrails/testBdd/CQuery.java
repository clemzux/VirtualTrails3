package fr.virtualtrails.testBdd;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by boblinux on 13/12/15.
 */
public class CQuery {
    private ArrayList <String> pseudos;
    public CQuery(){
    }

    public ArrayList<String> getPseudos() {
        return pseudos;
    }

    public void setPseudos(ArrayList<String> pseudos) {
        this.pseudos = pseudos;
    }

    public void afficherPseudos(){
        for(int i=0;i<pseudos.size();i++)
            if (pseudos!=null)
            Log.i("testbdd", pseudos.get(i));
    }
}
