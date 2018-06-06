package bliss.blissrecruitmentapp.dependencyInjection.modules;


import javax.inject.Singleton;

import bliss.blissrecruitmentapp.dependencyInjection.components.ApplicationComponent;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import bliss.blissrecruitmentapp.network.api.QuestionClient;
import bliss.blissrecruitmentapp.network.api.ShareClient;
import bliss.blissrecruitmentapp.repository.HealthRepository;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import bliss.blissrecruitmentapp.repository.ShareRepository;
import dagger.Module;
import dagger.Provides;


// Todo see if dagger can handle this dependencies without this class
@Module(includes = {
        ApplicationModule.class
})
public class RepositoryModule {

    @Provides
    @Singleton
    HealthRepository provideHealthRepository(HealthClient healthClient) {
        return new HealthRepository(healthClient);
    }

    @Provides
    @Singleton
    QuestionRepository provideQuestionRepository(QuestionClient questionClient) {
        return new QuestionRepository(questionClient);
    }

    @Provides
    @Singleton
    ShareRepository provideShareRepository(ShareClient shareClient) {
        return new ShareRepository(shareClient);
    }

}
