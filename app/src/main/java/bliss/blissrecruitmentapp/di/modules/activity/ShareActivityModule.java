package bliss.blissrecruitmentapp.di.modules.activity;

import android.arch.lifecycle.ViewModel;

import bliss.blissrecruitmentapp.di.modules.activity._base.BaseActivityModule;
import bliss.blissrecruitmentapp.di.qualifiers.ShareUrl;
import bliss.blissrecruitmentapp.di.qualifiers.ViewModelKey;
import bliss.blissrecruitmentapp.view.Share.ShareActivity;
import bliss.blissrecruitmentapp.view.Share.ShareActivityViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class ShareActivityModule extends BaseActivityModule<ShareActivity>{

    @Provides
    @ShareUrl
    static String provideShareUrl(ShareActivity shareActivity) {
        return shareActivity.getShareUrl();
    }

    @Binds
    @IntoMap
    @ViewModelKey(ShareActivityViewModel.class)
    abstract ViewModel bindShareActivityViewModel(ShareActivityViewModel questionDetailsActivityViewModel);

}
