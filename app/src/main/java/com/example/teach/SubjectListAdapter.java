package com.example.teach;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class SubjectListAdapter extends ArrayAdapter<SubjectForViews> {
    private final Context context;
    private final int resource;


    public SubjectListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<SubjectForViews> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int subjectId = getItem(position).getSubjectID();
        String subjectName = getItem(position).getSubjectName();
        String subjectAgeGroup = getItem(position).getSubjectAgeGroup();
        Bitmap image = getItem(position).getImage();
        boolean selected = getItem(position).isSelected();

        SubjectForViews sub = new SubjectForViews(subjectId, subjectName,subjectAgeGroup,image, selected);

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource,parent,false);

        TextView tvSubjectName = convertView.findViewById(R.id.subject_name);
        TextView tvSubjectAgeGroup = convertView.findViewById(R.id.subject_age_group);
        ImageView ivImage = convertView.findViewById(R.id.subject_pic);
        MaterialCardView rlBackground = convertView.findViewById(R.id.cv_background);


        if(sub.isSelected())
        {

            rlBackground.setCardBackgroundColor(context.getResources().getColor(R.color.green));
        }


        tvSubjectName.setText(subjectName);
        tvSubjectAgeGroup.setText(subjectAgeGroup);
        ivImage.setImageBitmap(Bitmap.createScaledBitmap(image,200,200,false));



        return convertView;
    }
}
