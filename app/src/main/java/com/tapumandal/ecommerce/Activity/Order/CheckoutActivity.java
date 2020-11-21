package com.tapumandal.ecommerce.Activity.Order;

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.DiscountTypeCondition;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityCheckoutBinding;

import java.util.List;

/**
 * Created by tapumandal on 11/18/2020.
 * For any query ask online.tapu@gmail.com
 */

public class CheckoutActivity extends BaseActivity {

    ActivityCheckoutBinding b;
    ProductControlViewModel viewModel;

    private UserProfile userProfile;
    private Cart myCart;

    int paymentDiscount = 0;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_checkout;
    }

    @Override
    protected void initComponent() {
        context = this;
        b = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        setToolbar("Checkout", true);

        userProfile = OfflineCache.getOfflineSingle(OfflineCache.MY_PROFILE);
        myCart = OfflineCache.getOfflineSingle(OfflineCache.MY_CART);
        
        if(myCart != null) {
            setCartDetails();
        }

        addressLayout();
        initAreaSpinner();
        clickEvent();
    }

    private void setCartDetails() {
        b.subTotal.setText(String.valueOf(myCart.getTotalProductPrice()));
        b.shipping.setText(String.valueOf(myCart.getDeliveryCharge()));

        b.totalDiscount.setText(String.valueOf(myCart.getTotalDiscount()));

        myCart.setTotalPayable( (myCart.getTotalProductPrice() - myCart.getTotalDiscount()) + myCart.getDeliveryCharge() );
        b.totalPayable.setText(String.valueOf(myCart.getTotalPayable()));

        if(myCart.getPaymentDiscountMessage() != "") {
            b.paymentDiscountMessageLayout.setVisibility(View.VISIBLE);
            b.paymentDiscountMessage.setText(myCart.getPaymentDiscountMessage());
        }
    }

    private void clickEvent() {
    }

    public void radioOnDelivery(View view){
        b.radioOnDelivery.setChecked(true);

        b.radioCardPayment.setChecked(false);
        b.radioMobilePayment.setChecked(false);
        Toast.makeText(context, "radioOnDelivery", Toast.LENGTH_SHORT).show();
    }
    public void radioCardPayment(View view){
        b.radioCardPayment.setChecked(true);

        b.radioOnDelivery.setChecked(false);
        b.radioMobilePayment.setChecked(false);

        Toast.makeText(context, "radioMobilePayment", Toast.LENGTH_SHORT).show();

        List<DiscountTypeCondition> cardDiscountCondition = myCart.getCardPaymentCondition();
        int calculativeAmount = 0;
        int maximumDiscountedAmount = 0;
        Log.d("CHECKOUT_DISCOUNT", "cardDiscountCondition:"+new Gson().toJson(cardDiscountCondition));
        if(cardDiscountCondition != null) {
            for (int i = 0; i < cardDiscountCondition.size(); i++) {
                if (cardDiscountCondition.get(i).getMinimumPurchaseLimit() < myCart.getTotalProductPrice()) {
                    calculativeAmount = cardDiscountCondition.get(i).getDiscountedAmount();
                    maximumDiscountedAmount = cardDiscountCondition.get(i).getMaximumDiscountedAmount();
                }
            }
        }
        Log.d("CHECKOUT_DISCOUNT", "calculativeAmount:"+calculativeAmount+" maximumDiscountedAmount:"+maximumDiscountedAmount);

        int tmpDiscountedAmount = 0;
        if(myCart.getCardPaymentDiscountType().equals("TotalPercentage")){
            tmpDiscountedAmount = (myCart.getTotalProductPrice()*calculativeAmount)/100;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }else if(myCart.getCardPaymentDiscountType().equals("OverallAmount")){
            tmpDiscountedAmount = calculativeAmount;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }
        Log.d("CHECKOUT_DISCOUNT", "getCardPaymentDiscountType:"+myCart.getCardPaymentDiscountType()+" tmpDiscountedAmount:"+tmpDiscountedAmount);

        if( tmpDiscountedAmount >= myCart.getTotalDiscount() ){
            b.cardPaymentDiscount.setVisibility(View.VISIBLE);
            b.cardPaymentDiscount.setText("৳ "+tmpDiscountedAmount);

            b.totalDiscount.setPaintFlags(b.totalDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            b.totalDiscount.setText("৳ "+myCart.getTotalDiscount());
            paymentDiscount = tmpDiscountedAmount;
        }else if( tmpDiscountedAmount < myCart.getTotalDiscount() ){
            b.cardPaymentDiscount.setVisibility(View.VISIBLE);
            b.cardPaymentDiscount.setText("৳ "+tmpDiscountedAmount);

            b.cardPaymentDiscount.setPaintFlags(b.cardPaymentDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            paymentDiscount = myCart.getTotalDiscount();
        }

    }
    public void radioMobilePayment(View view){
        b.radioMobilePayment.setChecked(true);

        b.radioOnDelivery.setChecked(false);
        b.radioCardPayment.setChecked(false);
        Toast.makeText(context, "radioMobilePayment", Toast.LENGTH_SHORT).show();

        List<DiscountTypeCondition> mobileDiscountCondition = myCart.getMobilePaymentCondition();
        int calculativeAmount = 0;
        int maximumDiscountedAmount = 0;
        if(mobileDiscountCondition != null) {
            for (int i = 0; i < mobileDiscountCondition.size(); i++) {
                if (mobileDiscountCondition.get(i).getMinimumPurchaseLimit() < myCart.getTotalProductPrice()) {
                    calculativeAmount = mobileDiscountCondition.get(i).getDiscountedAmount();
                    maximumDiscountedAmount = mobileDiscountCondition.get(i).getMaximumDiscountedAmount();
                }
            }
        }
        Log.d("CHECKOUT_DISCOUNT", "calculativeAmount:"+calculativeAmount+" maximumDiscountedAmount:"+maximumDiscountedAmount);

        int tmpDiscountedAmount = 0;
        if(myCart.getMobilePaymentDiscountType().equals("TotalPercentage")){
            tmpDiscountedAmount = (myCart.getTotalProductPrice()*calculativeAmount)/100;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }else if(myCart.getMobilePaymentDiscountType().equals("OverallAmount")){
            tmpDiscountedAmount = calculativeAmount;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }
        Log.d("CHECKOUT_DISCOUNT", "getMobilePaymentDiscountType:"+myCart.getMobilePaymentDiscountType()+" tmpDiscountedAmount:"+tmpDiscountedAmount);

        if( tmpDiscountedAmount >= myCart.getTotalDiscount() ){
            b.mobilePaymentDiscount.setVisibility(View.VISIBLE);
            b.mobilePaymentDiscount.setText("৳ "+tmpDiscountedAmount);

            b.totalDiscount.setPaintFlags(b.totalDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            b.totalDiscount.setText("৳ "+myCart.getTotalDiscount());
            paymentDiscount = tmpDiscountedAmount;
        }else if( tmpDiscountedAmount < myCart.getTotalDiscount() ){
            b.mobilePaymentDiscount.setVisibility(View.VISIBLE);
            b.mobilePaymentDiscount.setText("৳ "+tmpDiscountedAmount);

            b.mobilePaymentDiscount.setPaintFlags(b.mobilePaymentDiscount.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            paymentDiscount = myCart.getTotalDiscount();
        }

//        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
    }

    private void addressLayout() {
        if(userProfile == null){
            b.addressEditLayout.setVisibility(View.VISIBLE);
        }else{
            if(userProfile.getAddress() == null){
                b.addressEditLayout.setVisibility(View.VISIBLE);
            }else{
                b.existingAddressLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initAreaSpinner() {
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.area_array));
        b.areaSpinner.setAdapter(filterAdapter);
        b.areaSpinner.setSelection(-1);
        b.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String s = b.areaSpinner.getSelectedItem().toString();
                System.out.println(i+"--"+s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}