package fr.virtualtrails.manageFriends.informationFriend;

import android.util.Log;

/**
 * Created by boblinux on 15/12/15.
 */
public class CInformationFriendM {
    private static CInformationFriendM ourInstance = new CInformationFriendM();

    public static CInformationFriendM getInstance() {
        return ourInstance;
    }

    private CInformationFriendM() {
    }

    public int numTel;
    public String pseudo, mail;

    public void preInitViewFriend(String pPseudo){

        pseudo = pPseudo;
        Log.i("testbdd", "pseudo passé dans la méthode : " + pPseudo);
    }
}
