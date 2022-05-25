package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;
import be.kuleuven.timetoclimb.adapter.RecyclerAdapterViewDate;

public class ViewDate extends AppCompatActivity {

    private User user;
    private ArrayList<Event> eventList;
    private TextView lblDate;
    private RecyclerView rvEvents;
    private String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_date);
        lblDate = findViewById(R.id.lblDate);
        rvEvents = findViewById(R.id.rvEvents);
        eventList = new ArrayList<>();

        // populate eventList for testing only!!!!!!!!!! Implement a get Events from DB here! Maybe sort by datetime
        Event event1 = new Event("kamiel", 1, "een testertje", "test", "2022-02-12 19:30:00", "2022-02-12 20:30:00");
        Event event2 = new Event("kamie", 10, "een tweede testertje", "test2", "2022-02-13 19:30:00", "2022-02-13 20:30:00");
        eventList.add(event1);
        eventList.add(event2);
        
        setAdapter();

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        selectedDate = extras.get("SelectedDate").toString();
        user = (User) getIntent().getSerializableExtra("User");

        lblDate.setText(selectedDate);
    }

    private void setAdapter() {
        RecyclerAdapterViewDate adapterViewDate = new RecyclerAdapterViewDate(eventList, new RecyclerAdapterViewDate.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                passEvent(event);
            }
        });

        // set layoutmanager, itemanimator, adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvEvents.setLayoutManager(layoutManager);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvEvents.setAdapter(adapterViewDate);
    }

    private void passEvent(Event e) {
        Intent intentViewEvent = new Intent(this, ViewEvent.class);
        intentViewEvent.putExtra("Event", e);
        startActivity(intentViewEvent);
    }
}