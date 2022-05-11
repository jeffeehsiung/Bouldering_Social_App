package be.kuleuven.timetoclimb.dbConnection;

import android.net.Uri;

import org.json.JSONArray;

public interface ServerCallback {
    void onSuccess(JSONArray jsonArrayResponse);
}
