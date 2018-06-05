package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionDetailsBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsActivityViewModel;
import bliss.blissrecruitmentapp.viewmodel.factories.QuestionDetailsViewModelFactory;

public class QuestionDetailsActivity extends AppCompatActivity {
    private QuestionDetailsActivityViewModel mQuestionDetailsActivityViewModel;
    private ActivityQuestionDetailsBinding mBinding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        // for network errors
        RetrofitInstance.setmContext(mContext);

        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_details);


        // Get Intent Data
        int question_id = getIntent().getIntExtra(getString(R.string.question_id),-1);

        if(question_id == -1) {
            Toast.makeText(mContext, getString(R.string.error_question_details_activity_no_question), Toast.LENGTH_SHORT).show();
            return;
        }

        mQuestionDetailsActivityViewModel = ViewModelProviders.of(this, new QuestionDetailsViewModelFactory(question_id)).get(QuestionDetailsActivityViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setQuestionDetailsActivityViewModel(mQuestionDetailsActivityViewModel);




        // Init answers when question loaded
        mQuestionDetailsActivityViewModel.getmQuestionLoaded().observe(this, (@Nullable Boolean success) -> {
            if(success != null && success) {
                initAnswersButtons();
            }
        });


        // Show feedback when question updated
        mQuestionDetailsActivityViewModel.getUpdatedSuccessfully().observe(this,  (@Nullable Boolean success) -> {
            if(success != null) {
                String msg = success ? getString(R.string.error_activity_question_update_success) : getString(R.string.lbl_activity_question_update_error);
                Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                initAnswersButtons();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.runStartAnimation();
        mQuestionDetailsActivityViewModel.loadQuestion();
    }

    private void runStartAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_slide_from_bottom);
        animation.reset();

        mBinding.viewQuestionDetailsCard.clearAnimation();
        mBinding.viewQuestionDetailsCard.startAnimation(animation);
    }

    public void submitAnswer(View v) {

        // Get choice
        int checkId = mBinding.activityQuestionDetailsRadioGroup.getCheckedRadioButtonId();
        View checkView = mBinding.activityQuestionDetailsRadioGroup.findViewById(checkId);
        int choiceIndex = mBinding.activityQuestionDetailsRadioGroup.indexOfChild(checkView);

        // nothing selected
        if(choiceIndex == -1) {
            Toast.makeText(mContext, getString(R.string.error_question_details_activity_no_answer), Toast.LENGTH_SHORT).show();
            return;
        }

        // divide by two because of the text view associated with each radio button
        choiceIndex /=2;
        this.mQuestionDetailsActivityViewModel.submitQuestion(choiceIndex);

    }


    public void initAnswersButtons() {
        Question question = this.mQuestionDetailsActivityViewModel.getQuestion().get();

        if(question == null)
            return;

        mBinding.activityQuestionDetailsRadioGroup.removeAllViews();

        for (int i = 0; i < question.getChoices().size(); i++) {
            RadioButton rb  = new RadioButton(this);
            rb.setText(question.getChoices().get(i).getChoice());
            mBinding.activityQuestionDetailsRadioGroup.addView(rb);

            TextView tv = new TextView(mContext);
            tv.setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.END);
            tv.setText(String.format("Votes: %d", question.getChoices().get(i).getVotes()));
            mBinding.activityQuestionDetailsRadioGroup.addView(tv);
        }
    }

    public void shareQuestion(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(getString(R.string.share_url), mQuestionDetailsActivityViewModel.getAppLink());
        mContext.startActivity(intent);
    }


}
