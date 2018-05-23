package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;
import android.view.View;

import bliss.blissrecruitmentapp.model.Health;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivityViewModel extends ViewModel{

    // live data to notify activity to change
    private MutableLiveData<Boolean> mServiceAvailable;

    private final ObservableField<String> status = new ObservableField<>();
    private final ObservableField<Boolean> loading = new ObservableField<>();


    public LoadingActivityViewModel() {
        this.status.set("Loading");
        this.mServiceAvailable = new MutableLiveData<>();
        checkHealth();
    }

    public MutableLiveData<Boolean> getmServiceAvailable() {
        if(mServiceAvailable == null){
            mServiceAvailable = new MutableLiveData<>();
        }
        return mServiceAvailable;
    }

    // make a requester class
    private void checkHealth(){
        HealthClient healthClient = RetrofitInstance.getRetrofitInstance().create(HealthClient.class);
        Call<Health> call = healthClient.health();
        loading.set(true);

        call.enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                status.set(response.body().getmStatus());
                loading.set(false);

                if(response.code() == 200) {
                    //go to Questions List Screen
                    mServiceAvailable.setValue(true);
                }

                //notify for two way data binding


                Log.d("debug", status.get());
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                //handle error
            }
        });
    }


    public ObservableField<String> getStatus() {
        return status;
    }

    public ObservableField<Boolean> getLoading() {
        return loading;
    }

    public View.OnClickListener refresh() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHealth();
            }
        };
    }
}
