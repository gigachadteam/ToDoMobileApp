package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends AppCompatActivity {
    EditText loginUsername, loginPassword;
    Button loginButton;
    TextView signupRedirect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.login_username);
        loginPassword = findViewById(R.id.login_password);
        signupRedirect = findViewById(R.id.signupRedirect);
        loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String userName = document.getString("name");
                                        String userPassword = document.getString("password");
                                        if (userName != null && userPassword != null && userName.equals(loginUsername.getText().toString()) && userPassword.equals((loginPassword.getText().toString()))) {
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            intent.putExtra("user_name", loginUsername.getText().toString());
                                            startActivity(intent);
                                            Toast.makeText(Login.this,"LOGIN SUCESSED",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Login.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(Login.this,"something went wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        //This part redirects the user to the the sign up page to enter credentials
        signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

}