package com.example.marathonmanager;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Fragment loginFragment = new LoginFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, loginFragment, "login")
                    .commit();
        }
    }
}