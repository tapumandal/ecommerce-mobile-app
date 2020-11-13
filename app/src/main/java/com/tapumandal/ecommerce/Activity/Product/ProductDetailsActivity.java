package com.tapumandal.ecommerce.Activity.Product;

import android.util.Log;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.CartProduct;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityProductDetailsBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductDetailsActivity extends BaseActivity {

    ActivityProductDetailsBinding b;
    ProductControlViewModel viewModel;

    Product item;
    List<Product> myProducts;
    private Cart myCart;
    Constants constants;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initComponent() {
        context = this;
        b = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar("Product");
        myProducts = new  ArrayList<Product>();


        item = (Product) getIntent().getSerializableExtra("product");
        constants = new Constants();
        item = constants.cartMatchProduct(item);

        viewPopulate();
        clickEvent();

        System.out.println("Activity MY PRODUCT"+(new Gson().toJson(myProducts)));

    }

    private void clickEvent() {
        final Product itemTmp = item;
        b.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((itemTmp.getOrderQuantity()+1)<=itemTmp.getMaximumOrderQuantity()){
                    b.orderQuantity.setText(String.valueOf(constants.addProduct(itemTmp).getOrderQuantity()));
                }else{
                    Toast.makeText(context, "Maximum quantity reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((itemTmp.getOrderQuantity())>0){
                    b.orderQuantity.setText(String.valueOf(constants.removeProduct(itemTmp).getOrderQuantity()));
                }

            }
        });
    }

    private void viewPopulate(){
        System.out.println("IMAGE IMAGE IMAGE "+item.getImage());
        if(item.getImage() != null){
            String imgUrl  = item.getImage().replace("http://127.0.0.1:8080/api/v1/", "");
            imgUrl  = imgUrl.replace("thumbnail.", "");
            Picasso.get().load(URLs.ROOT_URL_MAIN+imgUrl).into(b.apvImage);
        }

        b.apvTitle.setText(item.getName());
        b.apvDescription.setText(item.getDescription());
        b.apvCurrency.setText("BDT");
        b.apvPrice.setText(String.valueOf(item.getSellingPricePerUnit()));
        b.apvAttribute.setText("Attribute");
        b.apvDiscount.setText(String.valueOf(item.getDiscountPrice()));
//        b.apvImage.setText(item.);
//        b.progressbar.setText(item.);
//        b.quantityRl.setText(item.);
        b.quantity.setText(String.valueOf(item.getQuantity()));
        b.orderQuantity.setText(String.valueOf(item.getOrderQuantity()));
//        b.quantityPlus
//        b.quantityMinus
    }
}