package bliss.blissrecruitmentapp.view.QuestionDetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.data.api.model.Question;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionDetailsBinding;
import bliss.blissrecruitmentapp.di.qualifiers.QuestionId;
import bliss.blissrecruitmentapp.utils.Utils;
import bliss.blissrecruitmentapp.view.Share.ShareActivity;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

public class QuestionDetailsActivity extends DaggerAppCompatActivity {
    private QuestionDetailsActivityViewModel mQuestionDetailsActivityViewModel;
    private ActivityQuestionDetailsBinding mBinding;
    private Context mContext;


    private int mQuestionId;

    @Inject
    QuestionChoicesAdapter questionChoicesAdapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Inject
    Lazy<ViewModelProvider.Factory> viewModelFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_details);
        mBinding.setLifecycleOwner(this);

        mQuestionId = getIntent().getIntExtra(getString(R.string.question_id),-1);


        if(mQuestionId == -1) {
            Toast.makeText(mContext, getString(R.string.error_question_details_activity_no_question), Toast.LENGTH_SHORT).show();
            return;
        }


        mQuestionDetailsActivityViewModel = ViewModelProviders.of(this, viewModelFactory.get()).get(QuestionDetailsActivityViewModel.class);
        mBinding.setLifecycleOwner(this);
        mBinding.setQuestionDetailsActivityViewModel(mQuestionDetailsActivityViewModel);


        // Init recycler view
        mBinding.activityQuestionDetailsQuestionRecyclerView.setAdapter(questionChoicesAdapter);
        mBinding.activityQuestionDetailsQuestionRecyclerView.setLayoutManager(linearLayoutManager);
        mBinding.activityQuestionDetailsQuestionRecyclerView.setNestedScrollingEnabled(false);


        // Update question choices
        mQuestionDetailsActivityViewModel.getQuestion().observe(this, (@Nullable Question question) -> {
            if(question != null) {
                questionChoicesAdapter.setChoices(question.getChoices());
            }
        });


        // Show feedback when question is updated
        mQuestionDetailsActivityViewModel.getUpdatedSuccessfully().observe(this,  (@Nullable Boolean success) -> {
            if(success != null) {
                String msg = success ? getString(R.string.error_activity_question_update_success) : getString(R.string.lbl_activity_question_update_error);
                Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.runViewAnimation(mContext, mBinding.getRoot(),R.anim.item_animation_slide_from_bottom);
        mQuestionDetailsActivityViewModel.loadQuestion();
    }

    public void submitAnswer(View v) {
        this.mQuestionDetailsActivityViewModel.submitQuestion(questionChoicesAdapter.getLastSelectedPosition());
    }


    public void shareQuestion(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(getString(R.string.share_url), mQuestionDetailsActivityViewModel.getAppLink());
        mContext.startActivity(intent);
    }

    @QuestionId
    public int getQuestionId() {
        return mQuestionId;
    }
}
