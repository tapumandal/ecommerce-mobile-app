package com.tapumandal.ecommerce.Utility;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tapumandal.ecommerce.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static void initRetrofit() {

        final String tokenId = MySharedPreference.getUserToken();
        final String id = MySharedPreference.getUserId();
        final Integer versionCode = BuildConfig.VERSION_CODE;


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient.addInterceptor(logging);

        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.connectTimeout(30, TimeUnit.SECONDS);

        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .addHeader("accept", "application/json")
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", tokenId)
                        .addHeader("appVersion", String.valueOf(versionCode))
                        .addHeader("id", id).build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
//                .excludeFieldsWithoutExposeAnnotation()
                .create();


        retrofit = new Retrofit.Builder()
                .baseUrl(URLs.ROOT_URL_MAIN)
                .client(okHttpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static APIInterface getApiClient() {
        return retrofit.create(APIInterface.class);
    }


}
