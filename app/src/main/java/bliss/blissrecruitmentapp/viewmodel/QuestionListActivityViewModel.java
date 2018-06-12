package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.data.api.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import bliss.blissrecruitmentapp.utils.Utils;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionListActivityViewModel extends ViewModel{

    private QuestionRepository mQuestionRepository;

    private final MutableLiveData<List<Question>> mQuestions;
    private final MutableLiveData<Boolean> mLoading;
    private final MutableLiveData<Boolean> mSearching;
    private String mSearchFilter;
    // Paging data
    private int mOffset, mLimit;


    // question request observer
    private SingleObserver<List<Question>> mQuestionsRequestObserver = new SingleObserver<List<Question>>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onSuccess(List<Question> questions) {
            //Notify activity about new incoming question data
            List<Question> questionsTmp = mQuestions.getValue();
            if(questionsTmp != null) {
                mQuestions.getValue().addAll(questions);
                mQuestions.setValue(mQuestions.getValue());
            } else
                mQuestions.setValue(questions);


            mLoading.setValue(false);
        }

        @Override
        public void onError(Throwable e) {
            mLoading.setValue(false);
        }

    };

    @Inject
    public QuestionListActivityViewModel(QuestionRepository questionRepository) {
        this.mQuestionRepository = questionRepository;
        this.mQuestions = new MutableLiveData<>();
        this.mLoading = new MutableLiveData<>();
        this.mSearching = new MutableLiveData<>();

        this.mLimit = 10;
        this.mOffset = 0;

        this.mSearching.setValue(false);
        this.mLoading.setValue(false);

    }


    public boolean checkHasMore(){
        return  (mQuestions.getValue().size() % this.mLimit != 10);
    }


    public void loadQuestions(){
        mLoading.setValue(true);

        if(mSearching.getValue())
            mQuestionRepository.getQuestions(mLimit, mOffset, mSearchFilter)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mQuestionsRequestObserver);
        else
            mQuestionRepository.getQuestions(mLimit, mOffset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mQuestionsRequestObserver);
    }

    public void loadNextQuestions() {
        this.mOffset +=this.mLimit;
        this.loadQuestions();
    }


    public void search(String search) {
        this.mSearchFilter = search;
        this.mSearching.setValue(true);
        this.mQuestions.setValue(new ArrayList<>());
        this.mOffset = 0;

        this.loadQuestions();
    }

    public void leaveSearchMode() {
        this.mOffset = 0;
        this.mSearching.setValue(false);
        this.mQuestions.setValue(new ArrayList<>());
        this.loadQuestions();
    }

    public MutableLiveData<List<Question>> getmQuestions() {
        return mQuestions;
    }

    public MutableLiveData<Boolean> getLoading() {
        return mLoading;
    }

    public MutableLiveData<Boolean> getSearching() {
        return mSearching;
    }

    public String getAppLink(){
        return String.format("%s?%s=%s", Utils.APP_BASE_LINK, Utils.APP_FILTER_PARAM, mSearchFilter);
    }
}
