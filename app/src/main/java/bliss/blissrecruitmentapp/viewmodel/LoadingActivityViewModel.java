package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.repository.HealthRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingActivityViewModel extends ViewModel{
    private final HealthRepository mHealthRepository;
    private final MutableLiveData<Boolean> mIsServiceAvailable;
    private final MutableLiveData<Boolean> mLoading;
    private CompositeDisposable mCompositeDisposable;


    @Inject
    public LoadingActivityViewModel(HealthRepository healthRepository, CompositeDisposable compositeDisposable) {
        this.mHealthRepository = healthRepository;
        this.mCompositeDisposable = compositeDisposable;

        this.mLoading = new MutableLiveData<>();
        this.mIsServiceAvailable = new MutableLiveData<>();

        this.checkHealth();
    }


    public MutableLiveData<Boolean> getmIsServiceAvailable() {
        return mIsServiceAvailable;
    }


    public void checkHealth(){
        mLoading.setValue(true);

        mCompositeDisposable.add(mHealthRepository.getHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if(data.code() == 200) {
                        //go to Questions List Screen
                        mIsServiceAvailable.setValue(true);
                    }else{
                        mLoading.setValue(false);
                    }
                }, throwable -> {
                    mLoading.setValue(false);
                }));

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }


}
