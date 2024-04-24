package com.example.signup_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextUsername, editTextPassword;
    private Button buttonSignup;

    private TextView loginBtn;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Authentication
        fAuth = FirebaseAuth.getInstance();

        // Find views
        editTextName = findViewById(R.id.signup_name);
        editTextEmail = findViewById(R.id.signup_email);
        editTextUsername = findViewById(R.id.signup_username);
        editTextPassword = findViewById(R.id.signup_password);
        buttonSignup = findViewById(R.id.signup_button);
        loginBtn = findViewById(R.id.loginRedirectText);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        // Set click listener for signup button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input
                String name = editTextName.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                
                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name is Required.");
                    return;
                }
                
                if(TextUtils.isEmpty(email)){
                    editTextEmail.setError("Email is Required.");
                    return;
                }
                
                if(TextUtils.isEmpty(username)){
                    editTextUsername.setError("Username is Required.");
                    return;
                }
                
                if(TextUtils.isEmpty(password)){
                    editTextPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6 ){
                    editTextPassword.setError("Password Must be >= Characters");
                    return;
                }

               fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(SignupActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       }
                       else {
                           Toast.makeText(SignupActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }


}
