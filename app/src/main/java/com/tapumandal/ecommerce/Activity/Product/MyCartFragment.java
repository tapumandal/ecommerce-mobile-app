package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;
import android.os.Bundle;
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
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentMyCartBinding;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MyCartFragment extends BaseFragment {

    FragmentMyCartBinding b;
    ProductListAdapter adapter;
    ProductControlViewModel viewModel;

    Context context;

    private Cart myCart;
    private List<Product> myProducts;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_my_cart;
    }

    @Override
    protected void initFragmentComponents() {
        b = getBinding();
        context = getContext();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        initRecycleView();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("On Resume On Resume On Resume On Resume On Resume On Resume ");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("On Pause On Pause On Pause On Pause On Pause On Pause On Pause ");
    }

    private void initRecycleView() {

        myProducts = new ArrayList<Product>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        b.recycleView.setLayoutManager(mLayoutManager);
        b.recycleView.setItemAnimator(new DefaultItemAnimator());
        b.recycleView.setHasFixedSize(true);

        adapter = new ProductListAdapter(context , myProducts);
        b.recycleView.setAdapter(adapter);

        getData();
    }


    public void getData() {


        Type type = (new TypeToken<Cart>() {}).getType();
        myCart = (Cart) new Gson().fromJson(MySharedPreference.getString(MySharedPreference.Key.MY_CART), type);
        if(myCart != null) {
            myProducts = myCart.getProducts();

            adapter.setData(myProducts);
            adapter.notifyDataSetChanged();
        }

    }
}