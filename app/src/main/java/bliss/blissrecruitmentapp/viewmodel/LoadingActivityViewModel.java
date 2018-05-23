package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import bliss.blissrecruitmentapp.model.Health;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivityViewModel extends ViewModel{
    private Health mHealth;
    private MutableLiveData<Boolean> mServiceAvailable;


    public LoadingActivityViewModel() {
        this.mHealth = new Health("loading");
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


        call.enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                mHealth = response.body();

                if(response.code() == 200) {
                  //go to Questions List Screen
                  mServiceAvailable.setValue(true);
                }
              

                Log.d("debug", mHealth.getmStatus());
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                //handle error
            }
        });
    }

    public String getStatus() {
        return mHealth.getmStatus();
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
