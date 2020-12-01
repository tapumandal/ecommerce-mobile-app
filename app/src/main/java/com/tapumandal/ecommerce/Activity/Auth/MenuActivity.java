package com.tapumandal.ecommerce.Activity.Auth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.lifecycle.ViewModelProviders;

import com.tapumandal.ecommerce.Activity.Product.MainActivity;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.UserControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityMenuBinding;

public class MenuActivity extends BaseActivity {

    ActivityMenuBinding b;
    Context context;
    private UserControlViewModel viewModel;

    UserProfile userProfile;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_menu;
    }

    @Override
    protected void initComponent() {
        context = this;
        b = getBinding();
        setToolbar("Menu");
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);

        userProfile = OfflineCache.getOfflineSingle(OfflineCache.MY_PROFILE);
        String name = userProfile.getName();
        b.name.setText(name);

//        Constants.loadImage(b.profielImage, SharedPreferencesEnum.getString(SharedPreferencesEnum.Key.PROFILE_IMAGE));


        clickHandle();


    }

    private void clickHandle() {

//        b.allTopicsBtn.setOnClickListener(v -> {
//            startActivity(TopicsActivity.class);
//        });


//        b.lawyerProfileBtn.setOnClickListener(v -> {
////            startActivity(LawyerProfileActivity.class);
//
//            String userId = SharedPreferencesEnum.getString(SharedPreferencesEnum.Key.USER_ID, "empty");
//
//            Intent intent = new Intent(context, LawyerProfileActivity.class);
//            intent.putExtra("userId", userId);
//            startActivity(intent);
//        });

//        b.chngPasswordBtn.setOnClickListener(v -> {
//            startActivity(ChangePasswordActivity.class);
//        });

        b.logoutBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Log out!!")
                    .setMessage("Sure you want to log out from the app ?")
                    .setCancelable(true)
                    .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            logout();
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        });

        b.tramsAndCondition.setOnClickListener(v -> {
//            Intent intent = new Intent(context, WebViewActivity.class);
//            intent.putExtra("url", "terms_and_condition");
//            intent.putExtra("title", "Terms And Condition");
//            context.startActivity(intent);
        });

        b.privacyPolicy.setOnClickListener(v -> {
//            Intent intent = new Intent(context, WebViewActivity.class);
//            intent.putExtra("url", "privacy");
//            intent.putExtra("title", "Privacy Policy");
//            context.startActivity(intent);
        });



    }

    public void logout() {
        removeUserProfile();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}
