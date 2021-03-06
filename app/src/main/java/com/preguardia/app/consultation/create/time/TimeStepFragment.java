package com.preguardia.app.consultation.create.time;

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
public class TimeStepFragment extends AbstractStep implements TimeStepContract.View {

    @Bind(R.id.step_time_list)
    RecyclerView recyclerView;

    private TimeStepContract.Presenter presenter;
    private TimeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_time, container, false);

        ButterKnife.bind(this, view);

        presenter = new TimeStepPresenter(this);

        adapter = new TimeListAdapter(getActivity(), new ArrayList<TimeItem>(0));

        // Config Recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getContext()).build());
        recyclerView.setAdapter(adapter);

        // Load list of items
        presenter.loadItems();

        return view;
    }

    @Override
    public void onStepVisible() {
        super.onStepVisible();
    }

    @Override
    public String name() {
        return mStepper.getString(R.string.consultation_create_step_time);
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
        return mStepper.getString(R.string.consultation_create_step_time_error);
    }

    @Override
    public void showItems(List<TimeItem> items) {
        adapter.replaceData(items);
    }

    @Override
    public String getData() {
        String selected = adapter.getSelectedItem().getName();

        return selected;
    }
}
