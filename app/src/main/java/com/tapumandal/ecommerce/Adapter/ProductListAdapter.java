package com.tapumandal.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Model.Product;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.databinding.ListProductBinding;

import java.util.ArrayList;

/**
 * Created by TapuMandal on 10/31/2020.
 * For any query ask online.tapu@gmail.com
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewFilesHolder> {

    private ArrayList<Product> list;
    private Context context;
    private LayoutInflater layoutInflater;
    String page;


    public ProductListAdapter(Context context, ArrayList<Product> list) {

        this.context = context;
        this.list = list;
        this.page = page;
        layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Product> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ProductListAdapter.ViewFilesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ListProductBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.list_product, parent, false);
        return new ProductListAdapter.ViewFilesHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ProductListAdapter.ViewFilesHolder holder, int position) {
        ListProductBinding b = holder.binding;
        Product item = list.get(position);


        b.productId.setText(item.getId());
        b.productName.setText(item.getName() );
        b.productShortDesc.setText(item.getDescription() );
        b.productPrice.setText(item.getSellingPricePerUnit() );
        b.sellingPrice.setText(item.getDiscountPrice() );
        b.brandName.setText(item.getCompany() );
        b.discount.setText(item.getDiscountPrice()  + " %   OFF");
        b.productQty.setText(item.getQuantity() );

        if (Integer.parseInt(item.getDiscountPrice() ) <= 0) {
            b.discount.setVisibility(View.GONE);
        }
//        if(selling_price.trim().equals(price.trim())){
//            holder.pro_price.setVisibility(View.GONE);
//        }



//        Picasso.with(context).load(img).placeholder(R.drawable.watermark_icon).into(holder.pro_img);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    class ViewFilesHolder extends RecyclerView.ViewHolder {

        private final ListProductBinding binding;

        ViewFilesHolder(final ListProductBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }


}