package com.example.teach;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ActivityEditSubjects extends AppCompatActivity {

    private ListView lwSubjects;
    DataBaseHelper dbh;
    Button btnBack;
    ArrayList<SubjectForViews> subListAllSubjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subjects);


        setUpContent();

        setOnClick();
    }

    private void setUpContent() {

        lwSubjects = findViewById(R.id.list_subjects);
        btnBack = findViewById(R.id.btn_go_back);
        dbh = new DataBaseHelper(this);


        subListAllSubjects = new ArrayList<>();
        subListAllSubjects = dbh.getAllSubjects();
        ArrayList<SubjectForViews> subListForUser= dbh.getAllSubjects(((LoggedInUser) getApplication()).getUser());
        subListAllSubjects = compareLists(subListAllSubjects,subListForUser);

        SubjectListAdapter adapter = new SubjectListAdapter(this, R.layout.subject_view_layout, subListAllSubjects);

        lwSubjects.setAdapter(adapter);

    }

    private void setOnClick() {


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityEditSubjects.this, ActivityProfile.class);
                startActivity(intent);
            }
        });

        lwSubjects.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (subListAllSubjects.get(position).isSelected())
                {
                    subListAllSubjects.get(position).setSelected(false);
                    MaterialCardView cv = view.findViewById(R.id.cv_background);
                    cv.setCardBackgroundColor(getResources().getColor(R.color.red));
                    dbh.removeSubjectForUser(((LoggedInUser)getApplication()).getUser(), subListAllSubjects.get(position).getSubjectID());
                }
                else{
                    subListAllSubjects.get(position).setSelected(true);
                    MaterialCardView cv = view.findViewById(R.id.cv_background);
                    cv.setCardBackgroundColor(getResources().getColor(R.color.green));
                    dbh.addSubjectForUser(((LoggedInUser)getApplication()).getUser(), subListAllSubjects.get(position).getSubjectID());
                }


            }
        });

    }

    private ArrayList<SubjectForViews> compareLists(ArrayList<SubjectForViews> subListAllSubjects, ArrayList<SubjectForViews> subListForUser) {
        for (SubjectForViews sub:subListAllSubjects){

            for (SubjectForViews subUser: subListForUser){

                if(sub.getSubjectID() == subUser.getSubjectID())
                {
                    sub.setSelected(true);
                }
            }
        }

        return subListAllSubjects;

    }
}