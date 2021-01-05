package com.example.reservationapp.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.reservationapp.Class.Reservation;
import com.example.reservationapp.Class.SharedPreference;
import com.example.reservationapp.DetailActivity;
import com.example.reservationapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.example.reservationapp.LoginActivity.URL;

public class MyReservationFragment extends ListFragment {
    private ArrayList<String> reservationList = new ArrayList<String>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences preferences;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance(URL).getReference();
        preferences = getContext().getSharedPreferences(SharedPreference.namePreference, MODE_PRIVATE);
        String currentUserId = preferences.getString(SharedPreference.KeyId,null);
        System.out.println("*****User ID:" + currentUserId);

        System.out.println("**************MYRESERVATIONFRAGMENT.JAVA***************");


        mDatabase.child("reservation").orderByChild("idUser").equalTo(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()){
                    String aux = userSnapshot.child("name").getValue(String.class) +
                            " @ "+ userSnapshot.child("time").getValue(String.class)
                            + " " + userSnapshot.child("date").getValue(String.class);
                    reservationList.add(aux);
                }

                /*Convert array list to Array of strings    */
                String[] reservations = new String[reservationList.size()];
                reservations =  reservationList.toArray(reservations);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(),
                        android.R.layout.simple_list_item_1,
                        reservations);
                setListAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        System.out.println("Position: "+position);
        System.out.println("Id: "+id);
        String[] reservations = new String[reservationList.size()];
        reservations =  reservationList.toArray(reservations);
        showReservationInfo(reservations, (int) id);

        super.onListItemClick(l, v, position, id);
    }

    private void showReservationInfo(String[]array,  int id){
        /*Este parte es hardcore con efectos de poder pasar un objecto a la clase Detail la cual en su
        * layout muestra la info de la reservación*/
        Reservation reservation = new Reservation();

        /*Informacion de prueba*/
        reservation.setName("Diego");
        reservation.setDate("01/02/2021");
        reservation.setPhone("6641234567");
        reservation.setTime("15:30");

        if (id == 0) {/*cuando se presiona el primer elemento de la lista manda el objecto creado de arriba en el Intent
         * Despues la clase que lo recibe lo usar para setear los valores en los TextViews correspondientes.*/
            Intent intent = new Intent(MyReservationFragment.this.getContext(), DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_RESERVATION_ID, (Serializable) reservation);
            startActivity(intent);
        }
    }
}