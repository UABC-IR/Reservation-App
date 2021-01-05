package com.example.reservationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Class.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import static com.example.reservationapp.LoginActivity.URL;

public class CreateReservationActivity extends AppCompatActivity {
    private EditText etDate, etTime, etName, etPhone;
    private String idUser,date, time, name, phone;
    private DatabaseReference mDatabase;
    public final static String EXTRA_RESERVATION_ID = "reservationId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        etDate = findViewById(R.id.fecha_reservacion);
        etTime = findViewById(R.id.time_reservation);
        etName = findViewById(R.id.personName_reservation);
        etPhone = findViewById(R.id.person_phoneNumber);

        etDate.setInputType(InputType.TYPE_NULL);
        etTime.setInputType(InputType.TYPE_NULL);

        Button btnReservation = findViewById(R.id.btnCreateReservation);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance(URL).getReference();

        SharedPreferences preferences = getSharedPreferences(SharedPreference.namePreference, MODE_PRIVATE);
        idUser = preferences.getString(SharedPreference.KeyId,null);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(etDate);
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog(etTime);
            }
        });

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
        final Reservation reservation = new Reservation(idUser,date,time,name,phone);
        reservation.setId(UUID.randomUUID().toString());
        mDatabase.child("reservation").child(reservation.getId()).setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CreateReservationActivity.this, R.string.msj_reservation1, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateReservationActivity.this, MainActivity.class);
                    System.out.println("Im in CreateReservationActivity Reservation ID:"+reservation.getId());
                    intent.putExtra(EXTRA_RESERVATION_ID, reservation.getId());
                    startActivity(intent);
                }
                else
                    Toast.makeText(CreateReservationActivity.this, R.string.msj_reservation2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTimeDialog(final EditText time_in) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                time_in.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new TimePickerDialog(CreateReservationActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        new DatePickerDialog(CreateReservationActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}