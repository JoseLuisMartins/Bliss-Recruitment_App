package bliss.blissrecruitmentapp.di.modules.activity._base;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import bliss.blissrecruitmentapp.di.qualifiers.ActivityContext;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class BaseActivityModule<A extends AppCompatActivity> {

    @Binds
    public abstract AppCompatActivity activity(A activity);

    @Provides
    @ActivityContext
    public static Context provideContext(AppCompatActivity activity) {
        return activity;
    }
}

