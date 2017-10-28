package com.example.logindemo.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.logindemo.Fragment.ChatFragment;
import com.example.logindemo.Fragment.HomeFragment;
import com.example.logindemo.Fragment.PersonFragment;
import com.example.logindemo.Fragment.StudyFragment;
import com.example.logindemo.R;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private StudyFragment studyFragment;
    private ChatFragment chatFragment;
    private PersonFragment personFragment;
    private HomeFragment homeFragment;
    private Fragment[] fragments;
    private int lastShowFragment = 0;

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
                case R.id.action_me:
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
        personFragment = new PersonFragment();
        homeFragment = new HomeFragment();
        fragments = new Fragment[]{homeFragment, studyFragment, chatFragment, personFragment};
        lastShowFragment = 0;
        Fragment fragment = new Fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .show(fragment)
                .commit();
    }
}
