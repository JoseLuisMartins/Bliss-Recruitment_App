package bliss.blissrecruitmentapp.network.repository;

import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.ShareClient;
import io.reactivex.Completable;

public class ShareRepository {
    private ShareClient mShareClient;

    public ShareRepository() {
        this.mShareClient = RetrofitInstance.getRetrofitInstance().create(ShareClient.class);
    }
    // Update question
    private Completable shareQuestion(String email, String url){
        return mShareClient.share(email, url);
    }
}
