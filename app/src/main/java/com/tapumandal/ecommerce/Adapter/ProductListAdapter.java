package com.tapumandal.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Activity.Product.ProductDetailsActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.CartProduct;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.databinding.ListProductBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewFilesHolder> {

    private List<Product> products;

    private Cart myCart;
    private List<Product> myProducts;
    private Context context;
    private LayoutInflater layoutInflater;

    public ProductListAdapter(Context context, List<Product> products) {

        this.context = context;
        this.products = products;
        this.myProducts = new ArrayList<Product>();
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public ProductListAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

        Type type = (new TypeToken<Cart>() {}).getType();
        myCart = (Cart) new Gson().fromJson(MySharedPreference.getString(MySharedPreference.Key.MY_CART), type);
        if(myCart != null) {
            myProducts = myCart.getProducts();
        }

        if(myProducts == null){
            System.out.println("myProducts IS NULL");
            myProducts = new ArrayList<Product>();
        }

        for(int i=0; i<myProducts.size(); i++){
            System.out.println(myProducts.get(i).getId());
            if(myProducts.get(i).getId() == item.getId()){
                item.setOrderQuantity(myProducts.get(i).getOrderQuantity());
                break;
            }
        }

        System.out.println("ADAPTER");
        System.out.println(new Gson().toJson(item));

        b.productName.setText(item.getName() );
        b.productShortDesc.setText(item.getDescription() );
        b.productPrice.setText(String.valueOf(item.getSellingPricePerUnit()));
        b.sellingPrice.setText(String.valueOf(item.getDiscountPrice()));
        b.brandName.setText(item.getCompany() );
        b.discount.setText(String.valueOf(item.getDiscountPrice())  + " %   OFF");
        b.orderQuantity.setText(String.valueOf(item.getOrderQuantity()));

        if (item.getDiscountPrice() <= 0) {
            b.discount.setVisibility(View.GONE);
        }

        if(item.getImage() != null){
            String imgUrl  = item.getImage().replace("http://127.0.0.1:8080/api/v1/", "");
            Picasso.get().load(URLs.ROOT_URL_MAIN+imgUrl).into(b.productImg);
        }

        Product finalItem = item;
        b.productItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProductDetailsActivity(finalItem);
            }
        });


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
    
    private void startProductDetailsActivity(Product product){
        Toast.makeText(context, "startProductDetailsActivity", Toast.LENGTH_SHORT).show();
        Intent intent;

        intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("product", product);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return products.size();
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