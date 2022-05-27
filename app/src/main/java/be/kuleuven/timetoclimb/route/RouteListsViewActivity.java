package be.kuleuven.timetoclimb.route;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRoutelistViewBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;

public class RouteListsViewActivity extends AppCompatActivity {

    private ActivityRoutelistViewBinding binding;
    private RecyclerView routeListRecyclerView;
    private RouteListRVAdapter routeListRVAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DBConnector dbConnector;
    private List<Route> routeList = new ArrayList<>();
    private User user = null;
    private static final String RouteListsViewActivity_TAG = RouteListsViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutelistViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.user = (User) getIntent().getSerializableExtra("User");

        dbConnector = new DBConnector(RouteListsViewActivity.this);
        retrieveDataFromDB("getRouteAndHallName");

        //inflating main layout
        routeListRecyclerView = findViewById(R.id.routeListRecyclerView);
        routeListRecyclerView.setHasFixedSize(true);
    }

    public List<Route> retrieveDataFromDB(String extendedUrl){
        //retrieve String objects
        dbConnector.JSONRequest(extendedUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {

                //for each obj in the list, create a route, then added to routeList
                if(jsonArrayResponse.length() == 0){ //if there is no object in the array
                    Toast.makeText(RouteListsViewActivity.this,"there is no routes",Toast.LENGTH_LONG).show();
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
                        routeList.add(new Route(hallName,routeNo,grade, author,description,b64String));

                    } catch (JSONException e) {
                        System.out.println("object is empty");
                        e.printStackTrace();
                    }
                }

                //configure the corresponding adapter
                routeListRVAdapter = new RouteListRVAdapter(RouteListsViewActivity.this,routeList,routeListRecyclerView,user);
                routeListRecyclerView.setAdapter(routeListRVAdapter);
                //set RV layout manager as linear since we have linear multiple
                routeListRecyclerView.setLayoutManager(new LinearLayoutManager(RouteListsViewActivity.this));
            }
        });
        return routeList;
    }

    public String ObjValueRequiresNonNull(JSONObject jsonObject, String keyName){
        //object requires nonNull
        try {
            if (jsonObject.isNull(keyName)){
                Toast.makeText(RouteListsViewActivity.this,"String null",Toast.LENGTH_SHORT).show();
                return "0";}
            else{ return jsonObject.getString(keyName);}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "method did not run";
    }
}