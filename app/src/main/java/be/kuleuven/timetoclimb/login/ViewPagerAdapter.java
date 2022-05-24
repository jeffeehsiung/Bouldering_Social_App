package be.kuleuven.timetoclimb.login;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    private int totalTabs;
    private static final String Adpater_TAG = ViewPagerAdapter.class.getSimpleName();

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Log.d(Adpater_TAG,"adapter fragments created");
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
