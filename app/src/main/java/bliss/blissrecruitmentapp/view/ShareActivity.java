package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.utils.Utils;
import bliss.blissrecruitmentapp.databinding.ActivityShareBinding;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.viewmodel.ShareActivityViewModel;
import bliss.blissrecruitmentapp.viewmodel.factories.ShareActivityViewModelFactory;

import static bliss.blissrecruitmentapp.utils.Utils.EMAIL_REGEX;

public class ShareActivity extends AppCompatActivity {
    private ActivityShareBinding mBinding;
    private Context mContext;
    private ShareActivityViewModel mShareActivityViewModel;
    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;

        // for network errors
        RetrofitInstance.setmContext(mContext);

        shareUrl = getIntent().getStringExtra(getString(R.string.share_url));

        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);
        mShareActivityViewModel = ViewModelProviders.of(this, new ShareActivityViewModelFactory(shareUrl)).get(ShareActivityViewModel.class);

        mBinding.setShareActivityViewModel(mShareActivityViewModel);

        //Observe the questions data changes ----------------------
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

    public boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public void shareLink(View view){

        String email = mBinding.activityShareMailEditText.getText().toString();

        if(!isEmailValid(email)) {
            Toast.makeText(mContext,mContext.getString(R.string.lbl_share_activity_invalid_email),Toast.LENGTH_SHORT).show();
            return;
        }

        mShareActivityViewModel.shareContent(email);
        Utils.hideFocusKeyboard(mContext, mBinding.activityShareMailEditText);
    }
}
