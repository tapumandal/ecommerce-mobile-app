package com.tapumandal.ecommerce.Activity.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.JsonObject;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivitySignupBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.MultipartBody;

public class SignupActivity extends BaseActivity {

    ActivitySignupBinding b;
    Context context;
    JsonObject object;
    UserControlViewModel viewModel;

    HashMap<String, String> params;
    HashMap<String, String> images;
    String userType;
    String selectedGender = "";

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_signup;
    }

    @Override
    protected void initComponent() {

        b = getBinding();
        context = this;
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);
        setToolbar("Registration", true);

        userType = getIntent().getStringExtra("type");
        params = new HashMap<>();
        params.put("type", userType);

        if (userType.equals("1")) {
            b.ageGenderLayout.setVisibility(View.GONE);
        }

        initGender();

        setTearmsAndCondition();
        b.signup.setOnClickListener(v -> {
            checkAndRegister();
        });

    }

    private void checkAndRegister() {
        String name = b.name.getText().toString();;
        String age = "";
        String phone = b.mobileNumber.getText().toString();
        String password = b.password.getText().toString();
        String confirmPassword = b.password.getText().toString();

        if (!userType.equals("1")) {
            age = b.age.getText().toString();

            if (age.isEmpty()) {
                b.age.setError("Input Age");
                showFailedToast("Input Age");
                return;
            }
            if (selectedGender.isEmpty() || selectedGender.equals("Select Gender")) {
                b.age.setError("Select gender");
                showFailedToast("Select gender");
                return;
            }

            params.put("age", age);
            params.put("gender", selectedGender);

        }

        if (name.isEmpty()) {
            b.name.setError("Input Name");
            showFailedToast("Input Name");
            return;
        }

        if (phone.isEmpty()) {
            b.mobileNumber.setError("Input Mobile Number");
            showFailedToast("Input Mobile Number");
            return;
        }

        if (password.isEmpty()) {
            b.password.setError("Input Password");
            showFailedToast("Input Password");
            return;
        }

        if (confirmPassword.isEmpty()) {
            b.confirmPassword.setError("Input Confirm Password");
            showFailedToast("Input Confirm Password");
            return;
        }
        if (!password.equals(confirmPassword)) {
            showFailedToast("Password and Confirm Password didn't match");
            return;
        }

        params.put("name", name);
        params.put("password", password);
        params.put("phone", phone);

        checkCredential( phone);
    }

    private void checkCredential( String phone) {

        JsonObject objectCredens = new JsonObject();
        objectCredens.addProperty("phone", phone);

        showProgressDialog("Signing Up..");
//        viewModel.checkCredential(objectCredens).observe(this, response -> {
//            hideProgressDialog();
//            if (response != null) {
//                if (response.isSuccess()) {
//                    showSuccessToast("A verification has been sent to you mobile number.");
//
//                    Intent intent = new Intent(context, VerificationActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("data", params);
//                    intent.putExtra("isLawyer", true);
//                    startActivity(intent);
//
//                } else {
//                    showFailedToast(response.getMessage());
//                }
//            } else {
//                showFailedToast(getString(R.string.something_went_wrong));
//            }
//        });
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

}
