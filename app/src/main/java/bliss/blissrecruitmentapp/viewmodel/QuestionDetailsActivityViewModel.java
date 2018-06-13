package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.di.qualifiers.QuestionId;
import bliss.blissrecruitmentapp.data.api.model.Choice;
import bliss.blissrecruitmentapp.data.api.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import bliss.blissrecruitmentapp.utils.Utils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionDetailsActivityViewModel extends ViewModel{
    private QuestionRepository mQuestionRepository;
    private final int mQuestionId;
    private CompositeDisposable mCompositeDisposable;
    private final MutableLiveData<Question> mQuestion;
    private final MutableLiveData<Boolean> mLoading;
    private final MutableLiveData<Boolean> mUpdatedSuccessfully;


    // question request observer
    private SingleObserver<Question> mQuestionRequestObserver = new SingleObserver<Question>() {
        @Override
        public void onSubscribe(Disposable d) {
            mCompositeDisposable.add(d);
        }

        @Override
        public void onSuccess(Question question) {
            mQuestion.setValue(question);
            mLoading.setValue(false);
        }

        @Override
        public void onError(Throwable e) {
            mLoading.setValue(false);
        }

    };

    @Inject
    public QuestionDetailsActivityViewModel(QuestionRepository questionRepository, @QuestionId int questionId, CompositeDisposable compositeDisposable) {
        this.mQuestionRepository = questionRepository;
        this.mQuestionId = questionId;
        this.mCompositeDisposable = compositeDisposable;

        this.mQuestion = new MutableLiveData<>();
        this.mLoading = new MutableLiveData<>();
        this.mUpdatedSuccessfully = new MutableLiveData<>();
    }


    public void loadQuestion(){
        mLoading.setValue(true);

        mQuestionRepository.getQuestion(mQuestionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mQuestionRequestObserver);
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public MutableLiveData<Question> getQuestion() {
        return mQuestion;
    }

    public void submitQuestion(int selectedChoice){
        Choice c = mQuestion.getValue().getChoices().get(selectedChoice);
        c.setVotes(c.getVotes() + 1);

        mLoading.setValue(true);
        mCompositeDisposable.add(mQuestionRepository.updateQuestion(mQuestion.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    mLoading.setValue(false);
                    mUpdatedSuccessfully.setValue(true);
                    // Update the UI
                    mQuestion.setValue(mQuestion.getValue());
                }, throwable -> {
                    mLoading.setValue(false);
                    mUpdatedSuccessfully.setValue(false);
                }));
    }

    public MutableLiveData<Boolean> getUpdatedSuccessfully() {
        return mUpdatedSuccessfully;
    }


    public String getAppLink(){
        return String.format("%s?%s=%d", Utils.APP_BASE_LINK, Utils.APP_QUESTION_PARAM, this.mQuestionId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }
}
