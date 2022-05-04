package be.kuleuven.timetoclimb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ViewPagerAdapter extends FragmentStateAdapter {

    private Context context;
    int totalTabs;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch(position){
            case 0: LoginTabFragment loginTabFragment = new LoginTabFragment();return loginTabFragment;
            case 1: SignupTabFragment signupTabFragment = new SignupTabFragment();return signupTabFragment;
            default: fragment = new LoginTabFragment(); return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
