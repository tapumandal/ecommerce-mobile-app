package com.tapumandal.ecommerce.Activity.Product;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.databinding.FragmentProductListBinding;

/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListFragment extends BaseFragment {

    FragmentProductListBinding binding;

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_product_list;
    }

    @Override
    protected void initFragmentComponents() {
        binding = getBinding();
    }
}