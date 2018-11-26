package com.abhijeet.kisanhubassignment;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;
import com.abhijeet.kisanhubassignment.data.remote.NetworkClient;
import com.abhijeet.kisanhubassignment.data.remote.WeatherAPIService;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.test.runner.AndroidJUnit4;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertEquals;
/**
 * The type Api test.
 */
@RunWith(AndroidJUnit4.class)
public class ApiTest {
    /**
     * Api test.
     */
    @Test
    public void apiTest() {
        // Context of the app under test.
        Call<List<WeatherModel>> listCall = NetworkClient.getRetrofit().create(WeatherAPIService.class)
                .getWeather("Rainfall", "UK");
        listCall.enqueue(new Callback<List<WeatherModel>>() {
            @Override
            public void onResponse(Call<List<WeatherModel>> call, Response<List<WeatherModel>> response) {
                int size = response.body().size();
                assertEquals(true, size > 1);
            }

            @Override
            public void onFailure(Call<List<WeatherModel>> call, Throwable t) {
            }
        });
        try {
            Response<List<WeatherModel>> call = listCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
