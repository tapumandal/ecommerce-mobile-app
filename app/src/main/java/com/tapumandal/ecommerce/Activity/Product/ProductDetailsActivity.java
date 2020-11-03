package com.tapumandal.ecommerce.Activity.Product;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityProductDetailsBinding;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductDetailsActivity extends BaseActivity {

    ActivityProductDetailsBinding binding;
    ProductControlViewModel viewModel;

    Product product;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setToolbar("Product");

        product = (Product) getIntent().getSerializableExtra("product");
        viewPopulate();
    }

    private void viewPopulate(){
        System.out.println("IMAGE IMAGE IMAGE "+product.getImage());
        if(product.getImage() != null){
            String imgUrl  = product.getImage().replace("http://127.0.0.1:8080/api/v1/", "");
            imgUrl  = imgUrl.replace("thumbnail.", "");
            Picasso.get().load(URLs.ROOT_URL_MAIN+imgUrl).into(binding.apvImage);
        }

        binding.apvTitle.setText(product.getName());
        binding.apvDescription.setText(product.getDescription());
        binding.apvCurrency.setText("BDT");
        binding.apvPrice.setText(product.getSellingPricePerUnit());
        binding.apvAttribute.setText("Attribute");
        binding.apvDiscount.setText(product.getDiscountPrice());
//        binding.apvImage.setText(product.);
//        binding.progressbar.setText(product.);
//        binding.quantityRl.setText(product.);
        binding.quantity.setText(product.getQuantity());
//        binding.quantityPlus
//        binding.quantityMinus
    }
}