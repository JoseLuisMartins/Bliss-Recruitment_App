package bliss.blissrecruitmentapp.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.view.View;

import java.util.List;

import bliss.blissrecruitmentapp.model.Question;
import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.QuestionClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionListViewModel extends ViewModel{
    // live data to notify activity to update questions
    private MutableLiveData<List<Question>> mQuestions;

    public QuestionListViewModel() {
        this.mQuestions = new MutableLiveData<>();
        getQuestions();
    }

    public MutableLiveData<List<Question>> getmQuestions() {
        if(this.mQuestions == null)
            this.mQuestions = new MutableLiveData<>();
        return mQuestions;
    }

    // make a requester class
    private void getQuestions(){
        QuestionClient questionClient = RetrofitInstance.getRetrofitInstance().create(QuestionClient.class);
        Call<List<Question>> call = questionClient.getQuestions(5,0);

        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                mQuestions.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {

            }
        });

    }


    public View.OnClickListener search() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }
}
