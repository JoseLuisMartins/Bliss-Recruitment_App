package bliss.blissrecruitmentapp.di.modules.activity;

import android.arch.lifecycle.ViewModel;

import bliss.blissrecruitmentapp.di.modules.activity._base.BaseActivityModule;
import bliss.blissrecruitmentapp.di.qualifiers.ViewModelKey;
import bliss.blissrecruitmentapp.view.HealthCheck.LoadingActivity;
import bliss.blissrecruitmentapp.view.HealthCheck.LoadingActivityViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoadingActivityModule extends BaseActivityModule<LoadingActivity> {


    @Binds
    @IntoMap
    @ViewModelKey(LoadingActivityViewModel.class)
    abstract ViewModel bindLoadingActivityViewModel(LoadingActivityViewModel loadingActivityViewModel);
}
