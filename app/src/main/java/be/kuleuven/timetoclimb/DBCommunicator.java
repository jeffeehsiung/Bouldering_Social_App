package be.kuleuven.timetoclimb;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class DBCommunicator {
    private RequestQueue requestQueue;
    private JSONArray result;
    private String urlGetUserComplete;
    private String urlGetAllUsernames;
    private Context c;
    private String allUsernames;

    public DBCommunicator (Context c) {
        this.urlGetUserComplete = "https://studev.groept.be/api/a21pt411/getUserComplete";
        this.urlGetAllUsernames = "https://studev.groept.be/api/a21pt411/getAllUsernames";
        this.c = c;
    }



    public void request(String requestURL, String field, TextView textView) {

        requestQueue = Volley.newRequestQueue(c);

        // request method can be GET or POST
        JsonArrayRequest submitRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        allUsernames = "";
                        try {
                            //result = new JSONArray(response.length());
                            //result = response;
                            for(int i = 0; i < response.length(); i++)
                            {
                                allUsernames += response.getJSONObject(i).getString(field);
                            }
                            textView.setText(allUsernames);
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

    }

    public JSONArray returnResult() {
        return result;
    }

    /*
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

     */
}
