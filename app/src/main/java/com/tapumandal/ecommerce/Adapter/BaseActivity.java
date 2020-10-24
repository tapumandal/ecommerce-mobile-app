package com.maxpro.covid19.Base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;
import com.maxpro.covid19.BuildConfig;
import com.maxpro.covid19.ModelClass.UserProfile;
import com.maxpro.covid19.ModelClass.VersionControlModel;
import com.maxpro.covid19.R;
import com.maxpro.covid19.Utility.ApiClient;
import com.maxpro.covid19.Utility.OfflineCache;
import com.maxpro.covid19.Utility.SharedPref;
import com.maxpro.covid19.ViewModel.UserControlViewModel;
import com.maxpro.covid19.databinding.AdminMessageDialogBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.text.util.Linkify.ALL;
import static androidx.databinding.DataBindingUtil.inflate;
import static com.maxpro.covid19.R.style.DialogTheme;
import static com.maxpro.covid19.Utility.OfflineCache.USER_PROFILE_FILE;


public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private Activity mActivity;


    public Context context;
    ShimmerFrameLayout shimmerFrameLayout;
    private View loadingView, noDataView;

    UserControlViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;
        viewModel = ViewModelProviders.of(this).get(UserControlViewModel.class);

        initVariable();


        //notification count from shared preference

        initComponent();


    }

    protected abstract int getLayoutResourceFile();

    protected abstract void initComponent();

    public <B> B getBinding() {

        return (B) DataBindingUtil.setContentView(this, getLayoutResourceFile());
    }

    private void initVariable() {
        context = getApplicationContext();
        mActivity = BaseActivity.this;
    }

    public void showProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(BaseActivity.this, null, "Please Wait...", true);
            }
        });
    }

    public void showProgressDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(BaseActivity.this, null, msg, true);
            }
        });
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            });
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);

        }
    }


    public Activity getActivity() {
        return this;
    }



    public void startActivity(Class<?> cls, boolean finishSelf) {
        Intent intent = new Intent(context, cls);

//        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        startActivity(intent);
        if (finishSelf) {
            finish();
        }
    }


    public Context getContext() {
        return this;
    }

    public void showSuccessToast(String txt) {
        FancyToast.makeText(mActivity, "" + txt, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
    }

    public void showFailedToast(String txt) {
        FancyToast.makeText(mActivity, "" + txt, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }

    public void datePickerDialog(final EditText editText) {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;
                String selectedDate = (year + "-" + month + "-" + dayOfMonth);

                editText.getText().clear();
                editText.setText(selectedDate);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                listener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();

    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.ThemeOverlay_Material_Dialog_Alert
            );
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month += 1;
        return year + "-" + month + "-" + day;
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    protected void removeFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
    }


    public void setToolbar(String name) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(name);
        }
    }

    public void setToolbar(String name, boolean enableBackBtn) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(name);

        if (enableBackBtn)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
    }

    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        startActivity(intent);
    }

    @Override
    protected void onStart() {


//        if (onStartCount > 1) {
//            this.overridePendingTransition(R.anim.anim_slide_in_right,
//                    R.anim.anim_slide_out_right);
//
//        } else if (onStartCount == 1) {
//            onStartCount++;
//        }

        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showImage(ImageView imageView, String imgUrl) {
        Picasso.get().load(imgUrl).placeholder(R.drawable.ic_place_image).error(R.drawable.ic_place_image).into(imageView);
    }

    public void initShimmer(ShimmerFrameLayout view) {
        this.shimmerFrameLayout = view;
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    public void stopShimmer() {
        if(shimmerFrameLayout!=null) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        if (shimmerFrameLayout != null)
            shimmerFrameLayout.stopShimmer();
        super.onPause();
    }


    public void checkForUpdate() {
        //        showProgressDialog("Signing Up..");
        viewModel.getVersionControlModel().observe(this, response -> {
            //            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess()) {
                    checkAppUpdate((VersionControlModel) response.getData());
                } else {
                    showFailedToast(response.getMessage());
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });
    }

    boolean appVersionDialogViewing = false;

    private void checkAppUpdate(VersionControlModel versionControlModel) {
        if (versionControlModel == null)
            return;

        if (appVersionDialogViewing)
            return;


        final Integer versionCode = BuildConfig.VERSION_CODE;

        if (versionControlModel.getAppVersion() > versionCode) {

            AdminMessageDialogBinding binding = inflate(getLayoutInflater(), R.layout.admin_message_dialog, null, false);

            final Dialog dialog = new Dialog(getContext(), DialogTheme);
            dialog.setContentView(binding.getRoot());

            binding.updateAppBtn.setVisibility(View.VISIBLE);
            binding.tvMessage.setAutoLinkMask(ALL);
            binding.title.setText(versionControlModel.getTitle());
            binding.tvMessage.setText(Html.fromHtml(versionControlModel.getMessage()));


            if (versionControlModel.getForce()) {
                dialog.setCancelable(false);
                binding.btnClose.setVisibility(View.GONE);
            } else {
                binding.btnClose.setVisibility(View.VISIBLE);
                dialog.setCancelable(true);

                if (SharedPref.getInt(SharedPref.Key.APP_UPDATE_SUPPRESED_VERSION) == versionControlModel.getAppVersion()) {
                    return;
                }
            }

            if (versionCode < versionControlModel.getForceableVersion()) {
                dialog.setCancelable(false);
                binding.btnClose.setVisibility(View.GONE);
            }


            binding.btnClose.setOnClickListener(view -> {
                SharedPref
                        .put(SharedPref.Key.APP_UPDATE_SUPPRESED_VERSION, versionControlModel.getAppVersion());
                dialog.dismiss();
            });


            binding.updateAppBtn.setOnClickListener(view -> {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            });
            dialog.show();
            appVersionDialogViewing = true;


        }

    }


    public void saveUserProfile(UserProfile userProfile) {
        SharedPref.put(SharedPref.Key.USER_TOKEN, "Bearer " + userProfile.getAccessToken());
        SharedPref.put(SharedPref.Key.USER_ID, userProfile.getId());
//        SharedPreferencesEnum.put(SharedPreferencesEnum.Key.USER_TYPE, userProfile.getType());
        SharedPref.put(SharedPref.Key.IS_LOGIN, true);

        OfflineCache.saveOffline(USER_PROFILE_FILE, userProfile);

        ApiClient.initRetrofit();

    }

    public void removeUserProfile() {

        OfflineCache.deleteAllCacheFile();
        SharedPref.clear();


        viewModel.logOut().observe(this, response -> {
        });

    }

    public void showPermissionDeniedSnackbar(View view) {
        try {
            Snackbar snackbar = Snackbar.make(view, getResources().getString(R.string.message_no_permission_snackbar), 4000);
            snackbar.setAction(getResources().getString(R.string.allow), v -> {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            });
            snackbar.show();
        } catch (Exception e) {

        }
    }

}
