package com.example.taskmanagerapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.taskmanagerapplication.R;
import com.example.taskmanagerapplication.fragment.DoingFragment;
import com.example.taskmanagerapplication.fragment.DoneFragment;
import com.example.taskmanagerapplication.fragment.ToDoFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ManagerActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private TabItem mTabTodo, mTabDoing, mTabDone;
    PageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        findViews();
        initView();
        setListeners();
    }

    private void initView() {
        mPageAdapter = new PageAdapter(this, mTabLayout.getTabCount());
        mViewPager.setAdapter(mPageAdapter);

        //TabLayoutMediator(mTabLayout,mViewPager)

    }

    private void findViews(){
        mTabLayout = findViewById(R.id.tabLayout);
        mTabDoing = findViewById(R.id.tabDoing);
        mTabTodo = findViewById(R.id.tabTodo);
        mTabDone = findViewById(R.id.tabDone);
        mViewPager = findViewById(R.id.viewPager);
    }

    private class PageAdapter extends FragmentStateAdapter {

        private int numOfTabs;

        public PageAdapter(@NonNull FragmentActivity fragmentActivity, int numOfTabs) {
            super(fragmentActivity);
            this.numOfTabs = numOfTabs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return ToDoFragment.newInstance();
                case 1:
                    return DoingFragment.newInstance();
                case 2:
                    return DoneFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return numOfTabs;
        }
    }

    private void setListeners(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0){
                    mPageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition()==1)
                    mPageAdapter.notifyDataSetChanged();
                else if (tab.getPosition()==2)
                    mPageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}