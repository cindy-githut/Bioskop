package com.cindymb.bioskop.di;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import com.cindymb.bioskop.BuildConfig;
import com.cindymb.bioskop.service.ApiService;
import com.cindymb.bioskop.ui.LoggingHelper;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = ViewModelModule.class)
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication() {
        return application;
    }

    @Singleton
    @Provides
    ApiService providesApiService(Retrofit aRetrofit) {
        return aRetrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    Retrofit provideNetwork(OkHttpClient aOkHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(aOkHttpClient)
                .build();
    }

    @Singleton
    @Provides
    OkHttpClient providesOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        try {

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptor);

            if (BuildConfig.DEBUG) {
                httpClient.addNetworkInterceptor(new StethoInterceptor());
                httpClient.addNetworkInterceptor(new StethoInterceptor());
            }
            httpClient.connectTimeout(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            LoggingHelper.information(AppModule.class, e.getMessage());
        }

        return httpClient.build();
    }


    /**
     * Provides the Application Context
     *
     * @return a Context
     */
    @Provides
    Context providesApplicationContext() {
        return application;
    }

    /**
     * Provides a single instance of the LocalBroadcastManager
     *
     * @return LocalBroadcastManager
     */
    @Provides
    LocalBroadcastManager providesLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(application);
    }


}
