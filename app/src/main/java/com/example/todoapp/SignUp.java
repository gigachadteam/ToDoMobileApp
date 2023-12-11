package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TextView loginRedirect;
    Button signupButton;
    EditText signupName, signupPassword, signupEmail, signupUsername;
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
                if(!Pattern.compile("^.{2,15}$").matcher(name).matches()){
                    Toast.makeText(SignUp.this, "Name length must be between 2-15", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.compile(".*@.*\\..*").matcher(email).matches()){
                    Toast.makeText(SignUp.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.compile("^.{2,15}$").matcher(user).matches()){
                    Toast.makeText(SignUp.this, "Username length must be between 2-15", Toast.LENGTH_SHORT).show();
                }
                else if(!Pattern.compile("^.{2,15}$").matcher(pass).matches()){
                    Toast.makeText(SignUp.this, "Password length must be between 2-15", Toast.LENGTH_SHORT).show();
                } else if(!Pattern.compile("^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?]).*$").matcher(pass).matches()){
                    Toast.makeText(SignUp.this, "Password must contain a capital letter and one special char", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}