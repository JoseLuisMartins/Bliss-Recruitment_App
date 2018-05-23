package bliss.blissrecruitmentapp.adapter;


import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import java.util.List;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.databinding.ItemForQuestionListBinding;
import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.viewmodel.ItemViewModel;

public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionListItemHolder> {

    List<Question> questions;

    public QuestionListAdapter(List<Question> questions) {
        this.questions = questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        notifyDataSetChanged();
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
        itemBinding.setQuestion(questions.get(position));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class QuestionListItemHolder extends RecyclerView.ViewHolder {

        private ItemForQuestionListBinding binding;

        public QuestionListItemHolder(ItemForQuestionListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
