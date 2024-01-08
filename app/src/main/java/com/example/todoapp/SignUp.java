package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    TextView loginRedirect;
    Button signupButton;
    EditText signupName, signupPassword, signupEmail, signupUsername;
    private boolean isExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginRedirect = findViewById(R.id.loginRedirect);
        signupButton = findViewById(R.id.signup_button);
        signupEmail = findViewById(R.id.signup_email);
        signupName = findViewById(R.id.signup_name);
        signupPassword = findViewById(R.id.signup_password);
        signupUsername = findViewById(R.id.signup_username);

//this button redirects the user to the login page to login
        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,Login.class);
                startActivity(intent);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String user = signupUsername.getText().toString();
                String pass = signupPassword.getText().toString();

                // Field validation
                // "^.{2,15}$" regex simply counts the letters
                if(!Pattern.compile("^.{2,15}$").matcher(name).matches()){
                    Toast.makeText(SignUp.this, "Name length must be between 2-15", Toast.LENGTH_SHORT).show();
                    return;
                }
                // the following regex makes sure there exists a @ and a . for the email
                else if(!Pattern.compile(".*@.*\\..*").matcher(email).matches()){
                    Toast.makeText(SignUp.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!Pattern.compile("^.{2,15}$").matcher(user).matches()){
                    Toast.makeText(SignUp.this, "Username length must be between 2-15", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!Pattern.compile("^.{2,15}$").matcher(pass).matches()){
                    Toast.makeText(SignUp.this, "Password length must be between 2-15", Toast.LENGTH_SHORT).show();
                    return;
                // the following regex makes sure there exists a capital letter and a special char for passwords
                } else if(!Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).*$").matcher(pass).matches()){
                    Toast.makeText(SignUp.this, "Password must contain a capital letter and one special char", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                // check if exists
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String userName = document.getString("name");
                                        String userEmail= document.getString("email");
                                        if (userName.equals(user) || userEmail.equals(email)) {
                                            Toast.makeText(SignUp.this,"user exists",Toast.LENGTH_SHORT).show();
                                            SignUp.this.isExist = true;
                                            return;
                                        }else{

                                            // Create a new user with a first and last name
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("name", name);
                                            userData.put("email", email);
                                            userData.put("username", user);
                                            userData.put("password", pass);

                                            db.collection("users")
                                                    .add(userData)
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

                                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                } else {
                                    Toast.makeText(SignUp.this,"something went wrong",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}