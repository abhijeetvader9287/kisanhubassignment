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
     * The Movie repository.
     */
    WeatherRepository movieRepository;

    /**
     * Instantiates a new Weather view model.
     *
     * @param movieRepository the movie repository
     */
    public WeatherViewModel(WeatherRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Gets weather.
     *
     * @param metric   the metric
     * @param location the location
     * @return the weather
     */
    public LiveData<Resource<List<WeatherModel>>> getWeather(String metric, String location) {
        return movieRepository.loadWeather(metric, location);
    }

    /**
     * Gets years.
     *
     * @return the years
     */
    public LiveData<List<Integer>> getYears() {
        return movieRepository.getYears();
    }
}
