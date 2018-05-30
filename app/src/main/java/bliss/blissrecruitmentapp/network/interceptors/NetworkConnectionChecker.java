package bliss.blissrecruitmentapp.network.interceptors;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import bliss.blissrecruitmentapp.network.exceptions.NoNetworkException;
import bliss.blissrecruitmentapp.view.NoConnectivityActivity;
import okhttp3.Interceptor;
import okhttp3.Response;

import static bliss.blissrecruitmentapp.Utils.Utils.isConnected;

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
            //throw new NoNetworkException();
            return new Response.Builder()
                    .code(520) // No error
                    .request(chain.request())
                    .build();
        }
    }


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
