package bliss.blissrecruitmentapp.dependencyInjection.modules.activity;

import bliss.blissrecruitmentapp.repository.ShareRepository;
import bliss.blissrecruitmentapp.view.ui.ShareActivity;
import bliss.blissrecruitmentapp.viewmodel.factories.ShareActivityViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class ShareActivityModule {

    @Provides
    ShareActivity provideQuestionDetailsActivity(ShareActivity shareActivity) {
        return shareActivity;
    }

    @Provides
    ShareActivityViewModelFactory provideQuestionDetailsViewModelFactory(ShareActivity shareActivity, ShareRepository repository) {
        //Todo find a way to pass url
        return new ShareActivityViewModelFactory(repository, "blissrecruitment://questions?question_filter=DaggerIsWorking!");
    }
}
