package bliss.blissrecruitmentapp.view.Share;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.di.qualifiers.ShareUrl;
import bliss.blissrecruitmentapp.repository.ShareRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class ShareActivityViewModel extends ViewModel{
    private final ShareRepository mShareRepository;
    private final String mUrl;
    private final CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<Boolean> mSuccessResponse;
    private final MutableLiveData<Boolean> mLoading;


    @Inject
    public ShareActivityViewModel(ShareRepository shareRepository, @ShareUrl String url, CompositeDisposable compositeDisposable) {
        this.mShareRepository = shareRepository;
        this.mUrl = url;
        this.mCompositeDisposable = compositeDisposable;

        this.mSuccessResponse = new MutableLiveData<>();
        this.mLoading = new MutableLiveData<>();

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

    public void shareContent(String email) {
        mLoading.setValue(true);

        mCompositeDisposable.add(mShareRepository.shareApp(email, this.mUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mLoading.setValue(false);
                    this.mSuccessResponse.setValue(true);
                }, throwable -> {
                    mLoading.setValue(false);
                    this.mSuccessResponse.setValue(false);
                }));
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
