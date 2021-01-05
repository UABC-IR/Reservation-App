package com.example.reservationapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Class.SharedPreference;
import com.example.reservationapp.CreateReservationActivity;
import com.example.reservationapp.DetailActivity;
import com.example.reservationapp.LoginActivity;
import com.example.reservationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import static android.content.Context.MODE_PRIVATE;
import static com.example.reservationapp.LoginActivity.URL;

public class TopFragment extends Fragment {
    private SharedPreferences preferences;
    // Get a reference to our posts


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);



        String reservationId = getActivity().getIntent().getStringExtra(CreateReservationActivity.EXTRA_RESERVATION_ID);
        System.out.println("Reservation ID: " + reservationId);
        final TextView reservationName = view.findViewById(R.id.home_reservationName);
        final TextView reservationDate = view.findViewById(R.id.home_reservationDate);
        final TextView reservationHour = view.findViewById(R.id.home_reservationHour);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(URL).getReference();
        if (reservationId != null){
            mDatabase.child("reservation").child(reservationId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    reservationName.setText(Objects.requireNonNull(dataSnapshot.child("name").getValue().toString()));
                    reservationDate.setText(Objects.requireNonNull(dataSnapshot.child("date").getValue().toString()));
                    reservationHour.setText(Objects.requireNonNull(dataSnapshot.child("time").getValue().toString()));
                    System.out.println(Objects.requireNonNull(dataSnapshot.child("phone").getValue()).toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        /*Boton que cierra sesión*/
        Button btnLogout = view.findViewById(R.id.button_logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit().clear().apply();
                startActivity(new Intent(TopFragment.this.getContext(), LoginActivity.class));
            }
        });

        /*Boton para crear una reservación*/
        ImageButton btnBooking = view.findViewById(R.id.imageButton_addReservation);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopFragment.this.getContext(), CreateReservationActivity.class);
                startActivity(intent);
            }
        });




        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferences = context.getSharedPreferences(SharedPreference.namePreference,MODE_PRIVATE);
    }
}