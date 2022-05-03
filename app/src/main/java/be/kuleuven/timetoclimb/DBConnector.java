package be.kuleuven.timetoclimb;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DBConnector<jsonArrayResponse> extends AppCompatActivity {

    private RequestQueue requestQueue;
    private TextView txtResponse;
    private Context context;
    private JSONArray jsonArrayResponse;
    private ContentLoadingProgressBar loader = (ContentLoadingProgressBar)findViewById(R.id.loader);
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

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        //make a copy of the response and store it
                        jsonArrayResponse = response;
                        loader.hide();
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        txtResponse.setText( error.getLocalizedMessage() );
                    }
                }
        );
        loader.show();
        requestQueue.add(jsonArrayRequest);
        return jsonArrayResponse;
    }
}
