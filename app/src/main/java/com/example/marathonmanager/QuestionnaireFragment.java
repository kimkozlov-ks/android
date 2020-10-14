package com.example.marathonmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class QuestionnaireFragment extends Fragment {
private Button nextBtn;
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

        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment dashboardFragment = new DashboardFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, dashboardFragment, "dashboardFragment").show(dashboardFragment)
                        .commit();
            }
        });
    }
}