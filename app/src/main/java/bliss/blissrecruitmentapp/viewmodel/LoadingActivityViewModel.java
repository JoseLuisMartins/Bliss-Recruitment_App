package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.repository.HealthRepository;
import dagger.multibindings.IntKey;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingActivityViewModel extends ViewModel{
    private final HealthRepository mHealthRepository;
    private final MutableLiveData<Boolean> mIsServiceAvailable;
    private final ObservableField<Boolean> mLoading;


    @Inject
    public LoadingActivityViewModel(HealthRepository healthRepository) {
        this.mHealthRepository = healthRepository;

        this.mLoading = new ObservableField<>();
        this.mIsServiceAvailable = new MutableLiveData<>();

        this.checkHealth();


    }


    public MutableLiveData<Boolean> getmIsServiceAvailable() {
        return mIsServiceAvailable;
    }


    public void checkHealth(){
        mLoading.set(true);

        Disposable disposable = mHealthRepository.getHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if(data.code() == 200) {
                        //go to Questions List Screen
                        mIsServiceAvailable.setValue(true);
                    }else{
                        mLoading.set(false);
                    }
                }, throwable -> {
                    mLoading.set(false);
                    Log.d("debug", "error on request: " + throwable);
                });
    }


    public ObservableField<Boolean> getLoading() {
        return mLoading;
    }


}
