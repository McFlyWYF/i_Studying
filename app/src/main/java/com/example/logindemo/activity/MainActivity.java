package com.example.logindemo.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.logindemo.Fragment.ChatFragment;
import com.example.logindemo.Fragment.HomeFragment;
import com.example.logindemo.Fragment.MyFragment;
import com.example.logindemo.Fragment.StudyFragment;
import com.example.logindemo.R;

public class MainActivity extends AppCompatActivity{

    private StudyFragment studyFragment;
    private ChatFragment chatFragment;
    private HomeFragment homeFragment;
    private MyFragment myFragment;

    private Fragment[] fragments;
    private int lastShowFragment = 0;

    private SearchView mSearchView;
    private DrawerLayout drawerLayout;
    private ImageView myPhotoview;

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
                case R.id.action_my:
                    if (lastShowFragment != 3) {
                        switchFrament(lastShowFragment, 3);
                        lastShowFragment = 3;
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
        myFragment = new MyFragment();
        fragments = new Fragment[]{homeFragment, studyFragment, chatFragment,myFragment};
        lastShowFragment = 0;
        Fragment fragment = new Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .show(fragment)
                .commit();
    }
}
