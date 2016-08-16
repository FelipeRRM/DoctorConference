package com.feliperrm.doctororganizer.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by felip on 15/08/2016.
 */
@Table(name = "Conferences")
public class Conference extends Model implements Serializable {

    @Column(name = "Subject")
    String subject;

    @Column(name = "Speaker")
    User user;

    @Column(name = "Start")
    String dateStart;

    @Column(name = "End")
    String dateEnd;



    public Conference(){
        super();
    }


}
