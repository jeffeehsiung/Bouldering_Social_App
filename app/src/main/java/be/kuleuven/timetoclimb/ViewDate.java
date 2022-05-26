package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapterViewDate;

public class ViewDate extends AppCompatActivity {

    private User user;
    private ArrayList<Event> eventList;
    private TextView lblDate;
    private RecyclerView rvEvents;
    private String displayDate;
    private String start;
    private String end;
    private String climbinghall;
    private String address;
    private Intent intentViewEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_date);
        lblDate = findViewById(R.id.lblDate);
        rvEvents = findViewById(R.id.rvEvents);
        eventList = new ArrayList<>();

        // Get data from intent
        Bundle extras = getIntent().getExtras();
        displayDate = extras.get("SelectedDate").toString();
        user = (User) getIntent().getSerializableExtra("User");

        // Build datetime for database extraction
        String selectedDate = displayDate.substring(6, 10) + "-" + displayDate.substring(3, 5) + "-" + displayDate.substring(0, 2);
        start = selectedDate + " " + "00:00:00";
        end = selectedDate + " " + "23:59:59";

        lblDate.setText(displayDate);

        populateEventList();

        intentViewEvent = new Intent(this, ViewEvent.class);
    }

    private void populateEventList() {
        String requestURL = "https://studev.groept.be/api/a21pt411/getEventsOfDay";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest submitRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject objResponse = new JSONObject();
                            for(int i = 0; i < jsonArray.length(); i++) {
                                objResponse = jsonArray.getJSONObject(i);
                                Event event = new Event(
                                        objResponse.getInt("id"),
                                        objResponse.getString("organiser"),
                                        objResponse.getInt("event_climbing_hall_id"),
                                        objResponse.getString("description_event"),
                                        objResponse.getString("title"),
                                        objResponse.getString("begin_datetime"),
                                        objResponse.getString("end_datetime")
                                        );
                                eventList.add(event);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        setAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Database", "onErrorResponse: " + error.getLocalizedMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("beginofday", start);
                params.put("endofday", end);
                return params;
            }
        };
        requestQueue.add(submitRequest);
    }

    private void passEventAndLocation(Event e) {
        String requestURL = "https://studev.groept.be/api/a21pt411/getLocationByHallID";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest submitRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("Response: \n" + response);
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject objResponse = jsonArray.getJSONObject(0);
                            String climbinghall = objResponse.getString("hall_name");
                            String address = objResponse.getString("address");
                            intentViewEvent.putExtra("User", user);
                            intentViewEvent.putExtra("Event", e);
                            intentViewEvent.putExtra("hall_name", climbinghall);
                            intentViewEvent.putExtra("address", address);
                            startActivity(intentViewEvent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Database", "onErrorResponse: " + error.getLocalizedMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", Integer.toString(e.getClimbingHallID()));
                System.out.println("Params were passed!");
                return params;
            }
        };
        requestQueue.add(submitRequest);
    }

    private void setAdapter() {
        RecyclerAdapterViewDate adapterViewDate = new RecyclerAdapterViewDate(eventList, new RecyclerAdapterViewDate.OnItemClickListener() {
            @Override
            public void onItemClick(Event event) {
                passEventAndLocation(event);
            }
        });

        // set layoutmanager, itemanimator, adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvEvents.setLayoutManager(layoutManager);
        rvEvents.setItemAnimator(new DefaultItemAnimator());
        rvEvents.setAdapter(adapterViewDate);
    }
}