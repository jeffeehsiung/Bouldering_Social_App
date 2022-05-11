package be.kuleuven.timetoclimb.dbConnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DBConnector extends AppCompatActivity {

    private RequestQueue requestQueue;
    private final Context context;
    private JSONArray jsonArrayResponse;
    private final String serverURL = "https://studev.groept.be/api/a21pt411/";
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
                response -> {
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

    public void imageUploadRequest(String databasurl, String username, Bitmap selectedImageBM) throws IOException {
        // will return unResizedBitmap
        requestQueue = Volley.newRequestQueue(context);

        final String profileimage;
        Bitmap unResizedBitmap;
        Bitmap Resizedbitmap;

        //getting image from gallery
        //unResizedBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        //Rescale the bitmap to 400px wide (avoid storing large images!)
        Resizedbitmap = getResizedBitmap(selectedImageBM, 400);

        //convert image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*Bitmap selectedImageBM = BitmapFactory.decodeStream(imageStream);*/
        //compress bitmap into JPEG and output to byte array with quality 100%
        Resizedbitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        profileimage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //the profileImage will be replaced later by param
        String upload = serverURL + databasurl;

        //Execute the Volley call. Note that we are not appending the image string to the URL, that happens further below
        StringRequest submitRequest = new StringRequest(Request.Method.POST, upload, response -> {
            //Turn the progress widget off
            Toast.makeText(context, "Post request executed", Toast.LENGTH_SHORT).show();

        }, error -> error.printStackTrace())
        { //NOTE THIS PART: here we are passing the parameter to the webservice, NOT in the URL!
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("profileimage", profileimage);
                params.put("username", username);
                return params;
            }
        };

        requestQueue.add(submitRequest);
    }

    /**
     * Retrieves the most recent image from the DB
     */
    public void imageRetrieveRequest( String retrieveImgUrl, String username, ImageView profileImage)
    {
        //sending simple request
        //instantiate the requestQueue
        requestQueue = Volley.newRequestQueue(context);

        String requestURL = serverURL+retrieveImgUrl+ "/" + username;
        //Standard Volley request. We don't need any parameters for this one
        JsonArrayRequest retrieveImageRequest = new JsonArrayRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Check if the DB actually contains an image
                        if( response.length() > 0 ) {
                            JSONObject o = null;
                            try {
                                o = response.getJSONObject(0);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //converting base64 string to image
                            String b64String = null;
                            try {
                                b64String = Objects.requireNonNull(o).getString("profile_picture");
                            } catch (JSONException e) {
                                System.out.println("profileImage is default");
                            }
                            byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
                            Bitmap bitmap2 = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

                            //Link the bitmap to the ImageView, so it's visible on screen
                            profileImage.setImageBitmap( bitmap2 );


                            //Just a double-check to tell us the request has completed
                            Toast.makeText(context, "Image retrieved from DB", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to communicate with server", Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(retrieveImageRequest);
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scale = ((float) newWidth) / width;

        // We create a matrix to transform the image
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create the new bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
}
