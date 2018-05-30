package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import bliss.blissrecruitmentapp.Utils.Utils;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.repository.QuestionRepository;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionDetailsViewModel extends ViewModel{
    // the repository
    private QuestionRepository mQuestionRepository;
    // Question id
    private final int mQuestionId;
    // live data to notify activity to update questions
    private final ObservableField<Question> mQuestion;
    //waiting for request
    private final ObservableField<Boolean> mLoading;

    // question request observer
    private SingleObserver<Question> mQuestionRequestObserver = new SingleObserver<Question>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onSuccess(Question question) {
            //Notify activity about new incoming question data
            mQuestion.set(question);

            mLoading.set(false);
        }

        @Override
        public void onError(Throwable e) {

        }

    };

    public QuestionDetailsViewModel(int questionId) {
        this.mQuestionId = questionId;

        this.mQuestionRepository = new QuestionRepository();
        this.mQuestion = new ObservableField<>();
        this.mLoading = new ObservableField<>();

        this.loadQuestion();
    }


    public void loadQuestion(){
        mLoading.set(true);
        mQuestionRepository.getQuestion(mQuestionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mQuestionRequestObserver);
    }

    public ObservableField<Question> getQuestion() {
        return mQuestion;
    }

    public void submitQuestion(){
        mLoading.set(true);
        mQuestionRepository.updateQuestion(mQuestion.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(); // TODO check onCompleted
    }

    public String getAppLink(){
        return String.format("%s?question_id=%d", Utils.APP_BASE_LINK, this.mQuestionId);
    }
}
