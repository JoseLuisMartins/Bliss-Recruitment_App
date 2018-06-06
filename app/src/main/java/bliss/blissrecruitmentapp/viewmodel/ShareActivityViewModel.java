package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import bliss.blissrecruitmentapp.repository.ShareRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ShareActivityViewModel extends ViewModel{
    private final ShareRepository mShareRepository;
    private final MutableLiveData<Boolean> mSuccessResponse;
    private final ObservableField<Boolean> mLoading;
    private final String mUrl;

    public ShareActivityViewModel(String url) {
        this.mShareRepository = new ShareRepository();
        this.mSuccessResponse = new MutableLiveData<>();
        this.mLoading = new ObservableField<>();
        this.mUrl = url;

    }

    public void shareContent(String email) {
        mLoading.set(true);

        Disposable disposable = mShareRepository.shareApp(email, this.mUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mLoading.set(false);
                    this.mSuccessResponse.setValue(true);
                }, throwable -> {
                    mLoading.set(false);
                    this.mSuccessResponse.setValue(false);
                });
    }

    public MutableLiveData<Boolean> getSuccessResponse() {
        return mSuccessResponse;
    }

    public String getUrl() {
        return mUrl;
    }

    public ObservableField<Boolean> getLoading() {
        return mLoading;
    }

}
