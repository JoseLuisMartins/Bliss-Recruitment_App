package bliss.blissrecruitmentapp;

import bliss.blissrecruitmentapp.di.components.DaggerApplicationComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class BlissRecruitmentApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
