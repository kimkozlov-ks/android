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
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    TextView weightStart;
    TextView weightEnd;
    BarChart barChart;

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
        barChart = getActivity().findViewById(R.id.dashboard_barchart);
        weightStart = getActivity().findViewById(R.id.dashboard_start_weight);
        weightEnd = getActivity().findViewById(R.id.dashboard_end_weight);

        Call<List<Measurement>> call = APIClient.getInstance().getMeasurementService().getAllMeasurement();
        call.enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                int it = 0;
                ArrayList<BarEntry> weights = new ArrayList<BarEntry>();
                for (Measurement measurement: response.body()) {
                    weights.add(new BarEntry(it, (float)measurement.getWeight()));
                    it++;
                }
                for (Measurement measurement: response.body()) {
                    weights.add(new BarEntry(it, (float)measurement.getWeight()));
                    it++;
                }

                weightStart.setText(response.body().get(0).toString());
                weightEnd.setText(response.body().get(response.body().size() - 1).toString());


                BarDataSet bardataset = new BarDataSet(weights, "Burn progress");
                barChart.animateY(5000);

                BarData data = new BarData(bardataset);
                bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
                barChart.setData(data);
                barChart.setVisibleXRange(4, 5);
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