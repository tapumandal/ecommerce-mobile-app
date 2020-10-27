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
import com.tapumandal.ecommerce.R;
import com.tapumandal.ecommerce.databinding.ActivityProductBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
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

    ExpandableListAdapter expandableListAdapter;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();
    ViewPager viewPager;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//    }

    @Override
    protected int getLayoutResourceFile() {
        return R.layout.activity_product;
    }

    @Override
    protected void initComponent() {
        context = this;
        binding = getBinding();
        expandableListAdapter = null;
        Toolbar toolbar = findViewById(R.id.toolbar);
//        setToolbar("Product");

//        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.navView.setNavigationItemSelectedListener(this);

        ViewPager tmpViewPager = findViewById(R.id.viewPager);

//        setupViewPager(binding.appHeader.findViewById(R.id.viewPager));
        setupViewPager(tmpViewPager);
    }

    private void setupViewPager(ViewPager tmp) {
        viewPager = tmp;
        ProductActivity.ViewPagerAdapter adapter = new ProductActivity.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductCategoryFragment(), "Categories");
        adapter.addFragment(new ProductListFragment(), "X Product List");
        viewPager.setAdapter(adapter);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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

    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Android WebView Tutorial", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial"); //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Java Tutorials", true, true, ""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123","here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "https://www.journaldev.com/19243/python-ast-abstract-syntax-tree");
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, "https://www.journaldev.com/19226/python-fractions");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }


    }

    private void populateExpandableList() {

        System.out.println("SSSSSSSSSSS");
        System.out.println(new Gson().toJson(headerList));
        System.out.println("==============");
        System.out.println(new Gson().toJson(childList));

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        binding.expandableListView.setAdapter(expandableListAdapter);

        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
//                    if (!headerList.get(groupPosition).hasChildren) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(headerList.get(groupPosition).url);
//                        onBackPressed();
//                    }
                    System.out.println("XXXXXXXXXX GROUP BTN CLICKED:"+groupPosition+"-"+id);
                }
                System.out.println("XXXXXXXXXX GROUP OUT IF"+groupPosition+"-"+id);
                viewPager.setCurrentItem(2);
                return false;
            }
        });

        binding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.length() > 0) {
//                        WebView webView = findViewById(R.id.webView);
//                        webView.loadUrl(model.url);
//                        onBackPressed();
                        System.out.println("XXXXXXXXXX CHILD BTN CLICKED:"+groupPosition+"-"+childPosition+"-"+id);
                    }
                    System.out.println("XXXXXXXXXX CHILD OUT IF");
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