package com.example.reservationapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Fragments.MyReservationFragment;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_RESERVATION_ID = "reservationId";
    private int mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Reservation reservation = (Reservation) getIntent().getSerializableExtra(EXTRA_RESERVATION_ID);

        if(reservation != null){
            TextView nombre = findViewById(R.id.reservacion_info_nombre);
            nombre.setText(reservation.getName());

            TextView fecha = findViewById(R.id.reservacion_info_fecha);
            fecha.setText(reservation.getDate());

            TextView hora = findViewById(R.id.reservacion_info_hora);
            hora.setText(reservation.getTime());
        }
    }

}