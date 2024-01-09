package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> taskList;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);

        Intent intent = getIntent();
        if (intent.hasExtra("user_name")) {
            userName = intent.getStringExtra("user_name");
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String username = document.getString("username");
                        // Check if the username matches the desired username
                        if (userName != null && userName.equals(username)) {
                            taskList.add((String) document.get("value"));
                            adapter.notifyDataSetChanged();
                        }

                    }
                }
            }
        });

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

                // add it to firebase
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> taskData = new HashMap<>();

                taskData.put("username", userName);
                taskData.put("value", task);

                db.collection("tasks")
                        .add(taskData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getApplicationContext(), "Failuer", Toast.LENGTH_LONG).show();
                            }
                        });

                // Add the task to the list and update the adapter
                if (!task.isEmpty()) {
                    taskList.add(task);
                    adapter.notifyDataSetChanged();

                    // Clear the EditText after adding the task
                    editTextTask.setText("");
                }
            }
        });

        // Bottom nav code
        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.getMenu().findItem(R.id.tasks).setChecked(true);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }
                else if (id == R.id.tasks){
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }
                else if (id == R.id.profile){
                    Intent intent = new Intent(MainActivity.this, Profile.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
}