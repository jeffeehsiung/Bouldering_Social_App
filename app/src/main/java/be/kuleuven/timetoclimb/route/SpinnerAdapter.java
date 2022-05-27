package be.kuleuven.timetoclimb.route;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class SpinnerAdapter {

    List<String> menuList = new ArrayList<>();
    ArrayAdapter adapter = null;

    public SpinnerAdapter(Context context, String extendedURL, String JSONkeyName){
        DBConnector dbConnector = new DBConnector(context);
        dbConnector.JSONRequest(extendedURL, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {
                System.out.println("hallName jsonArray length: "+ jsonArrayResponse.length());
                List<JSONObject> data = IntStream.range(0,jsonArrayResponse.length()).mapToObj(i-> {
                    try {
                        return jsonArrayResponse.getJSONObject(i);
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                    return null;
                }).collect(Collectors.toList());
                data.forEach(jsonObject -> {
                    try {
                        menuList.add(jsonObject.getString(JSONkeyName));
                        System.out.println("object content: "+ jsonObject.getString(JSONkeyName));
                        System.out.println("hallName menu length: "+ menuList.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<String> adapter = new ArrayAdapter(context,android.R.layout.simple_spinner_item,menuList);
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                System.out.println("adapter: "+ adapter);
                setAdapter(adapter);
            }
        });
    }

    public ArrayAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }
}
