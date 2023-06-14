package com.example.notes.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.model.Notebook;
import com.example.notes.viewmodel.DatabaseHelper;
import com.example.notes.viewmodel.NoteAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button button, delete_button;
    private List<Notebook> notesList;
    private DatabaseHelper database;
    private NoteAdapter adapter;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        button = findViewById(R.id.button);
        delete_button = findViewById(R.id.deleteAll);
        updateActivity();
        button.setOnClickListener(listener);
        delete_button.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(view.getId()) {
                    case R.id.button:
                        startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
                        break;
                    case R.id.deleteAll:
                        database.deleteAllNotes();
                        updateActivity();
                        break;

                }
            }
        };
    public void fetchAllNotes(){
        Cursor cursor = database.readNotes();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "Заметок нет", Toast.LENGTH_SHORT).show();
        } else{
            while (cursor.moveToNext()){
                notesList.add(new Notebook(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }

    public void updateActivity(){
        notesList = new ArrayList<>();
        database = new DatabaseHelper(MainActivity.this);
        fetchAllNotes();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new NoteAdapter(MainActivity.this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);
    }
}