package com.example.reservationapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserInfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        /*Esto se debe cambiar por los datos del usuario que esta dentro de la base de datos*/
        TextView name = view.findViewById(R.id.userProfile_name);
        name.setText("Diego");

        TextView lastName = view.findViewById(R.id.userProfile_lastName);
        lastName.setText("Caudillo");

        TextView email = view.findViewById(R.id.userProfile_email);
        email.setText("diego@email.com");
        return view;
    }
}