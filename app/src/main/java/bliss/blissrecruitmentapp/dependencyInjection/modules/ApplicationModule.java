package bliss.blissrecruitmentapp.dependencyInjection.modules;

import javax.inject.Singleton;

import bliss.blissrecruitmentapp.network.api.HealthClient;
import bliss.blissrecruitmentapp.network.api.QuestionClient;
import bliss.blissrecruitmentapp.network.api.ShareClient;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {
        NetworkModule.class
})
public class ApplicationModule {



    @Provides
    @Singleton
    HealthClient provideHealthClient(Retrofit retrofit) {
        return retrofit.create(HealthClient.class);
    }

    @Provides
    @Singleton
    QuestionClient provideQuestionClient(Retrofit retrofit) {
        return retrofit.create(QuestionClient.class);
    }

    @Provides
    @Singleton
    ShareClient provideShareClient(Retrofit retrofit) {
        return retrofit.create(ShareClient.class);
    }
}
