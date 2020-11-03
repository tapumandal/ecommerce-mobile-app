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
import com.tapumandal.ecommerce.Model.MenuModel;
import com.tapumandal.ecommerce.Model.MyMenu;
import com.tapumandal.ecommerce.R;
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

//public class ProductActivity extends AppCompatActivity {
//
//    private AppBarConfiguration mAppBarConfiguration;
//    private NavigationView navigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_product);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_gallery, R.id.nav_home, R.id.nav_slideshow)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//
//        navigationView.getMenu().performIdentifierAction(R.id.nav_gallery, 0);
//
//        navigationView.getMenu().add(0, 3, 2, "NEW BUTTON").setIcon(R.drawable.app_logo);
//
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                System.out.println(item.getTitle());
//                Toast.makeText(ProductActivity.this, "ITEM CLICKED:"+item.getTitle(), Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        final Menu menu = navigationView.getMenu();
//        for (int i = 0; i < 4; i++) {
//            menu.add("Menu Item " + (i + 1));
//        }
//        final SubMenu subMenu = menu.addSubMenu("SubMenu Title");
//        for (int i = 0; i < 2; i++) {
//            subMenu.add("SubMenu Item " + (i + 1));
//        }
//        for (int i = 0, count = navigationView.getChildCount(); i < count; i++) {
//            final View child = navigationView.getChildAt(i);
//            if (child != null && child instanceof ListView) {
//                final ListView menuView = (ListView) child;
//                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
//                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
//                wrapped.notifyDataSetChanged();
//            }
//        }
//
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        System.out.println("onCreateOptionsMenu");
//        System.out.println(new Gson().toJson(menu.size()));
//        // Inflate the menu; this adds items to the action bar if it is present.
//        System.out.println(new Gson().toJson(menu.size()));
//        getMenuInflater().inflate(R.menu.product, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        System.out.println("onSupportNavigateUp");
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
//    }
//}

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
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setToolbar("Product");
        loadMenu();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        ViewPager tmpViewPager = findViewById(R.id.viewPager);
        setupViewPager(tmpViewPager);

    }


    private void setupViewPager(ViewPager tmp) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedMenu", "");

        ProductListFragment productListFragment = new ProductListFragment();
        productListFragment.setArguments(bundle);

        viewPager = tmp;
        ProductActivity.ViewPagerAdapter adapter = new ProductActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(productListFragment, "X Product List");
//        adapter.addFragment(new ProductCategoryFragment(), "Categories");
        viewPager.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
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
            System.out.println("nav_home");
        } else if (id == R.id.nav_gallery) {

            System.out.println("nav_gallery");
        } else if (id == R.id.nav_slideshow) {

            System.out.println("nav_slideshow");
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

        System.out.println("SSSSSSSSSSS");
        System.out.println(new Gson().toJson(headerList));
        System.out.println(new Gson().toJson(childList));

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        binding.expandableListView.setAdapter(expandableListAdapter);

        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {



                if (headerList.get(groupPosition).isHasChildren()) {
//                    System.out.println("XXXXXXXXXX GROUP BTN CLICKED:"+groupPosition+"-"+id);
                }else{
                    if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    MyMenu head = headerList.get(groupPosition);

                    Bundle bundle = new Bundle();
                    bundle.putString("selectedMenu", head.getMenuName());
                    ProductListFragment productListFragment = new ProductListFragment();
                    productListFragment.setArguments(bundle);

                    ProductActivity.ViewPagerAdapter adapter = new ProductActivity.ViewPagerAdapter(getSupportFragmentManager());
                    adapter.addFragment(productListFragment, "X Product List");
                    viewPager.setAdapter(adapter);
                }
//                System.out.println("XXXXXXXXXX GROUP OUT IF"+groupPosition+"-"+id);



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

                    Bundle bundle = new Bundle();
                    bundle.putString("selectedMenu", child.getMenuName());
                    ProductListFragment productListFragment = new ProductListFragment();
                    productListFragment.setArguments(bundle);

                    ProductActivity.ViewPagerAdapter adapter = new ProductActivity.ViewPagerAdapter(getSupportFragmentManager());
                    adapter.addFragment(productListFragment, "X Product List");
                    viewPager.setAdapter(adapter);
                }

                return false;
            }
        });
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            notifyDataSetChanged();
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}