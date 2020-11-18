package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Activity.Order.CheckoutActivity;
import com.tapumandal.ecommerce.Adapter.CustomEventListener;
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.DiscountTypeCondition;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentMyCartBinding;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyCartFragment extends BaseFragment implements CustomEventListener {

    FragmentMyCartBinding b;
    ProductListAdapter adapter;
    ProductControlViewModel viewModel;

    Context context;

    private Cart myCart;
    private List<Product> myProducts;

    private String selectedDiscountOption;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_my_cart;
    }

    @Override
    protected void initFragmentComponents() {
        b = getBinding();
        context = getContext();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);

        myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

        initRecycleView();

        selectedDiscountOption = "ONPRODUCT";
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ProductActivity) getActivity()).setActionBarTitle("My Cart");
        adapter.notifyDataSetChanged();
        getData();
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.notifyDataSetChanged();
        getData();
    }

    private void initRecycleView() {

        myProducts = new ArrayList<Product>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        b.recycleView.setLayoutManager(mLayoutManager);
        b.recycleView.setItemAnimator(new DefaultItemAnimator());
        b.recycleView.setHasFixedSize(true);

        adapter = new ProductListAdapter(context , myProducts, "MY_CART", this);
        b.recycleView.setAdapter(adapter);

        getData();
        onClickEvent();
    }

    public void getData() {

        myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

        if(myCart.getDefaultDiscountBtn().equals("radioOnProduct")){
            radioOnProduct();

        }else if(myCart.getDefaultDiscountBtn().equals("radioSpecialOffer")){
            radioSpecialOffer();
        }

//        myCart = (Cart) OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

        if(myCart != null) {

            if(myCart.getDiscountBanner() != null && !myCart.getDiscountBanner().isEmpty()) {
                b.discountBanner.setVisibility(View.VISIBLE);
                Picasso.get().load(myCart.getDiscountBanner()).into(b.discountBanner);
            }

            myProducts = myCart.getProducts();

            adapter.setData(myProducts);
            adapter.notifyDataSetChanged();

            b.subTotal.setText(String.valueOf(myCart.getTotalProductPrice()));
            b.shipping.setText(String.valueOf(myCart.getDeliveryCharge()));

            b.totalDiscount.setText(String.valueOf(myCart.getTotalDiscount()));

            myCart.setTotalPayable( (myCart.getTotalProductPrice() - myCart.getTotalDiscount()) + myCart.getDeliveryCharge() );
            b.totalPayable.setText(String.valueOf(myCart.getTotalPayable()));
        }

    }

    private void onClickEvent(){

        if(myCart.getDefaultDiscountBtn().equals("radioOnProduct")){
            b.radioOnProduct.setChecked(true);

        }else if(myCart.getDefaultDiscountBtn().equals("radioSpecialOffer")){
            b.radioSpecialOffer.setChecked(true);
        }

        b.discountRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch(checkedId) {
                    case R.id.radioOnProduct: {
                        radioOnProduct();
                        break;
                    }
                    case R.id.radioSpecialOffer: {
                        radioSpecialOffer();
                        break;
                    }
                }
//                OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
                getData();
            }
        });

        b.checkoutBtnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Checkout Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, CheckoutActivity.class));
            }
        });

    }


    private void radioOnProduct(){
        myCart.setDefaultDiscountBtn("radioOnProduct");
        myCart.setTotalDiscount( myCart.getTotalProductDiscount() );
        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
    }
    private void radioSpecialOffer(){
        myCart.setDefaultDiscountBtn("radioSpecialOffer");
        List<DiscountTypeCondition> discountTypeCondition = myCart.getDiscountTypeCondition();
        int calculativeAmount = 0;
        if(discountTypeCondition != null) {
            for (int i = 0; i < discountTypeCondition.size(); i++) {
                if (discountTypeCondition.get(i).getMinimumAmount() < myCart.getTotalProductPrice()) {
                    calculativeAmount = discountTypeCondition.get(i).getDiscountedAmount();
                }
            }
        }

        if(myCart.getDiscountType().equals("TotalPercentage")){
            myCart.setTotalDiscount( (myCart.getTotalProductPrice()*calculativeAmount)/100 );
        }else if(myCart.getDiscountType().equals("OverallAmount")){
            myCart.setTotalDiscount( calculativeAmount );
        }
        OfflineCache.saveOffline(OfflineCache.MY_CART, myCart);
    }

    @Override
    public void cartBtnLayout(boolean visibility) {
        getData();
    }
}