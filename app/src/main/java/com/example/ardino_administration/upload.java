package com.example.ardino_administration;

public class upload {
    String ImageUrl;
    public upload(String s, String trim, String trim1, String s1, String trim2, String s2, String trim3, String s3, String trim4, String s4, String trim5, String s5) {
        //empty constructor needed
    }
    public upload(String ImageUrl){

        this.ImageUrl = ImageUrl;



    }




    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;


}}