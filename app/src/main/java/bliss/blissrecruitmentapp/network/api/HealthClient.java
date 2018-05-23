package bliss.blissrecruitmentapp.network.api;

import bliss.blissrecruitmentapp.model.Status;
import retrofit2.Call;
import retrofit2.http.GET;

public interface HealthClient {
    @GET("health")
    Call<Status> health();
}
