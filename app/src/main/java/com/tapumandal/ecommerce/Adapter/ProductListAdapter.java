package com.tapumandal.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Activity.Product.ProductDetailsActivity;
import com.tapumandal.ecommerce.Activity.Product.ProductListFragment;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.CartProduct;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.databinding.ListProductBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewFilesHolder> {

    private List<Product> products;

    private Cart myCart;
    private List<Product> myProducts;
    Constants constants;

    String origin = "";
    private Context context;
    private LayoutInflater layoutInflater;

    private CustomEventListener customEventListener;

    public ProductListAdapter(Context context, List<Product> products, String origin, CustomEventListener customEventListener) {

        this.context = context;
        this.origin = origin;
        this.products = products;
        this.myProducts = new ArrayList<Product>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.customEventListener = customEventListener;
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductListAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.constants = new Constants();

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ListProductBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_product, parent, false);
        return new ProductListAdapter.ViewFilesHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.ViewFilesHolder holder, int position) {
        ListProductBinding b = holder.binding;
        Product item = products.get(position);

        item = constants.cartMatchProduct(item);

        b.productName.setText(item.getName() );
        b.brandName.setText(item.getCompany() );

        int discountedPrice = item.getSellingPricePerUnit()-item.getDiscountPrice();
        b.productPrice.setText("Regular Price "+String.valueOf(item.getSellingPricePerUnit()));
        b.discountedPrice.setText("Discounted Price "+String.valueOf(discountedPrice));

        if(discountedPrice<1){
            b.discountedPrice.setVisibility(View.GONE);
//            b.productPrice.setTextColor(@android:color/background_dark);
            b.productPrice.setTextColor(ContextCompat.getColor(context, R.color.highlightTextColor));
        }


        b.orderQuantity.setText(String.valueOf(item.getOrderQuantity()));

//        if (item.getDiscountPrice() <= 0) {
//            b.discount.setVisibility(View.GONE);
//        }

        if(item.getImage() != null){
            String imgUrl  = item.getImage().replace("http://127.0.0.1:8080/api/v1/", "");
            Picasso.get().load(URLs.ROOT_URL_MAIN+imgUrl).placeholder(R.drawable.app_logo).into(b.productImg);
        }

        Product finalItem = item;
        b.productItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductDetailsActivity(finalItem);
            }
        });


        final Product itemTmp = item;
        b.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((itemTmp.getOrderQuantity()+1)<=itemTmp.getMaximumOrderQuantity()){
                    b.orderQuantity.setText(String.valueOf(constants.addProduct(itemTmp).getOrderQuantity()));
                    customEventListener.cartBtnLayout(true);
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
                    customEventListener.cartBtnLayout(true);
                }else{
                    customEventListener.cartBtnLayout(true);
                    if(origin.equals("MY_CART")) {
                        products.remove(position);
                        notifyDataSetChanged();
                    }
                }
            }
        });

    }
    
    private void startProductDetailsActivity(Product product){
        Toast.makeText(context, "startProductDetailsActivity", Toast.LENGTH_SHORT).show();
        Intent intent;

        intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(products != null){
            return products.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewFilesHolder extends RecyclerView.ViewHolder {

        private final ListProductBinding binding;

        ViewFilesHolder(final ListProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }



}