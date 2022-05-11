package be.kuleuven.timetoclimb.dbConnection;

import android.graphics.Bitmap;
import android.net.Uri;

import org.json.JSONArray;

import be.kuleuven.timetoclimb.ProfileActivity;

public interface ServerCallback {
    void onSuccess(JSONArray jsonArrayResponse);
}
