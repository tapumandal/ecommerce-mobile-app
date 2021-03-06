package com.tapumandal.ecommerce.Activity.Product;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.tapumandal.ecommerce.Activity.Auth.LoginActivity;
import com.tapumandal.ecommerce.Activity.Order.OrderHistoryActivity;
import com.tapumandal.ecommerce.Adapter.ExpandableListAdapter;
import com.tapumandal.ecommerce.Base.BaseActivity;
import com.tapumandal.ecommerce.Model.*;
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.Utility.Constants;
import com.tapumandal.ecommerce.Utility.MySharedPreference;
import com.tapumandal.ecommerce.Utility.OfflineCache;
import com.tapumandal.ecommerce.ViewModel.ProductControlViewModel;
import com.tapumandal.ecommerce.databinding.ActivityMainBinding;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tapumandal on 10/26/2020.
 * For any query ask online.tapu@gmail.com
 */

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    ProductControlViewModel viewModel;

    ExpandableListAdapter expandableListAdapter;
    List<MyMenu> headerList = new ArrayList<>();
    HashMap<MyMenu, List<MyMenu>> childList = new HashMap<>();
    ViewPager viewPager;
    Toolbar toolbar;
    Fragment fragment;

    UserProfile userProfile;
    BusinessSettings businessSettings;

    LinearLayout userDetailsMenuLayout;
    ImageView profileImageMenuView;
    LinearLayout loginMenuBtn;
    TextView name, mobileNumber, logout;

    boolean isCartActive = false;

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_main;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        viewModel = ViewModelProviders.of(this).get(ProductControlViewModel.class);
        expandableListAdapter = null;

        businessSettings = OfflineCache.getOfflineSingle(OfflineCache.BUSINESS_SETTINGS);
        VersionControlModel versionControlModel = getVersionControlModel(businessSettings);
        System.out.println("VersionControlModel:"+new Gson().toJson(versionControlModel));
        checkAppUpdate(businessSettings.getVersionControlModel());

        setActionBarTitle("Products");


        loadMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderMain = navigationView.getHeaderView(0);
        userDetailsMenuLayout = (LinearLayout) navHeaderMain.findViewById(R.id.userDetailsMenuLayout);
        loginMenuBtn = (LinearLayout) navHeaderMain.findViewById(R.id.loginMenuBtn);
        name = (TextView) navHeaderMain.findViewById(R.id.name);
        mobileNumber = (TextView) navHeaderMain.findViewById(R.id.mobileNumber);
        logout = (TextView) navHeaderMain.findViewById(R.id.logout);

        userProfile = OfflineCache.getOfflineSingle(OfflineCache.MY_PROFILE);

        fragment = new ProductListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedMenu", "");
        fragment.setArguments(bundle);
        addFragment(R.id.fragmentLayout, fragment, "FRAGMENT TAG");

        getAppActions();
        viewManagement();
        clickEvent();
    }

    private VersionControlModel getVersionControlModel(BusinessSettings businessSettings) {
        VersionControlModel versionControlModel = new VersionControlModel();
        versionControlModel.setForce(true);
        versionControlModel.setForceableVersion(2);
        versionControlModel.setAppVersion(2);
        versionControlModel.setTitle("Update!");
        versionControlModel.setMessage("To get new offer.");

        return versionControlModel;
    }

    private void viewManagement() {

        Toast.makeText(context, "viewManagement", Toast.LENGTH_SHORT).show();

        if(userProfile == null){
            loginMenuBtn.setVisibility(View.VISIBLE);
        }else{
            userDetailsMenuLayout.setVisibility(View.VISIBLE);
            name.setText(userProfile.getName());
            mobileNumber.setText(userProfile.getMobileNo());
        }
    }

    public void setActionBarTitle(String title) {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
    }

    private void clickEvent() {

        loginMenuBtn.setOnClickListener(v->{
            startActivity(LoginActivity.class, false);
        });

        logout.setOnClickListener(v->{
            logout();
            startActivity(MainActivity.class, true, true);
        });

        binding.orderHistory.setOnClickListener(v->{
            if(userProfile == null){
                startActivity(LoginActivity.class, false);
            }else {
                startActivity(OrderHistoryActivity.class, false);
            }
        });

//        binding.callUs.setOnClickListener(v->{
//            Intent intent = new Intent(Intent.ACTION_CALL);
//
//            intent.setData(Uri.parse("tel:" + "+8801519500400"));
//            context.startActivity(intent);
//        });

        binding.callUs.setOnClickListener(v -> Constants.callNumber(context, "+8801519500400"));

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
        ArrayList<MyMenu> menuList = OfflineCache.getOfflineSingle(OfflineCache.MY_MENU);
        Log.d("MAINACTIVITY", new Gson().toJson(menuList));
        Log.d("MAINACTIVITY", new Gson().toJson(businessSettings));
        if(menuList == null || businessSettings.isUpdateMenu()) {
            Toast.makeText(context, "Menu Loaded From LIVE", Toast.LENGTH_SHORT).show();
            getMenuListFromLive();
        }else{
            Toast.makeText(context, "Menu Loaded From Cache", Toast.LENGTH_SHORT).show();
            preparedMenu(menuList);
            populateExpandableList();
        }

    }
    public ArrayList<MyMenu> getMenuListFromLive() {

        ArrayList<MyMenu> menuList = new ArrayList<>();

        viewModel.getMenuList().observe(this, response -> {
            hideProgressDialog();
            if (response != null) {
                if (response.isSuccess() && response.getData() != null) {
                    menuList.addAll(response.getData());
                    OfflineCache.saveOffline(OfflineCache.MY_MENU, menuList);
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