package com.tapumandal.ecommerce.Activity.Auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;

import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.ViewModel.ApplicationControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityMobileOTPBinding;

import java.util.concurrent.TimeUnit;


/**
 * Created by tapumandal on 11/26/2020.
 * For any query ask online.tapu@gmail.com
 */

public class MobileOTPActivity extends BaseActivity {

    private Context context;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId, phone, password, name, phoneEmail;
    private FirebaseAuth mAuth;
    String code;
    String TAG = "OTP";
    JsonObject params;
    boolean phoneVerified;
    boolean isTimerOn;
    ApplicationControlViewModel viewModel;

    ActivityMobileOTPBinding b;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_mobile_o_t_p;
    }

    @Override
    protected void initComponent() {
        b = getBinding();
        viewModel = ViewModelProviders.of(this).get(ApplicationControlViewModel.class);

        context = this;
        mAuth = FirebaseAuth.getInstance();
        setToolbar("Verify Phone Number");

        mVerificationId = "";

        try {
            params = new JsonParser().parse(getIntent().getStringExtra("data")).getAsJsonObject();
            phone = params.get("phone").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show();
            finish();
        }


        phone = "+88" + phone;
        code = new String();

//        String message = new String("We have sent a verification code on your number <font color=\"" + Constants.getColorFromXml(context, R.color.appColor) + "\">" + phone + "</font>. Please enter the verification code below.");
        String message = new String("We have sent a verification code on your number " +  phone + ". Please enter the verification code below.");
        b.messageTv.setText(Html.fromHtml(message));


        if (b.txtPinEntry != null) {
            b.txtPinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().length() == 6) {
                        code = str.toString();
                    }
                }
            });
        }

        isTimerOn = true;
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                b.timeCount.setText("Please wait " + millisUntilFinished / 1000 + " seconds for resend code");
                b.resendCode.setFocusable(false);
            }

            public void onFinish() {
                isTimerOn = false;
                b.resendCode.setTextColor(getResources().getColor(R.color.appColor));
                b.timeCount.setVisibility(View.GONE);
            }

        }.start();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted:" + credential);

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
//                Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                showFailedToast(e.getLocalizedMessage());
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
//                Toast.makeText(context, "Code send successfully ", Toast.LENGTH_SHORT).show();
                showSuccessToast("Code send successfully!");

            }
        };


        if (phone != null) {
//            sendCodeToNumber(phone);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        //phn verify
//        phoneVerified = true;
    }

    public void resendCode(View view) {

        if (isTimerOn) {
            return;
        }
        if (phone != null) {
            Toast.makeText(context, "Resending code...", Toast.LENGTH_LONG).show();
            sendCodeToNumber(phone);

            isTimerOn = true;
            b.resendCode.setTextColor(getResources().getColor(R.color.Ash_Gray));
            b.timeCount.setVisibility(View.VISIBLE);
            new CountDownTimer(30000, 1000) {

                public void onTick(long millisUntilFinished) {
                    b.timeCount.setText("Please wait " + millisUntilFinished / 1000 + " seconds for resend code");
                }

                public void onFinish() {
                    isTimerOn = false;
                    b.resendCode.setTextColor(getResources().getColor(R.color.appColor));
                    b.timeCount.setVisibility(View.GONE);
                }

            }.start();
        }
    }

    private void sendCodeToNumber(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void verifyCode(View view) {

        if (phoneVerified) {
//            createAccountWithMobileAndPassword();

            return;
        }
        if (!code.isEmpty()) {
//            TestCode
//            Intent returnIntentX = new Intent();
//            returnIntentX.putExtra("phoneVerificationStatus","VERIFIED");
//            setResult(Activity.RESULT_OK, returnIntentX);
//            finish();
//            TestCode END

            if (!mVerificationId.isEmpty()) {

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                signInWithPhoneAuthCredential(credential);

            } else {
//                Toast.makeText(context, "Something went wrong, Try resending the code", Toast.LENGTH_SHORT).show();
                showFailedToast("Something went wrong, Try resending the code");
            }
        } else {
//            Toast.makeText(context, "Insert code", Toast.LENGTH_SHORT).show();
            showFailedToast("Insert code");
        }
//            TestCode
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("phoneVerificationStatus","NOT_VERIFIED");
//        setResult(Activity.RESULT_OK, returnIntent);
//        finish();
//            TestCode END
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d("OTP_CHECK", new Gson().toJson(task.getResult()));
                            System.out.println("XXXXXXXXXXXX");
                            System.out.println(new Gson().toJson(task.getResult()));


                            phoneVerified = true;
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("phoneVerificationStatus","VERIFIED");
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        } else {
                            Constants.dissmissProcess();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                b.txtPinEntry.setText(null);
//                                Toast.makeText(context, "Invalid pin, Insert correct pin", Toast.LENGTH_LONG).show();
                                showFailedToast("Invalid pin, Insert correct pin");
                            }
                            phoneVerified = false;
//                            createAccountWithMobileAndPassword();
                            Intent returnIntent = new Intent();
//                            returnIntent.putExtra("phoneVerificationStatus","NOT_VERIFIED");
                            returnIntent.putExtra("phoneVerificationStatus","VERIFIED");
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();

                        }
                    }
                });
    }

    private void createAccountWithMobileAndPassword() {


        Toast.makeText(context, "Creating Your Account...", Toast.LENGTH_SHORT).show();
//        showProgressDialog("Creating Your Account...");
//        viewModel.userSignUp(params).observe(this, response -> {
//            hideProgressDialog();
//
//            if (response != null) {
//                if (response.isSuccess()) {
//                    UserProfile userProfile = (UserProfile) response.getData();
//                    saveUserProfile(userProfile);
//
//                    ApiClient.initRetrofit();
//
//                    Intent intent = new Intent(context, HomePage.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                } else {
//                    showFailedToast(response.getMessage());
//                }
//            } else {
//                showFailedToast(getString(R.string.something_went_wrong));
//            }
//
//        });

    }

}
