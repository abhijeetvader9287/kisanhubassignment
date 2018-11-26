/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abhijeet.kisanhubassignment.data;
import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * The type Network bound resource.
 *
 * @param <ResultType>  the type parameter
 * @param <RequestType> the type parameter
 */
abstract class NetworkBoundResource<ResultType, RequestType> {
    /**
     * The Result.
     */
    final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();
    /**
     * The Metric.
     */
    String metric;
    /**
     * The Location.
     */
    String location;

    /**
     * Instantiates a new Network bound resource.
     *
     * @param online   the online
     * @param metric   the metric
     * @param location the location
     */
    @MainThread
    NetworkBoundResource(final boolean online, String metric, String location) {
        this.metric = metric;
        this.location = location;
        result.setValue((Resource<ResultType>) Resource.loading(null));
        final LiveData<ResultType> dbSource = loadFromDb(metric, location);
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);
                if (online) {
                    NetworkBoundResource.this.fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    /**
     * Fetch from network.
     *
     * @param dbSource the db source
     */
    @MainThread
    void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });
        createCall().enqueue(new Callback<RequestType>() {
            @Override
            public void onResponse(Call<RequestType> call, Response<RequestType> response) {
                result.removeSource(dbSource);
                saveResultAndReInit(response.body());
            }

            @Override
            public void onFailure(Call<RequestType> call, final Throwable t) {
                onFetchFailed();
                result.removeSource(dbSource);
                result.addSource(dbSource, new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue(Resource.error(t.getMessage(), newData));
                    }
                });
            }
        });
    }

    /**
     * Save result and re init.
     *
     * @param response the response
     */
    @MainThread
    void saveResultAndReInit(final RequestType response) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                saveCallResult(response);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                result.addSource(loadFromDb(metric, location), new Observer<ResultType>() {
                    @Override
                    public void onChanged(@Nullable ResultType newData) {
                        result.setValue(Resource.success(newData));
                    }
                });
            }
        }.execute();
    }

    /**
     * Save call result.
     *
     * @param item the item
     */
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);
    /**
     * Load from db live data.
     *
     * @param metric   the metric
     * @param location the location
     * @return the live data
     */
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb(String metric, String location);
    /**
     * Create call call.
     *
     * @return the call
     */
    @NonNull
    @MainThread
    protected abstract Call<RequestType> createCall();

    @MainThread
    private void onFetchFailed() {
    }

    /**
     * Gets as live data.
     *
     * @return the as live data
     */
    public final LiveData<Resource<ResultType>> getAsLiveData() {
        return result;
    }
}
