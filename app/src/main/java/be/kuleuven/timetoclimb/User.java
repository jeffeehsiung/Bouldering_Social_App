package be.kuleuven.timetoclimb;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.kuleuven.timetoclimb.dbConnection.DBConnector;

public class User implements Serializable {
    private String username;
    private String password;
    private String profileImage;
    private Uri imageUri;
    private ArrayList<User> friends;

    public User(String username, String password, String profileImage) {
        this.username = username;
        this.password = password;
        this.profileImage = profileImage;
    }

    public void addEvent(Event e, Context c) {
        RequestQueue requestQueue = Volley.newRequestQueue(c);

        String requestURL = "https://studev.groept.be/api/a21pt411/addEvent";

        StringRequest stringRequestRequest = new StringRequest(Request.Method.POST, requestURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        VolleyLog.v("Response:%n %s", response);
                        System.out.println(response.toString());
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
                params.put("organiser",username);
                params.put("climbinghallid", Integer.toString(e.getClimbingHallID()));
                params.put("starttime",e.getStartTime());
                params.put("endtime",e.getEndTime());
                params.put("title", e.getTitle());
                params.put("descriptionevent", e.getDescription());
                return params;
            }
        };

        requestQueue.add(stringRequestRequest);

    }
    public String getUsername() {return username;}
    public String getPassword() {return password;}

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    public String getProfileImage() { return profileImage; }
}
