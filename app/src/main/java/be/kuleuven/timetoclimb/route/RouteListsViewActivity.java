package be.kuleuven.timetoclimb.route;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.databinding.ActivityRoutelistViewBinding;

public class RouteListsViewActivity extends AppCompatActivity {

    private ActivityRoutelistViewBinding binding;
    private RecyclerView routeListRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutelistViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding = ActivityRoutelistViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        routeListRecyclerView = findViewById(R.id.routeListRecyclerView);
        routeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        routeListRecyclerView.setAdapter(new RouteListRVAdapter());
    }

}