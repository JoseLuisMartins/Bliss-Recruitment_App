package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionDetailsBinding;
import bliss.blissrecruitmentapp.viewmodel.QuestionDetailsViewModel;

public class QuestionDetails extends AppCompatActivity {
    private QuestionDetailsViewModel mQuestionDetailsViewModel;
    private ActivityQuestionDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_details);

        mQuestionDetailsViewModel = ViewModelProviders.of(this).get(QuestionDetailsViewModel.class);



        // Get Intent Data
        Intent intent = getIntent();
        int question_id = intent.getIntExtra(getString(R.string.question_id),-1);

        Log.d("debug", "id-> " + question_id);


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

}
