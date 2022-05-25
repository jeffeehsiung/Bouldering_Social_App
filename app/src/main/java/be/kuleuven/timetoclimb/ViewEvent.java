package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

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

        Event event = (Event) getIntent().getSerializableExtra("Event");
        lblTitle.setText(event.getTitle());
        lblClimbHall.setText(Integer.toString(event.getClimbingHallID()));  // Implement from DB here!
        lblAddress.setText(Integer.toString(event.getClimbingHallID()));
        lblBegin.setText(event.getStartTime());
        lblEnd.setText(event.getEndTime());
        lblDescription.setText(event.getDescription());

        // intent passing event id from ViewDate
        // DBPopulate();
    }

    private void DBPopulate(int eventID) {


        /*
        Get event details from DB and populate event header

        String requestURL = "https://studev.groept.be/api/a21pt411/getEventByID";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // add event details
                            JSONObject objResponse = response.getJSONObject(0);
                            lblTitle.setText(objResponse.getString("title"));
                            lblDescription.setText(objResponse.getString("description"));
                            lblBegin.setText(objResponse.getString("begin_datetime"));
                            lblEnd.setText(objResponse.getString("end_datetime"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Database", error.getLocalizedMessage(), error);
                    }
                }
        ) { //MIGHT HAVE AN ERROR HERE!!!!! PASSING INTEGER INSTEAD OF STRING
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", Integer.toString(eventID));
                return params;
            }
        };
        requestQueue.add(submitRequest);
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