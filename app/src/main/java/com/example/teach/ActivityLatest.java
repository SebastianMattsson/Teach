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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ActivityLatest extends AppCompatActivity {

    ListView lvQuestions;
    ArrayList<Question> questionsList;
    DataBaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest);

        setUpContent();

        setOnClick();







    }

    private void setOnClick() {

        lvQuestions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ActivityLatest.this,ActivityQuestion.class);
                intent.putExtra("questionId", questionsList.get(i).getId());
                startActivity(intent);

            }
        });

    }

    private void setUpContent() {

        dbh = new DataBaseHelper(ActivityLatest.this);

        lvQuestions = findViewById(R.id.lv_questions);
        lvQuestions.setScrollingCacheEnabled(false);

        ArrayList<SubjectForViews> subList = dbh.getAllSubjects(((LoggedInUser)getApplication()).getUser());
        questionsList = new ArrayList<>();
        questionsList = dbh.getQuestionsForUsersSubjects(subList);

        QuestionListAdapter adapter = new QuestionListAdapter(ActivityLatest.this, R.layout.question_view_layout, questionsList);

        lvQuestions.setAdapter(adapter);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.latest_questions);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.latest_questions:
                        return true;
                    case R.id.user_questions:
                        startActivity(new Intent(getApplicationContext(), ActivityUserQuestions.class));
                        overridePendingTransition(0, 0);
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