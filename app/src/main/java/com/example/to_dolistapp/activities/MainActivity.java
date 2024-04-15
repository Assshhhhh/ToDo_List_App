package com.example.to_dolistapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapp.R;
import com.example.to_dolistapp.adapters.TodoAdapter;
import com.example.to_dolistapp.databinding.ActivityMainBinding;
import com.example.to_dolistapp.room.Note;
import com.example.to_dolistapp.viewmodel.NoteViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NoteViewModel noteViewModel;
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        setTitle("To-Do");
        noteViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(NoteViewModel.class);
        todoAdapter = new TodoAdapter();


        // Setting Recycler
        binding.recyclerTodo.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTodo.setHasFixedSize(true);
        binding.recyclerTodo.setAdapter(todoAdapter);

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                todoAdapter.submitList(notes);
            }
        });

        // FAB (Add/Insert Data)
        binding.fabAdd.setOnClickListener(v -> {

            Intent intent = new Intent(this, DataInsertActivity.class);
            intent.putExtra("type", "addMode");
            startActivityForResult(intent, 1);

        });

        // Update and Delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.RIGHT){
                    noteViewModel.delete(todoAdapter.getNote(viewHolder.getAdapterPosition()));
                    Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
                } else if (direction == ItemTouchHelper.LEFT){
                    Intent intent = new Intent(MainActivity.this, DataInsertActivity.class);
                    intent.putExtra("title", todoAdapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                    intent.putExtra("disp", todoAdapter.getNote(viewHolder.getAdapterPosition()).getDisp());
                    intent.putExtra("id", todoAdapter.getNote(viewHolder.getAdapterPosition()).getId());
                    intent.putExtra("type", "updateMode");
                    startActivityForResult(intent, 2);
                }
            }
        }).attachToRecyclerView(binding.recyclerTodo);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("desc");
            Note note = new Note(title, disp);
            noteViewModel.insert(note);
            Toast.makeText(this, "Task Added!", Toast.LENGTH_SHORT).show();

        } else if (requestCode == 2) {

            String title = data.getStringExtra("title");
            String disp = data.getStringExtra("desc");
            Note note = new Note(title, disp);
            note.setId(data.getIntExtra("id", 0));
            noteViewModel.update(note);
            Toast.makeText(this, "Task Updated!", Toast.LENGTH_SHORT).show();
        }

    }
}