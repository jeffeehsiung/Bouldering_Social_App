package be.kuleuven.timetoclimb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Climbinghall {
    private int id;
    private String hallName;
    private String address;
    private String image;

    public Climbinghall(int id, String hallName, String address, String image) {
        this.id = id;
        this.hallName = hallName;
        this.address = address;
        this.image = image;
    }

    public Climbinghall(JSONObject jsonObject) throws JSONException {
        this.id = Integer.parseInt(jsonObject.getString("id"));
        this.hallName = jsonObject.getString("hall_name");
        this.address = jsonObject.getString("address");
        this.image = jsonObject.getString("image"); // for now
    }

    // TO DO: doesn't work yet (after calling csrtctor with json returns null or smthng)
    public static ArrayList<Climbinghall> DBContents(Context c, SelectClimbingHall instance) {
        ArrayList<Climbinghall> climbinghalls = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        String requestURL = "https://studev.groept.be/api/a21pt411/getAllClimbinghalls";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestURL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //make a copy of the response and store it
                        try {
                            //response pushed into parameter v in volley log, which can be access through external document.
                            VolleyLog.v("Response:%n %s", response.toString());
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                Climbinghall climbinghall = new Climbinghall(jsonArray.getJSONObject(i));
                                climbinghalls.add(climbinghall);
                            }
                            instance.setAdapter();
                            System.out.println("Volley workyworky");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("Fail json");
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
                        System.out.println("Fail");
                    }
                }
        );
        requestQueue.add(stringRequest);
        return climbinghalls;
    }

    /*
    Create bitmap from string
     */
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String  getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
