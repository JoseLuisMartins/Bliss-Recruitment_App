package bliss.blissrecruitmentapp.network.api;

import java.util.List;

import bliss.blissrecruitmentapp.model.Question;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionClient {
    @GET("questions")
    Call<List<Question>> getQuestions(@Query("limit") int limit, @Query("offset") int offset);

    @GET("questions/")
    Call<List<Question>> getQuestions(@Query("limit") int limit, @Query("offset") int offset, @Query("filter") int filter);

    @GET("questions/{question_id}")
    Call<List<Question>> getQuestionById(@Path("question_id") int question_id);

    @PUT("questions/")
    Call<List<Question>> getQuestionById(@Body Question question);

}
