package com.example.marathonmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marathonmanager.pojo.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;

public class ProfileFragment extends Fragment {
    EditText nameEt;
    EditText lastNameEt;
    EditText phoneEt;
    EditText birthdayEt;
    EditText lengthEt;
    ImageView backArrowIv;
    ImageView editNameIv;
    ImageView editLastNameIv;
    ImageView editPhoneIv;
    ImageView editLengthIv;
    ImageView acceptIv;
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        backArrowIv = getActivity().findViewById(R.id.profile_iv_arrow_back);
        backArrowIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment dashboardFragment = getActivity().getSupportFragmentManager().findFragmentByTag("dashboardFragment");
                if (dashboardFragment == null) {
                    dashboardFragment = new DashboardFragment();
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, dashboardFragment, "dashboardFragment").show(dashboardFragment)
                        .commit();
            }
        });

        nameEt = getActivity().findViewById(R.id.profile_name_tv);
        lastNameEt = getActivity().findViewById(R.id.profile_last_name_tv);
        phoneEt = getActivity().findViewById(R.id.profile_phone_tv);
        birthdayEt = getActivity().findViewById(R.id.profile_birthday_tv);
        lengthEt = getActivity().findViewById(R.id.profile_length_tv);
        acceptIv = getActivity().findViewById(R.id.profile_accept_iv);


        editNameIv = getActivity().findViewById(R.id.profile_name_edit_iv);
        editNameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameEt.setEnabled(true);
                acceptIv.setVisibility(View.VISIBLE);
            }
        });

        editLastNameIv = getActivity().findViewById(R.id.profile_last_name_edit_iv);
        editLastNameIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastNameEt.setEnabled(true);
                acceptIv.setVisibility(View.VISIBLE);
            }
        });

        editPhoneIv = getActivity().findViewById(R.id.profile_phone_edit_iv);
        editPhoneIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneEt.setEnabled(true);
                acceptIv.setVisibility(View.VISIBLE);
            }
        });

        editLengthIv = getActivity().findViewById(R.id.profile_length_edit_iv);
        editLengthIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengthEt.setEnabled(true);
                acceptIv.setVisibility(View.VISIBLE);
            }
        });

        acceptIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(nameEt.getText().toString());
                user.setLastName(lastNameEt.getText().toString());
                user.setPhone(phoneEt.getText().toString());
                user.setLength(Double.parseDouble(lengthEt.getText().toString()));

                Call<Void> call = APIClient.getInstance().getUserService().updateUserData(user);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), "It failed to load your data", Toast.LENGTH_LONG).show();
                            return;
                        }

                        acceptIv.setVisibility(View.GONE);
                        nameEt.setEnabled(false);
                        lastNameEt.setEnabled(false);
                        phoneEt.setEnabled(false);
                        lengthEt.setEnabled(false);
                    }
                    @Override
                    public void onFailure (Call<Void> call, Throwable t){
                        Toast.makeText(getActivity(), "There is an error to send data to server", Toast.LENGTH_LONG).show();
                    }

                });

                Toast.makeText(getActivity(), "Profile is updated successfully", Toast.LENGTH_LONG).show();

            }
        });

        Call<User> call = APIClient.getInstance().getUserService().getUserProfile();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "It failed to load your data", Toast.LENGTH_LONG).show();
                    return;
                }

                user = response.body();

                nameEt.setText(response.body().getName());
                lastNameEt.setText(response.body().getLastName());
                phoneEt.setText(response.body().getPhone());
                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                String strDate = dateFormat.format(response.body().getBirthday());
                birthdayEt.setText(strDate);
                lengthEt.setText(String.valueOf((response.body().getLength())));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), "There is an error to send data to server", Toast.LENGTH_LONG).show();
            }
        });
    }
}