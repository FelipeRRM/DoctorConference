package com.feliperrm.doctororganizer.Utils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.feliperrm.doctororganizer.Models.User;
import com.google.gson.Gson;

/**
 * Created by felip on 15/08/2016.
 */
public class Singleton {
    User loggedUser;

    Integer loggedUserId;
    private static final String LOGGED_USER_ID_KEY = "loggeduseridkey";

    private static final int NO_USER_INT  = -1;


    public User getLoggedUser() {
        if(loggedUser==null) {
            loggedUserId = Integer.valueOf(Geral.loadSharedPreference(MyApp.getContext(), LOGGED_USER_ID_KEY, String.valueOf(NO_USER_INT)));
            if(loggedUserId != NO_USER_INT)
                loggedUser = User.getUser(loggedUserId);
        }
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        Geral.salvarSharedPreference(MyApp.getContext(), LOGGED_USER_ID_KEY, String.valueOf(loggedUser.getId()));
        this.loggedUser = loggedUser;
    }

    public Singleton() {
        loggedUserId = Integer.valueOf(Geral.loadSharedPreference(MyApp.getContext(), LOGGED_USER_ID_KEY, String.valueOf(NO_USER_INT)));
        if(loggedUserId != NO_USER_INT)
            loggedUser = User.getUser(loggedUserId);
    }

    private static Singleton singleton;

    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }
}
