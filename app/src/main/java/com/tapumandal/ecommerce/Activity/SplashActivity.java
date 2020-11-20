package com.tapumandal.ecommerce.Activity;

import android.content.Intent;
import android.os.Handler;

import android.util.Log;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Activity.Product.ProductActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.DiscountTypeCondition;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.databinding.ActivitySplashBinding;

import java.util.ArrayList;
import java.util.List;

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

        setMyCart();
        setMyProfile();
        new Handler().postDelayed(() -> {
            if (MySharedPreference.getBoolean(MySharedPreference.Key.IS_LOGIN)) {
                startActivity(new Intent(context, ProductActivity.class));
            } else {
                startActivity(new Intent(context, ProductActivity.class));
            }
            finish();
        }, 1000);
    }

    private void setMyProfile() {
//        UserProfile userProfile = new UserProfile();
//        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
    }


    private void setMyCart() {
        Log.d("MYCART","ProductActivity setMyCart()");

        Cart myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);
        if(myCart == null) {
            myCart = new Cart();
            Log.d("MYCART","ProductActivity setMyCart myCart IS NULL");
            List<DiscountTypeCondition> discountTypeConditionList = new ArrayList<DiscountTypeCondition>();
            DiscountTypeCondition discountTypeCondition = new DiscountTypeCondition();
            discountTypeCondition.setMinimumAmount(100);
            discountTypeCondition.setDiscountedAmount(50);
            discountTypeConditionList.add(discountTypeCondition);
            myCart.setDiscountTypeCondition(discountTypeConditionList);


            myCart.setProducts(new ArrayList<Product>());

            OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
        }else {
            Log.d("MYCART","ProductActivity setMyCart myCart IS NOT NULL");
        }

    }
}