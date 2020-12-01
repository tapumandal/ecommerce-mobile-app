package com.tapumandal.ecommerce.Activity.Auth;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.ApiClient;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ForgetPasswordActivityBinding;

public class ForgetPasswordActivity extends BaseActivity {
    ForgetPasswordActivityBinding b;
    Context context;
    String phone;
    boolean isReset;
    String phoneNo;
    UserControlViewModel viewModel;


    @Override
    protected int getLayoutResourceFile() {
        return R.layout.forget_password_activity;
    }

    @Override
    protected void initComponent() {
        b = getBinding();
        context = this;
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);


        isReset = getIntent().getBooleanExtra("reset", false);
        phone = getIntent().getStringExtra("number");

        if (isReset) {
            setToolbar("Reset Password");
            b.resetPasswordLayout.setVisibility(View.VISIBLE);
        } else {
            setToolbar("Forgot Password");
            b.forgetPasswordLayout.setVisibility(View.VISIBLE);
        }

        b.forgetPassword.setOnClickListener(v -> {
            forgetPassword();
        });


    }


    public void confirm(View view) {

        if (isReset) {
            resetPassword();
        } else {
            forgetPassword();
        }
    }

    private void forgetPassword(){
        final String mobileNumber;
        mobileNumber = b.mobileNumber.getText().toString().trim();

        JsonObject params = new JsonObject();
        params.addProperty("phone", mobileNumber);

        showProgressDialog("Sending reset code...");



//        viewModel.forgetPasswordRequest(params).observe(this, response -> {
//            hideProgressDialog();
//            if (response != null) {
//                if (response.isSuccess()) {
//
//                    showSuccessToast("OTP send successfully");
//                    SharedPreferences.put(SharedPreferencesEnum.Key.FORGET_PASS_EMAIL, mobileNumber);
//                    Intent intent = new Intent(context, com.maxpro.law4u.Activity.ForgetPasswordActivity.class);
//                    intent.putExtra("reset", true);
//                    intent.putExtra("forgot", true);
//                    intent.putExtra("number", mobileNumber);
//                    startActivity(intent);
//
//                } else {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//
//                    showFailedToast(response.getMessage());
//                }
//            } else {
//                showFailedToast(getString(R.string.something_went_wrong));
//            }
//
//        });
    }

    private void resetPassword() {
        final String newPass, repeatPass, resetCode;

        resetCode = b.resetCode.getText().toString().trim();
        newPass = b.newPassword.getText().toString().trim();
        repeatPass = b.repeatPassword.getText().toString().trim();

        if (!newPass.equals(repeatPass)) {
            Toast.makeText(context, "New password and Repeat password didn't match, Check and try again", Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPass.length() < 8) {
            Toast.makeText(context, "Minimum password length should be 8 character", Toast.LENGTH_SHORT).show();
            return;
        }

        if (resetCode.length() < 6) {
            Toast.makeText(context, "Minimum reset code length should be 6 character", Toast.LENGTH_SHORT).show();
            return;
        }
//Remove when API is ready

        JsonObject params = new JsonObject();
        params.addProperty("verification_code", resetCode);
        params.addProperty("phone", phone);
        params.addProperty("new_password", newPass);

        showProgressDialog("Changing Password...");
        viewModel.resetPassword(params).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess()) {

                    showSuccessToast("Password Changed Successfully");
                    login(phone, newPass);

                } else {
                    showFailedToast(response.getMessage());
                }
            } else {
                showFailedToast(response.getMessage());

                showFailedToast(getString(R.string.something_went_wrong));
            }

        });

    }

    private void login(String email, String password) {
        JsonObject object = new JsonObject();
        object.addProperty("user_id", email);
        object.addProperty("password", password);
        showProgressDialog("Logging in...");

        viewModel.userLogin(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess()) {

                    UserProfile userProfile = (UserProfile) response.getData();
                    saveUserProfile(userProfile);
                    ApiClient.initRetrofit();

                    showSuccessToast("Successfully login");
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP| Intent.FLAG_ACTIVITY_CLEAR_TASK);
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


}