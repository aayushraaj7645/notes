package com.example.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
   private EditText tittle;
   private EditText input_box;
  private Button save;
   private Button fetch;
    private String tittle_text;
   private String input_box_text;

    notesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tittle=findViewById(R.id.tittle);
        input_box=findViewById(R.id.input_box);
        save=findViewById(R.id.save);
        fetch=findViewById(R.id.fetch);

        input_box.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        database = Room.databaseBuilder(getApplicationContext(),
                notesDatabase.class, "notesDatabase").build();


        class clicking {
            private final String tittle_text;
            private final String input_box_text;

            clicking(String tittle_text, String input_box_text) {
                this.tittle_text = tittle_text;
                this.input_box_text = input_box_text;
            }
        public void click() {
                new Thread(() -> {
                    if (tittle_text.isEmpty() || input_box_text.isEmpty()) {
                        String empty_field = getString(R.string.field_are_empty);
                        if (input_box_text.isEmpty()) {
                            input_box.setText(empty_field);
                        }
                        if (tittle_text.isEmpty()) {
                            tittle.setText(empty_field);
                        }
                        if (tittle_text.isEmpty() && input_box_text.isEmpty()) {
                            tittle.setText(empty_field);
                            input_box.setText(empty_field);
                        }

                    } else {
                        user user = new user(tittle_text, input_box_text);
                        database.userDao().insert(user);
                        runOnUiThread(() -> {
                            tittle.setText("");
                            input_box.setText("");
                        });
                    }
                }).start();
            }
        }
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                tittle_text=tittle.getText().toString();
                input_box_text=input_box.getText().toString();
                clicking click=new clicking(tittle_text,input_box_text);
                click.click();
            }


        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, fetcher.class);
                startActivity(intent);

            }
        });
        tittle.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                tittle.post(() -> tittle.setSelection(tittle.getText().length()));
            }

        });
        input_box.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                input_box.post(() -> {
                    input_box.setSelection(input_box.getText().length());
                    input_box.requestFocus();
                });
            }
        });

    }
}