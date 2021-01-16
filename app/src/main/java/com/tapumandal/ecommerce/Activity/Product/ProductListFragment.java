package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.tapumandal.ecommerce.Adapter.CustomEventListener;
import com.tapumandal.ecommerce.Adapter.ProductListAdapter;
import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.MyPagination;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.EndlessRecyclerViewScrollListener;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListFragment extends BaseFragment implements CustomEventListener {

    FragmentProductListBinding b;
    ProductListAdapter adapter;
    List<Product> product;
    ProductControlViewModel viewModel;

    Context context;
    String selectedMenu;
    MyPagination pagination;


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

        clickEvent();
    }

    @Override
    public void onResume() {
        super.onResume();
        cartBtnLayout(true);
        if(selectedMenu.equals(" ")){
            ((MainActivity) getActivity()).setActionBarTitle("Products");
        }else{
            ((MainActivity) getActivity()).setActionBarTitle(selectedMenu);
        }

        adapter.notifyDataSetChanged();
    }

    private void clickEvent() {

        b.cartBtnLayout.setOnClickListener(v->{


//
//            isCartActive = true;
//            Toast.makeText(context, "Cart BTN Clicked", Toast.LENGTH_SHORT).show();
//            toolbar.setTitle("My Cart");
//            fragment = new MyCartFragment();
//            replaceFragment(R.id.fragmentLayout, fragment, "FRAGMENT TAG", null);

            Fragment fragment= new MyCartFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, fragment, "MY_CART_FRAGMENT")
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void initRecycleView() {

        product = new ArrayList<Product>();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        b.recycleView.setLayoutManager(mLayoutManager);
        b.recycleView.setItemAnimator(new DefaultItemAnimator());
        b.recycleView.setHasFixedSize(true);

        adapter = new ProductListAdapter(context , product, "PRODUCT_LIST", this);
        b.recycleView.setAdapter(adapter);

//        initShimmer(b.loading.shimmerViewContainer);
        getData(1);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                page += 1;
                if (page <= pagination.getTotalPage()) {
                    getData(page);
                }
            }

            @Override
            public void onScroll(int dx, int dy) {

            }

        };
        b.recycleView.addOnScrollListener(scrollListener);
    }


    public void getData(int page) {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(page));

        if(selectedMenu.isEmpty()) {
            selectedMenu = " ";
        }
        //        showProgressDialog("Signing Up..");
        viewModel.getProductList(selectedMenu).observe(this, response -> {
            //            hideProgressDialog();
//            stopShimmer();
            if (response != null) {
                if (response.isSuccess()) {
                    pagination = response.getMyPagination();
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

    @Override
    public void cartBtnLayout(boolean visibility) {
        if(visibility) {
            Cart myCart = OfflineCache.getOfflineSingle(OfflineCache.MY_CART);

//            if(myCart.getProducts() != null)
            if(myCart != null)
            if(myCart.getProducts().size()>0) {
                b.cartBtnLayout.setVisibility(View.VISIBLE);
                b.totalAmount.setText((myCart.getTotalProductPrice()-myCart.getTotalDiscount())+" ৳");
            }else{
                b.cartBtnLayout.setVisibility(View.GONE);
            }
        }else{
            b.cartBtnLayout.setVisibility(View.GONE);
        }
    }
}