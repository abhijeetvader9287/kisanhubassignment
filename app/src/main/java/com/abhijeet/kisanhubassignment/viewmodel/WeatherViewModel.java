package com.abhijeet.kisanhubassignment.viewmodel;
import com.abhijeet.kisanhubassignment.data.Resource;
import com.abhijeet.kisanhubassignment.data.WeatherRepository;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
/**
 * The type Weather view model.
 */
public class WeatherViewModel extends ViewModel {
    /**
     * The Weather repository.
     */
    WeatherRepository weatherRepository;

    /**
     * Instantiates a new Weather view model.
     *
     * @param weatherRepository the weather repository
     */
    public WeatherViewModel(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    /**
     * Gets weather.
     *
     * @param metric   the metric
     * @param location the location
     * @return the weather
     */
    public LiveData<Resource<List<WeatherModel>>> getWeather(String metric, String location) {
        return weatherRepository.loadWeather(metric, location);
    }

    /**
     * Gets years.
     *
     * @return the years
     */
    public LiveData<List<Integer>> getYears() {
        return weatherRepository.getYears();
    }
}
