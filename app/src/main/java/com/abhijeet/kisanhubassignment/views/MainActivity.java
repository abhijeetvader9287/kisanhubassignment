package com.abhijeet.kisanhubassignment.views;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.abhijeet.kisanhubassignment.R;
import com.abhijeet.kisanhubassignment.Utilities.Utilities;
import com.abhijeet.kisanhubassignment.adapters.WeatherExpandableListAdapter;
import com.abhijeet.kisanhubassignment.data.Resource;
import com.abhijeet.kisanhubassignment.data.Status;
import com.abhijeet.kisanhubassignment.data.WeatherRepository;
import com.abhijeet.kisanhubassignment.data.local.WeatherDatabase;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;
import com.abhijeet.kisanhubassignment.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The Expandable list view.
     */
    ExpandableListView expandableListView;
    /**
     * The Progress bar.
     */
    ProgressBar progressBar;
    /**
     * The Toolbar.
     */
    Toolbar toolbar;
    /**
     * The Metric spinner.
     */
    Spinner metricSpinner;
    /**
     * The Location spinner.
     */
    Spinner locationSpinner;
    /**
     * The Years list.
     */
    List<Integer> yearsList;
    /**
     * The Weather model list.
     */
    Map<Integer, List<WeatherModel>> weatherModelList;
    /**
     * The Weather expandable list adapter.
     */
    WeatherExpandableListAdapter weatherExpandableListAdapter;
    /**
     * The Weather database.
     */
    WeatherDatabase weatherDatabase;
    /**
     * The Weather repository.
     */
    WeatherRepository weatherRepository;
    /**
     * The Weather view model.
     */
    WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expandableListView = findViewById(R.id.expandableListView);
        progressBar = findViewById(R.id.progressBar);
        toolbar = findViewById(R.id.toolbar);
        metricSpinner = findViewById(R.id.metricSpinner);
        locationSpinner = findViewById(R.id.locationSpinner);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setUpDB_API();
    }

    /**
     * Sets up db api.
     */
    void setUpDB_API() {
        weatherDatabase = WeatherDatabase.getAppDatabase(this);
        weatherRepository = new WeatherRepository(weatherDatabase
                .resultDao(), Utilities.isNetworkConnected(MainActivity.this));
        weatherViewModel = new WeatherViewModel(weatherRepository);
        ArrayAdapter locationSpinneradapter = ArrayAdapter.createFromResource(MainActivity.this
                , R.array.locations, R.layout.spinner_item);
        locationSpinneradapter.setDropDownViewResource(R.layout.spinner_list_item);
        locationSpinner.setAdapter(locationSpinneradapter);
        ArrayAdapter metricSpinneradapter = ArrayAdapter.createFromResource(MainActivity.this
                , R.array.metrics, R.layout.spinner_item);
        metricSpinneradapter.setDropDownViewResource(R.layout.spinner_list_item);
        metricSpinner.setAdapter(metricSpinneradapter);
        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        metricSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /**
     * Gets data.
     */
    void getData() {
        yearsList = new ArrayList<>();
        weatherModelList = new HashMap<>();
        weatherExpandableListAdapter = new WeatherExpandableListAdapter(MainActivity.this, yearsList, weatherModelList);
        expandableListView.setAdapter(weatherExpandableListAdapter);
        weatherRepository = new WeatherRepository(weatherDatabase
                .resultDao(), Utilities.isNetworkConnected(MainActivity.this));
        weatherViewModel = new WeatherViewModel(weatherRepository);
        String metric = metricSpinner.getSelectedItem().toString();
        String location = locationSpinner.getSelectedItem().toString();
        weatherViewModel.getWeather(metric, location)
                .observe(this, new Observer<Resource<List<WeatherModel>>>() {
                            @Override
                            public void onChanged(@Nullable final Resource<List<WeatherModel>> listResource) {
                                if (listResource.status == Status.SUCCESS) {
                                    if (listResource.data.size() > 0) {
                                        if (!Utilities.isNetworkConnected(MainActivity.this)) {
                                            Toast.makeText(MainActivity.this, "Offline data displayed.Please check your network connection.", Toast.LENGTH_LONG).show();
                                        }
                                        weatherViewModel.getYears().observe(MainActivity.this, new Observer<List<Integer>>() {
                                            @Override
                                            public void onChanged(@Nullable List<Integer> integers) {
                                                for (int i = 0; i < integers.size(); i++) {
                                                    List<WeatherModel> weatherModelList_temp = new ArrayList<>();
                                                    for (int j = 0; j < listResource.data.size(); j++) {
                                                        if (integers.get(i).equals(listResource.data.get(j).getYear())) {
                                                            weatherModelList_temp.add(listResource.data.get(j));
                                                            weatherModelList.put(integers.get(i), weatherModelList_temp);
                                                        }
                                                    }
                                                    weatherExpandableListAdapter = new WeatherExpandableListAdapter(MainActivity.this, integers, weatherModelList);
                                                    expandableListView.setAdapter(weatherExpandableListAdapter);
                                                }
                                            }
                                        });
                                    } else {
                                        if (!Utilities.isNetworkConnected(MainActivity.this)) {
                                            Toast.makeText(MainActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                                if (listResource.status == Status.ERROR) {
                                    progressBar.setVisibility(View.GONE);
                                }
                                if (listResource.status == Status.LOADING) {
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
