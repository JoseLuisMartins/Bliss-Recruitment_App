package bliss.blissrecruitmentapp.dependencyInjection;


import android.app.Application;

import javax.inject.Singleton;

import bliss.blissrecruitmentapp.BlissRecruitmentApplication;
import bliss.blissrecruitmentapp.dependencyInjection.modules.ApplicationModule;
import bliss.blissrecruitmentapp.dependencyInjection.modules.NetworkModule;
import bliss.blissrecruitmentapp.dependencyInjection.modules.RepositoryModule;
import dagger.BindsInstance;
import dagger.Component;



@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        RepositoryModule.class
})
public interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        ApplicationComponent build();
    }

    void inject(BlissRecruitmentApplication application);

}
