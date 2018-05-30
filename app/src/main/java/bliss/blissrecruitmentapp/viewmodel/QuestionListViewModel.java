package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import bliss.blissrecruitmentapp.Utils.Utils;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.repository.QuestionRepository;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionListViewModel extends ViewModel{

    // the repository
    private QuestionRepository mQuestionRepository;
    // live data to notify activity to update questions
    private final MutableLiveData<List<Question>> mQuestions;
    // Paging data
    private int mOffset, mLimit;
    //waiting for request
    private final ObservableField<Boolean> mLoading;
    //search
    private final ObservableField<Boolean> mSearching;
    private String mSearchFilter;

    // question request consumer
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
                // TODO better way of triggering activity?
                mQuestions.setValue(mQuestions.getValue());
            } else
                mQuestions.setValue(questions);


            mLoading.set(false);
        }

        @Override
        public void onError(Throwable e) {

        }

    };

    public QuestionListViewModel() {
        this.mQuestions = new MutableLiveData<>();
        this.mLoading = new ObservableField<>();
        this.mSearching = new ObservableField<>();
        this.mQuestionRepository = new QuestionRepository();

        this.mLimit = 10;
        this.mOffset = 0;

        mSearching.set(false);
        mLoading.set(true);

        //TODO initial mode could be searching, handle that
        mQuestionRepository.getQuestions(mLimit, mOffset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mQuestionsRequestObserver);
    }


    public boolean checkHasMore(){
        return  (mQuestions.getValue().size() % this.mLimit != 10);
    }



    public void loadNextQuestions() {
        this.mOffset +=this.mLimit;
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



    public void search(String search) {
        this.mSearchFilter = search;
        this.mSearching.set(true);
        this.mQuestions.setValue(new ArrayList<>());
        this.mOffset = 0;

        mLoading.set(true);
        mQuestionRepository.getQuestions(mLimit, mOffset, search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mQuestionsRequestObserver);
    }

    public void leaveSearchMode() {
        this.mOffset = 0;
        this.mSearching.set(false);
        this.mQuestions.setValue(new ArrayList<>());
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
        return String.format("%s?question_filter=%s", Utils.APP_BASE_LINK, mSearchFilter);
    }
}
