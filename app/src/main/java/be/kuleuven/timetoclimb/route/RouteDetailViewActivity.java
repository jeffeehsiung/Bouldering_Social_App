package be.kuleuven.timetoclimb.route;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRoutedetailViewBinding;
import be.kuleuven.timetoclimb.databinding.ActivityRoutelistViewBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;
import be.kuleuven.timetoclimb.toolsInterface.imageResolver;

public class RouteDetailViewActivity extends AppCompatActivity implements imageResolver {
    private ActivityRoutedetailViewBinding binding;
    private RecyclerView commentsRecyclerView;
    private RouteDetailRVAdapter routeDetailRVAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DBConnector dbConnector;
    private List<Comment> commentList = new ArrayList<>();
    private User user;
    private Route route;
    private Comment comment;
    private static final String RouteDetailViewActivity_TAG = RouteDetailViewActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutedetailViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.user = (User) getIntent().getSerializableExtra("User");
        this.route = (Route) getIntent().getSerializableExtra("Route");

        dbConnector = new DBConnector(RouteDetailViewActivity.this);

        //inflating main layout
        commentsRecyclerView = binding.commentsRecyclerView;
        commentsRecyclerView.setHasFixedSize(true);
    }

    public List<Comment> retrieveCommentsFromDB(String extendedUrl){
        //retrieve String objects
        dbConnector.JSONRequest(extendedUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {

                //for each obj in the list, create a route, then added to routeList
                if(jsonArrayResponse.length() == 0){ //if there is no object in the array
                    Toast.makeText(RouteDetailViewActivity.this,"there is no comments",Toast.LENGTH_LONG).show();
                    return;
                }

                for(int i = 0; i < jsonArrayResponse.length(); i++){

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = jsonArrayResponse.getJSONObject(i);
                        int discussion_id;
                        String poster_username;
                        int climbing_hall_id;
                        String description;
                        Date created_datetime;
                        String picture;

                        //special care for optional descrioptoin image
                        if(jsonObject.isNull("description")){
                            description = " "; }
                        else{
                            description = jsonObject.getString("description");
                        };
                        String b64String;
                        //special care for optional item image
                        if(jsonObject.isNull("picture")){
                            b64String = " "; }
                        else{
                            b64String = jsonObject.getString("route_picture");
                        };
                        commentList.add(new Comment(discussion_id, poster_username, climbing_hall_id, description, created_datetime,b64String));

                    } catch (JSONException e) {
                        System.out.println("object is empty");
                        e.printStackTrace();
                    }
                }

                //configure the corresponding adapter
                routeDetailRVAdapter = new RouteDetailRVAdapter(RouteDetailViewActivity.this,commentList,commentsRecyclerView,user);
                commentsRecyclerView.setAdapter(routeDetailRVAdapter);
                //set RV layout manager as linear since we have linear multiple
                commentsRecyclerView.setLayoutManager(new LinearLayoutManager(RouteDetailViewActivity.this));
            }
        });
        return commentList;
    }

    public String ObjValueRequiresNonNull(JSONObject jsonObject, String keyName){
        //object requires nonNull
        try {
            if (jsonObject.isNull(keyName)){
                Toast.makeText(RouteDetailViewActivity.this,"String null",Toast.LENGTH_SHORT).show();
                return "0";}
            else{ return jsonObject.getString(keyName);}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "method did not run";
    }
}
