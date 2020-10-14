package com.example.marathonmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMeasurementsFragment extends Fragment {
    private Button acceptBtn;
    private EditText weightEt;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        return inflater.inflate(R.layout.fragment_add_measurements, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        acceptBtn = getView().findViewById(R.id.add_measurements_fragment_btn_accept);
        weightEt = getView().findViewById(R.id.add_measurements_fragment_ed_weight);

        if(acceptBtn == null)
        {
            return;
        }

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO:
                //  1. Send data to server
                //  2. if everything is OK go to dashboad
                
                Measurement measurement = new Measurement();
                measurement.setWeight(Double.parseDouble(weightEt.getText().toString()));
                Call<Measurement> call = APIClient.getInstance().getMeasurementService().sendMeasurement(measurement);
                call.enqueue(new Callback<Measurement>() {
                    @Override
                    public void onResponse(Call<Measurement> call, retrofit2.Response<Measurement> response) {
                        Fragment dashboardFragment = getActivity().getSupportFragmentManager().findFragmentByTag("dashboardFragment");
                        if(dashboardFragment == null)
                        {
                            dashboardFragment = new DashboardFragment();
                        }

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, dashboardFragment, "dashboardFragment").show(dashboardFragment)
                                .commit();
                    }

                    @Override
                    public void onFailure(Call<Measurement> call, Throwable t) {
                        //
                    }

                });
            }
        });
    }
}