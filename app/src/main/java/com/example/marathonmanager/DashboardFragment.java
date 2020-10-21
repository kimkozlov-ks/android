package com.example.marathonmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marathonmanager.pojo.Measurement;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    TextView weightStartDate;
    TextView weightEndDate;
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
        weightStartDate = getActivity().findViewById(R.id.dashboard_start_weight_date);
        weightEndDate = getActivity().findViewById(R.id.dashboard_end_weight_date);

        addMeasurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new AddMeasurementsFragment(), "measurementsFragment")
                        .commit();
            }
        });
    }

    public void onResume() {
        super.onResume();
        Call<List<Measurement>> call = APIClient.getInstance().getMeasurementService().getAllMeasurement();
        call.enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(Call<List<Measurement>> call, Response<List<Measurement>> response) {
                int it = 1;
                ArrayList<BarEntry> weights = new ArrayList<BarEntry>();
                for (Measurement measurement : response.body()) {
                    weights.add(new BarEntry(it, (float) measurement.getWeight()));
                    it++;
                }

                weightStart.setText(String.valueOf(response.body().get(0).getWeight()));
                weightEnd.setText(String.valueOf(response.body().get(response.body().size() - 1).getWeight()));
                DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                String formattedDate = formatter.format(response.body().get(0).getDate());
                weightStartDate.setText(formattedDate.toString());
                formattedDate = formatter.format(response.body().get(response.body().size() - 1).getDate());
                weightEndDate.setText(formattedDate);

                BarDataSet bardataset = new BarDataSet(weights, "Burn progress");
                barChart.animateY(500);

                BarData data = new BarData(bardataset);
                bardataset.setColors(ColorTemplate.JOYFUL_COLORS);
                barChart.setData(data);
                barChart.setVisibleXRange(4, 5);
            }

            @Override
            public void onFailure(Call<List<Measurement>> call, Throwable t) {
                Toast.makeText(getActivity(), "No Data Available",Toast.LENGTH_LONG).show();
            }
        });
    }
}