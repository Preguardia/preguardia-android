package com.preguardia.app.consultation.create.patient;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.preguardia.app.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author amouly on 4/6/16.
 */
public class PatientStepFragment extends AbstractStep implements PatientStepContract.View {

    @Bind(R.id.step_patient_list)
    RecyclerView recyclerView;

    private PatientStepContract.Presenter presenter;
    private PatientListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_patient, container, false);

        ButterKnife.bind(this, view);

        presenter = new PatientStepPresenter(this);
        adapter = new PatientListAdapter(getActivity(), new ArrayList<PatientItem>(0));

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerView.setAdapter(adapter);

        presenter.loadItems();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_create_step_patient);
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean nextIf() {
        return adapter.hasItemSelected();
    }

    @Override
    public String error() {
        return mStepper.getString(R.string.consultation_create_step_patient_error);
    }

    @Override
    public void showItems(List<PatientItem> items) {
        adapter.replaceData(items);
    }

    @Override
    public String getData() {
        return adapter.getSelectedItem().getName();
    }
}