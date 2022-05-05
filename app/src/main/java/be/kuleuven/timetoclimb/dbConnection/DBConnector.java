package be.kuleuven.timetoclimb.dbConnection;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class DBConnector extends AppCompatActivity {

    private RequestQueue requestQueue;
    private Context context;
    private JSONArray jsonArrayResponse;
    private String serverURL = "https://studev.groept.be/api/a21pt411/";
    private static final String VOLLEY_TAG = DBConnector.class.getSimpleName();
    public DBConnector(Context context){
        this.context = context;
    }
    /**
     * Retrieve information from DB with Volley JSONRequest
     */
    public void JSONRequest(String extendedURL, ServerCallback callback)
    {
        //sending simple request
        //instantiate the requestQueue
        requestQueue = Volley.newRequestQueue(context);

        String requestURL = serverURL+extendedURL;
        System.out.println(requestURL);

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
                            //do wanted call back action here
                            setJsonArrayResponse(response);
                            callback.onSuccess(response);
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
                        Log.d( VOLLEY_TAG,error.getLocalizedMessage() );
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public JSONArray getJsonArrayResponse() {
        return jsonArrayResponse;
    }
    public void setJsonArrayResponse(JSONArray jsonArrayResponse) {
        this.jsonArrayResponse = jsonArrayResponse;
    }
}
