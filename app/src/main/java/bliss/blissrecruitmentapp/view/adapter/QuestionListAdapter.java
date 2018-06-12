package bliss.blissrecruitmentapp.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.data.api.model.Question;
import bliss.blissrecruitmentapp.databinding.ItemForQuestionListBinding;
import bliss.blissrecruitmentapp.view.ui.QuestionDetailsActivity;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListItemHolder> {

    private List<Question> mQuestions;

    private Context mContext;


    public QuestionListAdapter(List<Question> questions, Context context) {
        this.mContext = context;
        this.mQuestions = questions;
    }

    public void  setQuestions(List<Question> questions) {
        this.mQuestions = questions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

    @NonNull
    @Override
    public QuestionListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemForQuestionListBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_for_question_list,parent,false);

        return new QuestionListItemHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionListItemHolder holder, int position) {
        ItemForQuestionListBinding itemBinding = holder.binding;
        itemBinding.setQuestion(mQuestions.get(position));
        itemBinding.viewItemForQuestionDetailsButton.setOnClickListener((View view) -> {
                    Intent intent = new Intent(mContext, QuestionDetailsActivity.class);
                    intent.putExtra(mContext.getString(R.string.question_id), mQuestions.get(position).getId());
                    mContext.startActivity(intent);
        });
    }



    public class QuestionListItemHolder extends RecyclerView.ViewHolder {

        public ItemForQuestionListBinding binding;

        QuestionListItemHolder(ItemForQuestionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
