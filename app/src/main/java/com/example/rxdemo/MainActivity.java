package com.example.rxdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rxdemo.data.remote.AnimeRepositoryImpl;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progress;
    private MainActivityViewAdapter listAdapter;
    private TextView resLabel;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewModel viewModel = ViewModelProviders
                .of(this,
                        new MainActivityViewModelFactory(new AnimeRepositoryImpl()))
                .get(MainActivityViewModel.class);

        if (savedInstanceState == null) {
            viewModel.callKitsuApi();
        }
        setupViews();
        viewModel.getProgressTrigger().observe(this, aBoolean ->
                progress.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE));
        viewModel.getCallState().observe(this, stringResource -> {
            if (stringResource.hasSuccess()) {
                resLabel.setText(stringResource.getData());
            } else if (stringResource.hasError()) {
                resLabel.setText(stringResource.getException().getMessage());
            } else if (stringResource.isLoading()) {
                resLabel.setText("Loading episodes...");
            }
        });
        viewModel.getEpisodes().observe(this, data -> {
            if (data != null) {
                resLabel.setText("");
                listAdapter.submitList(data);
            }
        });
    }

    private void setupViews() {
        progress = ActivityCompat.requireViewById(this, R.id.progressBar);
        progress.setVisibility(View.INVISIBLE);
        resLabel = ActivityCompat.requireViewById(this, R.id.resource);
        RecyclerView recyclerView = ActivityCompat.requireViewById(this, R.id.listRecycled);
        listAdapter = new MainActivityViewAdapter(null);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), RecyclerView.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,
                false));
        recyclerView.setAdapter(listAdapter);
    }
}
