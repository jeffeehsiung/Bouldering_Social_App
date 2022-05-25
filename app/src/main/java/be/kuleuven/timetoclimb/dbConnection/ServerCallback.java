package be.kuleuven.timetoclimb.dbConnection;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import org.json.JSONArray;

public interface ServerCallback {
    void onSuccess(JSONArray jsonArrayResponse);
    default String onImageStringRetrieved(String b64String){

        byte[] imageBytes = Base64.decode( b64String, Base64.DEFAULT );
        Bitmap bitmap2 = BitmapFactory.decodeByteArray( imageBytes, 0, imageBytes.length );

        return b64String;

    }
}
