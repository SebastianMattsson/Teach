package com.example.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class ActivityImageFullScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full_screen);


        ImageView ivFullImage = findViewById(R.id.iv_full_screen);
        ivFullImage.setImageBitmap(((LoggedInUser)getApplication()).getCurrentQuestion().getImage());
    }
}