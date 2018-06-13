package bliss.blissrecruitmentapp.di.components;


import javax.inject.Singleton;

import bliss.blissrecruitmentapp.BlissRecruitmentApplication;
import bliss.blissrecruitmentapp.di.modules.ApplicationModule;
import bliss.blissrecruitmentapp.di.modules.NetworkModule;
import bliss.blissrecruitmentapp.di.modules.activity.binding.ActivityBuilder;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        ApplicationModule.class,
        NetworkModule.class,
})
public interface ApplicationComponent extends AndroidInjector<BlissRecruitmentApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BlissRecruitmentApplication> {}


}
