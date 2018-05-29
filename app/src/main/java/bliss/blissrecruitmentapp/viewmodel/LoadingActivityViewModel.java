package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import bliss.blissrecruitmentapp.network.repository.HealthRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoadingActivityViewModel extends ViewModel{
    private final HealthRepository mHealthRepository; // service
    private final MutableLiveData<Boolean> mIsServiceAvailable; // live data to notify activity to start intent
    private final ObservableField<Boolean> loading; // waiting for request


    public LoadingActivityViewModel() {
        this.mHealthRepository = new HealthRepository();

        this.loading = new ObservableField<>();
        this.mIsServiceAvailable = new MutableLiveData<>();

        this.checkHealth();


    }


    public MutableLiveData<Boolean> getmIsServiceAvailable() {
        return mIsServiceAvailable;
    }


    public void checkHealth(){
        loading.set(true);

        //TODO handle disposable
        Disposable disposable = mHealthRepository.getHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if(data.code() == 200) {
                        //go to Questions List Screen
                        mIsServiceAvailable.setValue(true);
                    }

                    loading.set(false);
                }, throwable -> {
                    //TODO refactor
                    Log.d("debug", "error on request: " + throwable);
                });

    }


    public ObservableField<Boolean> getLoading() {
        return loading;
    }


}
