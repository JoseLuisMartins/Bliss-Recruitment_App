package bliss.blissrecruitmentapp.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import bliss.blissrecruitmentapp.network.interceptors.NetworkConnectionChecker;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static bliss.blissrecruitmentapp.utils.Utils.API_BASE_URL;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static Context mContext;
    private static NetworkConnectionChecker mNetworkConnectionChecker;

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            mNetworkConnectionChecker = new NetworkConnectionChecker(mContext);

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            okHttpClientBuilder.addInterceptor(mNetworkConnectionChecker);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClientBuilder.build());

            retrofit = builder.build();
        }

        return retrofit;
    }

    public static void setmContext(Context mContext) {
        RetrofitInstance.mContext = mContext;

        if(mNetworkConnectionChecker != null)
            mNetworkConnectionChecker.setContext(mContext);

    }
}
