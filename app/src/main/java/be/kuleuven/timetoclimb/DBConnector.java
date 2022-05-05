package be.kuleuven.timetoclimb;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

public class DBConnector<jsonArrayResponse> extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private JSONArray jsonArrayResponse;
    private ProgressBar loader;
    String serverURL = "https://studev.groept.be/api/a21pt411/";

    public DBConnector(Context context){
        this.context = context;
    }
    /**
     * Retrieve information from DB with Volley JSONRequest
     */
    public JSONArray JSONRequest(String extendedURL)
    {
        //sending simple request
        //instantiate the requestQueue
        requestQueue = Volley.newRequestQueue(context);

        String requestURL = serverURL+extendedURL;

        loader = findViewById(R.id.progressBar);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //make a copy of the response and store it
                        jsonArrayResponse = response;
                        loader.setVisibility(View.INVISIBLE);
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d( "Database Connection Error",error.getLocalizedMessage() );
                        loader.setVisibility(View.GONE);
                    }
                }
        );
        loader.setVisibility(View.VISIBLE);
        requestQueue.add(jsonArrayRequest);
        return jsonArrayResponse;
    }
}
