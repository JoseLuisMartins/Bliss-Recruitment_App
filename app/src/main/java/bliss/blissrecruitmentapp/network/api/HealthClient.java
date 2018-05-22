package bliss.blissrecruitmentapp.network.api;

import bliss.blissrecruitmentapp.model.Health;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthClient {
    @GET("health")
    Call<Health> health();
}
