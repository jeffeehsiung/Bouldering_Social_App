package be.kuleuven.timetoclimb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;

public class SelectClimbingHall extends AppCompatActivity {

    private ArrayList<Climbinghall> climbinghalls;
    private RecyclerView recyclerView;
    private int hallID;
    private String hallName;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_climbing_hall);

        user = (User) getIntent().getSerializableExtra("User");
        recyclerView = findViewById(R.id.rvClimbinghalls);
        climbinghalls = Climbinghall.DBContents(getApplicationContext(), this); // Populate with all climbinghalls in

    }

    public void setAdapter() {
        // 3 things to set: 1. Layout manager ; 2. Item animator ; 3. Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(new RecyclerAdapter(climbinghalls, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Climbinghall item) {
                hallName = item.getHallName(); // Item is the Climbinghall object that was clicked
                hallID = item.getId();
                passResult();
            }
        }));
    }

    public void passResult(){
        // Pass name and id
        Intent intentEvent = new Intent(this, EventCreator.class);
        intentEvent.putExtra("hallName", hallName);
        intentEvent.putExtra("id", hallID);
        intentEvent.putExtra("User", user);
        setResult(Activity.RESULT_OK, intentEvent);
        finish();
    }
}
