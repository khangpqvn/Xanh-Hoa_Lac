package com.example.deathdevilt_t.googlemaps_testv1.ObjectClass;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DeathDevil.T_T on 09-Aug-17.
 */

public class ConflictScheduleObject extends RealmObject{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @PrimaryKey
    private int id;
    private String duration;
    private String hour;
    private String minute;
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
