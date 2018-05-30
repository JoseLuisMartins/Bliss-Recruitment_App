package bliss.blissrecruitmentapp.network.api;

import java.util.List;

import bliss.blissrecruitmentapp.model.Question;
import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionClient {
    @GET("questions")
    Single<List<Question>> getQuestions(@Query("limit") int limit, @Query("offset") int offset);

    @GET("questions")
    Single<List<Question>> getQuestions(@Query("limit") int limit, @Query("offset") int offset, @Query("filter") String filter);

    @GET("questions/{question_id}")
    Single<Question> getQuestionById(@Path("question_id") int question_id);

    @PUT("questions/{question_id}")
    Completable updateQuestion(@Path("question_id") int question_id, @Body Question question);

}
