package bliss.blissrecruitmentapp.viewmodel.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.repository.QuestionRepository;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsActivityViewModel;

public class QuestionDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mId;
    private QuestionRepository questionRepository;

    public QuestionDetailsViewModelFactory(QuestionRepository questionRepository, int mId) {
        this.mId = mId;
        this.questionRepository = questionRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionDetailsActivityViewModel(this.questionRepository, this.mId);
    }
}