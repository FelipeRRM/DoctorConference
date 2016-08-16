package com.feliperrm.doctororganizer.Utils;

import com.feliperrm.doctororganizer.Models.User;
import com.google.gson.Gson;

/**
 * Created by felip on 15/08/2016.
 */
public class Singleton {
    User loggedUser;
    private static final String LOGGED_USER_KEY  = "loggedUserKey";


    public User getLoggedUser() {
        if(loggedUser==null)
            loggedUser = new Gson().fromJson(Geral.loadSharedPreference(MyApp.getContext(),LOGGED_USER_KEY, new Gson().toJson(new User())), User.class);
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        Geral.salvarSharedPreference(MyApp.getContext(), LOGGED_USER_KEY, new Gson().toJson(loggedUser));
        this.loggedUser = loggedUser;
    }

    private static Singleton singleton;

    public static Singleton getSingleton(){
        if(singleton == null)
            singleton = new Singleton();
        return singleton;
    }
}
