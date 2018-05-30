package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import bliss.blissrecruitmentapp.Utils.Utils;
import bliss.blissrecruitmentapp.model.Choice;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
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
    // waiting for request
    private final ObservableField<Boolean> mLoading;
    // feedback
    private final MutableLiveData<String> mFeedback;

    // question request observer
    private SingleObserver<Question> mQuestionRequestObserver = new SingleObserver<Question>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onSuccess(Question question) {
            //Notify activity about new incoming question data
            mQuestion.set(question);

            mFeedback.setValue(null);
            mLoading.set(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("debug", "Error on question details request-> " + e);
            mLoading.set(false);
        }

    };

    public QuestionDetailsViewModel(int questionId) {
        this.mQuestionId = questionId;

        this.mQuestionRepository = new QuestionRepository();
        this.mQuestion = new ObservableField<>();
        this.mLoading = new ObservableField<>();
        this.mFeedback = new MutableLiveData<>();
    }


    public void loadQuestion(){
        mLoading.set(true);

        mQuestionRepository.getQuestion(mQuestionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mQuestionRequestObserver);
    }

    public ObservableField<Boolean> getLoading() {
        return mLoading;
    }

    public ObservableField<Question> getQuestion() {
        return mQuestion;
    }

    public void submitQuestion(int selectedChoice){
        //update question
        Choice c = mQuestion.get().getChoices().get(selectedChoice);
        c.setVotes(c.getVotes() + 1);

        //make request
        mLoading.set(true);
        Disposable disposable = mQuestionRepository.updateQuestion(mQuestion.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mLoading.set(false);
                    mFeedback.setValue("Question successfully updated");
                }, throwable -> {
                    mLoading.set(false);
                    mFeedback.setValue("An error has occurred please try again");
                });
    }

    public MutableLiveData<String> getmFeedback() {
        return mFeedback;
    }

    public String getAppLink(){
        return String.format("%s?question_id=%d", Utils.APP_BASE_LINK, this.mQuestionId);
    }
}
