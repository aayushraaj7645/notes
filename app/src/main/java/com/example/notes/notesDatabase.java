package com.example.notes;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {user.class}, version = 1)
public abstract class notesDatabase extends RoomDatabase {
    public abstract UserDao userDao();

}
