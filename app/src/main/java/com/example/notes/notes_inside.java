package com.example.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class notes_inside extends AppCompatActivity {
    private EditText tittle_data_box;
    private EditText notes_data_box;
    private Button edit_save;
    private String originalTitle = "";


    notesDatabase database;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_inside);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tittle_data_box =findViewById(R.id.tittle_data_box);
        notes_data_box=findViewById(R.id.notes_data_box);
        edit_save = findViewById(R.id.edit_save);
        ScrollView scrollView = findViewById(R.id.main);
        notes_data_box.setMovementMethod(new android.text.method.ScrollingMovementMethod());



        Intent intent = getIntent();
        String title = intent.getStringExtra("tittle");
        String content = intent.getStringExtra("input_box");

        tittle_data_box.setText(title);
        notes_data_box.setText(content);
        originalTitle = title;

        tittle_data_box.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                tittle_data_box.post(() -> tittle_data_box.setSelection(tittle_data_box.getText().length()));
            }

        });
        notes_data_box.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                notes_data_box.post(() -> {
                    notes_data_box.setSelection(notes_data_box.getText().length());
                    notes_data_box.requestFocus();
                });
            }
        });
//        edit_save.setOnClickListener(v -> {
//            // Your save logic
//
//            // Scroll to bottom so button is not hidden
//
//            scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
//        });

//
//        notes_data_box.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//
//                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    v.getParent().requestDisallowInterceptTouchEvent(false);
//                }
//
//                return false;
//            }
//        });
        class clicking {
            private final String tittle_text;
            private final String input_box_text;

            clicking(String tittle_text, String input_box_text)
            {
                this.tittle_text = tittle_text;
                this.input_box_text = input_box_text;
            }
            public void click() {
                new Thread(() -> {
                    if (tittle_text.isEmpty() || input_box_text.isEmpty()) {
                        String empty_field = getString(R.string.field_are_empty);
                        if (input_box_text.isEmpty()) {
                            notes_data_box.setText(empty_field);
                        }
                        if (tittle_text.isEmpty()) {
                            tittle_data_box.setText(empty_field);
                        }
                        if (tittle_text.isEmpty() && input_box_text.isEmpty()) {
                            tittle_data_box.setText(empty_field);
                            notes_data_box.setText(empty_field);
                        }

                    } else {
                        database = Room.databaseBuilder(getApplicationContext(),
                                notesDatabase.class, "notesDatabase").build();
                        user user = new user(tittle_text, input_box_text);
                        database.userDao().delete(originalTitle);
                        user newUser = new user(tittle_text, input_box_text);
                        database.userDao().insert(newUser);
                        runOnUiThread(() -> {
                            tittle_data_box.setText("");
                            notes_data_box.setText("");
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK, resultIntent);
                        });
                    }
                }).start();
            }
        }

        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle_text = tittle_data_box.getText().toString();
                String input_box_text = notes_data_box.getText().toString();
                clicking click = new clicking(tittle_text, input_box_text);
                click.click();

            }
        });
    }

}