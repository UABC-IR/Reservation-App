package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.reservationapp.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;
import static com.example.reservationapp.LoginActivity.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText etName, etLastname, etEmail, etPassword;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private String id, name,lastname, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName = findViewById(R.id.editText_user_name);
        etLastname = findViewById(R.id.editText_user_lastname);
        etEmail = findViewById(R.id.editText_user_email);
        etPassword = findViewById(R.id.editText_user_password);
        Button btnRegister = findViewById(R.id.button_register);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                lastname = etLastname.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                if(name.isEmpty() || lastname.isEmpty() || email.isEmpty() || password.isEmpty())
                    Toast.makeText(RegisterActivity.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                else {
                    if (password.length() < 8)
                        Toast.makeText(RegisterActivity.this, R.string.error_length_pass, Toast.LENGTH_SHORT).show();
                    else
                        Register();
                }
            }
        });
    }

    private void Register() {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        id = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                        User user = new User(name,lastname,email,password);

                        mDatabase.child("users").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, R.string.user_register, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    finish();
                                }
                                else
                                    Toast.makeText(RegisterActivity.this, R.string.user_register_fail1, Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else
                        Toast.makeText(RegisterActivity.this, R.string.user_register_fail, Toast.LENGTH_SHORT).show();
                }
        });
    }
}
