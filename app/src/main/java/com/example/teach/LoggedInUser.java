package com.example.teach;

import android.app.Application;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

//Denna klass används för att se vilken användare det är som är inloggad.
public class LoggedInUser extends Application {

    private User user;
    private Question currentQuestion;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}
