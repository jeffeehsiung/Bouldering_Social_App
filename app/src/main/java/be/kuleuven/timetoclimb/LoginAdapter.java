package be.kuleuven.timetoclimb;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class LoginAdapter extends FragmentStateAdapter {

    private Context context;
    int totalTabs;

    public LoginAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int totalTabs) {
        super(fragmentManager, lifecycle);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0: LoginTabFragment loginTabFragment = new LoginTabFragment();return loginTabFragment;
            case 1: SignupTabFragment signupTabFragment = new SignupTabFragment();return signupTabFragment;
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTabs;
    }
}
