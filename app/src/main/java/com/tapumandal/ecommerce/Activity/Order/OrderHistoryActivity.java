package com.tapumandal.ecommerce.Activity.Order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.tapumandal.ecommerce.Activity.Auth.LoginActivity;
import com.tapumandal.ecommerce.Adapter.OrderListAdapter;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.Model.UserProfile;
import com.tapumandal.ecommerce.R;
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

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.recycleView.setLayoutManager(mLayoutManager);
        binding.recycleView.setItemAnimator(new DefaultItemAnimator());
        binding.recycleView.setHasFixedSize(true);

        adapter = new OrderListAdapter(context , cartList, "PRODUCT_LIST");
        binding.recycleView.setAdapter(adapter);

        getOrderts();
    }

    private void getOrderts() {
        viewModel.getOrders(userProfile.getId()).observe(this, response -> {
            //            hideProgressDialog();
//            stopShimmer();
            if (response != null) {
                if (response.isSuccess()) {
                    adapter.setData(response.getData());
                    adapter.notifyDataSetChanged();
                } else {
                    showFailedToast(response.getMessage());

//                    b.noItem.mainLayout.setVisibility(View.VISIBLE);
//                    b.noItem.titleMessage.setText(response.getMessage());
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });
    }
}