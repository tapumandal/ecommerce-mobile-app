package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;
import android.content.Intent;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Activity.Order.CheckoutActivity;
import com.tapumandal.ecommerce.Adapter.CustomEventListener;
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.BusinessSettings;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentMyCartBinding;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends BaseFragment implements CustomEventListener {

    FragmentMyCartBinding b;
    ProductListAdapter adapter;
    ProductControlViewModel viewModel;
    BusinessSettings businessSettings;

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
        businessSettings = (BusinessSettings) OfflineCache.getOfflineSingle(OfflineCache.BUSINESS_SETTINGS);

        selectedDiscountOption = "ONPRODUCT";

        initRecycleView();
        getData();
        onClickEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("My Cart");
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

            if(businessSettings.getDiscountBanner() != null && !businessSettings.getDiscountBanner().isEmpty()) {
                b.discountBanner.setVisibility(View.VISIBLE);
                Picasso.get().load(businessSettings.getDiscountBanner()).into(b.discountBanner);
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

        Constants myConstants = new Constants();
        Cart tmpCart = myConstants.conditionalDiscountCalculation(myCart, businessSettings);

        OfflineCache.saveOffline(OfflineCache.MY_CART, tmpCart);
    }

    @Override
    public void cartBtnLayout(boolean visibility) {
        getData();
    }

    @Override
    public void loadFragment(String flag) {

    }
}