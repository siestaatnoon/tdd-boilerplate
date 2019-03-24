package com.oscarrrweb.tddboilerplate.presentation.di.modules;

import android.app.Activity;

import com.oscarrrweb.tddboilerplate.presentation.di.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity Activity provideActivity() {
        return activity;
    }
}
