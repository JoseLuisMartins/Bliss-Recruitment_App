package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityLoadingBinding;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsViewModel;

public class QuestionDetails extends AppCompatActivity {
    private QuestionDetailsViewModel mQuestionDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // data binding
        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_question_details);

        mQuestionDetailsViewModel = ViewModelProviders.of(this).get(QuestionDetailsViewModel.class);

        Intent intent = getIntent();
        int question_id = intent.getIntExtra(getString(R.string.question_id),-1);

        Log.d("debug", "id-> " + question_id);

    }

}
