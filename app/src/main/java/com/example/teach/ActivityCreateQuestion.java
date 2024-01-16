package com.example.teach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityCreateQuestion extends AppCompatActivity {

    private ArrayList<SubjectForViews> subList;
    DataBaseHelper dbh;
    RecyclerView rvSubjects;
    AdapterForSubjectsInQuestions subAdapter;
    EditText etTitle, etDescription;
    ImageView ivImage, ivCamera, ivGallery;
    Question question;
    Button buttonConfirm, buttonCancel;

    public static final int GALLERY_REQUEST = 1;
    public static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);


        setUpContent();

        setOnClick();


    }

    private void setUpContent() {

        dbh = new DataBaseHelper(this);
        ((LoggedInUser) getApplication()).setCurrentQuestion(new Question());

        rvSubjects = findViewById(R.id.rv_subjects);
        etTitle = findViewById(R.id.question_title);
        etDescription = findViewById((R.id.question_description));

        ivImage = findViewById(R.id.image_placeholder);
        ivCamera = findViewById(R.id.iv_camera);
        ivGallery = findViewById((R.id.iv_gallery));

        buttonConfirm = findViewById(R.id.button_confirm);
        buttonCancel = findViewById(R.id.button_cancel);

        subList = dbh.getAllSubjects(((LoggedInUser) getApplication()).getUser());

        //Set all subjects selected attribute to false, for use in adapter
        for (SubjectForViews sub : subList) {
            sub.setSelected(false);
        }

        //horisontell layout
        LinearLayoutManager lManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);

        rvSubjects.setLayoutManager(lManager);
        rvSubjects.setItemAnimator(new DefaultItemAnimator());

        subAdapter = new AdapterForSubjectsInQuestions(this, subList);

        rvSubjects.setAdapter(subAdapter);
    }

    private void setOnClick() {

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(ActivityCreateQuestion.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityCreateQuestion.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                } else {
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePhotoIntent, CAMERA_REQUEST);
                }

            }
        });


        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityCreateQuestion.this, ActivityImageFullScreen.class);
                startActivity(intent);
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int counter = 0;
                int subjectId = 0;
                for (SubjectForViews sub : subList) {
                    if (sub.isSelected()) {
                        counter++;
                        subjectId = sub.getSubjectID();

                    }

                }

                if (counter < 1) {
                    Toast.makeText(ActivityCreateQuestion.this, "Du måste välja ett ämne.", Toast.LENGTH_SHORT).show();
                } else if (counter > 1) {
                    Toast.makeText(ActivityCreateQuestion.this, "Du får endast välja ett ämne.", Toast.LENGTH_SHORT).show();
                } else if (etTitle.getText().toString().isEmpty() || etDescription.getText().toString().isEmpty()) {
                    Toast.makeText(ActivityCreateQuestion.this, "Alla textfält måste vara ifyllda.", Toast.LENGTH_SHORT).show();
                } else {
                    String title = etTitle.getText().toString();
                    String description = etDescription.getText().toString();
                    Bitmap image = null;
                    if (ivImage.getDrawable() != null) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivImage.getDrawable();
                        image = bitmapDrawable.getBitmap();
                    }

                    Date date = new Date();

                    Question question = new Question(subjectId,
                            title,
                            description
                            , ((LoggedInUser) getApplication()).getUser().getUsername(),
                            image, date);


                    boolean success = dbh.addQuestion(question);

                    if (success) {
                        Toast.makeText(ActivityCreateQuestion.this, "Din fråga är skapad", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ActivityCreateQuestion.this, ActivityUserQuestions.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityCreateQuestion.this, "Något gick fel", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!etTitle.getText().toString().isEmpty() || !etDescription.getText().toString().isEmpty() || ivImage.getDrawable() != null) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int whichButton) {

                            switch (whichButton) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    Intent intent = new Intent(ActivityCreateQuestion.this, ActivityUserQuestions.class);
                                    startActivity(intent);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }

                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Är du säker på att du vill avbryta?\nÄndringar sparas ej").setPositiveButton("Ja", dialogClickListener).setNegativeButton("Nej", dialogClickListener).show();
                } else {
                    Intent intent = new Intent(ActivityCreateQuestion.this, ActivityUserQuestions.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    ivImage.setImageBitmap(bitmap);
                    ((LoggedInUser) getApplication()).getCurrentQuestion().setImage(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivImage.setImageBitmap(bitmap);
                ((LoggedInUser) getApplication()).getCurrentQuestion().setImage(bitmap);
            }

        }
    }
}