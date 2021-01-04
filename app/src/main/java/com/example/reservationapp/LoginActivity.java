package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button btnSignin, btnSignup;
    String email, password;
    FirebaseAuth auth;
    DatabaseReference mDatabase;
    private static final String URL = "https://reservation-app-9b715-default-rtdb.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.editText_login_user);
        etPassword = findViewById(R.id.editText_login_password);
        btnSignin = findViewById(R.id.button_login_signIn);
        btnSignup = findViewById(R.id.button_login_register);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty())
                    Toast.makeText(LoginActivity.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                else
                    Login();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void Login(){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                else
                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
            }
        });

    }
}