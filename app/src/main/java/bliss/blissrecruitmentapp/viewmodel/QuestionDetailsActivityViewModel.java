package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import bliss.blissrecruitmentapp.utils.Utils;
import bliss.blissrecruitmentapp.model.Choice;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionDetailsActivityViewModel extends ViewModel{
    private QuestionRepository mQuestionRepository;
    private final int mQuestionId;
    private final ObservableField<Question> mQuestion;
    private final ObservableField<Boolean> mLoading;
    private final MutableLiveData<Boolean> mQuestionLoaded;
    private final MutableLiveData<Boolean> mUpdatedSuccessfully;


    // question request observer
    private SingleObserver<Question> mQuestionRequestObserver = new SingleObserver<Question>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onSuccess(Question question) {
            //Notify activity about new incoming question data
            mQuestion.set(question);
            mQuestionLoaded.setValue(true);
            mLoading.set(false);
        }

        @Override
        public void onError(Throwable e) {
            mQuestionLoaded.setValue(false);
            mLoading.set(false);
        }

    };

    public QuestionDetailsActivityViewModel(int questionId) {
        this.mQuestionId = questionId;

        this.mQuestionRepository = new QuestionRepository();
        this.mQuestion = new ObservableField<>();
        this.mLoading = new ObservableField<>();
        this.mQuestionLoaded = new MutableLiveData<>();
        this.mUpdatedSuccessfully = new MutableLiveData<>();
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
                    mUpdatedSuccessfully.setValue(true);
                }, throwable -> {
                    mLoading.set(false);
                    mUpdatedSuccessfully.setValue(false);
                });
    }

    public MutableLiveData<Boolean> getUpdatedSuccessfully() {
        return mUpdatedSuccessfully;
    }

    public MutableLiveData<Boolean> getmQuestionLoaded() {
        return mQuestionLoaded;
    }

    public String getAppLink(){
        return String.format("%s?%s=%d", Utils.APP_BASE_LINK, Utils.APP_QUESTION_PARAM, this.mQuestionId);
    }
}
