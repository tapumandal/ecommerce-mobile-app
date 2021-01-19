package com.tapumandal.ecommerce.Activity;

import android.content.Intent;
import android.os.Handler;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ApplicationControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivitySplashBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tapumandal on 10/24/2020.
 * For any query ask online.tapu@gmail.com
 */
public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;
    BusinessSettings businessSettings;
    ApplicationControlViewModel viewModel;
    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding =  getBinding();
        viewModel = ViewModelProviders.of(this).get(ApplicationControlViewModel.class);
        getBusinessSettingsFromLive();
//        setBusinessSettings();
        setMyProfile();
//        new Handler().postDelayed(() -> {
//            if (MySharedPreference.getBoolean(MySharedPreference.Key.IS_LOGIN)) {
//                startActivity(new Intent(context, MainActivity.class));
//            } else {
//                startActivity(new Intent(context, MainActivity.class));
//            }
//            finish();
//        }, 1000);
    }

    public void getBusinessSettingsFromLive() {

        if(isNetworkAvailable()) {

            viewModel.getBusinessSettings().observe(this, response -> {
                hideProgressDialog();
                if (response != null) {
                    if (response.isSuccess() && response.getData() != null) {
                        businessSettings = (BusinessSettings) response.getData();

//                    VersionControlModel versionControlModel = getVersionControlModel(businessSettings);
//                    System.out.println("VersionControlModel:"+new Gson().toJson(versionControlModel));
//                    checkAppUpdate(versionControlModel);

                        OfflineCache.saveOffline(OfflineCache.BUSINESS_SETTINGS, businessSettings);
                        Log.d("SPLASH_SCREEN", new Gson().toJson(businessSettings));
                        setMyCart();
                    } else {
//                    showFailedToast(response.getMessage());
                        binding.appOpeningStatusLayout.setVisibility(View.VISIBLE);
                        binding.appOpeningStatus.setText("Something went wrong \nPlease try again later!");
                    }
                } else {
//                    showFailedToast(getString(R.string.something_went_wrong));
                    binding.appOpeningStatusLayout.setVisibility(View.VISIBLE);
                    binding.appOpeningStatus.setText("Something went wrong \nPlease try again later!");
                }
            });
        }else{
            binding.appOpeningStatusLayout.setVisibility(View.VISIBLE);
            binding.appOpeningStatus.setText("Turn on internet !\n and try again!");
        }
    }

    //    All data will be loaded from API
    private void setBusinessSettings() {
        Log.d("BusinessSettings","ProductActivity setBusinessSettings()");

//        businessSettings = (BusinessSettings) OfflineCache.getOfflineSingle(OfflineCache.BUSINESS_SETTINGS);

        if(businessSettings == null) {
            businessSettings = new BusinessSettings();
            Log.d("BusinessSettings","ProductActivity setMyCart businessSettings IS NULL");

            businessSettings.setDeliveryCharge(30);
            businessSettings.setDefaultDiscountBtn("radioOnProduct");


//            SPECIAL DISCOUNT
            List<DiscountTypeCondition> discountTypeConditionList = new ArrayList<DiscountTypeCondition>();
            DiscountTypeCondition discountTypeCondition = new DiscountTypeCondition();
            discountTypeCondition.setMinimumPurchaseLimit(100);
            discountTypeCondition.setDiscountedAmount(50);
            discountTypeCondition.setMaximumDiscountedAmount(150);
            discountTypeConditionList.add(discountTypeCondition);

            discountTypeCondition = new DiscountTypeCondition();
            discountTypeCondition.setMinimumPurchaseLimit(800);
            discountTypeCondition.setDiscountedAmount(50);
            discountTypeCondition.setMaximumDiscountedAmount(350);
            discountTypeConditionList.add(discountTypeCondition);

            businessSettings.setDiscountTypeCondition(discountTypeConditionList);


//            PAYMENT Message
            businessSettings.setPaymentDiscountMessage("Surprising Offer! 50% off on Card Payment. Or 50 taka off on Bkash payment.");

//            CARD PAYMENT
            List<DiscountTypeCondition> cardPaymentDiscountTypeConditionList = new ArrayList<DiscountTypeCondition>();

            DiscountTypeCondition cardPaymentDiscountTypeCondition = new DiscountTypeCondition();
            cardPaymentDiscountTypeCondition.setMinimumPurchaseLimit(100);
            cardPaymentDiscountTypeCondition.setDiscountedAmount(50);
            cardPaymentDiscountTypeCondition.setMaximumDiscountedAmount(200);

            cardPaymentDiscountTypeConditionList.add(cardPaymentDiscountTypeCondition);
            businessSettings.setCardPaymentCondition(cardPaymentDiscountTypeConditionList);
            businessSettings.setCardPaymentDiscountName("Card Payment");
            businessSettings.setCardPaymentDiscountType("TotalPercentage");

//            MOBILE PAYMENT
            List<DiscountTypeCondition> mobilePaymentDiscountTypeConditionList = new ArrayList<DiscountTypeCondition>();

            DiscountTypeCondition mobilePaymentDiscountTypeCondition = new DiscountTypeCondition();
            mobilePaymentDiscountTypeCondition.setMinimumPurchaseLimit(100);
            mobilePaymentDiscountTypeCondition.setDiscountedAmount(50);
            mobilePaymentDiscountTypeCondition.setMaximumDiscountedAmount(240);

            mobilePaymentDiscountTypeConditionList.add(discountTypeCondition);
            businessSettings.setMobilePaymentCondition(mobilePaymentDiscountTypeConditionList);
            businessSettings.setMobilePaymentDiscountName("Mobile Payment");
            businessSettings.setMobilePaymentDiscountType("OverallAmount");

            OfflineCache.saveOffline(OfflineCache.BUSINESS_SETTINGS, businessSettings);
        }else {
            Log.d("BusinessSettings","ProductActivity setMyCart businessSettings IS NOT NULL");
        }
    }

//    Data Load From API
    private void setMyProfile() {
//        UserProfile userProfile = new UserProfile();
//        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
    }

    //    Data Load From API
    private void setMyCart() {
        Log.d("MYCART","ProductActivity setMyCart()");

        Cart myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);
        if(myCart == null) {
            myCart = new Cart();
            myCart.setProducts(new ArrayList<>());
        }

//        myCart.setId(businessSettings.getId());
        myCart.setDeliveryCharge(businessSettings.getDeliveryCharge());

        myCart.setDefaultDiscountBtn(businessSettings.getDefaultDiscountBtn());
//        myCart.setDiscountName(businessSettings.getDiscountName());
//        myCart.setDiscountType(businessSettings.getDiscountType());
//        myCart.setDiscountTypeCondition(businessSettings.getDiscountTypeCondition());
//        myCart.setDiscountBanner(businessSettings.getDiscountBanner());
//        myCart.setPaymentDiscountMessage(businessSettings.getPaymentDiscountMessage());
//        myCart.setPaymentDiscountBanner(businessSettings.getPaymentDiscountBanner());
//        myCart.setCardPaymentDiscountName(businessSettings.getCardPaymentDiscountName());
//        myCart.setCardPaymentDiscountType(businessSettings.getCardPaymentDiscountType());
//        myCart.setCardPaymentCondition(businessSettings.getCardPaymentCondition());
//        myCart.setMobilePaymentDiscountName(businessSettings.getMobilePaymentDiscountName());
//        myCart.setMobilePaymentDiscountType(businessSettings.getMobilePaymentDiscountType());
//        myCart.setMobilePaymentCondition(businessSettings.getMobilePaymentCondition());

        Log.d("SPLASH_SCREEN","BUSINESS_SETTINGS:"+new Gson().toJson(businessSettings));
        Log.d("SPLASH_SCREEN","MY_CART:"+new Gson().toJson(myCart));
        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);

        if (MySharedPreference.getBoolean(MySharedPreference.Key.IS_LOGIN)) {
            startActivity(new Intent(context, MainActivity.class));
        } else {
            startActivity(new Intent(context, MainActivity.class));
        }
    }
}