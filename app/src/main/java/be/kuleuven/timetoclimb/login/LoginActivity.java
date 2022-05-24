package be.kuleuven.timetoclimb.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.timetoclimb.R;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FloatingActionButton google;
    List<String> tabList = new ArrayList<>();
    private static final String LoginActivity_TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // c create:
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //c get elements
        tabLayout = findViewById(R.id.login_tab_layout);
        viewPager2 = findViewById(R.id.login_view_pager);
        google = findViewById(R.id.fab_google);

        tabList.add("LOGIN");
        tabList.add("SIGNUP");

        // the view_pager needs the adapter that we load inside
        LoginFMAdapter adapter = new LoginFMAdapter(getSupportFragmentManager(),getLifecycle(),this.tabList.size());
        viewPager2.setAdapter(adapter);
        Log.d(LoginActivity_TAG, "onCreate: " + adapter.getItemCount());

        new TabLayoutMediator(tabLayout,viewPager2,new TabLayoutMediator.TabConfigurationStrategy() {
            @Override public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabList.get(position));
            }
        }).attach();
    }
}
