package com.example.teach;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityQuestion extends AppCompatActivity {

    ImageView ivProfileImage, ivQuestionImage, ivButtonShowAnswer, ivAnswerImage, ivCamera, ivGallery;
    TextView tvUsername, tvTitle, tvDescription, tvPostDate;
    TextView tvAnswerDescription;
    MaterialCardView cvCreateNewAnswer;
    DataBaseHelper dbh;
    Question question;
    Button buttonAddAnswer;
    ListView lvAnswers;
    ArrayList<Answer> answerList;
    AnswersAdapter adapter;
    public static final int GALLERY_REQUEST = 1;
    public static final int CAMERA_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        setUpContent();
        setOnClick();
    }



    private void setUpContent() {
        dbh = new DataBaseHelper(ActivityQuestion.this);

        ivProfileImage = findViewById(R.id.profile_pic);
        ivQuestionImage = findViewById(R.id.question_img);
        tvUsername = findViewById(R.id.username);
        tvTitle = findViewById(R.id.question_title);
        tvDescription = findViewById(R.id.question_description);
        tvPostDate = findViewById(R.id.post_date);
        cvCreateNewAnswer = findViewById((R.id.input_answer));
        ivButtonShowAnswer = findViewById(R.id.iv_create_new_answer);
        ivAnswerImage = findViewById(R.id.iv_answer_image);
        ivCamera = findViewById(R.id.iv_camera);
        ivGallery = findViewById(R.id.iv_gallery);
        buttonAddAnswer = findViewById(R.id.button_add_answer);
        tvAnswerDescription = findViewById(R.id.answer_description);
        lvAnswers = findViewById(R.id.lv_question_answer);
        question = dbh.getQuestion(getIntent().getExtras().getInt("questionId"));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if(question.getImage() == null){
            ivQuestionImage.setVisibility(View.GONE);
        }
        else{
            ivQuestionImage.setImageBitmap(question.getImage());
        }

        tvUsername.setText(question.getUsername());
        tvTitle.setText(question.getTitle());
        tvDescription.setText(question.getDescription());
        ivProfileImage.setImageBitmap(dbh.getStudentImage(question.getUsername()));
        tvPostDate.setText(dateFormat.format(question.getPostDate()));
        cvCreateNewAnswer.setVisibility(View.GONE);

        if(((LoggedInUser)getApplication()).getUser().getClass().getSimpleName().equals("Student"))
        {
            ivButtonShowAnswer.setVisibility(View.GONE);
        }


        answerList = dbh.getAnswers(getIntent().getExtras().getInt("questionId"));

        adapter = new AnswersAdapter(ActivityQuestion.this,R.layout.answer_view_layout,answerList);

        lvAnswers.setAdapter(adapter);


    }

    private void setOnClick() {

        ivButtonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cvCreateNewAnswer.isShown())
                {
                    cvCreateNewAnswer.setVisibility(View.GONE);
                    ivButtonShowAnswer.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));
                }
                else
                {
                    if(ivAnswerImage.getDrawable() == null)
                    {
                        ivAnswerImage.setVisibility(View.GONE);
                    }
                    else
                    {
                        ivAnswerImage.setVisibility(View.VISIBLE);
                    }
                    cvCreateNewAnswer.setVisibility(View.VISIBLE);
                    ivButtonShowAnswer.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_remove_24));
                }
            }
        });


        buttonAddAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvAnswerDescription.getText().toString().isEmpty() && ivAnswerImage.getDrawable() == null)
                {
                    Toast.makeText(ActivityQuestion.this, "Du måste ha valt en bild/skrivit ett svar!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int questionId = getIntent().getExtras().getInt("questionId");
                    String username = ((LoggedInUser)getApplication()).getUser().getUsername();
                    String answer = tvAnswerDescription.getText().toString();
                    Bitmap answerImage = null;
                    if(ivAnswerImage.getDrawable() != null){
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivAnswerImage.getDrawable();
                        answerImage = bitmapDrawable.getBitmap();
                    }
                    Date date = new Date();
                    Answer questionAnswer = new Answer(username,questionId,answer,answerImage, date);

                    boolean success = dbh.addAnswer(questionAnswer);
                    answerList.add(questionAnswer);
                    adapter.notifyDataSetChanged();
                    ivButtonShowAnswer.callOnClick();

                    if(!success){
                        Toast.makeText(ActivityQuestion.this, "Något gick fel", Toast.LENGTH_SHORT).show();
                    }



                }
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

                if (ContextCompat.checkSelfPermission(ActivityQuestion.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ActivityQuestion.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                } else {
                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePhotoIntent, CAMERA_REQUEST);
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
                    ivAnswerImage.setImageBitmap(bitmap);
                    ivAnswerImage.setVisibility(View.VISIBLE);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CAMERA_REQUEST) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ivAnswerImage.setImageBitmap(bitmap);
                ivAnswerImage.setVisibility(View.VISIBLE);
            }

        }
    }
}