package com.cindymb.bioskop;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.cindymb.bioskop.di.AppComponent;
import com.cindymb.bioskop.di.AppModule;
import com.cindymb.bioskop.di.DaggerAppComponent;
import com.cindymb.bioskop.di.RepositoryModule;
import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;

public class BioskopApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .repoModule(new RepositoryModule())
                .build();
        appComponent.inject(this);

        if (BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
            Stetho.initializeWithDefaults(this);
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }
}
