package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Class.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import static com.example.reservationapp.LoginActivity.URL;

public class CreateReservationActivity extends AppCompatActivity {
    private EditText etDate, etTime, etName, etPhone;
    private String id,date, time, name, phone;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        etDate = findViewById(R.id.fecha_reservacion);
        etTime = findViewById(R.id.time_reservation);
        etName = findViewById(R.id.personName_reservation);
        etPhone = findViewById(R.id.person_phoneNumber);

        Button btnReservation = findViewById(R.id.btnCreateReservation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        SharedPreferences preferences = getSharedPreferences(SharedPreference.namePreference, MODE_PRIVATE);
        id = preferences.getString(SharedPreference.KeyId,null);

        btnReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = etDate.getText().toString();
                time = etTime.getText().toString();
                name = etName.getText().toString();
                phone = etPhone.getText().toString();
                if(date.isEmpty() || time.isEmpty() || name.isEmpty() || phone.isEmpty())
                    Toast.makeText(CreateReservationActivity.this, R.string.error_empty_fields, Toast.LENGTH_SHORT).show();
                else
                    RegisterReservation();
            }
        });
    }

    private void RegisterReservation() {
        Reservation reservation = new Reservation(id,date,time,name,phone);
        reservation.setId(UUID.randomUUID().toString());
        mDatabase.child("reservation").child(reservation.getId()).setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                    Toast.makeText(CreateReservationActivity.this, R.string.msj_reservation1, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(CreateReservationActivity.this, R.string.msj_reservation2, Toast.LENGTH_SHORT).show();
            }
        });
    }
}