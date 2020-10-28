package com.example.marathonmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.marathonmanager.pojo.User;

import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;

public class QuestionnaireFragment extends Fragment {
    private Button nextBtn;
    private EditText nameEt;
    private EditText lastNameEt;
    private DatePicker birthDayDp;
    private EditText lengthEt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_questionnaire, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO:
        //  1. update user data
        //  2. Redirect to the next fragment if everything is OK

        nextBtn = getActivity().findViewById(R.id.questionnaire_fragment_btn_next);
        nameEt = getActivity().findViewById(R.id.questionnaire_fragment_ed_name);
        lastNameEt = getActivity().findViewById(R.id.questionnaire_fragment_ed_last_name);
        birthDayDp = getActivity().findViewById(R.id.questionnaire_fragment_ed_birthday);
        lengthEt = getActivity().findViewById(R.id.questionnaire_fragment_ed_length);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = nameEt.getText().toString();
                String lastName = lastNameEt.getText().toString();
                int day = birthDayDp.getDayOfMonth();
                int month = birthDayDp.getMonth();
                int year = birthDayDp.getYear();
                Date birthDay = new GregorianCalendar(year, month - 1, day).getTime();
                Double length = Double.parseDouble(lengthEt.getText().toString());

                if(name.length() == 0)
                {
                    Toast.makeText(getActivity(), "Name can't be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                if(lastName.length() == 0)
                {
                    Toast.makeText(getActivity(), "Last name can't be empty",Toast.LENGTH_LONG).show();
                    return;
                }

                if(length < 50 || length > 230)
                {
                    Toast.makeText(getActivity(), "Incorrect length in cm",Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User();
                user.setName(name);
                user.setLastName(lastName);
                user.setBirthday(birthDay);
                user.setLength(length);

                Call<Void> call = APIClient.getInstance().getUserService().updateUserData(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if(response.isSuccessful()) {
                            AppRuntimeState.isQuestionnairePassed = true;
                            Fragment dashboardFragment = new DashboardFragment();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, dashboardFragment, "dashboardFragment").show(dashboardFragment)
                                    .commit();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Something wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getActivity(), "There is an error to send data to server",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}