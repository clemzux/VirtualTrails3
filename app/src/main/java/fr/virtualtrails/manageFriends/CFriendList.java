package fr.virtualtrails.manageFriends;

import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by boblinux on 14/12/15.
 */
public class CFriendList {
    public static ArrayList array;
    public LinkedList listFriends;
    public int []a;
    public String strings[];


    public CFriendList(ArrayList array){
        this.array = array;
    }
    public LinkedList<String> getListFriends() {
        System.out.println(listFriends);
        return listFriends;
    }

    public void setListFriends(LinkedList<String> listFriends) {
        Log.i("testbdd", listFriends.toString());
        if (listFriends!=null)
            this.listFriends = listFriends;
    }

    public void setArray(String string) {
        array.add(string);
    }
}
