package com.abhijeet.kisanhubassignment.data.local;
import android.content.Context;

import com.abhijeet.kisanhubassignment.data.local.dao.WeatherDao;
import com.abhijeet.kisanhubassignment.data.model.WeatherModel;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
/**
 * The type Weather database.
 */
@Database(entities = {WeatherModel.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    /**
     * The Instance.
     */
    static WeatherDatabase INSTANCE;

    /**
     * Gets app database.
     *
     * @param context the context
     * @return the app database
     */
    public static WeatherDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (WeatherDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WeatherDatabase.class, "weather-database")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Destroy instance.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Result dao weather dao.
     *
     * @return the weather dao
     */
    public abstract WeatherDao resultDao();
}
