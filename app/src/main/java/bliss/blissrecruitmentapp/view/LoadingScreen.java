package bliss.blissrecruitmentapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.model.Health;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        HealthClient healthClient = RetrofitInstance.getRetrofitInstance().create(HealthClient.class);
        Call<Health> call = healthClient.health();



        call.enqueue(new Callback<Health>() {
            @Override
            public void onResponse(Call<Health> call, Response<Health> response) {
                Health health = response.body();
                Log.d("debug", "Health: " + health.getmStatus());
            }

            @Override
            public void onFailure(Call<Health> call, Throwable t) {
                //handle error
            }
        });


    }
}
