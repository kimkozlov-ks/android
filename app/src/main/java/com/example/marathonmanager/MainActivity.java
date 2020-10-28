package com.example.marathonmanager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.marathonmanager.pojo.AccessToken;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_FILE = "Account";
    public static final String PREF_ACCESS = "AccessToken";
    public static final String PREF_REFRESH = "RefreshToken";
    public static final String PREF_IS_QUESTIONNARE_PASSED = "IsQuestionnairePassed";
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getSharedPreferences(PREF_ACCESS, MODE_PRIVATE);
        AuthTokens.accessToken = settings.getString(PREF_ACCESS, "");
        AuthTokens.refreshToken = settings.getString(PREF_REFRESH, "");
        AppRuntimeState.isQuestionnairePassed = settings.getBoolean(PREF_IS_QUESTIONNARE_PASSED, false);

        if (savedInstanceState == null) {
            if(!AuthTokens.refreshToken.isEmpty()) {
                refreshToken();
            }
            else
            {
                activateLoginFragment();
            }
        }
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor prefEditor = settings.edit();

        if(AppRuntimeState.isRemembered) {
            prefEditor.putString(PREF_ACCESS, AuthTokens.accessToken);
            prefEditor.putString(PREF_REFRESH, AuthTokens.refreshToken);

        } else {
            prefEditor.putString(PREF_ACCESS, "");
            prefEditor.putString(PREF_REFRESH, "");
        }

        prefEditor.putBoolean(PREF_IS_QUESTIONNARE_PASSED, AppRuntimeState.isQuestionnairePassed);
        prefEditor.apply();
        prefEditor.commit();
    }

    private void activateLoginFragment()
    {
        Fragment loginFragment = new LoginFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, loginFragment, "login")
                .commit();
    }

    private void refreshToken()
    {
        Call<AccessToken> call = APIClient.getInstance().getAuthService().refresh("Refresh=" + AuthTokens.refreshToken);
        call.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, retrofit2.Response<AccessToken> response) {
                if(response.code() != 200)
                {
                    activateLoginFragment();
                    return;
                }

                String refreshCookie = response.headers().values("Set-Cookie").get(0);
                AuthTokens.refreshToken = refreshCookie.split(";")[0];
                AuthTokens.refreshToken = AuthTokens.refreshToken.substring(AuthTokens.refreshToken.indexOf("=") + 1).trim();
                AuthTokens.accessToken = response.body().getAccessToken();

                if (response.isSuccessful()) {
                    if(AppRuntimeState.isQuestionnairePassed) {
                        Fragment dashboardFragment = new DashboardFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, dashboardFragment, "dashboardFragment")
                                .commit();
                    }
                    else {
                        Fragment questionnaireFragment = new QuestionnaireFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, questionnaireFragment, "questionnaire")
                                .commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                activateLoginFragment();
            }
        });
    }
}