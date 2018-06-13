package bliss.blissrecruitmentapp.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;

import javax.inject.Singleton;

import bliss.blissrecruitmentapp.BlissRecruitmentApplication;
import bliss.blissrecruitmentapp.di.qualifiers.ApplicationContext;
import bliss.blissrecruitmentapp.data.api.endpoints.HealthClient;
import bliss.blissrecruitmentapp.data.api.endpoints.QuestionClient;
import bliss.blissrecruitmentapp.data.api.endpoints.ShareClient;
import bliss.blissrecruitmentapp.data.api.interceptors.NetworkConnectionChecker;
import bliss.blissrecruitmentapp.viewmodel.factories.GenericViewModelFactory;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

@Module
public abstract class ApplicationModule {

    @Binds
    @ApplicationContext
    public abstract Context provideContext(BlissRecruitmentApplication application);

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(GenericViewModelFactory genericViewModelFactory);

    @Provides
    public static CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    public static HealthClient provideHealthClient(Retrofit retrofit) {
        return retrofit.create(HealthClient.class);
    }

    @Provides
    @Singleton
    public static QuestionClient provideQuestionClient(Retrofit retrofit) {
        return retrofit.create(QuestionClient.class);
    }

    @Provides
    @Singleton
    public static ShareClient provideShareClient(Retrofit retrofit) {
        return retrofit.create(ShareClient.class);
    }

    @Provides
    @Singleton
    public static NetworkConnectionChecker provideNetworkConnectionChecker(@ApplicationContext Context context) {
        return new NetworkConnectionChecker(context);
    }


}
