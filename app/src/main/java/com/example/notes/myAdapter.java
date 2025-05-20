package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.myViewHolder>{

    List<user> users;
 notesDatabase database;
    public myAdapter(List<user> users) {
         this.users = users;
    }
    @NotNull
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_layout, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
      holder.tittle_box.setText(users.get(position).getTittle());
      holder.notes_data.setText(users.get(position).getInput_box());



    }
    @Override
    public int getItemCount() {
        return users.size();
    }
        public class myViewHolder extends RecyclerView.ViewHolder {
           EditText tittle_box;
            EditText notes_data;
            ImageView imageView;

            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                tittle_box = itemView.findViewById(R.id.tittle_box);
                notes_data = itemView.findViewById(R.id.notes_data);
                imageView = itemView.findViewById(R.id.imageView);
                notes_data.setMovementMethod(new ScrollingMovementMethod());

            }
        }

    }

