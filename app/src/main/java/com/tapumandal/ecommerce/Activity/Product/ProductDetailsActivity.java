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

        Type type = (new TypeToken<Cart>() {}).getType();
        myCart = (Cart) new Gson().fromJson(MySharedPreference.getString(MySharedPreference.Key.MY_CART), type);
        if(myCart != null) {
            myProducts = myCart.getProducts();
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

        viewPopulate();
        clickEvent();

        System.out.println("Activity MY PRODUCT"+(new Gson().toJson(myProducts)));

    }

    private void clickEvent() {
        b.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getOrderQuantity()<item.getMaximumOrderQuantity()){
                    item.setOrderQuantity(item.getOrderQuantity()+1);
                    b.orderQuantity.setText(String.valueOf(item.getOrderQuantity()));

                    boolean matched = false;
                    for (int i = 0; i < myProducts.size(); i++) {
                        if (myProducts.get(i).getId() == item.getId()) {
                            System.out.println("MATCHED "+myProducts.get(i).getId() +"=="+ item.getId());
                            myProducts.get(i).setOrderQuantity(item.getOrderQuantity());
                            matched = true;
                        }
                    }
                    if(!matched){
                        System.out.println("NOT MATCHED");
                        myProducts.add(item);
                    }

                    myCart.setProducts(myProducts);
                    Log.d("STATUS", new Gson().toJson(myCart));
                    MySharedPreference.put(MySharedPreference.Key.MY_CART, new Gson().toJson(myCart));

                }else{
                    Toast.makeText(context, "Maximum quantity reached!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        b.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getOrderQuantity()>0){
                    item.setOrderQuantity(item.getOrderQuantity()-1);
                    b.orderQuantity.setText(String.valueOf(item.getOrderQuantity()));

                    for (int i = 0; i < myProducts.size(); i++) {
                        if (myProducts.get(i).getId() == item.getId()) {
                            myProducts.get(i).setOrderQuantity(item.getOrderQuantity());
                        }
                    }
                    myCart.setProducts(myProducts);
                    Log.d("STATUS", new Gson().toJson(myCart));
                    MySharedPreference.put(MySharedPreference.Key.MY_CART, new Gson().toJson(myCart));
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