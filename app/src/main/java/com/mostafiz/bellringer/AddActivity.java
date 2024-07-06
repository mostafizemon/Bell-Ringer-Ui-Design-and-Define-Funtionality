package com.mostafiz.bellringer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddActivity extends AppCompatActivity {

    EditText add_room;
    Button submit;
    String room;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        add_room=findViewById(R.id.add_room);
        submit=findViewById(R.id.submit);

        databaseHelper=new DatabaseHelper(AddActivity.this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                room=add_room.getText().toString();
                databaseHelper.add_room(room);
                Toast.makeText(AddActivity.this,"Room Name Insert Successfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                finish();
            }
        });


    }
}