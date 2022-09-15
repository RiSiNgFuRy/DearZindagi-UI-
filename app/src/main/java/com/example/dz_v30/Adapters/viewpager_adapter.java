package com.example.dz_v30.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class viewpager_adapter extends FragmentStateAdapter {
    private ArrayList<Fragment> fragments = new ArrayList<>();
    public static ArrayList<String> titles = new ArrayList<>();

    public viewpager_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addTabs(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
