package com.example.teach;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionListAdapter extends ArrayAdapter<Question> {
    private final Context context;
    private final int resource;
    DataBaseHelper dbh;

    public QuestionListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Question> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.dbh = new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource,parent,false);
            holder = new ViewHolder();
            holder.ivSubject = convertView.findViewById(R.id.subject_image);
            holder.ivQuestionImage = convertView.findViewById(R.id.question_img);
            holder.ivUserImage = convertView.findViewById(R.id.profile_pic);
            holder.tvusername = convertView.findViewById(R.id.username);
            holder.tvTitle = convertView.findViewById((R.id.question_title));
            holder.tvDescription = convertView.findViewById(R.id.question_description);
            holder.tvPostDate = convertView.findViewById(R.id.post_date);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Question question = getItem(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        holder.ivSubject.setImageBitmap(dbh.getSubjectImage(question.getSubjectId()));
        if(question.getImage() == null)
        {
            holder.ivQuestionImage.setVisibility(View.GONE);
        }
        else{
            holder.ivQuestionImage.setImageBitmap(question.getImage());
        }

        holder.ivUserImage.setImageBitmap(dbh.getStudentImage(question.getUsername()));
        holder.tvPostDate.setText(dateFormat.format(question.getPostDate()));
        holder.tvTitle.setText(question.getTitle());
        holder.tvDescription.setText(question.getDescription());
        holder.tvusername.setText(question.getUsername());


        return convertView;
    }

    static class ViewHolder{
        ImageView ivSubject;
        ImageView ivUserImage;
        ImageView ivQuestionImage;
        TextView tvusername;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvPostDate;

    }
}
