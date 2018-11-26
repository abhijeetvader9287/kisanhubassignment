package com.abhijeet.kisanhubassignment.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * The type Weather model.
 */
@Entity(tableName = "WeatherModel")
public class WeatherModel {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    @SerializedName("metric")
    @Expose
    private String metric;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("value")
    @Expose
    private double value;
    @SerializedName("year")
    @Expose
    private int year;
    @SerializedName("month")
    @Expose
    private int month;

    /**
     * Gets metric.
     *
     * @return the metric
     */
    public String getMetric() {
        return metric;
    }

    /**
     * Sets metric.
     *
     * @param metric the metric
     */
    public void setMetric(String metric) {
        this.metric = metric;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets month.
     *
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets month.
     *
     * @param month the month
     */
    public void setMonth(int month) {
        this.month = month;
    }
}
