package com.example.marathonmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.marathonmanager.pojo.Measurement;

import java.util.Date;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;

public class AddMeasurementsFragment extends Fragment {
    private Button acceptBtn;
    private EditText weightEt;
    private ImageView arrowBackIv;
    private DatePicker datePicker;
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
        arrowBackIv = getView().findViewById(R.id.add_measurements_fragment_iv_arrow_back);
        datePicker = getView().findViewById(R.id.add_measurements_fragment_date_picker);

        if(acceptBtn == null)
        {
            return;
        }

        arrowBackIv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BackToDashboard(false);
                }
            }
        );

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO:
                //  1. Send data to server
                //  2. if everything is OK let's go back to dashboard
                
                Measurement measurement = new Measurement();
                measurement.setWeight(Double.parseDouble(weightEt.getText().toString()));
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Date date = new GregorianCalendar(year, month - 1, day).getTime();
                measurement.setDate(date);
                Call<Measurement> call = APIClient.getInstance().getMeasurementService().sendMeasurement(measurement);
                call.enqueue(new Callback<Measurement>() {
                    @Override
                    public void onResponse(Call<Measurement> call, retrofit2.Response<Measurement> response) {
                        BackToDashboard(true);
                    }

                    @Override
                    public void onFailure(Call<Measurement> call, Throwable t) {
                        //
                    }

                });
            }
        });
    }

    private void BackToDashboard(Boolean isNeedToUpdateDataFromApi) {
        Fragment dashboardFragment = getActivity().getSupportFragmentManager().findFragmentByTag("dashboardFragment");
        if(dashboardFragment == null)
        {
            dashboardFragment = new DashboardFragment();
        }

        if(isNeedToUpdateDataFromApi) {
            dashboardFragment.onResume();
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, dashboardFragment, "dashboardFragment").show(dashboardFragment)
                .commit();
    }
}