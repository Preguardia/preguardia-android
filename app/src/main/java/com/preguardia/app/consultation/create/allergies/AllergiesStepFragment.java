package com.preguardia.app.consultation.create.allergies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;

/**
 * @author amouly on 4/6/16.
 */
public class AllergiesStepFragment extends AbstractStep {

    private int i = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_alergy, container, false);

        return view;
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
        // do something
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_new_step_allergies);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        return true;
    }

    @Override
    public String error() {
        return "<b>You must click!</b> <small>this is the condition!</small>";
    }
}