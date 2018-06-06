package bliss.blissrecruitmentapp.viewmodel.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import bliss.blissrecruitmentapp.repository.ShareRepository;
import bliss.blissrecruitmentapp.viewmodel.ShareActivityViewModel;

public class ShareActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private ShareRepository shareRepository;
    private String url;

    public ShareActivityViewModelFactory(ShareRepository shareRepository, String url) {
        this.shareRepository = shareRepository;
        this.url = url;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ShareActivityViewModel(this.shareRepository, this.url);
    }
}
