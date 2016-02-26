package com.preguardia.app.user.register.patient;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.preguardia.app.R;

import butterknife.ButterKnife;

/**
 * @author amouly on 2/20/16.
 */
public class RegisterPatientFragment extends Fragment {


    public static RegisterPatientFragment newInstance(int param) {
        RegisterPatientFragment sampleSlide = new RegisterPatientFragment();

        Bundle args = new Bundle();
        args.putInt("PARAM", param);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_register_patient, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
