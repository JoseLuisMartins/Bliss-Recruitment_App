package bliss.blissrecruitmentapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import bliss.blissrecruitmentapp.di.qualifiers.ApplicationContext;
import bliss.blissrecruitmentapp.di.qualifiers.BaseApiUrl;
import bliss.blissrecruitmentapp.data.api.interceptors.NetworkConnectionChecker;
import bliss.blissrecruitmentapp.utils.Utils;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Provides
    @BaseApiUrl
    public String provideBaseApiUrl(){
        return Utils.API_BASE_URL;
    }


    @Provides
    @Singleton
    HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor();
    }

    @Provides
    @Singleton
    public static NetworkConnectionChecker provideNetworkConnectionChecker(@ApplicationContext Context context) {
        return new NetworkConnectionChecker(context);
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor, NetworkConnectionChecker networkConnectionCheckerInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        okHttpClientBuilder.addInterceptor(networkConnectionCheckerInterceptor);

        return okHttpClientBuilder.build();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofitClient(@BaseApiUrl String baseApiUrl,@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseApiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

}
