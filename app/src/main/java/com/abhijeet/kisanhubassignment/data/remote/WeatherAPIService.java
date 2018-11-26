package com.abhijeet.kisanhubassignment.data.remote;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
/**
 * The interface Weather api service.
 */
public interface WeatherAPIService {
    /**
     * Gets weather.
     *
     * @param metric   the metric
     * @param location the location
     * @return the weather
     */
    @GET("{metric}-{location}.json")
    Call<List<WeatherModel>> getWeather(@Path("metric") String metric, @Path("location") String location);
}
