package com.example.teach;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.Date;

//Klassen Answer är en klass som används för att kunna skapa svarobjekt till frågor som ska användas i applikationen
public class Answer {
    public int id;
    public String username;
    public int questionId;
    public String answer;
    public Bitmap image;
    public Date answerDate;

    public Answer(String username, int questionId, String answer, Bitmap image, Date answerDate) {
        this.username = username;
        this.questionId = questionId;
        this.answer = answer;
        this.image = image;
        this.answerDate = answerDate;
    }

    public Answer(int id, String username, int questionId, String answer, Bitmap image, Date answerDate) {
        this.id = id;
        this.username = username;
        this.questionId = questionId;
        this.answer = answer;
        this.image = image;
        this.answerDate = answerDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date answerDate) {
        this.answerDate = answerDate;
    }

    public byte[] convertImage()
    {
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
