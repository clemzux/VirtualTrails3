package fr.virtualtrails.testList;

import java.util.ArrayList;
import java.util.LinkedList;

import fr.virtualtrails.manageFriends.CFriendList;

/**
 * Created by boblinux on 14/12/15.
 */
public class LinkedTest {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        CFriendList cFriendList = new CFriendList(arrayList);

        String a,b,c;
        a="t";
        b="adazdazd";

        cFriendList.array.add(0,a);
        cFriendList.array.add(1,b);

        //linkedList.add(a);
        //cFriendList.listFriends.add(a);
        /*linkedList.add(a);
        linkedList.add(b);
        linkedList.add(c);*/


        /*
        LinkedList<String> linkedList2 = new LinkedList();
        linkedList2 = cFriendList.getListFriends();*/

    }

}
