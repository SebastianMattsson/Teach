package com.example.teach;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;

//En klass för studenter som ska finnas i systemet, här skulle i framtiden ytterligare attribut
//kunna läggas till
public class Student extends User{

    public Student(String username, String password, String eMail, Bitmap bitmap)
    {
        super(username, password, eMail, bitmap);
    }
}
