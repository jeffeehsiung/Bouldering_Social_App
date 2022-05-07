package be.kuleuven.timetoclimb.dbConnection;

import org.json.JSONArray;

public interface ServerCallback {
    void onSuccess(JSONArray jsonArrayResponse);
}
