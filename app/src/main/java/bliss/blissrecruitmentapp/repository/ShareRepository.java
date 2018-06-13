package bliss.blissrecruitmentapp.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import bliss.blissrecruitmentapp.data.api.endpoints.ShareClient;
import io.reactivex.Completable;

@Singleton
public class ShareRepository {
    private final ShareClient mShareClient;

    @Inject
    public ShareRepository(ShareClient shareClient) {
        this.mShareClient = shareClient;
    }


    public Completable shareApp(String email, String url){
        return mShareClient.share(email, url);
    }
}
