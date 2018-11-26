package com.abhijeet.kisanhubassignment.data.remote;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * The type Network client.
 */
public class NetworkClient {
    /**
     * The Retrofit.
     */
    static Retrofit retrofit;

    /**
     * Instantiates a new Network client.
     */
    public NetworkClient() {
    }

    /**
     * Gets retrofit.
     *
     * @return the retrofit
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://s3.eu-west-2.amazonaws.com/interview-question-data/metoffice/")
                    .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
