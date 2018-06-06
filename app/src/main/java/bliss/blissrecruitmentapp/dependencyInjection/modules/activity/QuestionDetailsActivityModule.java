package bliss.blissrecruitmentapp.dependencyInjection.modules.activity;

import bliss.blissrecruitmentapp.repository.QuestionRepository;
import bliss.blissrecruitmentapp.view.ui.QuestionDetailsActivity;
import bliss.blissrecruitmentapp.viewmodel.factories.QuestionDetailsViewModelFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class QuestionDetailsActivityModule {

    @Provides
    QuestionDetailsActivity provideQuestionDetailsActivity(QuestionDetailsActivity questionDetailsActivity) {
        return questionDetailsActivity;
    }

    @Provides
    QuestionDetailsViewModelFactory provideQuestionDetailsViewModelFactory(QuestionDetailsActivity questionDetailsActivity, QuestionRepository repository) {
        //Todo find a way to pass id
        return new QuestionDetailsViewModelFactory(repository,1);
    }
}
