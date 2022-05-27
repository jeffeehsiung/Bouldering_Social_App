package be.kuleuven.timetoclimb.route;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import be.kuleuven.timetoclimb.CommentItem;
import be.kuleuven.timetoclimb.R;
import be.kuleuven.timetoclimb.User;
import be.kuleuven.timetoclimb.databinding.ActivityRoutedetailViewBinding;
import be.kuleuven.timetoclimb.dbConnection.DBConnector;
import be.kuleuven.timetoclimb.dbConnection.ServerCallback;
import be.kuleuven.timetoclimb.toolsInterface.imageResolver;

public class RouteDetailViewActivity extends AppCompatActivity implements imageResolver {

    private ActivityRoutedetailViewBinding binding;
    private RecyclerView commentsRecyclerView;
    private RouteDetailRVAdapter routeDetailRVAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private DBConnector dbConnector;
    private List<CommentItem> commentItemList = new ArrayList<>();
    private User user;
    private Route route;

    private TextView tvRouteTitle,tvAuthor,tvDescription;
    private ImageView ivDetailRoutePicture,ivAuthor;
    private Button sendButton;

    private EditText commentEditText;

    private static final String RouteDetailViewActivity_TAG = RouteDetailViewActivity.class.getSimpleName();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoutedetailViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.user = (User) getIntent().getSerializableExtra("User");
        this.route = (Route) getIntent().getSerializableExtra("Route");

        //for retrieving comments
        dbConnector = new DBConnector(RouteDetailViewActivity.this);
        retrieveFromDB("getAllComments");

        //instantiate
        this.tvRouteTitle = binding.tvRouteTitle;
        this.tvAuthor = binding.tvAuthor;
        this.tvDescription = binding.tvDescription;
        this.ivDetailRoutePicture = binding.ivDetailRoutePicture;
        this.ivAuthor = binding.ivAuthor;
        this.commentEditText = binding.commentEditText;
        this.sendButton = binding.sendButton;

        //from route
        this.tvRouteTitle.setText("Route Number: "+ String.valueOf(route.getRouteNO()));
        this.tvAuthor.setText(route.getAuthor());
        if(route.getDescription() == null){
            this.tvDescription.setText("Description: ");}
        else{ this.tvDescription.setText("Description: "+ route.getDescription());}

        if (route.getRoutePicture() == null){
            Bitmap defaultBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.route);
            this.ivDetailRoutePicture.setImageBitmap(defaultBitmap);
        }else {this.ivDetailRoutePicture.setImageBitmap(StringToBitmap(route.getRoutePicture()));}
        this.ivAuthor.setImageBitmap(StringToBitmap(user.getProfileImage()));

        //inflating main layout
        commentsRecyclerView = binding.commentsRecyclerView;

        sendButton.setOnClickListener(new View.OnClickListener() {
            String serverName = "addNewComment";
            final LocalDate[] date = {LocalDate.now()};
            @Override
            public void onClick(View view) {
                int discussionid = route.getRouteNO();
                String username = user.getUsername();
                String hallid = route.getHallName();
                String description = commentEditText.getText().toString().trim().replaceAll("\\s","+");
                LocalDate createddate = date[0];
                String urlExtention = serverName + "/" + discussionid + "/" + username+ "/" + hallid + "/" + description + "/" +createddate;
                System.out.println(urlExtention);
                dbConnector.JSONRequest(urlExtention, new ServerCallback() {
                    @Override
                    public void onSuccess(JSONArray jsonArrayResponse) {
                        Toast.makeText(getApplicationContext(),"comment uploaded",Toast.LENGTH_LONG).show();
                        System.out.println("comment uploaded");
                        commentEditText.setText("");;
                    }
                });
            }
        });

    }

    public void retrieveFromDB(String extendedUrl){
        //retrieve String objects
        dbConnector.JSONRequest(extendedUrl, new ServerCallback() {
            @Override
            public void onSuccess(JSONArray jsonArrayResponse) {

                //for each obj in the list, create a route, then added to List
                if(jsonArrayResponse.length() == 0){ //if there is no object in the array
                    Toast.makeText(RouteDetailViewActivity.this,"there is no comments",Toast.LENGTH_LONG).show();
                    return;
                }
                //for jsonArrayResponse having data:
                for(int i = 0; i < jsonArrayResponse.length(); i++){

                    JSONObject jsonObject = null;

                    try {
                        jsonObject = jsonArrayResponse.getJSONObject(i);

                        int discussion_id = Integer.parseInt((jsonObject.getString("discussion_id")));
                        String poster_username = jsonObject.getString("poster_username");
                        String climbing_hall_Name = jsonObject.getString("climbing_hall_id");
                        String description = jsonObject.getString("description");
                        String created_datetime = null;
                        if(!jsonObject.isNull("created_datetime")){created_datetime = jsonObject.getString("created_datetime");};
                        String b64String = null;
                        if(!jsonObject.isNull("picture")){ b64String = jsonObject.getString("picture"); }
                        commentItemList.add(new CommentItem(discussion_id, poster_username, climbing_hall_Name, description, created_datetime, b64String));

                    } catch (JSONException e) {
                        System.out.println("object is empty");
                        e.printStackTrace();
                    }
                }
                //configure the corresponding adapter
                routeDetailRVAdapter = new RouteDetailRVAdapter(RouteDetailViewActivity.this,commentItemList,commentsRecyclerView);
                commentsRecyclerView.setAdapter(routeDetailRVAdapter);
                commentsRecyclerView.setHasFixedSize(true);
                //set RV layout manager as linear since we have linear multiple
                commentsRecyclerView.setLayoutManager(new LinearLayoutManager(RouteDetailViewActivity.this));
            }
        });
    }
}
