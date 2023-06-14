package com.example.notes.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notes.R;
import com.example.notes.viewmodel.DatabaseHelper;

public class AddNoteActivity extends AppCompatActivity {
    EditText title, description;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        add = findViewById(R.id.add);
        add.setOnClickListener(listener);
    }
    View.OnClickListener listener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                DatabaseHelper database = new DatabaseHelper(AddNoteActivity.this);
                database.addNotes(title.getText().toString(), description.getText().toString());

                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else{
                Toast.makeText(AddNoteActivity.this, "Изменений не внесено", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
