package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {

    public String userName;
    public int tasksCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        TextView numberOfTasks = findViewById(R.id.numberOfTasks);
        TextView userNameTextField = findViewById(R.id.userName);


        Intent intent = getIntent();
        if (intent.hasExtra("user_name")) {
            userName = intent.getStringExtra("user_name");
        }

        userNameTextField.setText(userName);
        // get data form firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Assuming the field for the username is called "username"
                                String username = document.getString("username");

                                // Check if the username matches the desired username
                                if (userName != null && userName.equals(username)) {
                                    tasksCount++;
                                }
                            }
                            numberOfTasks.setText("Number of tasks: " + tasksCount);
                        } else {
                            Toast.makeText(Profile.this,"something went wrong",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        // Bottom nav code
        BottomNavigationView nav = findViewById(R.id.nav1);
        nav.getMenu().findItem(R.id.profile).setChecked(true);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.home){
                    Intent intent = new Intent(Profile.this, Home.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }
                else if (id == R.id.tasks){
                    Intent intent = new Intent(Profile.this, MainActivity.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }
                else if (id == R.id.profile){
                    Intent intent = new Intent(Profile.this, Profile.class);
                    intent.putExtra("user_name", userName);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
}