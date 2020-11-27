package com.tapumandal.ecommerce.Activity.Order;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Activity.Product.ProductActivity;
import com.tapumandal.ecommerce.Activity.Security.MobileOTPActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityCheckoutBinding;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tapumandal on 11/18/2020.
 * For any query ask online.tapu@gmail.com
 */

public class CheckoutActivity extends BaseActivity {

    public static final String ON_DELIVERY = "OnDelivery";
    public static final String CARD_PAYMENT = "CardPayment";
    public static final String MOBILE_PAYMENT = "MobilePayment";
    int LAUNCH_OTP_ACTIVITY = 701;

    boolean mobileNoValidationStatus = false;

    ActivityCheckoutBinding b;
    ProductControlViewModel viewModel;

    private UserProfile userProfile;
    private Cart myCart;
    private BusinessSettings businessSettings;
    private String selectedPaymentMethod = "";

    int paymentDiscount = 0;
    String area = "";

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
        Log.d("USER_PROFILE", "initComponent : "+userProfile);
        myCart = OfflineCache.getOfflineSingle(OfflineCache.MY_CART);
        businessSettings = OfflineCache.getOfflineSingle(OfflineCache.BUSINESS_SETTINGS);

        if(myCart != null) {
            setCartDetails();
        }

        addressLayout();
        initAreaSpinner();
        clickEvent();
    }

    private void clickEvent() {
        b.checkoutConfirmBtn.setOnClickListener(v->{
            checkout();
        });

        b.addressEditBtn.setOnClickListener(v -> {
            b.existingAddressLayout.setVisibility(View.GONE);
            b.addressEditLayout.setVisibility(View.VISIBLE);
            userProfile.setMobileNoIsValid(false);
            OfflineCache.deleteCacheFile(OfflineCache.MY_PROFILE);
        });
    }

    private void checkout() {
        UserProfile uProfile = new UserProfile();
        if(userProfile == null){
            uProfile = setProfileData();
            if(!validateFields(uProfile)){
//                Checked every field and payment method
                return;
            }
        }else{
            if(!validateFields(userProfile)){
//                Checked every field and payment method
                return;
            }
        }

        Log.d("USER_PROFILE", "OnCLICK CONFIRM: "+new Gson().toJson(userProfile));

        if(!userProfile.isMobileNoIsValid()){
            validateMobileNo();
            return;
        }else{
            checkPaymentStatusAndPostOrder();
        }
    }

    private void addressLayout(){

        Log.d("USER_PROFILE", "addressLayout: "+new Gson().toJson(userProfile));

        if(userProfile == null){
            b.addressEditLayout.setVisibility(View.VISIBLE);
            b.addressEditBtn.setVisibility(View.GONE);
        }else{
            if(userProfile.getAddress() == null){
                b.addressEditLayout.setVisibility(View.VISIBLE);
            }else{
                b.existingAddressLayout.setVisibility(View.VISIBLE);
            }

            b.name.setText(userProfile.getName());
            b.phone.setText(userProfile.getMobileNo());
            b.name.setText(userProfile.getAddress().get(0).getName());
            b.phone.setText(userProfile.getAddress().get(0).getMobileNo());
            b.block.setText(userProfile.getAddress().get(0).getBlock());
            b.road.setText(userProfile.getAddress().get(0).getRoad());
            b.house.setText(userProfile.getAddress().get(0).getHouse());
            b.flat.setText(userProfile.getAddress().get(0).getFlat());
            b.details.setText(userProfile.getAddress().get(0).getDetails());

            b.existingName.setText(userProfile.getName());
            b.existingPhone.setText(userProfile.getMobileNo());
            b.existingAddressDetails.setText(address());

        }

    }

    private void setCartDetails() {
        b.subTotal.setText(String.valueOf(myCart.getTotalProductPrice()));
        b.shipping.setText(String.valueOf(myCart.getDeliveryCharge()));

        b.totalDiscount.setText(String.valueOf(myCart.getTotalDiscount()));

        myCart.setTotalPayable( (myCart.getTotalProductPrice() - myCart.getTotalDiscount()) + myCart.getDeliveryCharge() );
        b.totalPayable.setText(String.valueOf(myCart.getTotalPayable()));

        if(businessSettings.getPaymentDiscountMessage() != "") {
            b.paymentDiscountMessageLayout.setVisibility(View.VISIBLE);
            b.paymentDiscountMessage.setText(businessSettings.getPaymentDiscountMessage());
        }
    }

    private void checkPaymentStatusAndPostOrder() {
        if(selectedPaymentMethod.equals(ON_DELIVERY)){
            postCart();
        }else if(selectedPaymentMethod.equals(CARD_PAYMENT)){
//                hitCardPaymentAPI();
//                IF success postCart();
        }else if(selectedPaymentMethod.equals(MOBILE_PAYMENT)){
//                hitCardPaymentAPI();
//                IF success postCart();
        }
    }

    private void postCart() {


//        JsonObject object = new JsonObject();

//        object.addProperty("deliveryCharge", "90");
//        object.addProperty("totalDiscount", "900");
//        object.addProperty("totalProductPrice", "900");
//        object.addProperty("totalPayable", "900");

        String objectStr = new Gson().toJson(myCart);
        JSONObject object = new JSONObject();
        try {

            object = new JSONObject(objectStr);

            Log.d("USER_PROFILE", "STRING TO JSONObject"+object.toString());

        } catch (Throwable t) {
            Log.d("USER_PROFILE", "STRING TO JSONObject FAILED");
        }

        viewModel.postCart(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {

                    myCart.setTotalProductDiscount(0);
                    myCart.setTotalProductQuantity(0);
                    myCart.setTotalProductPrice(0);
                    myCart.setTotalDiscount(0);
                    myCart.setTotalPayable(0);
                    myCart.setProducts(new ArrayList<>());
                    Log.d("USER_PROFILE", "POST ORDER : "+new Gson().toJson(userProfile));
                    OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
                    OfflineCache.saveOffline(OfflineCache.MY_PROFILE, userProfile);
                    startActivity(new Intent(context, ProductActivity.class));
                    Log.d("POSTCART", "SUCCESSFUL POST: "+response.getData());
                } else {
                    showFailedToast(response.getMessage());
                    Log.d("POSTCART", "FAILED POST NULL Data : "+new Gson().toJson(response));
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
                Log.d("POSTCART", "FAILED POST NULL Response : "+response.getMessage());
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_OTP_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String phoneVerificationStatus = data.getStringExtra("phoneVerificationStatus");
                Toast.makeText(context, phoneVerificationStatus, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private boolean validateMobileNo(){
        JsonObject object = new JsonObject();
        object.addProperty("phone", userProfile.getMobileNo());
        Intent intent = new Intent(this, MobileOTPActivity.class);
        intent.putExtra("data", object.toString());
        startActivityForResult(intent, LAUNCH_OTP_ACTIVITY);

//        JsonObject object = new JsonObject();
//        object.addProperty("name", "Tapu Mandal");
//        object.addProperty("phone", "01739995117");
//
//        Intent intent = new Intent(context, MobileOTPActivity.class);
//        intent.putExtra("obj", object.toString());
//        startActivity(intent);

//        startActivity(new Intent(context, MobileOTPActivity.class));

//        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which){
//                    case DialogInterface.BUTTON_POSITIVE:
//                        //Yes button clicked
//                        mobileNoValidationStatus = true;
//                        break;
//
//                    case DialogInterface.BUTTON_NEGATIVE:
//                        //No button clicked
//                        mobileNoValidationStatus = false;
//                        break;
//                }
//            }
//        };
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setMessage("Validate Mobile Number?"+userProfile.getMobileNo()).setPositiveButton("Yes", dialogClickListener)
//                .setNegativeButton("No", dialogClickListener).show();
//
//        Toast.makeText(context, "Mobile Number Validation is: "+mobileNoValidationStatus, Toast.LENGTH_SHORT).show();

        return mobileNoValidationStatus;
    }

    private UserProfile setProfileData() {
        UserProfile uProfile = new UserProfile();
        uProfile = new UserProfile();
        uProfile.setName(b.name.getText().toString());

        String tmpMobileNo = b.phone.getText().toString();
        if(tmpMobileNo.length()>10) {
            tmpMobileNo = tmpMobileNo.substring(tmpMobileNo.length() - 11);
        }else{
            tmpMobileNo = "";
        }
        uProfile.setMobileNo(tmpMobileNo);

        uProfile.setMobileNoIsValid(false);

        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setName(b.name.getText().toString());
        address.setMobileNo(b.phone.getText().toString());
        address.setArea(area);
        address.setBlock(b.block.getText().toString());
        address.setRoad(b.road.getText().toString());
        address.setHouse(b.house.getText().toString());
        address.setFlat(b.flat.getText().toString());
        address.setDetails(b.details.getText().toString());
        addresses.add(address);
        uProfile.setAddress(addresses);

        return uProfile;
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
                area = s;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void radioOnDelivery(View view){
        b.radioOnDelivery.setChecked(true);

        b.radioCardPayment.setChecked(false);
        b.radioMobilePayment.setChecked(false);

        b.totalDiscount.setPaintFlags(0);
        b.cardPaymentDiscount.setVisibility(View.GONE);
        b.mobilePaymentDiscount.setVisibility(View.GONE);

        selectedPaymentMethod = "OnDelivery";
    }
    public void radioCardPayment(View view){
        b.radioCardPayment.setChecked(true);

        b.radioOnDelivery.setChecked(false);
        b.radioMobilePayment.setChecked(false);


        List<DiscountTypeCondition> cardDiscountCondition = businessSettings.getCardPaymentCondition();
        int calculativeAmount = 0;
        int maximumDiscountedAmount = 0;
        if(cardDiscountCondition != null) {
            for (int i = 0; i < cardDiscountCondition.size(); i++) {
                if (cardDiscountCondition.get(i).getMinimumPurchaseLimit() < myCart.getTotalProductPrice()) {
                    calculativeAmount = cardDiscountCondition.get(i).getDiscountedAmount();
                    maximumDiscountedAmount = cardDiscountCondition.get(i).getMaximumDiscountedAmount();
                }
            }
        }

        int tmpDiscountedAmount = 0;
        if(businessSettings.getCardPaymentDiscountType().equals("TotalPercentage")){
            tmpDiscountedAmount = (myCart.getTotalProductPrice()*calculativeAmount)/100;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }else if(businessSettings.getCardPaymentDiscountType().equals("OverallAmount")){
            tmpDiscountedAmount = calculativeAmount;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }


        b.mobilePaymentDiscount.setVisibility(View.GONE);
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
            b.totalDiscount.setPaintFlags(0);
            paymentDiscount = myCart.getTotalDiscount();
        }

        selectedPaymentMethod = "CardPayment";
    }
    public void radioMobilePayment(View view){
        b.radioMobilePayment.setChecked(true);

        b.radioOnDelivery.setChecked(false);
        b.radioCardPayment.setChecked(false);

        List<DiscountTypeCondition> mobileDiscountCondition = businessSettings.getMobilePaymentCondition();
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

        int tmpDiscountedAmount = 0;
        if(businessSettings.getMobilePaymentDiscountType().equals("TotalPercentage")){
            tmpDiscountedAmount = (myCart.getTotalProductPrice()*calculativeAmount)/100;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }else if(businessSettings.getMobilePaymentDiscountType().equals("OverallAmount")){
            tmpDiscountedAmount = calculativeAmount;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
        }

        b.cardPaymentDiscount.setVisibility(View.GONE);
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
            b.totalDiscount.setPaintFlags(0);
            paymentDiscount = myCart.getTotalDiscount();
        }

//        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
        selectedPaymentMethod = "MobilePayment";
    }

    private boolean validateFields(UserProfile uProfile) {

        if (uProfile.getName().equals("")) {
            showAlertDialog("Required", "Name");
            return false;
        }
        if (uProfile.getMobileNo().equals("")) {
            showAlertDialog("Required", "Phone \n Example 017 12345678");
            return false;
        }
        if (uProfile.getAddress().get(0).getArea().equals("")) {
            showAlertDialog("Required", "Area");
            return false;
        }
        if (uProfile.getAddress().get(0).getBlock().equals("")) {
            showAlertDialog("Required", "Block");
            return false;
        }
        if (selectedPaymentMethod.equals("")) {
            showAlertDialog("Required", "Select Payment Method");
            return false;
        }
        return true;
    }

    private String address() {
        String address = "";
        if(!userProfile.getAddress().get(0).getFlat().equals("")){
            address = address+"Flat: "+userProfile.getAddress().get(0).getFlat()+", ";
        }
        if(!userProfile.getAddress().get(0).getHouse().equals("")){
            address = address+"House: "+userProfile.getAddress().get(0).getHouse()+", ";
        }
        if(!userProfile.getAddress().get(0).getRoad().equals("")){
            address = address+"Road: "+userProfile.getAddress().get(0).getRoad()+", ";
        }
        if(!userProfile.getAddress().get(0).getBlock().equals("")){
            address = address+"Block: "+userProfile.getAddress().get(0).getBlock()+", ";
        }
        if(!userProfile.getAddress().get(0).getArea().equals("")){
            address = address+" "+userProfile.getAddress().get(0).getArea()+".";
        }

        if(!userProfile.getAddress().get(0).getDetails().equals("")) {
            address = address + "\n" + userProfile.getAddress().get(0).getDetails() + ".";
        }

        return address;
    }



}