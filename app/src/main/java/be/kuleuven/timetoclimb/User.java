package be.kuleuven.timetoclimb;

import android.content.Context;
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

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;
    private String emailAddress;
    private String password;
    private String profileImage;
    private ArrayList<User> friends;

    public User(String username, String password, String profileImage) {
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
    }

    public void addEvent(Event e, Context c) {
        RequestQueue requestQueue = Volley.newRequestQueue(c);

        String requestURL = "https://studev.groept.be/api/a21pt411/addEvent/" + getUsername() + "/" + Integer.toString(e.getClimbingHallID()) + "/" + e.getStartTime() + "/" + e.getEndTime() + "/" + e.getTitle()  + "/" + e.getDescription();
        requestURL = requestURL.replace(" ", "%20");
        System.out.println(requestURL);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, requestURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //make a copy of the response and store it
                        try {
                            //response pushed into parameter v in volley log, which can be access through external document.
                            VolleyLog.v("Response:%n %s", response.toString(4));

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
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    public String getUsername() {return username;}
    public String getEmailAddress() {return emailAddress;}
    public String getPassword() {return password;}
}
