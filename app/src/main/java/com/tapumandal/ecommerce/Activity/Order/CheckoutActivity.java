package com.tapumandal.ecommerce.Activity.Order;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import com.google.gson.Gson;
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
        initAreaSpinner();
    }

    private void initAreaSpinner() {
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.area_array));
        binding.areaSpinner.setAdapter(filterAdapter);
        binding.areaSpinner.setSelection(-1);
        binding.areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                String s = binding.areaSpinner.getSelectedItem().toString();
                System.out.println(i+"--"+s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}