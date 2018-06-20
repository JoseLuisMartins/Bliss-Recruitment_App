package bliss.blissrecruitmentapp.di.modules.activity.binding;

import bliss.blissrecruitmentapp.di.modules.activity.LoadingActivityModule;
import bliss.blissrecruitmentapp.di.modules.activity.QuestionDetailsActivityModule;
import bliss.blissrecruitmentapp.di.modules.activity.QuestionListActivityModule;
import bliss.blissrecruitmentapp.di.modules.activity.ShareActivityModule;
import bliss.blissrecruitmentapp.view.HealthCheck.LoadingActivity;
import bliss.blissrecruitmentapp.view.NoConnection.NoConnectivityActivity;
import bliss.blissrecruitmentapp.view.QuestionDetails.QuestionDetailsActivity;
import bliss.blissrecruitmentapp.view.QuestionList.QuestionListActivity;
import bliss.blissrecruitmentapp.view.Share.ShareActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract NoConnectivityActivity bindNoConnectivityActivity();


    @ContributesAndroidInjector(modules = {LoadingActivityModule.class})
    abstract LoadingActivity bindLoadingActivity();


    @ContributesAndroidInjector(modules = {QuestionListActivityModule.class})
    abstract QuestionListActivity bindQuestionListActivity();


    @ContributesAndroidInjector(modules = {QuestionDetailsActivityModule.class})
    abstract QuestionDetailsActivity bindQuestionDetailsActivity();


    @ContributesAndroidInjector(modules = {ShareActivityModule.class})
    abstract ShareActivity bindShareActivity();

}
