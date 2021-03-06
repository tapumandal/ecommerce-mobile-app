package com.tapumandal.ecommerce.Activity.Auth;

import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.LoginResponse;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding b;
    Context context;
    JsonObject object;
    UserControlViewModel viewModel;

    HashMap<String, String> params;
    HashMap<String, String> images;
    String userType;
    String selectedGender = "";
    String name, phone;

    public static final int LAUNCH_OTP_ACTIVITY = 702;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initComponent() {

        b = getBinding();
        context = this;
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);
//        setToolbar("Registration", true);

        initGender();

        setTearmsAndCondition();
        b.signup.setOnClickListener(v -> {
            checkAndRegister();
        });

    }

    private void checkAndRegister() {

        name = b.name.getText().toString();
        phone = b.mobileNumber.getText().toString();

        if(validateFields(name, selectedGender, phone)){
            validateMobileNo(phone);
        }

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
                if(data.getStringExtra("phoneVerificationStatus").equals("VERIFIED")){
                    register(data.getStringExtra("userIdToken"));
                }else{
                    showFailedToast("Mobile number is not verified!");
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult


    private void register(String userTokenId) {
        //        Without API Login

        if(!isNetworkAvailable()){
            showFailedToast("Internet is not available!");
            return;
        }

        JsonObject object = new JsonObject();
        object.addProperty("name", name);
        object.addProperty("gender", selectedGender);
        object.addProperty("mobileNo", b.mobileNumber.getText().toString());
        object.addProperty("username", b.mobileNumber.getText().toString());
        object.addProperty("userTokenId", userTokenId);

        showProgressDialog("Signing Up..");
        viewModel.registration(object).observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {

                    Log.d("REGISTRATION", "SUCCESSFUL : "+new Gson().toJson(response.getData()));

                    LoginResponse loginResponse = (LoginResponse) response.getData();

                    UserProfile myProfile = new UserProfile();
                    myProfile = (UserProfile) loginResponse.getUser();
                    myProfile.setAccessToken(loginResponse.getJwt());
                    saveUserProfile(myProfile);

                    showSuccessToast("Successfully Signup");
                    startActivity(MainActivity.class, true, true);

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

    private void setTearmsAndCondition() {

//        MyClickableSpan termsAndConditionClick = new MyClickableSpan() {
//
//            @Override
//            public void onClick(View widget) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("url", "terms_and_condition");
//                intent.putExtra("title", "Terms And Condition");
//                context.startActivity(intent);
//            }
//        };
//
//        MyClickableSpan privacyPolicyClick = new MyClickableSpan() {
//
//            @Override
//            public void onClick(View widget) {
//                Intent intent = new Intent(context, WebViewActivity.class);
//                intent.putExtra("url", "privacy");
//                intent.putExtra("title", "Privacy Policy");
//                context.startActivity(intent);
//            }
//        };
//
//
//        SpannableString title;
//        String toc = "Terms & Condition";
//        String pp = "Privacy Policy";
//        title = new SpannableString("By Registration You Agree to " + toc + " and " + pp + " of Lawyer Club");
//        int end = title.toString().indexOf(toc) + toc.length();
//        int end1 = title.toString().indexOf(pp) + pp.length();
//
//        title.setSpan(new StyleSpan(Typeface.BOLD), title.toString().indexOf(toc), end, 0);
//        title.setSpan(new StyleSpan(Typeface.BOLD), title.toString().indexOf(pp), end1, 0);
//
//        title.setSpan(termsAndConditionClick, title.toString().indexOf(toc), end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        title.setSpan(privacyPolicyClick, title.toString().indexOf(pp), end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//
//        b.termsAndCondition.setText(title);
//        b.termsAndCondition.setMovementMethod(LinkMovementMethod.getInstance());

    }

    private void initGender() {
        ArrayList<String> classListString = new ArrayList<>();

        classListString.add("Select Gender");
        classListString.add("Male");
        classListString.add("Female");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_spinner, classListString);

        b.genderSpinner.setAdapter(adapter);

        b.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGender = b.genderSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGender = "";
            }

        });
    }

    private boolean validateFields(String name, String gender, String mobileNumber) {

        if (mobileNumber.equals("")) {
            b.mobileNumber.setError("Mobile number in empty");
            showFailedToast("Mobile number in empty");
            return false;
        }else if (mobileNumber.length() != 11) {
            b.mobileNumber.setError("Invalid Mobile number");
            showFailedToast("Invalid Mobile number. \nExample 017 12345678");
            return false;
        }

        if (gender.equals("")) {
            b.mobileNumber.setError("Select Gender");
            showFailedToast("Select Gender");
            return false;
        }

        if (name.equals("")) {
            b.mobileNumber.setError("Enter your name");
            showFailedToast("Enter your name");
            return false;
        }
        return true;
    }
}
