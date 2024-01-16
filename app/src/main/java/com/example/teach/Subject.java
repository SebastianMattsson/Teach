package com.example.teach;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

//Klassen Subject används mest för att kunna lägga till ämnen i databasen,
//Klassen SubjectForViews är den klass som används för olika användsområden i applikationen
public class Subject {
    public String name;
    public String[] AgeGroups;
    public Bitmap bitmap;

    public Subject(String name, Bitmap bitmap)
    {
        this.name = name;
        this.bitmap = bitmap;

        this.AgeGroups = new String[]{"Mellanstadiet", "Högstadiet", "Gymnasiet"};

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getAgeGroups() {
        return AgeGroups;
    }

    public void setAgeGroups(String[] ageGroups) {
        AgeGroups = ageGroups;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public byte[] convertImage()
    {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        boolean success = bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArray);

        byte[] img = byteArray.toByteArray();
        return img;
    }




}
