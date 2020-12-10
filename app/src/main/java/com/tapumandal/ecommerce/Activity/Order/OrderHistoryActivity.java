package com.tapumandal.ecommerce.Activity.Order;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Activity.Auth.LoginActivity;
import com.tapumandal.ecommerce.Adapter.OrderListAdapter;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.MyPagination;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.EndlessRecyclerViewScrollListener;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityOrderHistoryBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TapuMandal on 11/01/2020.
 * For any query ask online.tapu@gmail.com
 */
public class OrderHistoryActivity extends BaseActivity {

    OrderListAdapter adapter;
    ActivityOrderHistoryBinding binding;
    ProductControlViewModel viewModel;
    List<Cart> cartList;
    UserProfile userProfile;
    MyPagination pagination;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_order_history;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        setToolbar("My Orders", true);
        userProfile = OfflineCache.getOfflineSingle(OfflineCache.MY_PROFILE);

        cartList = new ArrayList<Cart>();

        initRecyclerView();
        getOrders(1);
    }

    private void initRecyclerView() {

        binding.recycleView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recycleView.setLayoutManager(mLayoutManager);
        binding.recycleView.setItemAnimator(new DefaultItemAnimator());
        binding.recycleView.setHasFixedSize(true);

        adapter = new OrderListAdapter(context , cartList, "PRODUCT_LIST");
        binding.recycleView.setAdapter(adapter);


        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page += 1;
                if (page <= pagination.getTotalPage()) {
                    getOrders(page);
                }
            }

            @Override
            public void onScroll(int dx, int dy) {

            }

        };

        binding.recycleView.addOnScrollListener(scrollListener);
    }

    private void getOrders(int page) {
        viewModel.getOrders(page).observe(this, response -> {
            //            hideProgressDialog();
//            stopShimmer();
            if (response != null) {
                if (response.isSuccess()) {

                    Log.d("ORDER_HISTORY", "Pagination: "+ new Gson().toJson(response.getMyPagination()));
                    Log.d("ORDER_HISTORY", "Data: "+new Gson().toJson(response.getMyPagination()));
                    pagination = response.getMyPagination();
                    cartList.addAll(response.getData());
                    populateView();

                } else {
                    showFailedToast(response.getMessage());

//                    binding.noItem.mainLayout.setVisibility(View.VISIBLE);
//                    binding.noItem.titleMessage.setText(response.getMessage());
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });
    }

    private void populateView() {
        adapter.setData(cartList);
        adapter.notifyDataSetChanged();
    }
}