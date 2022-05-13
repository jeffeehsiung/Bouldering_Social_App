package be.kuleuven.timetoclimb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(new RecyclerAdapter(climbinghalls, new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Climbinghall item) {
                hallName = item.getHallName(); // Item is the Climbinghall object that was clicked
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                String requestURL = "https://studev.groept.be/api/a21pt411/getHallIDByName";
                JsonArrayRequest jsonRequestRequest = new JsonArrayRequest(Request.Method.GET, requestURL,null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                VolleyLog.v("Response:%n %s", response);
                                System.out.println(response.toString());
                                try {
                                    hallID = Integer.parseInt(response.getJSONObject(0).getString("id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                passResult();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Database", error.getLocalizedMessage(), error);
                                System.out.println("error: " + error.getLocalizedMessage());
                            }
                        }
                ) { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("hallname", item.getHallName());
                        return params;
                    }
                };
                requestQueue.add(jsonRequestRequest);
            }
        }));
    }

    public void onBtnSelect_Click(View v) {

    }

    public void passResult(){
        // Pass name and id
        Intent intentEvent = new Intent(this, EventCreator.class);
        intentEvent.putExtra("hallName", hallName);
        intentEvent.putExtra("id", hallID);
        setResult(Activity.RESULT_OK, intentEvent);
        finish();
        System.out.println("Pass result id: " + hallID);
    }
}
