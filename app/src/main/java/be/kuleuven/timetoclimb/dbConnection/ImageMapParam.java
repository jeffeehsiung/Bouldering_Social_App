package be.kuleuven.timetoclimb.dbConnection;

import org.json.JSONArray;

import java.util.Map;

public interface ImageMapParam {
    void setParam(Map<String, String> params, String encodedImage);
}
