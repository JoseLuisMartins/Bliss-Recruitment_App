package bliss.blissrecruitmentapp.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.adapter.QuestionListAdapter;
import bliss.blissrecruitmentapp.databinding.ActivityQuestionListBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.viewmodel.QuestionListViewModel;


public class QuestionListActivity extends AppCompatActivity {
    private Context mContext;
    private ActivityQuestionListBinding mBinding;
    private QuestionListViewModel mQuestionListViewModel;
    // recycler adapter
    private QuestionListAdapter mQuestionListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Context
        mContext = this;

        // data binding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_question_list);

        mQuestionListViewModel = ViewModelProviders.of(this).get(QuestionListViewModel.class);
        mBinding.setQuestionListViewModel(mQuestionListViewModel);


        //Create Recycler View Adapter
        mQuestionListAdapter = new QuestionListAdapter(new ArrayList<>(), mContext);
        // Set Recycler View Adapter
        mBinding.viewActivityQuestionListRecycler.setAdapter(mQuestionListAdapter );
        mBinding.viewActivityQuestionListRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.viewActivityQuestionListRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));



        //Observer the questions data changes ----------------------

        final Observer<List<Question>> questionsObserver = (@Nullable List<Question> questions) -> {
                if(questions != null) {
                    mQuestionListAdapter.setQuestions(questions);
                    //test animation
                    LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(mContext, R.anim.layout_slide_from_right);
                    mBinding.viewActivityQuestionListRecycler.setLayoutAnimation(animationController);
                    mQuestionListAdapter.notifyDataSetChanged();
                    mBinding.viewActivityQuestionListRecycler.scheduleLayoutAnimation();
                }
            };

        mQuestionListViewModel.getmQuestions().observe(this, questionsObserver);


        mBinding.viewActivityQuestionListRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        if (mQuestionListViewModel.checkHasMore()) {
                            mQuestionListViewModel.loadNextQuestions();
                        }
                    }

                }
            }
        });
        //-------------------------------

        // handle search

        mBinding.viewActivityQuestionListSearch.setActivated(true);
        mBinding.viewActivityQuestionListSearch.setQueryHint(getString(R.string.lbl_question_activity_search));
        mBinding.viewActivityQuestionListSearch.onActionViewExpanded();
        mBinding.viewActivityQuestionListSearch.setIconified(false);
        mBinding.viewActivityQuestionListSearch.clearFocus();

        mBinding.viewActivityQuestionListSearch.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("debug","on close");
                mQuestionListViewModel.leaveSearchMode();
                return false;
            }
        });


        mBinding.viewActivityQuestionListSearch.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuestionListViewModel.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        //deep link
        Intent intent = getIntent();
        Uri data = intent.getData();
        if(data != null) {

            String filter = data.getQueryParameter("question_filter");

            if(filter != null) {
                // set query and submit
                mBinding.viewActivityQuestionListSearch.setQuery(filter, true);
                // expand search
                mBinding.viewActivityQuestionListSearch.setIconified(false);
            }
        }
    }

    public void shareSearch(View v) {
        Intent intent = new Intent(mContext, ShareActivity.class);
        mContext.startActivity(intent);
    }

}
