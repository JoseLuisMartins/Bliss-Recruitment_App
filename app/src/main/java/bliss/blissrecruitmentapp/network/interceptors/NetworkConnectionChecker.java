package bliss.blissrecruitmentapp.network.interceptors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import bliss.blissrecruitmentapp.network.exceptions.NoNetworkException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionChecker implements Interceptor {
    private final Context mContext;

    public NetworkConnectionChecker(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isConnected()) {
            return chain.proceed(chain.request());
        } else {
            throw new NoNetworkException();
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
    }
}
