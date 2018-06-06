package bliss.blissrecruitmentapp.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import bliss.blissrecruitmentapp.network.RetrofitInstance;
import bliss.blissrecruitmentapp.network.api.ShareClient;
import io.reactivex.Completable;

@Singleton
public class ShareRepository {
    private ShareClient mShareClient;

    @Inject
    public ShareRepository(ShareClient shareClient) {
        this.mShareClient = shareClient;
    }


    public Completable shareApp(String email, String url){
        return mShareClient.share(email, url);
    }
}
