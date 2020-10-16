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

import retrofit2.Call;
import retrofit2.Callback;

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

                AuthUser authUser = new AuthUser();
                authUser.setPhone(phone.getText().toString());
                authUser.setPassword(password.getText().toString());

                Call<AuthUser> call = APIClient.getInstance().getAuthService().authenticate(authUser);
                call.enqueue(new Callback<AuthUser>() {
                    @Override
                    public void onResponse(Call<AuthUser> call, retrofit2.Response<AuthUser> response) {
                        int z = 0;
                        if(response.isSuccessful()) {
                            Fragment questionnaireFragment = new QuestionnaireFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, questionnaireFragment, "questionnaire")
                                    .commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthUser> call, Throwable t) {
                        //
                    }
                });
            }
        });
    }
}