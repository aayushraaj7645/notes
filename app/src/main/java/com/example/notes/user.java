package com.example.notes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class user {
    @PrimaryKey(autoGenerate = true)
     public int uid;

    @ColumnInfo(name = "tittle")
    String tittle;

    @ColumnInfo(name = "input_box")
    String input_box;

    public static void remove(int position) {
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getInput_box() {
        return input_box;
    }

    public void setInput_box(String input_box) {
        this.input_box = input_box;
    }


    public user(String tittle, String input_box) {
        this.tittle = tittle;
        this.input_box = input_box;
    }





}
