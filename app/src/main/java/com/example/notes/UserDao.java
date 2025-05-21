package com.example.notes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(user  users);

    @Query("SELECT * FROM user ORDER BY uid DESC")
    List<user> fetch(); // fetch recent first

//    @Query("SELECT * FROM User")
//    List<user> fetch();

//        @Delete
//        void delete(user user); // delete by object



    @Query("DELETE FROM User WHERE tittle = :tittle")
    void delete(String tittle);

}