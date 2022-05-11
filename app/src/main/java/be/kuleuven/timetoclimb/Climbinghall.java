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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Climbinghall {
    private int id;
    private String hall_name;
    private String address;
    private Bitmap image;

    public Climbinghall(int id, String hall_name, String address) {
        this.id = id;
        this.hall_name = hall_name;
        this.address = address;
        //this.image = image; STILL NEEDS TO BE IMPLEMENTED AFTER TESTING!!!
    }

    private Climbinghall(JSONObject jsonObject) throws JSONException {
        this.id = Integer.parseInt(jsonObject.getString("id"));
        this.hall_name = jsonObject.getString("hall_name");
        this.address = jsonObject.getString("address");
        this.image = null; // for now
    }

    // TO DO: doesn't work yet (after calling csrtctor with json returns null or smthng)
    public static ArrayList<Climbinghall> DBContents(Context c) {
        ArrayList<Climbinghall> climbinghalls = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(c);

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
                            System.out.println(climbinghalls.toString());
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

    public String getHall_name() {
        return hall_name;
    }

    public void setHall_name(String hall_name) {
        this.hall_name = hall_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
