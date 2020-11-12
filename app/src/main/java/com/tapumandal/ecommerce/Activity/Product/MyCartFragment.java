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
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentMyCartBinding;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

import java.util.ArrayList;
import java.util.HashMap;


public class MyCartFragment extends BaseFragment {

    FragmentMyCartBinding b;
    ProductListAdapter adapter;
    ArrayList<Product> product;
    ProductControlViewModel viewModel;

    Context context;
    String selectedMenu;


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

        product = new ArrayList<Product>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        b.recycleView.setLayoutManager(mLayoutManager);
        b.recycleView.setItemAnimator(new DefaultItemAnimator());
        b.recycleView.setHasFixedSize(true);

        adapter = new ProductListAdapter(context , product);
        b.recycleView.setAdapter(adapter);

        getData();
    }


    public void getData() {

    }
}