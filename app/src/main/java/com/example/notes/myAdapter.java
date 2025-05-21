package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {
      holder.tittle_box.setText(users.get(position).getTittle());
      holder.notes_data.setText(users.get(position).getInput_box());
      holder.delete.setOnClickListener(new View.OnClickListener() {
          @SuppressLint("NotifyDataSetChanged")
          @Override
          public void onClick(View v) {
              new Thread(() -> {
                  database = Room.databaseBuilder(v.getContext(),
                          notesDatabase.class, "notesDatabase").build();
                  database.userDao().delete(users.get(position).getTittle());
                  ((Activity) holder.tittle_box.getContext()).runOnUiThread(() -> {
                      users.remove(position);
                      //update the fresh list of the array list to the recyclerview
                      notifyDataSetChanged();
                  });
              }).start();
          }
      });
  holder.open.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(v.getContext(),notes_inside.class);
        intent.putExtra("tittle",users.get(position).getTittle());
        intent.putExtra("input_box",users.get(position).getInput_box());
        v.getContext().startActivity(intent);

      }
  });


    }
    @Override
    public int getItemCount() {
        return users.size();
    }
        public class myViewHolder extends RecyclerView.ViewHolder {
           EditText tittle_box;
            EditText notes_data;
            ImageView delete;
            ImageView open;

            @SuppressLint("ClickableViewAccessibility")
            public myViewHolder(@NonNull View itemView) {
                super(itemView);
                tittle_box = itemView.findViewById(R.id.tittle_box);
                notes_data = itemView.findViewById(R.id.notes_data);
                delete = itemView.findViewById(R.id.imageView);
                 open = itemView.findViewById(R.id.open_image);

                notes_data.setMovementMethod(new ScrollingMovementMethod());

                notes_data.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);

                        if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        return false;
                    }
                });
            }

        }

    }

