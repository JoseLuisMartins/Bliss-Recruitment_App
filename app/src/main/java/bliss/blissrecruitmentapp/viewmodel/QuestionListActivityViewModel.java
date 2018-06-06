package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import bliss.blissrecruitmentapp.utils.Utils;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionListActivityViewModel extends ViewModel{

    // the repository
    private QuestionRepository mQuestionRepository;
    // live data to notify activity to update questions
    private final MutableLiveData<List<Question>> mQuestions;
    // Paging data
    private int mOffset, mLimit;
    private final ObservableField<Boolean> mLoading;
    private final ObservableField<Boolean> mSearching;
    private String mSearchFilter;

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


            mLoading.set(false);
        }

        @Override
        public void onError(Throwable e) {
            Log.d("debug", "Error on question list request-> " + e);
            mLoading.set(false);
        }

    };

    public QuestionListActivityViewModel() {
        this.mQuestions = new MutableLiveData<>();
        this.mLoading = new ObservableField<>();
        this.mSearching = new ObservableField<>();
        this.mQuestionRepository = new QuestionRepository();

        this.mLimit = 10;
        this.mOffset = 0;

        this.mSearching.set(false);
        this.mLoading.set(false);

    }


    public boolean checkHasMore(){
        return  (mQuestions.getValue().size() % this.mLimit != 10);
    }


    public void loadQuestions(){
        mLoading.set(true);

        if(mSearching.get())
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
        this.mSearching.set(true);
        this.mQuestions.setValue(new ArrayList<>());
        this.mOffset = 0;

        this.loadQuestions();
    }

    public void leaveSearchMode() {
        this.mOffset = 0;
        this.mSearching.set(false);
        this.mQuestions.setValue(new ArrayList<>());
        this.loadQuestions();
    }

    public MutableLiveData<List<Question>> getmQuestions() {
        return mQuestions;
    }

    public ObservableField<Boolean> getLoading() {
        return mLoading;
    }

    public ObservableField<Boolean> getSearching() {
        return mSearching;
    }

    public String getAppLink(){
        return String.format("%s?%s=%s", Utils.APP_BASE_LINK, Utils.APP_FILTER_PARAM, mSearchFilter);
    }
}
