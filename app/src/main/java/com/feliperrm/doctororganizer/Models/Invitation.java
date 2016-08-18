package com.feliperrm.doctororganizer.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by felip on 17/08/2016.
 */
@Table(name = "Invitations")
public class Invitation extends Model {

    @Column(name = "Conference")
    Conference conference;

    @Column(name = "User")
    User user;

    public Invitation() {
        super();
    }

    public Invitation(Conference conference, User user) {
        super();
        this.conference = conference;
        this.user = user;
    }

    public List<Conference> conferences() {
        return getMany(Conference.class, "Invitations");
    }
    public List<User> users() {
        return getMany(User.class, "Invitations");
    }

    List<Conference> getInvitationsToUser(User user){
        List<Conference> conferences = ((Invitation) new Select().from(Invitation.class)
                .where("User = ?", user.getId())
                .executeSingle()).conferences();
        return conferences;
    }

}
