package bliss.blissrecruitmentapp.network.interceptors;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;

import bliss.blissrecruitmentapp.view.ui.NoConnectivityActivity;
import okhttp3.Interceptor;
import okhttp3.Response;

import static bliss.blissrecruitmentapp.utils.Utils.isConnected;

public class NetworkConnectionChecker implements Interceptor {
    private Context mContext;

    public NetworkConnectionChecker(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isConnected(mContext)) {
            return chain.proceed(chain.request());
        } else {
            Intent i = new Intent(mContext, NoConnectivityActivity.class);
            mContext.startActivity(i);
            return new Response.Builder()
                    .code(520)
                    .request(chain.request())
                    .build();
        }
    }


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
