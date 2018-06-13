package bliss.blissrecruitmentapp.view.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.data.api.model.Question;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionListBinding;
import bliss.blissrecruitmentapp.utils.Utils;
import bliss.blissrecruitmentapp.view.adapter.QuestionListAdapter;
import bliss.blissrecruitmentapp.viewmodel.QuestionListActivityViewModel;
import dagger.android.support.DaggerAppCompatActivity;


public class QuestionListActivity extends DaggerAppCompatActivity {
    private Context mContext;
    private ActivityQuestionListBinding mBinding;
    private QuestionListActivityViewModel mQuestionListActivityViewModel;

    @Inject
    LinearLayoutManager mQuestionListLayoutManager;

    @Inject
    QuestionListAdapter mQuestionListAdapter;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;


        Uri data = getIntent().getData();
        Set<String> parameterNames;

        if(data != null)
            parameterNames = data.getQueryParameterNames();
        else
            parameterNames = new HashSet<>();


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_list);
        mBinding.setLifecycleOwner(this);

        mQuestionListActivityViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuestionListActivityViewModel.class);
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

        Boolean loading = mQuestionListActivityViewModel.getLoading().getValue();

        if(loading != null && !loading && mQuestionListActivityViewModel.getQuestions().getValue() == null) {
            mQuestionListActivityViewModel.loadQuestions();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void shareSearch(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        intent.putExtra(getString(R.string.share_url), mQuestionListActivityViewModel.getAppLink());
        mContext.startActivity(intent);
    }

    private void initQuestionList(){
        RecyclerView questionsRecyclerView = mBinding.viewActivityQuestionListRecycler;
        questionsRecyclerView.setAdapter(mQuestionListAdapter);
        questionsRecyclerView.setLayoutManager(mQuestionListLayoutManager);


        //Observe the questions data changes
        mQuestionListActivityViewModel.getQuestions().observe(this, (@Nullable List<Question> questions) -> {
            if(questions != null) {
                mQuestionListAdapter.setQuestions(questions);
            }
        });

        //Observe searching mode
        mQuestionListActivityViewModel.getSearching().observe(this, (@Nullable Boolean searchMode) -> mQuestionListAdapter.resetAdapter());

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


}
