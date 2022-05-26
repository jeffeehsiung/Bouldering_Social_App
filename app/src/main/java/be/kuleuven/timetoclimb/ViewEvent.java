package be.kuleuven.timetoclimb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
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
    private TextView lblOrganiser;
    private Switch swAttend;
    private RecyclerView rvAttendees;
    private RecyclerAdapterAttendees adapterAttendees;
    private ArrayList<User> attendees;
    private User user;
    boolean switchFlagPrevSet;
    Event event;

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
        lblDescription = findViewById(R.id.lblDescription);
        lblOrganiser = findViewById(R.id.lblOrganiser);
        swAttend = findViewById(R.id.swAttend);
        rvAttendees = findViewById(R.id.rvAttendees);

        // Populate from intent
        event = (Event) getIntent().getSerializableExtra("Event");
        user = (User) getIntent().getSerializableExtra("User");
        Bundle extras = getIntent().getExtras();
        lblTitle.setText(event.getTitle());
        lblClimbHall.setText(extras.getString("hall_name"));  // Implement from DB here!
        lblAddress.setText(extras.getString("address"));
        lblBegin.setText(event.getStartTime());
        lblEnd.setText(event.getEndTime());
        lblOrganiser.setText("Organiser " + event.getOrganiser() + ": ");
        lblDescription.setTypeface(lblDescription.getTypeface(), Typeface.ITALIC);
        lblDescription.setText("\" " + event.getDescription() + " \"");

        // populate attendees from database
        attendees = new ArrayList<>();
        DBPopulate();

    }

    public void setAdapter() {
        // 3 things to set: 1. Layout manager ; 2. Item animator ; 3. Adapter
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvAttendees.setLayoutManager(layoutManager);
        rvAttendees.setItemAnimator(new DefaultItemAnimator());
        adapterAttendees = new RecyclerAdapterAttendees(attendees);
        rvAttendees.setAdapter(adapterAttendees);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSwitch() {
        // set switch listener to add or remove user from event
        ViewEvent passingInstance = this;
        switchFlagPrevSet = attendees.stream().anyMatch(a -> a.getUsername().equals(user.getUsername()));  // Previous setting flag: false = it was off ; true = it was on
        swAttend.setChecked(switchFlagPrevSet); // set the button to checked based off being an attendee or not
        swAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swAttend.isChecked() && switchFlagPrevSet == false) {
                    switchFlagPrevSet = true;
                    user.attend(event, getApplicationContext(), passingInstance);
                } else if(!swAttend.isChecked() && switchFlagPrevSet == true) {
                    switchFlagPrevSet = false;
                    user.unAttend(event, getApplicationContext(), passingInstance);
                }
            }
        });
    }

    public void DBPopulate() {
        /*
        Get attendees and populate attendee list
         */
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt411/getAttendeesOfEvent";
        StringRequest stringRequestRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response)
                    {
                        VolleyLog.v("Response:%n %s", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            // Set empty list if no attendees, otherwise add by iteration
                            if (jsonArray.length() == 0) {
                                setAdapter();
                                setSwitch();
                            } else {
                                for(int i = 0; i < jsonArray.length(); i++){
                                    System.out.println("event id: " + Integer.toString(event.getEventID()));
                                    JSONObject objResponse = jsonArray.getJSONObject(i);
                                    String attendee = objResponse.getString("attendee");
                                    DBAddAttendee(attendee, i, jsonArray.length() - 1);
                                }
                            }

                        } catch (JSONException e) {
                            System.out.println(e.getLocalizedMessage());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Database" ,error.getLocalizedMessage(), error);
                        System.out.println("error: " + error.getLocalizedMessage());
                    }
                }
        ) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("eventid", Integer.toString(event.getEventID()));
                return params;
            }
        };
        requestQueue.add(stringRequestRequest);

        /*
        Get user profile pic from DB and add image
         */

    }

    /*
    Helper method for building attendee list
     */
    private void DBAddAttendee(String attendee, int index, int cycle) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt411/getUser";
        StringRequest stringRequestRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response)
                    {
                        VolleyLog.v("Response:%n %s", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject objResponse = jsonArray.getJSONObject(0);
                            String username = objResponse.getString("username");
                            String password = objResponse.getString("password");
                            String picture = objResponse.getString("profile_picture");
                            if(username != null) {
                                User usrAttendee = new User(username, password, picture);
                                attendees.add(usrAttendee);
                            }
                            if(index == cycle) {
                                setSwitch();
                                setAdapter();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getLocalizedMessage());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Database" ,error.getLocalizedMessage(), error);
                        System.out.println("error: " + error.getLocalizedMessage());
                    }
                }
        ) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", attendee);
                return params;
            }
        };
        requestQueue.add(stringRequestRequest);
    }
    public void addUpdateUser() {
        attendees.add(user);
        int addIndex = attendees.size() - 1;
        adapterAttendees.notifyItemInserted(addIndex);
    }

    public void rmUpdateUser() {
        boolean found = false;
        int i = 0;
        while(!found) {
            if(attendees.get(i).getUsername().equals(user.getUsername())) {
                found = true;
            } else {
                i++;
            }
        }
        attendees.remove(i);
        adapterAttendees.notifyItemRemoved(i);
    }
}