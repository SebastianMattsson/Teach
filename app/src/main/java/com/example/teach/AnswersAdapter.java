package com.example.teach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AnswersAdapter extends ArrayAdapter<Answer> {
    private final Context context;
    private final int resource;
    DataBaseHelper dbh;

    public AnswersAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Answer> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.dbh = new DataBaseHelper(context);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource,parent,false);
            holder = new ViewHolder();
            holder.ivAnswerImage = convertView.findViewById(R.id.answer_img);
            holder.ivUserImage = convertView.findViewById(R.id.profile_pic);
            holder.tvUsername = convertView.findViewById(R.id.username);
            holder.tvDescription = convertView.findViewById(R.id.answer_description);
            holder.tvPostDate = convertView.findViewById(R.id.post_date);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Answer answer = getItem(position);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        holder.ivUserImage.setImageBitmap(dbh.getTeacherImage(answer.getUsername()));
        holder.tvDescription.setText(answer.getAnswer());
        if(answer.getImage() == null)
        {
            holder.ivAnswerImage.setVisibility(View.GONE);
        }
        else{
            holder.ivAnswerImage.setImageBitmap(answer.getImage());
        }

        holder.tvPostDate.setText(dateFormat.format(answer.getAnswerDate()));
        holder.tvUsername.setText(answer.getUsername());


        return convertView;
    }

    static class ViewHolder{
        ImageView ivUserImage;
        ImageView ivAnswerImage;
        TextView tvUsername;
        TextView tvDescription;
        TextView tvPostDate;

    }
}
