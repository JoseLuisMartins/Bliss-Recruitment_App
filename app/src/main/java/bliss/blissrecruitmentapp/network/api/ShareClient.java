package bliss.blissrecruitmentapp.network.api;

import bliss.blissrecruitmentapp.model.Status;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ShareClient {
    @GET("share")
    Call<Status> share(@Query("destination_email") String email, @Query("content_url") String content);
}
