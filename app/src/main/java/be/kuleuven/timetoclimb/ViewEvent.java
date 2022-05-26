package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;
import be.kuleuven.timetoclimb.adapter.RecyclerAdapterAttendees;

public class ViewEvent extends AppCompatActivity {
    private TextView lblTitle;
    private TextView lblLocation;
    private TextView lblClimbHall;
    private TextView lblAddress;
    private TextView lblTime;
    private TextView lblBegin;
    private TextView lblEnd;
    private ImageView imgOrganiser;
    private TextView lblDescription;
    private Switch swAttend;
    private RecyclerView rvAttendees;
    private ArrayList<User> attendees;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        lblTitle = findViewById(R.id.lblTitle);
        lblLocation = findViewById(R.id.lblLocation);
        lblClimbHall = findViewById(R.id.lblClimbHall);
        lblAddress = findViewById(R.id.lblAdress);
        lblTime = findViewById(R.id.lblTime);
        lblBegin = findViewById(R.id.lblBegin);
        lblEnd = findViewById(R.id.lblEnd);
        imgOrganiser = findViewById(R.id.imgOrganiser);
        lblDescription = findViewById(R.id.lblDescription);
        swAttend = findViewById(R.id.swAttend);
        rvAttendees = findViewById(R.id.rvAttendees);



        // Populate from intent
        Event event = (Event) getIntent().getSerializableExtra("Event");
        user = (User) getIntent().getSerializableExtra("User");
        Bundle extras = getIntent().getExtras();
        lblTitle.setText(event.getTitle());
        lblClimbHall.setText(extras.getString("hall_name"));  // Implement from DB here!
        lblAddress.setText(extras.getString("address"));
        lblBegin.setText(event.getStartTime());
        lblEnd.setText(event.getEndTime());
        lblDescription.setText(event.getDescription());

        // Populate attendees TEST FOR NOW
        attendees = new ArrayList<>();
        User attendee1 = new User("kamiel", "yesman", null);
        User attendee2 = new User("Rikkert", "Batsbak", null);
        attendees.add(attendee1);
        attendees.add(attendee2);

        setAdapter();
    }

    private void setAdapter() {
        // 3 things to set: 1. Layout manager ; 2. Item animator ; 3. Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAttendees.setLayoutManager(layoutManager);
        rvAttendees.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        rvAttendees.setAdapter(new RecyclerAdapterAttendees(attendees));
    }

    private void DBPopulate(int eventID) {


        /*
        Get event details from DB and populate event header
        */

        /*
        Get climbinghall from DB  and add adress to event
         */

        /*
        Get user profile pic from DB and add image
         */


        /*
        Get attendees and populate attendee list
         */
    }
}