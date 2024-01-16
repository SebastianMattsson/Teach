package com.example.teach;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Date;

//Klassen Question är en klass som används för att kunna skapa frågeobjekt som ska användas i applikationen
public class Question {

    public int id;
    public int subjectId;
    public String title;
    public String description;
    public String username;
    public Bitmap image;
    public Date postDate;

    public Question() {
    }

    public Question(int subjectId, String title, String description, String username, Bitmap image, Date postDate) {
        this.subjectId = subjectId;
        this.title = title;
        this.username = username;
        this.image = image;
        this.description = description;
        this.postDate = postDate;
    }

    public Question(int id, int subjectId, String title, String description, String username, Bitmap image, Date postDate) {
        this.id = id;
        this.subjectId = subjectId;
        this.title = title;
        this.username = username;
        this.image = image;
        this.description = description;
        this.postDate = postDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public byte[] convertImage() {

        if(image == null)
        {
            return null;
        }
        else{
            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 80, byteArray);
            byte[] img = byteArray.toByteArray();
            return img;
        }


    }
}
