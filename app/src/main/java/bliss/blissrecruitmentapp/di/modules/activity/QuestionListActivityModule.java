package bliss.blissrecruitmentapp.di.modules.activity;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import bliss.blissrecruitmentapp.di.modules.activity._base.BaseActivityModule;
import bliss.blissrecruitmentapp.di.qualifiers.ActivityContext;
import bliss.blissrecruitmentapp.di.qualifiers.ViewModelKey;
import bliss.blissrecruitmentapp.view.adapter.QuestionListAdapter;
import bliss.blissrecruitmentapp.view.ui.QuestionListActivity;
import bliss.blissrecruitmentapp.viewmodel.QuestionListActivityViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;

@Module
public abstract class QuestionListActivityModule extends BaseActivityModule<QuestionListActivity> {

    @Provides
    static QuestionListAdapter provideQuestionListAdapter(@ActivityContext Context activityContext) {
        return new QuestionListAdapter(new ArrayList<>(), activityContext);
    }

    @Provides
    static LinearLayoutManager provideLinearLayoutManager(@ActivityContext Context activityContext) {
        return new LinearLayoutManager(activityContext);
    }

    @Binds
    @IntoMap
    @ViewModelKey(QuestionListActivityViewModel.class)
    abstract ViewModel bindQuestionListActivityViewModel(QuestionListActivityViewModel questionDetailsActivityViewModel);

}
