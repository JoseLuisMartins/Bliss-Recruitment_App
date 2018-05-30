package bliss.blissrecruitmentapp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import bliss.blissrecruitmentapp.R;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        String shareLink = getIntent().getStringExtra(getString(R.string.question_filter));
        Log.d("debug","link-> " + shareLink);

    }
}
