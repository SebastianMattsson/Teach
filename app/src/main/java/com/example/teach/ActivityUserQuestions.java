package com.example.teach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityUserQuestions extends AppCompatActivity {

    ImageView ivCreateQuestion;
    ListView lvQuestions;
    ArrayList<Question> questionsList;
    DataBaseHelper dbh;
    TextView tvTitleUserQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);

        setUpContent();

        setOnClick();


    }

    private void setOnClick() {

        ivCreateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityUserQuestions.this, ActivityCreateQuestion.class);
                startActivity(intent);
            }
        });

        lvQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ActivityUserQuestions.this, ActivityQuestion.class);
                intent.putExtra("questionId", questionsList.get(i).getId());
                startActivity(intent);

            }
        });

    }

    private void setUpContent() {

        dbh = new DataBaseHelper(ActivityUserQuestions.this);

        ivCreateQuestion = findViewById(R.id.iv_create_new_question);
        lvQuestions = findViewById(R.id.lv_questions);
        lvQuestions.setScrollingCacheEnabled(false);
        tvTitleUserQuestions = findViewById(R.id.tv_title_user_questions);

        if (((LoggedInUser) getApplication()).getUser().getClass().getSimpleName().equals("Teacher")) {
            tvTitleUserQuestions.setText("Frågor du besvarat");
        }

        questionsList = new ArrayList<>();
        if (((LoggedInUser) getApplication()).getUser().getClass().getSimpleName().equals("Student")) {
            questionsList = dbh.getQuestions(((LoggedInUser) getApplication()).getUser().getUsername());
        } else {
            questionsList = dbh.getAnsweredQuestions(((LoggedInUser) getApplication()).getUser().getUsername());
            ivCreateQuestion.setVisibility(View.GONE);
        }

        QuestionListAdapter adapter = new QuestionListAdapter(ActivityUserQuestions.this, R.layout.question_view_layout, questionsList);

        lvQuestions.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.user_questions);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.latest_questions:
                        startActivity(new Intent(getApplicationContext(), ActivityLatest.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.user_questions:
                        return true;
                    case R.id.user_profile:
                        startActivity(new Intent(getApplicationContext(), ActivityProfile.class));
                        overridePendingTransition(0, 0);
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
}