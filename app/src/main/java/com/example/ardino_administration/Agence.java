package com.example.ardino_administration;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Agence implements Serializable {
private String id;
    private String img;
    private String name;
    private float review;
    private String first_wilaya;
    private String second_wilaya;
    private String price;
    private String duration_min;
    private String duration_max;
    private String cell_phone;
    private String telephone;
    private String email;
    private String bus_number;

    public Agence(String id, String img, String name, String first_wilaya, String second_wilaya, String price, String duration_min, String duration_max, String cell_phone, String telephone, String email, String bus_number) {
        this.id = id;
        this.img = img;
        this.name = name;

        this.first_wilaya = first_wilaya;
        this.second_wilaya = second_wilaya;
        this.price = price;
        this.duration_min = duration_min;
        this.duration_max = duration_max;
        this.cell_phone = cell_phone;
        this.telephone = telephone;
        this.email = email;
        this.bus_number = bus_number;
    }

    public Agence(String img , String name , float review){
        this.img = img;
        this.name = name;
        this.review= review;
    }
   public Agence(){}
    public Agence(/*int img,*/ String name, /*float review,*/ String first_wilaya, String second_wilaya, String price, String duration_min, String duration_max, String cell_phone, String telephone, String email , String bus_number) {
//        this.img = img;
        this.name = name;
//        this.review = review;
        this.first_wilaya = first_wilaya;
        this.second_wilaya = second_wilaya;
        this.price = price;
        this.duration_min = duration_min;
        this.duration_max = duration_max;
        this.cell_phone = cell_phone;
        this.telephone = telephone;
        this.email = email;
        this.bus_number=bus_number;
    }

    protected Agence(Parcel in) {
        img =in.readString();
        name = in.readString();
        review = in.readFloat();
        first_wilaya = in.readString();
        second_wilaya = in.readString();
        price = in.readString();
        duration_min = in.readString();
        duration_max = in.readString();
        cell_phone = in.readString();
        telephone = in.readString();
        email = in.readString();
        bus_number = in.readString();
    }

//    public static final Creator<Agence> CREATOR = new Creator<Agence>() {
//        @Override
//        public Agence createFromParcel(Parcel in) {
//            return new Agence(in);
//        }
//
//        @Override
//        public Agence[] newArray(int size) {
//            return new Agence[size];
//        }
//    };

    public String getImg(){
        return img;
    }


    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getReview() {
        return review;
    }

    public void setReview(float review) {
        this.review = review;
    }

    public String getFirst_wilaya() {
        return first_wilaya;
    }

    public void setFirst_wilaya(String first_wilaya) {
        this.first_wilaya = first_wilaya;
    }

    public String getSecond_wilaya() {
        return second_wilaya;
    }

    public void setSecond_wilaya(String second_wilaya) {
        this.second_wilaya = second_wilaya;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration_min() {
        return duration_min;
    }

    public void setDuration_min(String duration_min) {
        this.duration_min = duration_min;
    }

    public String getDuration_max() {
        return duration_max;
    }

    public void setDuration_max(String duration_max) {
        this.duration_max = duration_max;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    //==================

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(img);
//        parcel.writeString(name);
//        parcel.writeFloat(review);
//        parcel.writeString(first_wilaya);
//        parcel.writeString(second_wilaya);
//        parcel.writeInt(price);
//        parcel.writeString(duration_min);
//        parcel.writeString(duration_max);
//        parcel.writeString(cell_phone);
//        parcel.writeString(telephone);
//        parcel.writeString(email);
//        parcel.writeInt(bus_number);
    //}

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
