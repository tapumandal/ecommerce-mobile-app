package com.tapumandal.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.databinding.ListOrderBinding;
import com.tapumandal.ecommerce.databinding.ListProductBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewFilesHolder> {

    private List<Cart> carts;

    private Cart myCart;
    private List<Cart> myProducts;
    Constants constants;

    String origin = "";
    private Context context;
    private LayoutInflater layoutInflater;

    private CustomEventListener customEventListener;

    public OrderListAdapter(Context context, List<Cart> carts, String origin) {

        this.context = context;
        this.origin = origin;
        this.carts = carts;
        this.myProducts = new ArrayList<Cart>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.customEventListener = customEventListener;
    }

    public void setData(List<Cart> carts) {
        this.carts = carts;
        notifyDataSetChanged();
    }

    @Override
    public OrderListAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ListOrderBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_order, parent, false);
        return new OrderListAdapter.ViewFilesHolder(binding);
    }

    @Override
    public void onBindViewHolder(final OrderListAdapter.ViewFilesHolder holder, int position) {
        ListOrderBinding b = holder.binding;
        Cart item = carts.get(position);

        b.subTotal.setText("৳ "+String.valueOf(item.getTotalProductPrice()));
        b.shipping.setText("৳ "+String.valueOf(item.getDeliveryCharge()));
        b.discount.setText("৳ "+String.valueOf(item.getTotalDiscount()));
        b.purchaseAmount.setText("৳ "+String.valueOf(item.getTotalPayable()));
    }
    
    private void startProductDetailsActivity(Cart cart){
        Toast.makeText(context, "Cart Clicked", Toast.LENGTH_SHORT).show();
//        Intent intent;
//        intent = new Intent(context, ProductDetailsActivity.class);
//        intent.putExtra("cart", cart);
//        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if(carts != null){
            return carts.size();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewFilesHolder extends RecyclerView.ViewHolder {

        private final ListOrderBinding binding;

        ViewFilesHolder(final ListOrderBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }



}