package bliss.blissrecruitmentapp.repository;

import java.util.List;

import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.QuestionClient;
import io.reactivex.Completable;
import io.reactivex.Single;

public class QuestionRepository {
    private QuestionClient mQuestionClient;

    public QuestionRepository() {
        this.mQuestionClient = RetrofitInstance.getRetrofitInstance().create(QuestionClient.class);
    }


    // Get question by id
    public Single<Question> getQuestion(int id){
        return mQuestionClient.getQuestionById(id);
    }

    // Get questions
    public Single<List<Question>> getQuestions(int limit, int offset){
        return mQuestionClient.getQuestions(limit,offset);
    }

    // Get questions with search field
    public Single<List<Question>> getQuestions(int limit, int offset, String filter){
        return mQuestionClient.getQuestions(limit,offset, filter);
    }

    // Update question
    public Completable updateQuestion(Question question){
        return mQuestionClient.updateQuestion(question.getId(), question);
    }
}
