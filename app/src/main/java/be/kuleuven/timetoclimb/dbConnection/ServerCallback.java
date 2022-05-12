package be.kuleuven.timetoclimb.dbConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import be.kuleuven.timetoclimb.ProfileActivity;

public interface ServerCallback {
    void onSuccess(JSONArray jsonArrayResponse);
    default String onImageStringRetrieved(String b64String){

        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap2 = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

        return b64String;

    }
}
