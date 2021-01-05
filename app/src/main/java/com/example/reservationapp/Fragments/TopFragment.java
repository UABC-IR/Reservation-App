package com.example.reservationapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.reservationapp.DetailActivity;
import com.example.reservationapp.LoginActivity;
import com.example.reservationapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class TopFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top, container, false);

        Button button = view.findViewById(R.id.button_details);
        Button logout = view.findViewById(R.id.button_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TopFragment.this.getContext(), LoginActivity.class);
                startActivity(intent);
                getFragmentManager().beginTransaction().remove(getTargetFragment()).commit();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopFragment.this.getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}