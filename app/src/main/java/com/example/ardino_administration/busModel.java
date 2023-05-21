package com.example.ardino_administration;

public class busModel {
   String image;
    String duration;
    String leaveTime;
    String date;
    String name;
    String from;
    String to;
    String nmbrofseat;
    String id;

    public String getNmbrofseat() {
        return nmbrofseat;
    }

    public void setNmbrofseat(String nmbrofseat) {
        this.nmbrofseat = nmbrofseat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {

        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public busModel(String image, String duration, String leaveTime, String date, String name, String from, String to, String seatsRemaining, String price) {
        this.image = image;
        this.duration = duration;
        this.leaveTime = leaveTime;
        this.date = date;
        this.name = name;
        this.from = from;
        this.to = to;
        this.seatsRemaining = seatsRemaining;
        this.price = price;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeatsRemaining() {
        return seatsRemaining;
    }

    public void setSeatsRemaining(String seatsRemaining) {
        this.seatsRemaining = seatsRemaining;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String seatsRemaining;
    String price;
public busModel(){}
    public busModel(String image, String duration, String leaveTime, String date, String seatsRemaining, String price) {
        this.image = image;
        this.duration = duration;
        this.leaveTime = leaveTime;
        this.date = date;
        this.seatsRemaining = seatsRemaining;
        this.price = price;
    }
}
