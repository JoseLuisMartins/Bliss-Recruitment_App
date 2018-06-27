package bliss.blissrecruitmentapp.view.Share;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityShareBinding;
import bliss.blissrecruitmentapp.di.qualifiers.ShareUrl;
import bliss.blissrecruitmentapp.utils.Utils;
import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

import static bliss.blissrecruitmentapp.utils.Utils.isEmailValid;


public class ShareActivity extends DaggerAppCompatActivity {
    private ActivityShareBinding mBinding;
    private Context mContext;
    private ShareActivityViewModel mShareActivityViewModel;
    private String mShareUrl;

    @Inject
    Lazy<ViewModelProvider.Factory> viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        mShareUrl = getIntent().getStringExtra(getString(R.string.share_url));


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        mBinding.setLifecycleOwner(this);

        mShareActivityViewModel = ViewModelProviders.of(this, viewModelFactory.get()).get(ShareActivityViewModel.class);
        mBinding.setShareActivityViewModel(mShareActivityViewModel);


        //Get share action response
        mShareActivityViewModel.getSuccessResponse().observe(this, (@Nullable Boolean success) -> {
            if(success != null){
                if(success) {
                    Toast.makeText(mContext,getString(R.string.lbl_share_activity_success) ,Toast.LENGTH_SHORT).show();
                    mBinding.activityShareMailEditText.setText("");
                }else {
                    Toast.makeText(mContext,getString(R.string.error_share_activity_fail) ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.runViewAnimation(mContext, mBinding.getRoot(),R.anim.item_animation_slide_from_bottom);
    }


    public void shareLink(View view){

        String email = mShareActivityViewModel.getEmail();

        if(!isEmailValid(email)) {
            mBinding.activityShareMailEditText.setError(mContext.getString(R.string.lbl_share_activity_invalid_email));
            return;
        }

        mBinding.activityShareMailEditText.setError(null);
        mShareActivityViewModel.shareContent();
        Utils.hideFocusKeyboard(mContext, mBinding.activityShareMailEditText);
    }

    @ShareUrl
    public String getShareUrl() {
        return mShareUrl;
    }
}
