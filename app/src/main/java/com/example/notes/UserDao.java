package com.example.notes;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(user  users);

    @Query("SELECT * FROM User")
    List<user> fetch();
    @Query("DELETE FROM User WHERE tittle = :id")
    void delete(int id);

}