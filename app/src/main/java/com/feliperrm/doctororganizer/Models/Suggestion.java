package com.feliperrm.doctororganizer.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by felip on 17/08/2016.
 */
@Table(name = "Suggestions")
public class Suggestion extends Model {

    @Column(name = "Subject")
    String subject;

    @Column(name = "Speaker")
    User user;

    @Column(name = "Day")
    int day;

    @Column(name = "Month")
    int month;

    @Column(name = "Year")
    int year;

    @Column(name = "Start")
    int start;

    @Column(name = "End")
    int end;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Suggestion(){
        super();
    }

    public static List<Suggestion> getSuggestion(){
        return new Select().from(Suggestion.class).execute();
    }

    public static List<Suggestion> getSuggestion(int month){
        return new Select().from(Suggestion.class).where("Month = ?", month).execute();
    }

    public static List<Suggestion> getSuggestion(int day, int month, int year){
        return new Select().from(Suggestion.class)
                .where("Day = ?", day)
                .where("Month = ?", month)
                .where("Year = ?", year)
                .execute();
    }
}
