package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityLoadingBinding;
import bliss.blissrecruitmentapp.viewmodel.LoadingActivityViewModel;

public class LoadingActivity extends AppCompatActivity {

    private LoadingActivityViewModel mLoadingViewModel;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        // data binding
        ActivityLoadingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_loading);

        mLoadingViewModel = ViewModelProviders.of(this).get(LoadingActivityViewModel.class);
        binding.setLoadingViewModel(mLoadingViewModel);

        final Observer<Boolean> successObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                   Intent intent = new Intent(mContext, QuestionList.class);
                   startActivity(intent);
                }

            }
        };

        mLoadingViewModel.getmServiceAvailable().observe(this, successObserver);
    }


}
