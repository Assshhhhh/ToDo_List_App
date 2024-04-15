package com.example.to_dolistapp.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_list")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title, disp;
    public Note(String title, String disp) {
        this.title = title;
        this.disp = disp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisp() {
        return disp;
    }

    public void setDisp(String disp) {
        this.disp = disp;
    }



}

