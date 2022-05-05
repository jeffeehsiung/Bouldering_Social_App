package be.kuleuven.timetoclimb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    FloatingActionButton google;
    ProgressBar progressBar;
    TextView txtUsername;
    TextView txtPassword;
    TextView lblError;
    Button btnLoginAttempt;
    User user;

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
        progressBar = findViewById(R.id.progressBar);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLoginAttempt = findViewById(R.id.btnLoginAttempt);
        lblError = findViewById(R.id.lblError);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // the view_pager needs the adapter that we load inside
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        Log.d("viewPager2", "onCreate: " + adapter.getItemCount());

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

    public void onBtnLoginAttempt_Click(View caller) {
        // Create intent
        Intent intentLogin = new Intent(this, MainActivity.class);

        // Parse user input
        String givenUsername = txtUsername.getText().toString();
        String givenPassword = txtPassword.getText().toString();

        // Check database
        String requestURL = "https://studev.groept.be/api/a21pt411/getUserByUsername/" + givenUsername;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final JSONArray[] testResult = new JSONArray[1];
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String username = "";
                        String password = "";
                        try {
                            username = response.getJSONObject(0).getString("username");
                            password = response.getJSONObject(0).getString("password");
                        } catch (JSONException e) {
                            Log.e( "Login", e.getMessage(), e );
                        }
                        if(username.equals(givenUsername) && password.equals(givenPassword)) {
                            lblError.setText("");
                            user = new User(username, password);
                            // Pass user with intent
                            intentLogin.putExtra("username", user.getUsername());
                            intentLogin.putExtra("password", user.getPassword());
                            startActivity(intentLogin);
                        }
                        else {
                            lblError.setText("Wrong password, please try again");
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        lblError.setText("Username does no exist");
                    }
                }
        );
        requestQueue.add(submitRequest);

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
