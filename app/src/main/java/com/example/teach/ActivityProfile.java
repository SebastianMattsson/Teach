package com.example.teach;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity {

    TextView tvUsername, tvUserEmail, tvUserType, tvNumberOfSubjects, tvNumberOfQuestions;
    ImageView ivProfilePic;
    Button btnEditSubjects, btnEditProfile, btnLogOut;
    DataBaseHelper dbh;

    public static final int GALLERY_REQUEST =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setUpContent();

        setOnClick();
    }

    private void setUpContent() {

        dbh = new DataBaseHelper(this);

        tvUsername = findViewById(R.id.username);
        tvUserEmail = findViewById(R.id.email);
        tvUserType = findViewById(R.id.user_type);
        tvNumberOfSubjects = findViewById(R.id.number_subject);
        tvNumberOfQuestions = findViewById((R.id.number_questions));
        ivProfilePic = findViewById(R.id.profile_pic);

        ivProfilePic.setImageBitmap(((LoggedInUser) this.getApplication()).getUser().getImage());
        tvUsername.setText(((LoggedInUser) this.getApplication()).getUser().getUsername());
        tvUserEmail.setText(((LoggedInUser)this.getApplication()).getUser().geteMail());

        tvNumberOfSubjects.setText(Integer.toString(dbh.getAllSubjects(((LoggedInUser)getApplication()).getUser()).size()));
        tvNumberOfQuestions.setText(Integer.toString(dbh.getQuestions(((LoggedInUser)getApplication()).getUser().getUsername()).size()));


        if (((LoggedInUser)this.getApplication()).getUser().getClass().getSimpleName().equals("Teacher"))
        {
            tvUserType.setText("Lärare");
        }
        else
        {
            tvUserType.setText("Student");
        }


        //För implementeringen av en bottom navigationbar har jag använt mig av koden från en hemsida
        //jag har gjort om koden för att passa bättre till min applikation men jag tar med länken nedan
        //https://www.geeksforgeeks.org/how-to-implement-bottom-navigation-with-activities-in-android/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.latest_questions:
                        startActivity(new Intent(getApplicationContext(), ActivityLatest.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user_questions:
                        startActivity(new Intent(getApplicationContext(), ActivityUserQuestions.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user_profile:
                        return true;
                    case R.id.notification:
                        startActivity(new Intent(getApplicationContext(), ActivityNotification.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });



    }


    public void setOnClick(){

        btnEditSubjects = findViewById(R.id.btn_edit_subjects);
        btnLogOut = findViewById(R.id.btn_log_out);

        btnEditSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProfile.this, ActivityEditSubjects.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityProfile.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

    }


    //Metod för när användaren har tagit kort med kameran eller försökt hämta en bild ur galleriet
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                ivProfilePic.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 300,300,false));
                ((LoggedInUser)getApplication()).getUser().setImage(bitmap);
                boolean success = dbh.updateProfilePicture(((LoggedInUser)getApplication()).getUser());

                if (success)
                {
                    Toast.makeText(this, "Bild ändrad", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Något gick fel", Toast.LENGTH_SHORT).show();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}