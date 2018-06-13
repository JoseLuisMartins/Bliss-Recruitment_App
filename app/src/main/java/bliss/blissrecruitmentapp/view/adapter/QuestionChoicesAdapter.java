package bliss.blissrecruitmentapp.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bliss.blissrecruitmentapp.R;
import bliss.blissrecruitmentapp.data.api.model.Choice;
import bliss.blissrecruitmentapp.databinding.ItemForQuestionDetailsBinding;

public class QuestionChoicesAdapter extends RecyclerView.Adapter<QuestionChoicesAdapter.QuestionChoiceItemHolder> {

    private List<Choice> mChoices;
    private int mLastSelectedPosition;

    public QuestionChoicesAdapter(List<Choice> choices) {
        this.mChoices = choices;
    }

    public void setChoices(List<Choice> choices) {
        this.mChoices = choices;
        notifyDataSetChanged();
    }

    public int getLastSelectedPosition() {
        return mLastSelectedPosition;
    }

    @NonNull
    @Override
    public QuestionChoicesAdapter.QuestionChoiceItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemForQuestionDetailsBinding itemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_for_question_details,parent,false);

        return new QuestionChoicesAdapter.QuestionChoiceItemHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionChoicesAdapter.QuestionChoiceItemHolder  holder, int position) {
        ItemForQuestionDetailsBinding itemBinding = holder.binding;
        itemBinding.setChoice(mChoices.get(position));

        itemBinding.viewItemForQuestionDetailsRadioButton.setChecked(position == mLastSelectedPosition);
    }

    @Override
    public int getItemCount() {
        return mChoices.size();
    }

    public class QuestionChoiceItemHolder extends RecyclerView.ViewHolder {

        public ItemForQuestionDetailsBinding binding;

        QuestionChoiceItemHolder(ItemForQuestionDetailsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            this.binding.viewItemForQuestionDetailsRadioButton.setOnClickListener((View view) -> {
                mLastSelectedPosition = getAdapterPosition();
                notifyDataSetChanged();
            });
        }
    }

}
