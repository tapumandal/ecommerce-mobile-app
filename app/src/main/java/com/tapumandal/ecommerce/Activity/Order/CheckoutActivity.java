package com.tapumandal.ecommerce.Activity.Order;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityCheckoutBinding;

/**
 * Created by tapumandal on 11/18/2020.
 * For any query ask online.tapu@gmail.com
 */

public class CheckoutActivity extends BaseActivity {

    ActivityCheckoutBinding binding;
    ProductControlViewModel viewModel;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_checkout;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);

        setToolbar("Checkout", true);
//        setToolbar("Checkout");
    }
}