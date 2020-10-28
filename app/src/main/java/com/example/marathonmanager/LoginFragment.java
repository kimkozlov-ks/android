package com.example.marathonmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.marathonmanager.pojo.AccessToken;
import com.example.marathonmanager.pojo.AuthUser;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginFragment extends Fragment {
    private Button login;
    private EditText phone;
    private EditText password;
    private CheckBox isRemenber;

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

                Call<AccessToken> call = APIClient.getInstance().getAuthService().authenticate(authUser);
                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Login failed",Toast.LENGTH_LONG).show();
                            return;
                        }

                        String refreshCookie = response.headers().values("Set-Cookie").get(0);
                        AuthTokens.refreshToken = refreshCookie.split(";")[0];
                        AuthTokens.refreshToken = AuthTokens.refreshToken.substring(AuthTokens.refreshToken.indexOf("=")+1).trim();
                        AuthTokens.accessToken = response.body().getAccessToken();

                        String res = response.body().toString();
                        if(response.isSuccessful()) {
                            Fragment questionnaireFragment = new QuestionnaireFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, questionnaireFragment, "questionnaire")
                                    .commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Toast.makeText(getActivity(), "There is an error to send data to server",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}