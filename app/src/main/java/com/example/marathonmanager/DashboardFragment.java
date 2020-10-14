package com.example.marathonmanager;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class DashboardFragment extends Fragment {
    ImageView addMeasurement;
    ImageView profile;
    ImageView notification;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addMeasurement = getActivity().findViewById(R.id.dashboard_iv_add_measurement);
        profile = getActivity().findViewById(R.id.dashboard_iv_profile);
        notification = getActivity().findViewById(R.id.dashboard_iv_notification);

        Call<List<Measurement>> call = APIClient.getInstance().getMeasurementService().getAllMeasurement();
        call.enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                int z = 0;
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        addMeasurement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment addMeasurementsFragment =  new AddMeasurementsFragment();

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, addMeasurementsFragment, "addMeasurementsFragment")
                        .commit();
            }
        });
    }
}