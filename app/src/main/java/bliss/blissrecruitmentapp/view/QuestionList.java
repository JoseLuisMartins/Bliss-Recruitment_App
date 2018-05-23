package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionListBinding;
import bliss.blissrecruitmentapp.viewmodel.QuestionListViewModel;

public class QuestionList extends AppCompatActivity {

    private QuestionListViewModel mQuestionListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // data binding
        ActivityQuestionListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_question_list);


        mQuestionListViewModel = ViewModelProviders.of(this).get(QuestionListViewModel.class);
        binding.setQuestionListViewModel(mQuestionListViewModel);
    }
}
