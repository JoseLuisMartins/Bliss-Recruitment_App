package bliss.blissrecruitmentapp.di.modules.activity;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import bliss.blissrecruitmentapp.di.modules.activity._base.BaseActivityModule;
import bliss.blissrecruitmentapp.di.qualifiers.ActivityContext;
import bliss.blissrecruitmentapp.di.qualifiers.QuestionId;
import bliss.blissrecruitmentapp.di.qualifiers.ViewModelKey;
import bliss.blissrecruitmentapp.view.adapter.QuestionChoicesAdapter;
import bliss.blissrecruitmentapp.view.ui.QuestionDetailsActivity;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsActivityViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class QuestionDetailsActivityModule extends BaseActivityModule<QuestionDetailsActivity> {

    @Provides
    @QuestionId
    static int provideQuestionId(QuestionDetailsActivity questionDetailsActivity) {
        return questionDetailsActivity.getQuestionId();
    }

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(@ActivityContext Context activityContext) {
        return new LinearLayoutManager(activityContext);
    }

    @Provides
    static QuestionChoicesAdapter provideQuestionChoiceAdapter(QuestionDetailsActivity questionDetailsActivity) {
        return new QuestionChoicesAdapter(new ArrayList<>());
    }

    @Binds
    @IntoMap
    @ViewModelKey(QuestionDetailsActivityViewModel.class)
    abstract ViewModel bindQuestionDetailsActivityViewModel(QuestionDetailsActivityViewModel questionDetailsActivityViewModel);

}
