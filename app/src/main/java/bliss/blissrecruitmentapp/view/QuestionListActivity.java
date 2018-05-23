package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.adapter.QuestionListAdapter;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionListBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.viewmodel.QuestionListViewModel;

public class QuestionListActivity extends AppCompatActivity {

    private QuestionListViewModel mQuestionListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // data binding
        ActivityQuestionListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_question_list);

        mQuestionListViewModel = ViewModelProviders.of(this).get(QuestionListViewModel.class);
        binding.setQuestionListViewModel(mQuestionListViewModel);


        //Create Recycler View Adapter
        final QuestionListAdapter questionListAdapter = new QuestionListAdapter(new ArrayList<Question>());
        // Set Recycler View Adapter
        binding.viewActivityQuestionListRecycler.setAdapter(questionListAdapter);
        binding.viewActivityQuestionListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.viewActivityQuestionListRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        //Observer the questions data changes
        final Observer<List<Question>> questionsObserver = new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                questionListAdapter.setQuestions(questions);
            }
        };

        mQuestionListViewModel.getmQuestions().observe(this, questionsObserver);

    }

}
