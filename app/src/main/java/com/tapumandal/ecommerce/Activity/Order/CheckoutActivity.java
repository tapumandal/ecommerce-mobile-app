package com.tapumandal.ecommerce.Activity.Order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Activity.Auth.MobileOTPActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
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
    public static final int LAUNCH_OTP_ACTIVITY = 703;

    boolean checkPaymentStatus = false;

    ActivityCheckoutBinding b;
    ProductControlViewModel viewModel;
    UserControlViewModel userViewModel;

    private UserProfile userProfile;
    private UserProfile newProfile;
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
        userViewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);
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

            b.name.setText(userProfile.getName());
            b.phone.setText(userProfile.getMobileNo());
            b.name.setText(userProfile.getAddresses().get(0).getName());
            b.phone.setText(userProfile.getAddresses().get(0).getMobileNo());
            b.block.setText(userProfile.getAddresses().get(0).getBlock());
            b.road.setText(userProfile.getAddresses().get(0).getRoad());
            b.house.setText(userProfile.getAddresses().get(0).getHouse());
            b.flat.setText(userProfile.getAddresses().get(0).getFlat());
            b.details.setText(userProfile.getAddresses().get(0).getDetails());

            userProfile.setAddresses(null);
            OfflineCache.saveOffline(OfflineCache.MY_PROFILE, userProfile);
        });

        b.promoCodeBtn.setOnClickListener(v->{
            promoCode(b.promoCode.getText().toString());
        });
    }

    private void promoCode(String promoCode){
        if (promoCode.equals("")) {
            b.promoCode.setError("Promo Code Empty");
            showFailedToast("Promo Code Empty");
            return;
        }

        showFailedToast("Promo Code is not valid");
        return;

//        JsonObject object = new JsonObject();
//        object.addProperty("promoCode", promoCode);
//        object.addProperty("purchaseAmount", myCart.getTotalProductPrice());
//        if(userProfile != null) {
//            object.addProperty("userId", userProfile.getId());
//        }else{
//            object.addProperty("userId", "0");
//        }
//
//
//        viewModel.checkPromoCode(object).observe(this, response -> {
//            hideProgressDialog();
//            if (response != null) {
//                if (response.isSuccess() && response.getData() != null) {
//
//                    Log.d("PROMO_CODE", "SUCCESSFUL POST: "+response.getData());
//                } else {
//                    showFailedToast(response.getMessage());
//                    Log.d("PROMO_CODE", "FAILED POST NULL Data : "+new Gson().toJson(response));
//                }
//            } else {
//                showFailedToast(getString(R.string.something_went_wrong));
//                Log.d("PROMO_CODE", "FAILED POST NULL Response : "+response.getMessage());
//            }
//        });

    }

    private void checkout() {

        if(userProfile == null) {
            Log.d("CHECKOUT_BTN", "1");
            newProfile = newProfileData();

            if(!validateFields(newProfile)){
                Log.d("CHECKOUT_BTN", "2");
                return;
            }
            validateMobileNo(newProfile.getMobileNo());

        }else if(userProfile.getAddresses() == null || userProfile.getAddresses().isEmpty()){
            Log.d("CHECKOUT_BTN", "3");
            newProfile = newProfileData();
            if(!validateFields(newProfile)){
                Log.d("CHECKOUT_BTN", "4");
                return;
            }

            if(!userProfile.getMobileNo().equals(newProfile.getAddresses().get(0).getMobileNo())){
                Log.d("CHECKOUT_BTN", "5");
                validateMobileNo(newProfile.getAddresses().get(0).getMobileNo());
            }else{
                Log.d("CHECKOUT_BTN", "6");
                userProfile.setAddresses(newProfile.getAddresses());
                addAddress(userProfile); //Then checkPaymentStatusAndPost
//                checkPaymentStatusAndPost();
            }

        }else{
            Log.d("CHECKOUT_BTN", "7");
            checkPaymentStatusAndPost();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_OTP_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){

                if(data.getStringExtra("phoneVerificationStatus").equals("VERIFIED")){
                    if(userProfile == null) {
                        Log.d("CHECKOUT_BTN", "8");
                        newProfile.setUserTokenId(data.getStringExtra("userIdToken"));
                        userRegistration(newProfile); //Then checkPaymentStatusAndPost
                    }else if(userProfile.getAddresses() == null){
                        Log.d("CHECKOUT_BTN", "9");
                        userProfile.setUserTokenId(data.getStringExtra("userIdToken"));
                        userProfile.setAddresses(newProfile.getAddresses());
                        addAddress(userProfile); //Then checkPaymentStatusAndPost
                    }else{
                        Log.d("CHECKOUT_BTN", "10");
                        userProfile.setUserTokenId(data.getStringExtra("userIdToken"));
                        checkPaymentStatusAndPost();
                    }
                }else{
                    showFailedToast("Phone number is not verified!");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void checkPaymentStatusAndPost() {
        if(selectedPaymentMethod.equals(ON_DELIVERY)){
                    checkPaymentStatus = true;
            Toast.makeText(context, "ON_DELIVERY", Toast.LENGTH_SHORT).show();
        }else if(selectedPaymentMethod.equals(CARD_PAYMENT)){
                    checkPaymentStatus = true;
            Toast.makeText(context, "CARD_PAYMENT", Toast.LENGTH_SHORT).show();
        }else if(selectedPaymentMethod.equals(MOBILE_PAYMENT)){
                    checkPaymentStatus = true;
            Toast.makeText(context, "MOBILE_PAYMENT", Toast.LENGTH_SHORT).show();
        }

        if(checkPaymentStatus){
            postCart();
        }
    }

    private void addressLayout(){

        Log.d("USER_PROFILE", "addressLayout: "+new Gson().toJson(userProfile));

        if(userProfile == null){
//            NO User Found
            b.addressEditLayout.setVisibility(View.VISIBLE);
        }else{
//            User Found

            if(userProfile.getAddresses() == null || userProfile.getAddresses().isEmpty()){
//              User Found But Not Address
                b.addressEditLayout.setVisibility(View.VISIBLE);

                b.name.setText(userProfile.getName());
                b.phone.setText(userProfile.getMobileNo());

            }else{
//              User and Address both Found
                b.existingAddressLayout.setVisibility(View.VISIBLE);
                b.addressEditBtn.setVisibility(View.VISIBLE);

                String name = userProfile.getAddresses().get(0).getName();
                String mobileNo = userProfile.getAddresses().get(0).getMobileNo();

                if(name.isEmpty()){
                    name = userProfile.getName();
                }
                if(mobileNo.isEmpty()){
                    mobileNo = userProfile.getName();
                }

                b.existingName.setText(name);
                b.existingPhone.setText(mobileNo);
                b.existingAddressDetails.setText(address());
            }
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

    private void postCart() {
        Toast.makeText(context, "POST CART function", Toast.LENGTH_SHORT).show();
        myCart.setUserId(userProfile.getId());

        myCart.setName(userProfile.getAddresses().get(0).getName());
        myCart.setMobileNo(userProfile.getAddresses().get(0).getMobileNo());
        myCart.setArea(userProfile.getAddresses().get(0).getArea());
        myCart.setAddress(address());
        String objectStr = new Gson().toJson(myCart);
        JSONObject object = new JSONObject();
        try {
            object = new JSONObject(objectStr);
        } catch (Throwable t) {
            Log.d("USER_PROFILE", "STRING TO JSONObject FAILED");
        }


        JsonObject jsonObject = (JsonObject) new Gson().toJsonTree(myCart);
//        Log.d("USER_PROFILE", "STRING TO JSONObject"+object.toString());
        Log.d("USER_PROFILE", "STRING TO JSONObject"+jsonObject);

//        myCart.setName(userProfile.getName());
//        myCart.setMobileNumber(userProfile.getMobileNo());
//        myCart.setArea(userProfile.getAddresses().get(0).getArea());
//        myCart.setAddresses(address());

        viewModel.postCart(jsonObject).observe(this, response -> {
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
                    startActivity(MainActivity.class, true);
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

    private void validateMobileNo(String phone){
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", phone);
        Intent intent = new Intent(this, MobileOTPActivity.class);
        intent.putExtra("data", object.toString());
        startActivityForResult(intent, LAUNCH_OTP_ACTIVITY);
    }

    private UserProfile newProfileData() {
        UserProfile newProfile = new UserProfile();
        newProfile.setName(b.name.getText().toString());

        String tmpMobileNo = b.phone.getText().toString();
        if(tmpMobileNo.length()==11) {
//            tmpMobileNo = tmpMobileNo.substring(tmpMobileNo.length() - 11);
        }else{
            tmpMobileNo = "";
        }
        newProfile.setMobileNo(tmpMobileNo);

        newProfile.setMobileNoIsValid(false);

        List<Address> addresses = new ArrayList<>();
        Address address = new Address();
        address.setName(b.name.getText().toString());
        address.setMobileNo(tmpMobileNo);
        address.setArea(area);
        address.setBlock(b.block.getText().toString());
        address.setRoad(b.road.getText().toString());
        address.setHouse(b.house.getText().toString());
        address.setFlat(b.flat.getText().toString());
        address.setDetails(b.details.getText().toString());
        addresses.add(address);
        newProfile.setAddresses(addresses);

        return newProfile;
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

    private boolean validateFields(UserProfile newProfile) {

        if (newProfile.getName().equals("")) {
            showAlertDialog("Required", "Name");
            return false;
        }
        if (newProfile.getMobileNo().equals("")) {
            showAlertDialog("Required", "Phone \n Example 017 12345678");
            return false;
        }
        if (newProfile.getAddresses().get(0).getArea().equals("")) {
            showAlertDialog("Required", "Area");
            return false;
        }
        if (newProfile.getAddresses().get(0).getBlock().equals("")) {
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
        if(!userProfile.getAddresses().get(0).getFlat().equals("")){
            address = address+"Flat: "+userProfile.getAddresses().get(0).getFlat()+", ";
        }
        if(!userProfile.getAddresses().get(0).getHouse().equals("")){
            address = address+"House: "+userProfile.getAddresses().get(0).getHouse()+", ";
        }
        if(!userProfile.getAddresses().get(0).getRoad().equals("")){
            address = address+"Road: "+userProfile.getAddresses().get(0).getRoad()+", ";
        }
        if(!userProfile.getAddresses().get(0).getBlock().equals("")){
            address = address+"Block: "+userProfile.getAddresses().get(0).getBlock()+", ";
        }
        if(!userProfile.getAddresses().get(0).getArea().equals("")){
            address = address+" "+userProfile.getAddresses().get(0).getArea()+".";
        }

        if(!userProfile.getAddresses().get(0).getDetails().equals("")) {
            address = address + "\n" + userProfile.getAddresses().get(0).getDetails() + ".";
        }

        return address;
    }


    private void addAddress(UserProfile userProfile) {

        if(!isNetworkAvailable()){
            showFailedToast("Internet is not available!");
            return;
        }

        JsonObject object = (JsonObject) new Gson().toJsonTree(userProfile);
        Log.d("REGISTRATION", "SUCCESSFUL object : "+new Gson().toJson(object));
//        showProgressDialog("Signing Up..");
        userViewModel.addNewUserAddress(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {

                    Log.d("REGISTRATION", "ADDRESS UPDATE SUCCESSFUL : "+new Gson().toJson(response.getData()));

                    UserProfile userProfile1 = (UserProfile) response.getData();

                    OfflineCache.saveOffline(OfflineCache.MY_PROFILE, userProfile1);
                    startActivity(MainActivity.class, true);
                    checkPaymentStatusAndPost();

                } else {
                    showFailedToast(response.getMessage());
                    Log.d("REGISTRATION", "FAILED POST NULL Data : "+new Gson().toJson(response));
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
                Log.d("REGISTRATION", "FAILED POST NULL Response : "+response.getMessage());
            }
        });
    }

    private void userRegistration(UserProfile profile) {

        profile.setUsername(profile.getMobileNo());
        if(!isNetworkAvailable()){
            showFailedToast("Internet is not available!");
            return;
        }

        Log.d("REGISTRATION", "SUCCESSFUL profile : "+profile);
        JsonObject object = (JsonObject) new Gson().toJsonTree(profile);
        Log.d("REGISTRATION", "SUCCESSFUL object : "+new Gson().toJson(object));
        showProgressDialog("Signing Up..");
        userViewModel.registration(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {

                    Log.d("REGISTRATION", "SUCCESSFUL : "+new Gson().toJson(response.getData()));

                    LoginResponse loginResponse = (LoginResponse) response.getData();

                    UserProfile myProfile = (UserProfile) loginResponse.getUser();
                    myProfile.setAccessToken(loginResponse.getJwt());
                    saveUserProfile(myProfile);
                    userProfile = myProfile;
                    checkPaymentStatusAndPost();

                } else {
                    showFailedToast(response.getMessage());
                    Log.d("REGISTRATION", "FAILED POST NULL Data : "+new Gson().toJson(response));
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
                Log.d("REGISTRATION", "FAILED POST NULL Response : "+response.getMessage());
            }
        });

    }

}