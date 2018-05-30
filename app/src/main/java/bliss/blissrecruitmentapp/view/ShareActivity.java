package bliss.blissrecruitmentapp.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.regex.Pattern;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ActivityShareBinding;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.repository.ShareRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static bliss.blissrecruitmentapp.Utils.Utils.EMAIL_REGEX;

public class ShareActivity extends AppCompatActivity {
    private ActivityShareBinding mBinding;
    private Context mContext;
    private ShareRepository mRepository;
    private String shareUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRepository = new ShareRepository();
        mContext=this;

        // for network errors
        RetrofitInstance.setmContext(mContext);

        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_share);

        shareUrl = getIntent().getStringExtra(getString(R.string.share_url));

        mBinding.setUrl(shareUrl);

    }

    public boolean isEmailValid(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

    public void shareLink(View view){
        String email = mBinding.viewActivityShareMailEdittext.getText().toString();

        if(!isEmailValid(email)) {
            Toast.makeText(mContext,mContext.getString(R.string.lbl_share_activity_invalid_email),Toast.LENGTH_SHORT).show();
            return;
        }


        Disposable disposable = mRepository.shareApp(email, shareUrl)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(() -> {
                Toast.makeText(mContext,mContext.getString(R.string.lbl_share_activity_success),Toast.LENGTH_SHORT).show();
            }, throwable -> {
                Toast.makeText(mContext,mContext.getString(R.string.lbl_share_activity_fail),Toast.LENGTH_SHORT).show();
            });
    }
}
