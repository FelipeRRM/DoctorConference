package com.feliperrm.doctororganizer.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;

/**
 * Created by felip on 15/08/2016.
 */
@Table(name = "Users")
public class User extends Model implements Serializable {
    @Column(name = "Name")
    String name;
    @Column(name = "User_Name", unique = true, onUniqueConflict = Column.ConflictAction.FAIL)
    String userName;
    @Column(name = "Password")
    String password;
    @Column(name = "Admin")
    boolean admin;
    @Column(name = "Picture")
    String picture;
    @Column(name = "Join_Date")
    String joinDate;
    @Column(name = "Last_Login")
    String lastLogin;

    public User(){
        super();
    }

    public User(String name, String userName, String password, boolean admin, String picture, String joinDate, String lastLogin) {
        super();
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.admin = admin;
        this.picture = picture;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }

    public static User getUser(String userName){
        return new Select().from(User.class).where("User_Name = ?", userName).executeSingle();
    }

    public static User getUser(int id){
        return new Select().from(User.class).where("Id = ?", id).executeSingle();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
