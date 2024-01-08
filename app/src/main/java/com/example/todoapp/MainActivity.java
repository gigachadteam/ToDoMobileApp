package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        setContentView(R.layout.activity_main);

        // Initialize taskList and adapter
        taskList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);

        // Set the adapter to the ListView
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // Get references to UI elements
        EditText editTextTask = findViewById(R.id.editTextTask);
        Button buttonAddTask = findViewById(R.id.buttonAddTask);

        // Set onClickListener for the "Add" button
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task from the EditText
                String task = editTextTask.getText().toString().trim();

                // Add the task to the list and update the adapter
                if (!task.isEmpty()) {
                    taskList.add(task);
                    adapter.notifyDataSetChanged();

                    // Clear the EditText after adding the task
                    editTextTask.setText("");
                }
            }
        });
    }
}