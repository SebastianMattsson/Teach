package com.example.teach;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class AdapterForSubjectsInQuestions extends RecyclerView.Adapter<AdapterForSubjectsInQuestions.ViewHolder> {
    ArrayList<SubjectForViews> subList;
    Context context;
    ArrayList<MaterialCardView> cardList;

    public AdapterForSubjectsInQuestions( Context context, ArrayList<SubjectForViews> subList) {
        this.subList = subList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_in_questions_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvSubName.setText(subList.get(position).getSubjectName());
        holder.tvSubAgeGroup.setText(subList.get(position).getSubjectAgeGroup());
        holder.subImage.setImageBitmap(subList.get(position).getImage());
        holder.cvSubject.setChecked(subList.get(position).isSelected());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.cvSubject.isChecked()){
                    holder.cvSubject.setChecked(false);
                    subList.get(holder.getAdapterPosition()).setSelected(false);
                }
                else{
                    holder.cvSubject.setChecked(true);
                    subList.get(holder.getAdapterPosition()).setSelected(true);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return subList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView subImage;
        TextView tvSubName, tvSubAgeGroup;
        MaterialCardView cvSubject;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subImage = itemView.findViewById(R.id.subject_image);
            tvSubName = itemView.findViewById(R.id.subject_name);
            tvSubAgeGroup = itemView.findViewById(R.id.subject_age_group);
            cvSubject = itemView.findViewById(R.id.card_subject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
