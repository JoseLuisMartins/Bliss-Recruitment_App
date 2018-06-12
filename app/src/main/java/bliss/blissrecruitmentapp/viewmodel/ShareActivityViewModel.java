package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.di.qualifiers.ShareUrl;
import bliss.blissrecruitmentapp.repository.ShareRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ShareActivityViewModel extends ViewModel{
    private final ShareRepository mShareRepository;
    private final MutableLiveData<Boolean> mSuccessResponse;
    private final MutableLiveData<Boolean> mLoading;
    private final String mUrl;

    @Inject
    public ShareActivityViewModel(ShareRepository shareRepository, @ShareUrl String url) {
        this.mShareRepository = shareRepository;
        this.mUrl = url;
        this.mSuccessResponse = new MutableLiveData<>();
        this.mLoading = new MutableLiveData<>();

    }

    public void shareContent(String email) {
        mLoading.setValue(true);

        Disposable disposable = mShareRepository.shareApp(email, this.mUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mLoading.setValue(false);
                    this.mSuccessResponse.setValue(true);
                }, throwable -> {
                    mLoading.setValue(false);
                    this.mSuccessResponse.setValue(false);
                });
    }

    public MutableLiveData<Boolean> getSuccessResponse() {
        return mSuccessResponse;
    }

    public String getUrl() {
        return mUrl;
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

}
