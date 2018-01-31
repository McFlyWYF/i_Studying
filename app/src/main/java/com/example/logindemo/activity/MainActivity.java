package com.example.logindemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.logindemo.Fragment.ChatFragment;
import com.example.logindemo.Fragment.HomeFragment;
import com.example.logindemo.Fragment.StudyFragment;
import com.example.logindemo.R;

public class MainActivity extends AppCompatActivity{

    private ViewPager viewPager;
    private StudyFragment studyFragment;
    private ChatFragment chatFragment;
    private HomeFragment homeFragment;
    private Fragment[] fragments;
    private int lastShowFragment = 0;
    private SearchView mSearchView;
    private DrawerLayout drawerLayout;

    //监听各个控件
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_home:
                    if (lastShowFragment != 0) {
                        switchFrament(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.action_study:
                    if (lastShowFragment != 1) {
                        switchFrament(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.action_chat:
                    if (lastShowFragment != 2) {
                        switchFrament(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        NavigationListener();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.btn_menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initFragments();

    }


    //添加各个Fragment
    public void switchFrament(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fragment_container, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    //初始化各控件
    private void initFragments() {
        studyFragment = new StudyFragment();
        chatFragment = new ChatFragment();
        homeFragment = new HomeFragment();
        fragments = new Fragment[]{homeFragment, studyFragment, chatFragment};
        lastShowFragment = 0;
        Fragment fragment = new Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .show(fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        //通过MenuItem得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setSubmitButtonEnabled(true);//显示提交按钮
        mSearchView.setQueryHint("请输入要搜索的内容");//输入框提示语
        return super.onCreateOptionsMenu(menu);
    }

    public void NavigationListener(){

        NavigationView navView = (NavigationView)findViewById(R.id.nav_view);
        navView.setCheckedItem(R.id.nav_collection);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                drawerLayout.closeDrawers();
                return true;
            }
        });
        navView.setCheckedItem(R.id.nav_log);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                drawerLayout.closeDrawers();
                return true;
            }
        });
        navView.setCheckedItem(R.id.nav_setting);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                drawerLayout.closeDrawers();
                return true;
            }
        });
        navView.setCheckedItem(R.id.nav_about);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }
}
