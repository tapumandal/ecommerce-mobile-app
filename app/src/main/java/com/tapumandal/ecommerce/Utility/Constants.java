package com.tapumandal.ecommerce.Utility;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.DiscountTypeCondition;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import id.zelory.compressor.Compressor;

import static android.content.Context.MODE_PRIVATE;

public class Constants {

    public static final String UPDATE_FILE = "UPDATE_FILE";
    public static final String SETTINGS = "SETTINGS";
    public static String ALL_FILE = "All_file";


    private static ProgressDialog progressDialog;
    private static Dialog dialog;
    public static boolean dataArrived;


    static String sharedPreferencesFile = "sharedPreferencesFile";


    private static SharedPreferences getPref(Context context) {

        return context.getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE);

    }

    public static void clearSharedprefFile(Context context) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.clear();
        editor.apply();

    }

    public static void saveLogingStage(Context context, boolean login) {
        SharedPreferences.Editor editor = getPref(context).edit();
        editor.putBoolean("login", login);
        editor.apply();
    }

    public static boolean getLogingStage(Context context) {
        return getPref(context).getBoolean("login", false);
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void showProcessDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        try {
            progressDialog.show();
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
    }

    public static void showProcessDialogNotCancleable(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
        }
    }

    public static void showProcessDialogCustomText(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        try {
            progressDialog.show();
        } catch (Exception e) {
        }
    }

    public static void showProcessDialogCustomTextCancleable(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        try {
            progressDialog.show();
        } catch (Exception e) {
        }
    }

    public static void dissmissProcess() {
        try {
            progressDialog.dismiss();
        } catch (Exception e) {

        }

        try {
            dialog.dismiss();
        } catch (Exception e) {
            Log.d("Dialog", "dissmissProcess: " + e.getLocalizedMessage());
        }
    }

    public static void showNoInternetSnackbar(final Context context, View view) {

        try {
            Snackbar snackbar = Snackbar
                    .make(view, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_dark))
                    .setAction("CONNECT", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));

                        }
                    });

            snackbar.show();
        } catch (Exception e) {
            Toast.makeText(context, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }

    }

    public static void loadImage(ImageView imageView, String url) {
        try {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
    }

    public static void loadImage(ImageView imageView, Uri url) {
        try {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
    }

    public static void loadImageSqure(ImageView imageView, String url, int cornerRadius, int borderRadius) {
        try {
            Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.WHITE)
                    .borderWidthDp(borderRadius)
                    .cornerRadiusDp(cornerRadius)
                    .oval(false)
                    .build();

            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.round_shape_5dp_corner_light_aysh)
                    .error(R.drawable.round_shape_5dp_corner_light_aysh)
                    .transform(transformation)
                    .resize(800, 800)
                    .centerCrop()
                    .into(imageView);
        } catch (Exception e) {
//            Crashlytics.logException(e);
        }
    }

    public static void showSuccessToast(Context context, String txt) {
        FancyToast.makeText(context, "" + txt, FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
    }

    public static void showFailedToast(Context context, String txt) {
        FancyToast.makeText(context, "" + txt, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
    }

    public static File compresssImage(Context context, File accualImageFile) {
        File file = null;
        int fileLen = (int) (accualImageFile.length() / 1024);
        int convertQuality = 75;

        if (fileLen > 10000) {
            convertQuality = 30;
        } else if (fileLen > 3000)
            convertQuality = 50;

        try {
            file = new Compressor(context)
                    .setMaxWidth(1080)
                    .setQuality(convertQuality)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/Happihub").getAbsolutePath())
                    .compressToFile(accualImageFile);
        } catch (Exception e) {
            Toast.makeText(context, "Unable to process the image, Please change or recapture the image", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        return file;
    }

    public static void fullScreenImageSingle(Context context, String url) {
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.setContentView(R.layout.full_screen_image);
        PhotoView imageView = dialog.findViewById(R.id.image);

        loadImage(imageView, url);

        dialog.show();

    }

    public static void fullScreenImageSingle(Context context, Uri url) {

        try {
            final Dialog dialog = new Dialog(context, R.style.DialogTheme);
            dialog.setContentView(R.layout.full_screen_image);
            PhotoView imageView = dialog.findViewById(R.id.image);

            loadImage(imageView, url);

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
        }

    }

    public static String serverDateToUserDate(String server) {
        String date;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM, yy");

        try {
            calendar.setTime(simpleDateFormat.parse(server));
            date = format.format(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
            return server;
        }


        return date;
    }

    public static long serverDateToCalenderMilis(String server) {
        long date;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            calendar.setTime(simpleDateFormat.parse(server));
            calendar.set(Calendar.HOUR_OF_DAY, 1);
            calendar.set(Calendar.MINUTE, 1);
            calendar.set(Calendar.SECOND, 1);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
            e.printStackTrace();
            return calendar.getTimeInMillis();
        }
    }

    public static String userDateToServerDate(String user) {
        String date;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("dd MMM, yy");

        try {
            calendar.setTime(format.parse(user));
            date = simpleDateFormat.format(calendar.getTimeInMillis());
        } catch (ParseException e) {
            e.printStackTrace();
            return user;
        }


        return date;
    }

    public static String calenderToUserDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM, yy");

        return format.format(calendar.getTimeInMillis());
    }

    public static void callNumber(Context context, String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null));
        context.startActivity(intent);
    }


    public static String calenderToUserDate2(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(calendar.getTimeInMillis());
    }

    public static void smsNumber(Context context, String number, String message){

        Intent sendIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null));
        sendIntent.putExtra("sms_body", message);
        context.startActivity(sendIntent);
    }

//    private Cart myCart;
    private List<Product> myProducts;
    public Product cartMatchProduct(Product item){

        Cart myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

        if(myCart != null) {
            myProducts = myCart.getProducts();
            Log.d("MYCART","Constants cartMatchProduct myCart IS NOT NULL:"+new Gson().toJson(myCart));
        }

        if(myProducts == null){
            myProducts = new ArrayList<Product>();
        }

        for(int i=0; i<myProducts.size(); i++){
            if(myProducts.get(i).getId() == item.getId()){
                item.setOrderQuantity(myProducts.get(i).getOrderQuantity());
                break;
            }
        }
        return item;
    }

    public Product addProduct(Product item){
        Cart myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

        if(myCart == null) {
            Log.d("MYCART","Constants addProduct myCart IS NULL");
            myCart = new Cart();
        }

        if(item.getOrderQuantity()<item.getMaximumOrderQuantity()){

            myCart.setTotalProductQuantity( myCart.getTotalProductQuantity() + 1 );
            myCart.setTotalProductPrice( myCart.getTotalProductPrice() + item.getSellingPricePerUnit() );
            myCart.setTotalProductDiscount( myCart.getTotalProductDiscount() + item.getDiscountPrice() );

            if(myCart.getDeliveryCharge() < item.getDeliveryCharge()) {
                myCart.setDeliveryCharge(item.getDeliveryCharge());
            }

            item.setOrderQuantity(item.getOrderQuantity()+1);
            boolean matched = false;
            for (int i = 0; i < myProducts.size(); i++) {
                if (myProducts.get(i).getId() == item.getId()) {
                    Log.d("MYCART","Constants MATCHED "+myProducts.get(i).getId() +"=="+ item.getId());
                    myProducts.get(i).setOrderQuantity(item.getOrderQuantity());
                    matched = true;
                }
            }
            if(!matched){
                Log.d("MYCART","Constants NOT MATCHED");
                myProducts.add(item);
            }

            myCart.setProducts(myProducts);
            Log.d("STATUS", new Gson().toJson(myCart));
            OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
        }
        return item;
    }

    public Product removeProduct(Product item){

        Cart myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);
        item.setOrderQuantity(item.getOrderQuantity()-1);

        myCart.setTotalProductQuantity( myCart.getTotalProductQuantity() - 1 );
        myCart.setTotalProductPrice( myCart.getTotalProductPrice() - item.getSellingPricePerUnit() );
        myCart.setTotalProductDiscount( myCart.getTotalProductDiscount() - item.getDiscountPrice() );

        for (int i = 0; i < myProducts.size(); i++) {
            if (myProducts.get(i).getId() == item.getId()) {
                myProducts.get(i).setOrderQuantity(item.getOrderQuantity());

                Log.d("STATUS", myProducts.get(i).getId()+":Product Quanty:"+myProducts.get(i).getOrderQuantity());
                if(myProducts.get(i).getOrderQuantity() == 0){
                    myProducts.remove(i);
                }
                break;
            }
        }
        Log.d("STATUS", new Gson().toJson(myProducts));
        myCart.setProducts(myProducts);
        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);

        return item;
    }


    public Cart conditionalDiscountCalculation(Cart cart){
        List<DiscountTypeCondition> discountTypeCondition = cart.getDiscountTypeCondition();
        int calculativeAmount = 0;
        int maximumDiscountedAmount = 0;
        if(discountTypeCondition != null) {
            for (int i = 0; i < discountTypeCondition.size(); i++) {
                if (discountTypeCondition.get(i).getMinimumPurchaseLimit() < cart.getTotalProductPrice()) {
                    calculativeAmount = discountTypeCondition.get(i).getDiscountedAmount();
                    maximumDiscountedAmount = discountTypeCondition.get(i).getMaximumDiscountedAmount();
                }
            }
        }

        if(cart.getDiscountType().equals("TotalPercentage")){
            int tmpDiscountedAmount = (cart.getTotalProductPrice()*calculativeAmount)/100;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
            cart.setTotalDiscount( tmpDiscountedAmount );
        }else if(cart.getDiscountType().equals("OverallAmount")){
            int tmpDiscountedAmount = calculativeAmount;
            if(tmpDiscountedAmount>maximumDiscountedAmount){
                tmpDiscountedAmount = maximumDiscountedAmount;
            }
            cart.setTotalDiscount( tmpDiscountedAmount );
        }

        return cart;
    }
}
