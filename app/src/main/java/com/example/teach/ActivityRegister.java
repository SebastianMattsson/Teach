package com.example.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ActivityRegister extends AppCompatActivity {

    Button buttonRegister;
    EditText username, password, rePassword, email;
    RadioGroup rg;
    TextView tvGoToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        rePassword = findViewById(R.id.password_re_register);
        email = findViewById(R.id.email_register);
        rg = findViewById(R.id.rg_user_type);
        tvGoToLogIn = findViewById(R.id.tv_login);

        buttonRegister = findViewById(R.id.button_register);

        setOnClick();


    }

    private void setOnClick() {

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dbh = new DataBaseHelper(ActivityRegister.this);
                List<User> list = dbh.getUsers();

                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || rePassword.getText().toString().isEmpty() || email.getText().toString().isEmpty() || rg.getCheckedRadioButtonId() == -1)
                {
                    Toast.makeText(ActivityRegister.this, "Alla fält måste vara ifyllda", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    boolean success = true;

                    for (User u:list)
                    {
                        if(u.getUsername().toString().equals(username.getText().toString()) || u.geteMail().toString().equals(email.getText().toString().toLowerCase()))
                        {
                            success = false;
                        }
                    }

                    if (success)
                    {
                        if(!password.getText().toString().equals(rePassword.getText().toString()))
                        {
                            Toast.makeText(ActivityRegister.this, "Lösenorden matchar inte", Toast.LENGTH_SHORT).show();
                        }
                        else if(rg.getCheckedRadioButtonId() == R.id.rb_teacher)
                        {
                            Teacher teacher = new Teacher(username.getText().toString(), password.getText().toString(),email.getText().toString().toLowerCase(), BitmapFactory.decodeResource(getResources(), R.drawable.teacher1));
                            success = dbh.addTeacher(teacher);
                            if (success)
                            {
                                Toast.makeText(ActivityRegister.this, "Ditt konto har skapats", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                        else if(rg.getCheckedRadioButtonId() == R.id.rb_student)
                        {
                            Student student = new Student(username.getText().toString(), password.getText().toString(),email.getText().toString().toLowerCase(), BitmapFactory.decodeResource(getResources(), R.drawable.student1));
                            success = dbh.addStudent(student);
                            if (success)
                            {
                                Toast.makeText(ActivityRegister.this, "Ditt konto har skapats", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(ActivityRegister.this, "Ett konto med det användarnamnet eller e-posten existerar redan", Toast.LENGTH_LONG).show();
                    }


                }

            }
        });


        tvGoToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
                startActivity(intent);
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
}