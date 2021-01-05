package com.example.reservationapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.reservationapp.Class.Reservation;

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