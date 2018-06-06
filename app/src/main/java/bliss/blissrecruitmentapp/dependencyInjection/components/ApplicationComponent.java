package bliss.blissrecruitmentapp.dependencyInjection.components;


import javax.inject.Singleton;

import bliss.blissrecruitmentapp.BlissRecruitmentApplication;
import bliss.blissrecruitmentapp.dependencyInjection.modules.ApplicationModule;
import bliss.blissrecruitmentapp.dependencyInjection.modules.NetworkModule;
import bliss.blissrecruitmentapp.dependencyInjection.modules.RepositoryModule;
import bliss.blissrecruitmentapp.dependencyInjection.modules.activity.ActivityBuilder;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilder.class,
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent extends AndroidInjector<BlissRecruitmentApplication> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<BlissRecruitmentApplication> {}
}
