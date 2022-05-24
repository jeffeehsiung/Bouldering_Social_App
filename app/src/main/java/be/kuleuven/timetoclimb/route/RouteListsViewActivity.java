package be.kuleuven.timetoclimb.route;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRoutelistViewBinding;

public class RouteListsViewActivity extends AppCompatActivity {

    private ActivityRoutelistViewBinding binding;
    private RecyclerView routeListRecyclerView;

    private User user = null;
    private static final String RouteListsViewActivity_TAG = RouteListsViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        this.user = (User) getIntent().getSerializableExtra("User");

        binding = ActivityRoutelistViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //inflating layout
        routeListRecyclerView = findViewById(R.id.routeListRecyclerView);
        //set RV layout manager as linear since we have linear multiple
        routeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //configure the corresponding adapter
        routeListRecyclerView.setAdapter(new RouteListRVAdapter());
    }

}