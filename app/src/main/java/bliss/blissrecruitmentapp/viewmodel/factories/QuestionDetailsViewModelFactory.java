package bliss.blissrecruitmentapp.viewmodel.factories;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsActivityViewModel;

public class QuestionDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private int mId;

    public QuestionDetailsViewModelFactory(int mId) {
        this.mId = mId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new QuestionDetailsActivityViewModel(this.mId);
    }
}