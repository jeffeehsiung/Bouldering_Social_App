package be.kuleuven.timetoclimb.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import be.kuleuven.timetoclimb.fragment.LoginTabFragment;
import be.kuleuven.timetoclimb.fragment.SignupTabFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    int totalTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch(position){
            case 0: fragment = new LoginTabFragment();return fragment;
            case 1: fragment = new SignupTabFragment();return fragment;
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
