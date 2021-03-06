package com.tapumandal.ecommerce.Activity.Auth;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.LoginResponse;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.ApiClient;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityLoginBinding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends BaseActivity {

    private Context context;
    ActivityLoginBinding b;
    UserControlViewModel viewModel;
    String mobileNumber;

    public static final int LAUNCH_OTP_ACTIVITY = 701;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_login;
    }

    @Override
    protected void initComponent() {


        context = this;

        b = getBinding();

        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);

        b.createAccount.setOnClickListener(v -> {
            startActivity(SignupActivity.class, false);
        });

        b.login.setOnClickListener(v -> {
            mobileNumber = b.mobileNumber.getText().toString();
            if (validateFields(mobileNumber)) {
                validateMobileNo(mobileNumber);
            }
//            else {
//                Toast.makeText(context, "Login credential is not right", Toast.LENGTH_SHORT).show();
//            }
        });

//        b.forgetPassword.setOnClickListener(v -> {
//            startActivity(ForgetPasswordActivity.class, false);
//        });


        printKeyHash();
    }

    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.maxpro.law4u", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }

    private void generatePublishedKeyHashForFacebook() {
        //From the play console app signing we get a sha1 and from the Sha-1 we can generate the key hash
        byte[] sha1 = {
                (byte) 0x93, 0x1E, 0x7A, 0x24, (byte) 0xA4, (byte) 0xAD, 0x1D, 0x37, 0x4E, 0x49, 0x5E, (byte) 0xD9, 0x78, (byte) 0xF1, 0x18, 0x59, (byte) 0x8A, 0x0E, (byte) 0xD9, (byte) 0xA9
        };
        System.out.println("keyhashGooglePlaySignIn:"+ Base64.encodeToString(sha1, Base64.NO_WRAP));
    }

    private void loginAttempt(String username, String password) {

        showProgressDialog("Login in...");
        JsonObject object = new JsonObject();
        object.addProperty("user_id", username);
        object.addProperty("password", password);

        viewModel.userLogin(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess()) {

                    UserProfile userProfile = (UserProfile) response.getData();
                    saveUserProfile(userProfile);
                    ApiClient.initRetrofit();
                    showSuccessToast("Successfully login");
                    Intent intent = new Intent(context, Product.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    showFailedToast(response.getMessage());
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean validateFields(String mobileNumber) {

        if (mobileNumber.equals("")) {
            b.mobileNumber.setError("Mobile number in empty");
            showFailedToast("Mobile number in empty");
            return false;
        }else if (mobileNumber.length() != 11) {
            b.mobileNumber.setError("Invalid Mobile number");
            showFailedToast("Invalid Mobile number. \nExample 017 12345678");
            return false;
        }
        return true;
    }

    private void validateMobileNo(String phone){
        JsonObject object = new JsonObject();
        object.addProperty("mobileNo", phone);
        Intent intent = new Intent(this, MobileOTPActivity.class);
        intent.putExtra("data", object.toString());
        startActivityForResult(intent, LAUNCH_OTP_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_OTP_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String phoneVerificationStatus = data.getStringExtra("phoneVerificationStatus");
                if(data.getStringExtra("phoneVerificationStatus").equals("VERIFIED")){
                    callUserProfile(data.getStringExtra("userIdToken"));
                }else{
                    showFailedToast("Mobile number is not verified!");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void callUserProfile(String userTokenId) {

        JsonObject object = new JsonObject();
        object.addProperty("username", mobileNumber);
        object.addProperty("userTokenId", userTokenId);
        showProgressDialog("Logging In..");
        viewModel.userLogin(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {

                    Log.d("REGISTRATION", "SUCCESSFUL : "+new Gson().toJson(response.getData()));

                    LoginResponse loginResponse = (LoginResponse) response.getData();

                    UserProfile myProfileTmp = new UserProfile();
                    myProfileTmp = (UserProfile) loginResponse.getUser();
                    myProfileTmp.setAccessToken(loginResponse.getJwt());
                    saveUserProfile(myProfileTmp);

                    showSuccessToast("Successfully login");
                    startActivity(MainActivity.class, true, true);

                } else {
                    if(response.getMessage().equals("HTTP 403 ")){
                        logout();
                        showFailedToast("Your authentication is expired. \nPlease login.");
                    }
                    Log.d("POSTCART", "FAILED POST NULL Data : "+new Gson().toJson(response));
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }
        });
    }
}
