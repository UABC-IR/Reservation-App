package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.reservationapp.Class.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private Button btnSignin, btnSignup;
    private String email, password;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    public static final String URL = "https://reservation-app-9b715-default-rtdb.firebaseio.com/";
    private SharedPreferences preferences;
    private static final String KeyChild1 = "users";

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

        preferences = getSharedPreferences(SharedPreference.namePreference, MODE_PRIVATE);
        email = preferences.getString(SharedPreference.KeyEmail,null);
        password = preferences.getString(SharedPreference.KeyPassword,null);

        if(email != null && password != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

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
                if(task.isSuccessful()) {
                    SaveSharedPreferences(auth.getUid());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SaveSharedPreferences(final String id){
        mDatabase.child(KeyChild1).child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(SharedPreference.KeyId,id);
                    edit.putString(SharedPreference.KeyName,dataSnapshot.child("name").getValue().toString());
                    edit.putString(SharedPreference.KeyLastname,dataSnapshot.child("lastname").getValue().toString());
                    edit.putString(SharedPreference.KeyEmail,dataSnapshot.child("email").getValue().toString());
                    edit.putString(SharedPreference.KeyPassword,dataSnapshot.child("password").getValue().toString());
                    edit.commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}