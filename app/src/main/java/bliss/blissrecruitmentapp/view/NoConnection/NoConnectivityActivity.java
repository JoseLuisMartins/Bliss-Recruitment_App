package bliss.blissrecruitmentapp.view.NoConnection;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import bliss.blissrecruitmentapp.R;
import dagger.android.support.DaggerAppCompatActivity;

import static bliss.blissrecruitmentapp.utils.Utils.isConnected;

public class NoConnectivityActivity extends DaggerAppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connectivity);

        mContext= this;
    }

    public void retryConnection(View v){
        if(isConnected(mContext)) {
            finish();
        }else {
            Toast.makeText(mContext, getString(R.string.lbl_no_connectivity_activity_error), Toast.LENGTH_SHORT).show();
        }
    }

}
