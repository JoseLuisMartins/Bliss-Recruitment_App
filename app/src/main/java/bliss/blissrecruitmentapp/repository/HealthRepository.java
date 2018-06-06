package bliss.blissrecruitmentapp.repository;

import bliss.blissrecruitmentapp.model.Status;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import io.reactivex.Single;
import retrofit2.Response;

public class HealthRepository {
    private HealthClient mHealthClient;

    public HealthRepository() {
        this.mHealthClient = RetrofitInstance.getRetrofitInstance().create(HealthClient.class);
    }

    public Single<Response<Status>>  getHealth(){
        return mHealthClient.health();
    }
}
