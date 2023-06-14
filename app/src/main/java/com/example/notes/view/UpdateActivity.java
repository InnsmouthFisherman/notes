package com.example.notes.view;
import com.example.notes.R;
import com.example.notes.viewmodel.DatabaseHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    private EditText title, description;
    private Button update, delete;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");

        update.setOnClickListener(listener);
        delete.setOnClickListener(listener);
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                DatabaseHelper database = new DatabaseHelper(UpdateActivity.this);

                switch(view.getId()){
                    case R.id.update:
                        database.updateNotes(title.getText().toString(), description.getText().toString(), id);
                        break;
                    case R.id.delete:
                        database.deleteItem(id);
                        break;
                }

                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            } else{
                Toast.makeText(UpdateActivity.this, "Изменений не внесено", Toast.LENGTH_SHORT).show();
            }
        }
    };
}