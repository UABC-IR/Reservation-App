package com.example.reservationapp.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.reservationapp.Class.SharedPreference;
import com.example.reservationapp.Class.User;
import com.example.reservationapp.R;
import static android.content.Context.MODE_PRIVATE;

public class UserInfoFragment extends Fragment{
    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        User user = LoadSharedPreferences();

        TextView name = view.findViewById(R.id.userProfile_name);
        name.setText(user.getName());

        TextView lastName = view.findViewById(R.id.userProfile_lastName);
        lastName.setText(user.getLastname());

        TextView email = view.findViewById(R.id.userProfile_email);
        email.setText(user.getEmail());
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        preferences = context.getSharedPreferences(SharedPreference.namePreference,MODE_PRIVATE);
    }

    private User LoadSharedPreferences(){
        String name, lastname, email;
        User useA = new User();
        name = preferences.getString(SharedPreference.KeyName,null);
        lastname = preferences.getString(SharedPreference.KeyLastname,null);
        email = preferences.getString(SharedPreference.KeyEmail,null);
        useA.setName(name);
        useA.setLastname(lastname);
        useA.setEmail(email);
        return useA;
    }

}