package com.example.notes;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class fetcher extends AppCompatActivity {
    RecyclerView recyclerView;

    notesDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fetcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);


    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    database = Room.databaseBuilder(getApplicationContext(),
                    notesDatabase.class, "notesDatabase")
            // .addMigrations(MIGRATION_1_2)
            .build();

       getroomdata();
    }


    public void getroomdata(){
        new Thread(() -> {


            List<user> user = database.userDao().fetch();


            runOnUiThread(() -> {
                myAdapter adapter = new myAdapter(user);
                recyclerView.setAdapter(adapter);

            });
        }).start();
    }
}
