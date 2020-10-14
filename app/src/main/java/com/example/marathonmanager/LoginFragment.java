package com.example.marathonmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class LoginFragment extends Fragment {
    Button login;
    EditText phone;
    EditText password;
    CheckBox isRemenber;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        login = getView().findViewById(R.id.login_fragment_btn_log_in);
        phone = getView().findViewById(R.id.login_fragment_ed_phone);
        password = getView().findViewById(R.id.login_fragment_ed_password);
        isRemenber = getView().findViewById(R.id.login_fragment_cb_remember);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO:
                //  1. Login
                //  2. Recieve a token
                //  3. Redirect to next fragment if everything is OK

//                String url = "http://localhost:5001/login";
//
                Fragment questionnaireFragment = new QuestionnaireFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, questionnaireFragment, "questionnaire")
                        .commit();
            }
        });
    }
}