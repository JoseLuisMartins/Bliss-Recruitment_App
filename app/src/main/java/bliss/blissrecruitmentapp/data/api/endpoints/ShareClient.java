package bliss.blissrecruitmentapp.data.api.endpoints;

import io.reactivex.Completable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShareClient {
    @POST("share")
    Completable share(@Query("destination_email") String email, @Query("content_url") String content);
}
