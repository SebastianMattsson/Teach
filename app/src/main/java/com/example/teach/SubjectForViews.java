package com.example.teach;

import android.graphics.Bitmap;

public class SubjectForViews {
    public int subjectID;
    public String subjectName;
    public String subjectAgeGroup;
    public Bitmap image;
    public boolean selected;

    public SubjectForViews(int subjectID,String subjectName, String subjectAgeGroup, Bitmap image, boolean selected)
    {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.subjectAgeGroup = subjectAgeGroup;
        this.image = image;
        this.selected = selected;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAgeGroup() {
        return subjectAgeGroup;
    }

    public void setSubjectAgeGroup(String subjectAgeGroup) {
        this.subjectAgeGroup = subjectAgeGroup;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
