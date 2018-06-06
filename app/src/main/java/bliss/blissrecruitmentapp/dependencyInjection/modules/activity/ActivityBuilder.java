package bliss.blissrecruitmentapp.dependencyInjection.modules.activity;

import bliss.blissrecruitmentapp.view.ui.LoadingActivity;
import bliss.blissrecruitmentapp.view.ui.NoConnectivityActivity;
import bliss.blissrecruitmentapp.view.ui.QuestionDetailsActivity;
import bliss.blissrecruitmentapp.view.ui.QuestionListActivity;
import bliss.blissrecruitmentapp.view.ui.ShareActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract LoadingActivity bindLoadingActivity();


    @ContributesAndroidInjector
    abstract NoConnectivityActivity bindNoConnectivityActivity();


    @ContributesAndroidInjector
    abstract QuestionListActivity bindQuestionListActivity();


    @ContributesAndroidInjector(modules = {QuestionDetailsActivityModule.class})
    abstract QuestionDetailsActivity bindQuestionDetailsActivity();


    @ContributesAndroidInjector(modules = {ShareActivityModule.class})
    abstract ShareActivity bindShareActivity();

}
