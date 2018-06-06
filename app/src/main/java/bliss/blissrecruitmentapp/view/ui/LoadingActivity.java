package bliss.blissrecruitmentapp.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityLoadingBinding;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.viewmodel.LoadingActivityViewModel;

public class LoadingActivity extends AppCompatActivity {

    private LoadingActivityViewModel mLoadingViewModel;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        // for network errors
        RetrofitInstance.setContext(mContext);

        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);

        mLoadingViewModel = ViewModelProviders.of(this).get(LoadingActivityViewModel.class);
        binding.setLoadingViewModel(mLoadingViewModel);

        final Observer<Boolean> successObserver = (@Nullable Boolean success) -> {
                if(success){
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
