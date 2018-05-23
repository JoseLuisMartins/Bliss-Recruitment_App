package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import bliss.blissrecruitmentapp.model.Question;

public class ItemViewModel extends ViewModel {
    private Question question;


    public ItemViewModel(Question question) {
        this.question = question;
        Log.d("debug", "Item View Model ->" + question.toString());
    }

    //V go to details


}
