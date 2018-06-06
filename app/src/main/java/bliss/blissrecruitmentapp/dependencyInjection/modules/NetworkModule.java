package bliss.blissrecruitmentapp.dependencyInjection.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import bliss.blissrecruitmentapp.dependencyInjection.components.ApplicationComponent;
import bliss.blissrecruitmentapp.network.interceptors.NetworkConnectionChecker;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static bliss.blissrecruitmentapp.utils.Utils.API_BASE_URL;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    NetworkConnectionChecker provideNetworkConnectionChecker(@NonNull Context context) {
        return new NetworkConnectionChecker(context);
    }


    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(NetworkConnectionChecker networkConnectionCheckerInterceptor) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        okHttpClientBuilder.addInterceptor(networkConnectionCheckerInterceptor);

        return okHttpClientBuilder.build();
    }



    @Provides
    @Singleton
    Retrofit provideRetrofitClient(@NonNull OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


}
