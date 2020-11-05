package com.tapumandal.ecommerce.Activity.Product;

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
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
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

    Product product;

    List<Product> myProducts;

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

        Type type = (new TypeToken<List<Product>>() {}).getType();
        myProducts = (List<Product>) new Gson().fromJson(MySharedPreference.getString(MySharedPreference.Key.MY_CART), type);
        product = (Product) getIntent().getSerializableExtra("product");

        for(int i=0; i<myProducts.size(); i++){
            System.out.println(myProducts.get(i).getId());
            if(myProducts.get(i).getId() == product.getId()){
                product = (Product) myProducts.get(i);
                break;
            }
        }

        viewPopulate();
        clickEvent();


        System.out.println("PPPPPPPPPPPPP");
        System.out.println(new Gson().toJson(myProducts));

    }

    private void clickEvent() {
        b.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentQuantity = Integer.parseInt(b.orderQuantity.getText().toString());
                if(currentQuantity<product.getQuantity()){
                    currentQuantity++;
                    product.setOrderQuantity(currentQuantity);
                    b.orderQuantity.setText(String.valueOf(product.getOrderQuantity()));

                    for(int i=0; i<myProducts.size(); i++){
                        if(myProducts.get(i).getId() == product.getId()){
                            myProducts.get(i).setOrderQuantity(product.getOrderQuantity());
                        }else {
                            myProducts.add(product);
                        }
                    }
                    if(myProducts.size()<1){
                        myProducts.add(product);
                    }
                }else{
                    Toast.makeText(context, "Maximum quantity reached!", Toast.LENGTH_SHORT).show();
                }
                System.out.println(new Gson().toJson(myProducts));

                MySharedPreference.put(MySharedPreference.Key.MY_CART, new Gson().toJson(myProducts));
            }
        });

        b.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentQuantity = Integer.parseInt(b.orderQuantity.getText().toString());
                if(currentQuantity>0){
                    currentQuantity--;
                    product.setOrderQuantity(currentQuantity);
                    b.orderQuantity.setText(String.valueOf(product.getOrderQuantity()));

                    for(int i=0; i<myProducts.size(); i++){
                        if(myProducts.get(i).getId() == product.getId()){
                            myProducts.get(i).setOrderQuantity(product.getOrderQuantity());
                        }else {
                            myProducts.add(product);
                        }
                    }
                }
                System.out.println(new Gson().toJson(myProducts));
                MySharedPreference.put(MySharedPreference.Key.MY_CART, new Gson().toJson(myProducts));
            }
        });
    }

    private void viewPopulate(){
        System.out.println("IMAGE IMAGE IMAGE "+product.getImage());
        if(product.getImage() != null){
            String imgUrl  = product.getImage().replace("http://127.0.0.1:8080/api/v1/", "");
            imgUrl  = imgUrl.replace("thumbnail.", "");
            Picasso.get().load(URLs.ROOT_URL_MAIN+imgUrl).into(b.apvImage);
        }

        b.apvTitle.setText(product.getName());
        b.apvDescription.setText(product.getDescription());
        b.apvCurrency.setText("BDT");
        b.apvPrice.setText(String.valueOf(product.getSellingPricePerUnit()));
        b.apvAttribute.setText("Attribute");
        b.apvDiscount.setText(String.valueOf(product.getDiscountPrice()));
//        b.apvImage.setText(product.);
//        b.progressbar.setText(product.);
//        b.quantityRl.setText(product.);
        b.quantity.setText(String.valueOf(product.getQuantity()));
        b.orderQuantity.setText(String.valueOf(product.getOrderQuantity()));
//        b.quantityPlus
//        b.quantityMinus
    }
}