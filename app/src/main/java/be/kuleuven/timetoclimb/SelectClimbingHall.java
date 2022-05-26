package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import be.kuleuven.timetoclimb.adapter.RecyclerAdapter;

public class SelectClimbingHall extends AppCompatActivity {

    private ArrayList<Climbinghall> climbinghalls;
    private RecyclerView recyclerView;
    private int hallID;
    private String hallName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_climbing_hall);

        recyclerView = findViewById(R.id.rvClimbinghalls);

        /*
            --- Populate climbing halls from database ---
         */
        climbinghalls = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String requestURL = "https://studev.groept.be/api/a21pt411/getAllClimbinghalls";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //make a copy of the response and store it
                        try {
                            //response pushed into parameter v in volley log, which can be access through external document.
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            for(int i = 0; i < response.length(); i++) {
                                climbinghalls.add(new Climbinghall(response.getJSONObject(i)));
                            }
                            setAdapter();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Database" ,error.getLocalizedMessage(), error);
                        System.out.println(error.getLocalizedMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void setAdapter() {
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
        setResult(Activity.RESULT_OK, intentEvent);
        finish();
    }
}
