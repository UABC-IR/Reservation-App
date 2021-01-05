package com.example.reservationapp.Class;

import java.io.Serializable;
import java.util.UUID;

public class Reservation implements Serializable {
    private String id;
    private String idUser;
    private String date;
    private String time;
    private String name;
    private String phone;

    public Reservation() {
    }

    public Reservation(String idUser, String date, String time, String name, String phone) {
        this.idUser = idUser;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
    }

    public Reservation(String id, String idUser, String date, String time, String name, String phone) {
        this.id = id;
        this.idUser = idUser;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
