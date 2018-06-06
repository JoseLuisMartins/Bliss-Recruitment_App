package bliss.blissrecruitmentapp.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import bliss.blissrecruitmentapp.model.Status;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.HealthClient;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class HealthRepository {
    private HealthClient mHealthClient;

    @Inject
    public HealthRepository(HealthClient healthClient) {
        this.mHealthClient = healthClient;
    }

    public Single<Response<Status>>  getHealth(){
        return mHealthClient.health();
    }
}
