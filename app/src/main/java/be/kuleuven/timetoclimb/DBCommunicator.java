package be.kuleuven.timetoclimb;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCommunicator extends AppCompatActivity {
    private RequestQueue requestQueue;
    private JSONArray result;
    private String urlGetUserComplete;
    private String urlGetAllUsernames;
    private Context c;

    public DBCommunicator(Context c) {
        this.urlGetUserComplete = "https://studev.groept.be/api/a21pt411/getUserComplete";
        this.urlGetAllUsernames = "https://studev.groept.be/api/a21pt411/getAllUsernames";
        this.c = c;
    }

    public JSONArray request(String requestURL) {
        requestQueue = Volley.newRequestQueue(c);

        // request method can be GET or POST
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            result = new JSONArray(response.length());
                            result = response;
                        } catch (JSONException e) {
                            Log.e( "Database", e.getMessage(), e );
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Database", error.getLocalizedMessage(), error);
                    }
                }
        );
        requestQueue.add(submitRequest);
        return result;
    }

    public String getUserNames() {
        JSONArray usernameArray = request(urlGetAllUsernames);
        String allUsernames = "";
        try {
            for(int i = 0; i < usernameArray.length(); i++)
            {
                allUsernames += usernameArray.getString(i);
            }
        } catch (JSONException e) {
                Log.e( "Database", e.getMessage(), e );
            }
        return allUsernames;
    }
}
