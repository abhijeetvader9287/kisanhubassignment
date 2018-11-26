package com.abhijeet.kisanhubassignment;
import android.content.Context;

import com.abhijeet.kisanhubassignment.data.local.WeatherDatabase;
import com.abhijeet.kisanhubassignment.data.local.dao.WeatherDao;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
/**
 * The type Db read write test.
 */
@RunWith(AndroidJUnit4.class)
public class DBReadWriteTest {
    /**
     * The Weather dao.
     */
    WeatherDao weatherDao;
    /**
     * The Weather database.
     */
    WeatherDatabase weatherDatabase;

    /**
     * Create db.
     */
    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        weatherDatabase = Room.inMemoryDatabaseBuilder(context, WeatherDatabase.class).build();
        weatherDao = weatherDatabase.resultDao();
    }

    /**
     * Close db.
     *
     * @throws IOException the io exception
     */
    @After
    public void closeDb() throws IOException {
        weatherDatabase.close();
    }

    /**
     * Db read and write.
     *
     * @throws Exception the exception
     */
    @Test
    public void DBReadAndWrite() throws Exception {
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.setLocation("UK");
        weatherModel.setId(1);
        weatherModel.setMetric("TMin");
        weatherModel.setMonth(1);
        weatherModel.setValue(1.8);
        weatherModel.setYear(7);
        weatherDao.insert(weatherModel);//.insert(user);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<WeatherModel> byName = weatherDao.getResult(weatherModel.getMetric(), weatherModel.getLocation());
        assertThat(byName.get(0).getLocation(), equalTo(weatherModel.getLocation()));
        assertThat(byName.get(0).getId(), equalTo(weatherModel.getId()));
        assertThat(byName.get(0).getMetric(), equalTo(weatherModel.getMetric()));
        assertThat(byName.get(0).getMonth(), equalTo(weatherModel.getMonth()));
        assertThat(byName.get(0).getYear(), equalTo(weatherModel.getYear()));
        assertThat(byName.get(0).getValue(), equalTo(weatherModel.getValue()));
    }
}