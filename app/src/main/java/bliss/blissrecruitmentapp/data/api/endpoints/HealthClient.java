package bliss.blissrecruitmentapp.data.api.endpoints;

import bliss.blissrecruitmentapp.data.api.model.Status;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface HealthClient {
    @GET("health")
    Single<Response<Status>> health();
}
