package bliss.blissrecruitmentapp.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import bliss.blissrecruitmentapp.data.api.endpoints.QuestionClient;
import bliss.blissrecruitmentapp.data.api.model.Question;
import io.reactivex.Completable;
import io.reactivex.Single;

@Singleton
public class QuestionRepository {
    private final QuestionClient mQuestionClient;

    @Inject
    public QuestionRepository(QuestionClient questionClient) {
        this.mQuestionClient = questionClient;
    }


    public Single<Question> getQuestion(int id){
        return mQuestionClient.getQuestionById(id);
    }


    public Single<List<Question>> getQuestions(int limit, int offset){
        return mQuestionClient.getQuestions(limit,offset);
    }

    public Single<List<Question>> getQuestions(int limit, int offset, String filter){
        return mQuestionClient.getQuestions(limit,offset, filter);
    }

    public Completable updateQuestion(Question question){
        return mQuestionClient.updateQuestion(question.getId(), question);
    }
}
