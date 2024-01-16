package com.example.teach;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

//Detta är den abstrakta klassen User, i applikationen vill jag inte att man ska
//kunna skapa objekt av klassen därför är den abstrakt.
//Klasserna Student och Teacher ärver av denna klass
public abstract class User {

    public String username;
    public String password;
    public String eMail;
    public Bitmap image;

    public User(String username, String password, String eMail, Bitmap image)
    {
        this.username = username;
        this.password = password;
        this.eMail = eMail;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    //En metod för att konvertera användarnas profilbilder från Bitmap till en byte array
    //denna metod behövs för att en databasen endast kan lagra byte arrays
    public byte[] convertImage()
    {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,80,byteArray);

        byte[] img = byteArray.toByteArray();
        return img;
    }


}
