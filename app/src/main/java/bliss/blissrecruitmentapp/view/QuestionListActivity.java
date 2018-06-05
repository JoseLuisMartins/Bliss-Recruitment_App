package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.Utils.Utils;
import bliss.blissrecruitmentapp.adapter.QuestionListAdapter;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionListBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.viewmodel.QuestionListActivityViewModel;


public class QuestionListActivity extends AppCompatActivity {
    private Context mContext;
    private ActivityQuestionListBinding mBinding;
    private QuestionListActivityViewModel mQuestionListActivityViewModel;
    // recycler adapter
    private QuestionListAdapter mQuestionListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Context
        mContext = this;

        // for network errors
        RetrofitInstance.setmContext(mContext);

        //handle deep linking
        Uri data = getIntent().getData();
        Set<String> parameterNames;

        if(data != null)
            parameterNames = data.getQueryParameterNames();
        else
            parameterNames = new HashSet<>();



        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_list);

        mQuestionListActivityViewModel = ViewModelProviders.of(this).get(QuestionListActivityViewModel.class);
        mBinding.setQuestionListActivityViewModel(mQuestionListActivityViewModel);

        this.initQuestionList();
        this.initSearch();

        if (parameterNames.contains(Utils.APP_FILTER_PARAM)) {
            mBinding.viewActivityQuestionListSearch.setQuery(data.getQueryParameter(Utils.APP_FILTER_PARAM),true);

            if(data.getQueryParameter(Utils.APP_FILTER_PARAM).length() == 0)
                mBinding.viewActivityQuestionListSearch.setIconified(false);

        } else if(parameterNames.contains(Utils.APP_QUESTION_PARAM)) {
            Intent intent = new Intent(mContext, QuestionDetailsActivity.class);
            intent.putExtra(mContext.getString(R.string.question_id), Integer.parseInt(data.getQueryParameter(Utils.APP_QUESTION_PARAM)));
            mContext.startActivity(intent);

        } else {
            mQuestionListActivityViewModel.loadQuestions();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mQuestionListActivityViewModel.getLoading().get() && (mQuestionListActivityViewModel.getmQuestions().getValue() == null))
            mQuestionListActivityViewModel.loadQuestions();
    }

    private void initQuestionList(){
        mQuestionListAdapter = new QuestionListAdapter(new ArrayList<>(), mContext);
        RecyclerView questionsRecyclerView = mBinding.viewActivityQuestionListRecycler;
        questionsRecyclerView.setAdapter(mQuestionListAdapter );
        questionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Observe the questions data changes ----------------------
        mQuestionListActivityViewModel.getmQuestions().observe(this, (@Nullable List<Question> questions) -> {
            if(questions != null) {
                mQuestionListAdapter.setQuestions(questions);
                // Animation
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_slide_from_right);
                questionsRecyclerView .setLayoutAnimation(animationController);
                mQuestionListAdapter.notifyDataSetChanged();
                questionsRecyclerView .scheduleLayoutAnimation();
            }
        });

        questionsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        if (mQuestionListActivityViewModel.checkHasMore()) {
                            mQuestionListActivityViewModel.loadNextQuestions();
                        }
                    }

                }
            }
        });
    }

    private void initSearch(){
        SearchView searchView = mBinding.viewActivityQuestionListSearch;
        searchView.setQueryHint(getString(R.string.lbl_question_activity_search));
        searchView.onActionViewExpanded();
        searchView.clearFocus();

        //Handle search mode exit
        searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn).setOnClickListener((View v) -> {
            mQuestionListActivityViewModel.leaveSearchMode();
            searchView.setIconified(true);
            searchView.clearFocus();
        });

        //Handle search submission
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuestionListActivityViewModel.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void shareSearch(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(getString(R.string.share_url), mQuestionListActivityViewModel.getAppLink());
        mContext.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }
}
