package com.tapumandal.ecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.Model.Cart;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.URLs;
import com.tapumandal.ecommerce.databinding.ListOrderBinding;
import com.tapumandal.ecommerce.databinding.ListProductBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(final OrderListAdapter.ViewFilesHolder holder, int position) {
        ListOrderBinding b = holder.binding;
        Cart item = carts.get(position);

        b.invoiceNo.setText(item.getInvoiceNo());
        b.orderStatus.setText(item.getStatus());
        b.orderDate.setText(" ("+getAppFormatDate(item.getCreatedAt())+")");
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getAppFormatDate(String time){
        System.out.println("Before SUB: " + time);
        time = time.substring(0,20);
        System.out.println("AFTER SUB: " + time);

//        LocalDateTime localDateTime = LocalDateTime.parse("2018-12-14T09:55:00");
        LocalDateTime localDateTime = LocalDateTime.parse(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy, hh:mm a");
        String output = formatter.format(localDateTime);
        System.out.println("Before formatting DT: " + output);

//        System.out.println("Before formatting: " + myDateObj);
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//
//        String formattedDate = myDateObj.format(myFormatObj);
//        System.out.println("After formatting: " + formattedDate);

        return output;
//        String inputPattern = "yyyy-MM-dd HH:mm:ss";
//        String outputPattern = "dd-MMM-yyyy h:mm a";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//
//        Date date = null;
//        String str = null;
//
//        System.out.println("TIMEDATE: "+time);
//
//        try {
//            date = inputFormat.parse(time);
//            System.out.println("TIMEDATE: "+new Gson().toJson(date));
//            str = outputFormat.format(date);
//            System.out.println("TIMEDATE: "+new Gson().toJson(str));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
    }


}