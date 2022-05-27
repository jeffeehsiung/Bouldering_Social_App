package be.kuleuven.timetoclimb.route;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class RouteDBAdapter {
    private DBConnector dbConnector;
    private Context context;
    private List<Route> routeList = new ArrayList<>();
    private String extendedUrl;
    public RouteDBAdapter(Context context, String extendedUrl){
        this.context = context;
        this.dbConnector = new DBConnector(context);
        this.extendedUrl = extendedUrl;
    }

    //define method for retreiving data from DB
    public List<Route> retrieveDataFromDB(String extendedUrl){
        //retrieve String objects
        dbConnector.JSONRequest(extendedUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {

                //for each obj in the list, create a route, then added to routeList
                if(jsonArrayResponse.length() == 0){ //if there is no object in the array
                    Toast.makeText(context,"there is no routes",Toast.LENGTH_LONG).show();
                    return;
                }

                for(int i = 0; i < jsonArrayResponse.length(); i++){

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = jsonArrayResponse.getJSONObject(i);

                        String hallName = ObjValueRequiresNonNull(jsonObject,"hall_name");
                        int routeNo = Integer.parseInt(ObjValueRequiresNonNull(jsonObject,"route_nr"));
                        float grade = Float.parseFloat(ObjValueRequiresNonNull(jsonObject,"grade"));
                        String author = ObjValueRequiresNonNull(jsonObject,"author");
                        String description;
                        //special care for optional descrioptoin image
                        if(jsonObject.isNull("description")){
                            description = " "; }
                        else{
                            description = jsonObject.getString("description");
                        };
                        String b64String;
                        //special care for optional item image
                        if(jsonObject.isNull("route_picture")){
                            b64String = " "; }
                        else{
                            b64String = jsonObject.getString("route_picture");
                        };
                        System.out.println("params: "+ hallName + " " + routeNo + " "+ " "+ author + " descriptoin: "+ description+ " null?" );
                        routeList.add(new Route(hallName,routeNo,grade, author,description,b64String));
                        System.out.println("how many routes added: "+ routeList.size());

                    } catch (JSONException e) {
                        System.out.println("object is empty");
                        e.printStackTrace();
                    }
                }
            }
        });
        return routeList;
    }

    public String ObjValueRequiresNonNull(JSONObject jsonObject, String keyName){
        //object requires nonNull
        try {
            if (jsonObject.isNull(keyName)){
                Toast.makeText(context,"String null",Toast.LENGTH_SHORT).show();
                return "0";}
            else{ return jsonObject.getString(keyName);}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "method did not run";
    }
}
