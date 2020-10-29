package com.tapumandal.ecommerce.Activity.Product;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tapumandal.ecommerce.Base.BaseFragment;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.databinding.FragmentProductCategoryBinding;
/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductCategoryFragment extends BaseFragment {

    FragmentProductCategoryBinding binding;
    Context context;

    public ProductCategoryFragment(){}

    @Override
    protected Integer layoutResourceId() {
        return R.layout.fragment_product_category;
    }

    @Override
    protected void initFragmentComponents() {
        binding = getBinding();
        context = getContext();
    }
}