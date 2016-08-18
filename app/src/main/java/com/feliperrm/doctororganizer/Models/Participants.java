package com.feliperrm.doctororganizer.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by felip on 15/08/2016.
 */
@Table(name = "Participants")
public class Participants extends Model {

    @Column(name = "Conference")
    Conference conference;

    @Column(name = "User")
    User user;



    public Participants() {
        super();
    }

    public Participants(Conference conference, User user) {
        super();
        this.conference = conference;
        this.user = user;
    }

    public List<Conference> conferences() {
        return getMany(Conference.class, "Participants");
    }
    public List<User> users() {
        return getMany(User.class, "Participants");
    }

    List<User> getUsersInConference(Conference conference){
        List<User> users = ((Participants) new Select().from(Participants.class)
                .where("Conference = ?", conference.getId())
                .executeSingle()).users();
        return users;
    }
}
