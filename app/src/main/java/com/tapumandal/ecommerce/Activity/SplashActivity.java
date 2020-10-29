package com.tapumandal.ecommerce.Activity;

import android.content.Intent;
import android.os.Handler;

import com.tapumandal.ecommerce.Activity.Product.ProductActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.databinding.ActivitySplashBinding;

/**
 * Created by tapumandal on 10/24/2020.
 * For any query ask online.tapu@gmail.com
 */
public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        context = this;

        binding =  getBinding();



        new Handler().postDelayed(() -> {
            if (MySharedPreference.getBoolean(MySharedPreference.Key.IS_LOGIN)) {
                startActivity(new Intent(context, ProductActivity.class));
            } else {
                startActivity(new Intent(context, ProductActivity.class));
            }
            finish();
        }, 1000);
    }
}