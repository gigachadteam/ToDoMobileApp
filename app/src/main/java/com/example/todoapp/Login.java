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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
                                        if (userName != null && userName.equals(loginUsername.getText().toString())) {
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(Login.this,"LOGIN SUCESSED",Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Login.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                } else {
                                    Toast.makeText(Login.this,"something went wrong",Toast.LENGTH_SHORT).show();
                                }





//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {

//                                        Log.d(TAG, document.getId() + " => " + document.getData());
//                                    }
//                                } else {
//                                    Log.w(TAG, "Error getting documents.", task.getException());
//                                }
                            }
                        });



//                if(loginUsername.getText().toString().equals("admin")&& loginPassword.getText().toString().equals("admin")){
//                    //correct
//                    Intent intent = new Intent(Login.this, MainActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(Login.this,"LOGIN SUCCESSFUL",Toast.LENGTH_SHORT).show();
//                }else
//                    //incorrect
//                    Toast.makeText(Login.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
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