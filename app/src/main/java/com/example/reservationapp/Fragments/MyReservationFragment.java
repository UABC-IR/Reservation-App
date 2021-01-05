package com.example.reservationapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Class.User;
import com.example.reservationapp.DetailActivity;
import com.example.reservationapp.R;

import java.io.Serializable;

public class MyReservationFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.reservation_example));

        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        System.out.println("Position: "+position);
        System.out.println("Id: "+id);
        showReservationInfo((int) id);

        super.onListItemClick(l, v, position, id);
    }

    private void showReservationInfo(int id){
        /*Este parte es hardcore con efectos de poder pasar un objecto a la clase Detail la cual en su
        * layout muestra la info de la reservación*/
        Reservation reservation = new Reservation();

        /*Informacion de prueba*/
        reservation.setName("Diego");
        reservation.setDate("01/02/2021");
        reservation.setPhone("6641234567");
        reservation.setTime("15:30");

        switch (id){
            case 0:
                /*cuando se presiona el primer elemento de la lista manda el objecto creado de arriba en el Intent
                * Despues la clase que lo recibe lo usar para setear los valores en los TextViews correspondientes.*/
                Intent intent = new Intent(MyReservationFragment.this.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_RESERVATION_ID, (Serializable) reservation);
                startActivity(intent);
        }
    }
}