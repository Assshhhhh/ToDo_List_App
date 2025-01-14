package com.example.to_dolistapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_dolistapp.repo.NoteRepo;
import com.example.to_dolistapp.room.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepo noteRepo;
    private LiveData<List<Note>> notesList;
    public NoteViewModel(@NonNull Application application) {
        super(application);

        noteRepo = new NoteRepo(application);
        notesList = noteRepo.getAllData();

    }

    public void insert(Note note){
        noteRepo.insertData(note);
    }

    public void update(Note note){
        noteRepo.updateData(note);
    }

    public void delete(Note note){
        noteRepo.deleteData(note);
    }

    public LiveData<List<Note>> getAllNotes(){
        return notesList;
    }

}
