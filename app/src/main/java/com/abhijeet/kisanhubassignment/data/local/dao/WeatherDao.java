package com.abhijeet.kisanhubassignment.data.local.dao;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
/**
 * The interface Weather dao.
 */
@Dao
public interface WeatherDao {
    /**
     * Load results live data.
     *
     * @param metric   the metric
     * @param location the location
     * @return the live data
     */
    @Query("SELECT * FROM WeatherModel where metric= :metric and location= :location")
    LiveData<List<WeatherModel>> loadResults(String metric, String location);
    /**
     * Load years live data.
     *
     * @return the live data
     */
    @Query("SELECT distinct year FROM WeatherModel ")
    LiveData<List<Integer>> loadYears();
    /**
     * Save results.
     *
     * @param weatherModels the weather models
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveResults(List<WeatherModel> weatherModels);
    /**
     * Insert.
     *
     * @param weatherModels the weather models
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WeatherModel weatherModels);
    /**
     * Gets result.
     *
     * @param metric   the metric
     * @param location the location
     * @return the result
     */
    @Query("SELECT * FROM WeatherModel where metric= :metric and location= :location")
    List<WeatherModel> getResult(String metric, String location);
}
