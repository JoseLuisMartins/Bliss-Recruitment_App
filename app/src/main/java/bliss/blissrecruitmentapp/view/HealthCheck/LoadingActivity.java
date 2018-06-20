package bliss.blissrecruitmentapp.view.HealthCheck;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityLoadingBinding;
import bliss.blissrecruitmentapp.view.QuestionList.QuestionListActivity;
import dagger.android.support.DaggerAppCompatActivity;

public class LoadingActivity extends DaggerAppCompatActivity{

    private LoadingActivityViewModel mLoadingViewModel;
    private Context mContext;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;


        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);
        binding.setLifecycleOwner(this);

        mLoadingViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoadingActivityViewModel.class);
        binding.setLoadingViewModel(mLoadingViewModel);

        final Observer<Boolean> successObserver = (@Nullable Boolean success) -> {
                if(success != null && success){
                   Intent intent = new Intent(mContext, QuestionListActivity.class);
                   startActivity(intent);
                   finish();
                }
            };

        mLoadingViewModel.getmIsServiceAvailable().observe(this, successObserver);
    }

    public void refresh(View v){
        mLoadingViewModel.checkHealth();
    }


}
