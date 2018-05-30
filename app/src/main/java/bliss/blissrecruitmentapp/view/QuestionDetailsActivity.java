package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.Toast;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionDetailsBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsViewModel;
import bliss.blissrecruitmentapp.viewmodel.factories.QuestionDetailsViewModelFactory;

public class QuestionDetailsActivity extends AppCompatActivity {
    private QuestionDetailsViewModel mQuestionDetailsViewModel;
    private ActivityQuestionDetailsBinding mBinding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_details);


        // Get Intent Data
        Intent intent = getIntent();
        int question_id = intent.getIntExtra(getString(R.string.question_id),-1);

        Log.d("debug", "id-> " + question_id);

        mQuestionDetailsViewModel = ViewModelProviders.of(this, new QuestionDetailsViewModelFactory(question_id)).get(QuestionDetailsViewModel.class);
        mBinding.setQuestionDetailsViewModel(mQuestionDetailsViewModel);


        // Setup feedback observer
        final Observer<String> feedbackObserver = (@Nullable String feedback) -> {
            Toast.makeText(mContext,feedback,Toast.LENGTH_SHORT).show();
            initAnswersButtons();
        };

        mQuestionDetailsViewModel.getmFeedback().observe(this, feedbackObserver);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.runStartAnimation();
    }

    private void runStartAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_slide_from_bottom);
        animation.reset();

        mBinding.viewQuestionDetailsCard.clearAnimation();
        mBinding.viewQuestionDetailsCard.startAnimation(animation);
    }

    public void submitAnswer(View v) {

        // Get choice
        int checkId = mBinding.viewActivityQuestionDetailsRadioGroup.getCheckedRadioButtonId();
        View checkView = mBinding.viewActivityQuestionDetailsRadioGroup.findViewById(checkId);
        int choiceIndex = mBinding.viewActivityQuestionDetailsRadioGroup.indexOfChild(checkView);

        if(choiceIndex != -1) {
            this.mQuestionDetailsViewModel.submitQuestion(choiceIndex);
        }
    }

    public void shareQuestion(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(getString(R.string.question_filter), mQuestionDetailsViewModel.getAppLink());
        mContext.startActivity(intent);
    }

    public void initAnswersButtons() {
        Question question = this.mQuestionDetailsViewModel.getQuestion().get();

        mBinding.viewActivityQuestionDetailsRadioGroup.removeAllViews();

        for (int i = 0; i < question.getChoices().size(); i++) {
            RadioButton rb  = new RadioButton(this);
            rb.setText(question.getChoices().get(i).getChoice());
            mBinding.viewActivityQuestionDetailsRadioGroup.addView(rb);
        }

    }

}
