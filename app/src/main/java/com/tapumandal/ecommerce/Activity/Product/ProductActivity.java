package com.tapumandal.ecommerce.Activity.Product;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.Menu;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Adapter.ExpandableListAdapter;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityProductBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */

public class ProductActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityProductBinding binding;
    ProductControlViewModel viewModel;

    ExpandableListAdapter expandableListAdapter;
    List<MyMenu> headerList = new ArrayList<>();
    HashMap<MyMenu, List<MyMenu>> childList = new HashMap<>();
    ViewPager viewPager;
    Toolbar toolbar;
    Fragment fragment;

    boolean isCartActive = false;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_product;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        expandableListAdapter = null;

        setActionBarTitle("Products");

        loadMenu();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);


        fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedMenu", "");
        fragment.setArguments(bundle);
        addFragment(R.id.fragmentLayout, fragment, "FRAGMENT TAG");

        getAppActions();

        clickEvent();
    }

    public void setActionBarTitle(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    private void clickEvent() {
//        FloatingActionButton btnCart = findViewById(R.id.btnCart);
//
//        btnCart.setOnClickListener(v->{
//            isCartActive = true;
//            Toast.makeText(context, "Cart BTN Clicked", Toast.LENGTH_SHORT).show();
//            toolbar.setTitle("My Cart");
//            fragment = new MyCartFragment();
//            replaceFragment(R.id.fragmentLayout, fragment, "FRAGMENT TAG", null);
//        });
    }

    private void getAppActions() {

//        get app action from api.
//        what I mean by app action is like Discount Conditions, Important announcement, Banners If have any....etc

        setCartAction();

    }

    private void setCartAction() {
//        Cart cart = new Cart();
//        cart.setDiscountType("ProductDiscount");
//        cart.setDeliveryCharge(30);
//        OfflineCache.saveOffline(OfflineCache.MY_CART, cart);
    }
    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(isCartActive){
                Log.d("STATUS", "isCartActive TRUE");
                removeFragment(new MyCartFragment());
                isCartActive = false;
            }
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void loadMenu(){
//        check local data exist or not;
//        IF FOUDN call populateExpandableList();
//        If not load from API
        getMenuListFromLive();

    }
    public ArrayList<MyMenu> getMenuListFromLive() {

        ArrayList<MyMenu> menuList = new ArrayList<>();

        viewModel.getMenuList().observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {
                    menuList.addAll(response.getData());

                    preparedMenu(menuList);
                    populateExpandableList();
                } else {
                    showFailedToast(response.getMessage());
                }
            } else {
                showFailedToast(getString(R.string.something_went_wrong));
            }

        });

        return menuList;
    }
    protected void preparedMenu(ArrayList<MyMenu> menus){
        for (MyMenu parent: menus) {
            headerList.add(parent);
            if(!parent.isHasChildren()){
                childList.put(parent, null);
            }else{
                childList.put(parent, parent.getChild());
            }
        }
    }


    private void populateExpandableList() {
        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        binding.expandableListView.setAdapter(expandableListAdapter);

        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isHasChildren()) {
                    Log.d("STATUS", "HAS Child: "+new Gson().toJson(headerList.get(groupPosition)));
                }else{
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    MyMenu head = headerList.get(groupPosition);

                    productListFragment(head.getMenuName());
                }
                return false;
            }
        });

        binding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {

                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                    }

                    MyMenu child = childList.get(headerList.get(groupPosition)).get(childPosition);
                    productListFragment(child.getMenuName());
                }
                return false;
            }
        });
    }

    public void productListFragment(String selectedMenu) {
//        removeFragment(fragment);
        if(isCartActive){
            Log.d("STATUS", "isCartActive TRUE");
            removeFragment(new MyCartFragment());
            isCartActive = false;
        }

        fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedMenu", selectedMenu);
        fragment.setArguments(bundle);
        replaceFragment(R.id.fragmentLayout, fragment, "FRAGMENT TAG", null);
    }


}