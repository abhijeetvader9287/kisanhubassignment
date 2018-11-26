package com.abhijeet.kisanhubassignment.data;
import com.abhijeet.kisanhubassignment.data.local.dao.WeatherDao;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;
import com.abhijeet.kisanhubassignment.data.remote.NetworkClient;
import com.abhijeet.kisanhubassignment.data.remote.WeatherAPIService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;
/**
 * The type Weather repository.
 */
public class WeatherRepository {
    /**
     * The Weather dao.
     */
    final WeatherDao weatherDao;
    /**
     * The Online.
     */
    boolean online;

    /**
     * Instantiates a new Weather repository.
     *
     * @param weatherDao the weather dao
     * @param online     the online
     */
    public WeatherRepository(WeatherDao weatherDao, boolean online) {
        this.weatherDao = weatherDao;
        this.online = online;
    }

    /**
     * Load weather live data.
     *
     * @param metric   the metric
     * @param location the location
     * @return the live data
     */
    public LiveData<Resource<List<WeatherModel>>> loadWeather(final String metric, final String location) {
        return new NetworkBoundResource<List<WeatherModel>, List<WeatherModel>>(online, metric, location) {
            @Override
            protected void saveCallResult(@NonNull List<WeatherModel> items) {
                for (int i = 0; i < items.size(); i++) {
                    items.get(i).setId(i + 1);
                    items.get(i).setMetric(metric);
                    items.get(i).setLocation(location);
                }
                weatherDao.saveResults(items);
            }

            @NonNull
            @Override
            protected LiveData<List<WeatherModel>> loadFromDb(String metric, String location) {
                return weatherDao.loadResults(metric, location);
            }

            @NonNull
            @Override
            protected Call<List<WeatherModel>> createCall() {
                return NetworkClient.getRetrofit().create(WeatherAPIService.class)
                        .getWeather(metric, location);
            }
        }.getAsLiveData();
    }

    /**
     * Gets years.
     *
     * @return the years
     */
    public LiveData<List<Integer>> getYears() {
        return weatherDao.loadYears();
    }
}
