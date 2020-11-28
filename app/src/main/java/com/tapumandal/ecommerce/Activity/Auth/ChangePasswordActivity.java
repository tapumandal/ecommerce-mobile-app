package com.tapumandal.ecommerce.Activity.Auth;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ChangePasswordActivityBinding;

public class ChangePasswordActivity extends BaseActivity {

    private Context context;
    ChangePasswordActivityBinding b;
    UserControlViewModel viewModel;


    @Override
    protected int getLayoutResourceFile() {
        return R.layout.change_password_activity;
    }

    @Override
    protected void initComponent() {

        context = this;
        b = getBinding();
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);
        setToolbar("Change Password");
        b.changePassword.setOnClickListener(v -> {


            if (passwordValidation(b.oldPassword.getText().toString(), b.newPassword.getText().toString())) {
                changePassword(b.oldPassword.getText().toString(), b.newPassword.getText().toString());
            } else {
                Toast.makeText(context, "Input is not right", Toast.LENGTH_SHORT).show();
            }
        });


    }




    private void changePassword(String oldPassword, String newPassword) {
        JsonObject object = new JsonObject();
        object.addProperty("old_password", oldPassword);
        object.addProperty("new_password", newPassword);

//        viewModel.changePasswordRequest(object).observe(this, response -> {
//            hideProgressDialog();
//            if (response != null) {
//                if (response.isSuccess()) {
//
//                    UserProfile userProfile = (UserProfile) response.getData();
//
//                    showSuccessToast("Password Change Successfully");
//                    Intent intent = new Intent(context, MenuActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intent);
//                    finish();
//
//                } else {
//                    showFailedToast(response.getMessage());
//                }
//            } else {
//                showFailedToast(getString(R.string.something_went_wrong));
//            }
//
//        });
    }

    public boolean passwordValidation(String oldPassword, String newPassword) {

        if (oldPassword.isEmpty()) {
            b.oldPassword.setError("Input Password");
            showFailedToast("Input Password");
            return false;
        }
        if (newPassword.isEmpty()) {
            b.newPassword.setError("Input Password");
            showFailedToast("Input Password");
            return false;
        }

        return true;
    }



}
