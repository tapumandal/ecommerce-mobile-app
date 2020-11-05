package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListFragment extends BaseFragment {

    FragmentProductListBinding b;
    ProductListAdapter adapter;
    ArrayList<Product> product;
    ProductControlViewModel viewModel;

    Context context;
    String selectedMenu;


    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected void initFragmentComponents() {
        b = getBinding();
        selectedMenu = getArguments().getString("selectedMenu");
        context = getContext();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        initRecycleView();
        Toast.makeText(getContext(), selectedMenu, Toast.LENGTH_SHORT).show();
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

//        initShimmer(b.loading.shimmerViewContainer);
        getData("1");

//        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                page += 1;
//                if (page <= totalPage) {
//                    getData(String.valueOf(page));
//                    //b.progressBar.setVisibility(View.VISIBLE);
//                }
//            }
//
//            @Override
//            public void onScroll(int dx, int dy) {
//
//            }
//
//        };
//        b.recycleView.addOnScrollListener(scrollListener);
    }


    public void getData(String page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", page);

        if(selectedMenu.isEmpty()) {
            selectedMenu = " ";
        }
        //        showProgressDialog("Signing Up..");
        viewModel.getProductList(selectedMenu).observe(this, response -> {
            //            hideProgressDialog();
//            stopShimmer();
            if (response != null) {
                if (response.isSuccess()) {
                    adapter.setData(response.getData());
                    adapter.notifyDataSetChanged();

                    System.out.println("XXXXXXX Data Loaded");
                    System.out.println(new Gson().toJson(response.getData()));

                } else {
                    System.out.println("ELSE");
                    showFailedToast(response.getMessage());

//                    b.noItem.mainLayout.setVisibility(View.VISIBLE);
//                    b.noItem.titleMessage.setText(response.getMessage());
                }
            } else {
                System.out.println("ELSE ELSE");
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });

    }
}