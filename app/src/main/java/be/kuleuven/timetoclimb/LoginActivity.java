package be.kuleuven.timetoclimb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import be.kuleuven.timetoclimb.adapter.ViewPagerAdapter;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FloatingActionButton google;
    ProgressBar progressBar;

    float opacityf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // c create:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //c get elements
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // the view_pager needs the adapter that we load inside
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        Log.d("viewPager2", "onCreate: " + adapter.getItemCount());
        viewPager2.addOnLayoutChangeListener();

        // if there is no tabs
        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText("Tab " + (position + 1));
                    }
                }).attach();

        google.setTranslationY(300);
        tabLayout.setTranslationY(300);
        google.setAlpha(opacityf);
        tabLayout.setAlpha(opacityf);
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();

    }

    public class CollectionDemoFragment extends Fragment {
        // When requested, this adapter returns a DemoObjectFragment,
        // representing an object in the collection.
        ViewPagerAdapter viewPagerAdapter;
        ViewPager2 viewPager;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.activity_login, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            viewPagerAdapter = new ViewPagerAdapter(this.getActivity());
            viewPager = view.findViewById(R.id.view_pager);
            viewPager.setAdapter(viewPagerAdapter);
        }
    }
}
