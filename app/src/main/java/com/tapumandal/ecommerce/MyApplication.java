package com.tapumandal.ecommerce;

import android.app.Application;

import com.tapumandal.ecommerce.Utility.ApiClient;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;

/**
 * Created by TapuMandal on 10/25/2020.
 * For any query ask online.tapu@gmail.com
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySharedPreference.initSharedPref(getApplicationContext());
        ApiClient.initRetrofit();
        OfflineCache.initOfflineCache(getApplicationContext());
    }
}
