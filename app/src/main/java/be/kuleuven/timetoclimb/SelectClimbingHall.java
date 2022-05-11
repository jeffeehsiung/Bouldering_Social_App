package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;

public class SelectClimbingHall extends AppCompatActivity {

    private ArrayList<Climbinghall> climbinghalls;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_climbing_hall);
        recyclerView = findViewById(R.id.rvClimbinghalls);

        climbinghalls = new ArrayList<>();

        // Climbinghall population for TESTING ONLY!!!! create a factory constr in Climbinghall to return an arraylist of clhalls from db
        climbinghalls.add(new Climbinghall(1, "Boulder", "Persilstraat 51, 3020 Herent"));
        climbinghalls.add(new Climbinghall(10, "Stordeur", "'Aarschotsesteenweg 112, 3012 Leuven'"));


        /*
        other test stuff
        climbinghalls = Climbinghall.DBContents(getApplicationContext());
        txtDescription.setText(climbinghalls.get(0).getId());
         */

        setAdapter();
    }

    private void setAdapter() {
        // 3 things to set: 1. Layout manager ; 2. Item animator ; 3. Adapter
        RecyclerAdapter adapter = new RecyclerAdapter(climbinghalls);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }
}