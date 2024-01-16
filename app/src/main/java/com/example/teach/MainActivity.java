package com.example.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button buttonLogIn;
    DataBaseHelper dbh;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setUpContent();

        insertHardcoded();

        setOnClick();


    }
    //Metod för alla knapptryck som kan ske i aktiviteten
    private void setOnClick() {

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<User> list = dbh.getUsers();
                boolean accountExists = false;

                for (User u : list) {
                    if (u.getUsername().equals(username.getText().toString()) && u.getPassword().equals(password.getText().toString())) {
                        ((LoggedInUser) getApplication()).setUser(u);
                        Intent intent = new Intent(MainActivity.this, ActivityLatest.class);
                        startActivity(intent);
                        accountExists = true;
                    }

                }

                if (!accountExists) {
                    Toast.makeText(MainActivity.this, "Fel användarnamn eller lösenord", Toast.LENGTH_LONG).show();
                }


            }


        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityRegister.class);
                startActivity(intent);
            }
        });
    }

    //Metod för att initiera alla Views som finns i aktiviteten som ska användas
    private void setUpContent() {

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        buttonLogIn = findViewById(R.id.button_log_in);
        tvRegister = findViewById(R.id.tv_register);
        dbh = new DataBaseHelper(this);
    }

    //Metod för att hårdkodat lägga in ämnen i databasen
    public void insertHardcoded() {
        List<Subject> subjectList = new ArrayList<Subject>();
        Subject math = new Subject("Matte", BitmapFactory.decodeResource(getResources(), R.drawable.matte));
        Subject physics = new Subject("Fysik", BitmapFactory.decodeResource(getResources(), R.drawable.fysik));
        Subject geography = new Subject("Geografi", BitmapFactory.decodeResource(getResources(), R.drawable.geografi));
        Subject chemistry = new Subject("Kemi", BitmapFactory.decodeResource(getResources(), R.drawable.kemi));

        subjectList.add(math);
        subjectList.add(physics);
        subjectList.add(geography);
        subjectList.add(chemistry);

        dbh.createHardcodedGrades(math);
        dbh.createHardcodedSubjectNames(subjectList);
        dbh.createHardcodedSubjects(subjectList);


    }

    //Metod för att ta bort tangentbordet om en användare trycker utanför textfältet som nuvarande redigerar
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

}