package bliss.blissrecruitmentapp.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import bliss.blissrecruitmentapp.data.api.model.Status;
import bliss.blissrecruitmentapp.data.api.endpoints.HealthClient;
import io.reactivex.Single;
import retrofit2.Response;

@Singleton
public class HealthRepository {
    private final HealthClient mHealthClient;

    @Inject
    public HealthRepository(HealthClient healthClient) {
        this.mHealthClient = healthClient;
    }

    public Single<Response<Status>>  getHealth(){
        return mHealthClient.health();
    }
}
